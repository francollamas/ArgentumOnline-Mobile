package org.gszone.jfenix13.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import org.gszone.jfenix13.connection.ClientPackages;
import org.gszone.jfenix13.general.Config;
import org.gszone.jfenix13.Main;
import org.gszone.jfenix13.graphics.DrawParameter;
import org.gszone.jfenix13.graphics.Drawer;
import org.gszone.jfenix13.listeners.ClickLongPressListener;
import org.gszone.jfenix13.objects.*;
import org.gszone.jfenix13.utils.Position;
import org.gszone.jfenix13.utils.Rect;

import static com.badlogic.gdx.Input.Keys.*;
import static org.gszone.jfenix13.general.Config.*;

/**
 * Mundo donde se muestra y se interactúa con una parte del mapa, personajes, npcs, etc.
 *
 * moving: indica si la pantalla se esta moviendo
 * pos: posición actual del mapa
 * addToPos: vector que indica para que dirección y sentido se esta moviendo
 * offset: cantidad de pixeles desde que arrancó el movimiento hasta un momento en donde se sigue moviendo.
 * mouseTile: indica en que tile del mapa se encuentra el mouse.
 * techo: indica si en la posición actual se está bajo techo.
 * screenTile: rectángulo con las posiciones del mundo que se visualizan (para capas 1 y 2)
 * screenBigTile: lo mismo que screenTile pero más grande (para dibujar incluso donde no se ve: para capa 3, etc).
 *                Es para evitar que objetos muy grandes aparezcan de repente.
 */
public class World extends Actor {
    private boolean moving;
    private Position pos;
    private Position addToPos;
    private Position offset;
    private Position mouseTile;
    private boolean techo;
    private Rect screenTile;
    private Rect screenBigTile;
    private ClickLongPressListener listener;

    public World() {
        setSize(getConfig().getWindowsTileWidth() * getConfig().getTilePixelWidth(),
                getConfig().getWindowsTileHeight() * getConfig().getTilePixelHeight());

        listener = new ClickLongPressListener() {

            @Override
            public void tUp(InputEvent event, float x, float y, int pointer, int button) {
                if (button == 0) {
                    if (getTapCount() == 1)
                        getClPack().writeLeftClick(mouseTile);
                    else if (getTapCount() == 2)
                        getClPack().writeDoubleClick(mouseTile);
                }

                else if (button == 1)
                        getClPack().writeWarpChar("YO", Main.getInstance().getGameData().getCurrentUser().getMap(), mouseTile);
            }
        };
        addListener(listener);
        listener.setLongPressMobileOnly(true);

        pos = new Position(50, 50);
        addToPos = new Position();
        offset = new Position();
        mouseTile = new Position();
        screenTile = new Rect();
        screenBigTile = new Rect();
    }


    public boolean isMoving() {
        return moving;
    }

    public Position getPos() {
        return pos;
    }

    public Map getMapa() {
        return Main.getInstance().getAssets().getMapa();
    }

    /**
     * Devuelve el rectángulo donde se contiene
     */
    public Rectangle getRect() {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }

    /**
     * Activa el flag para ver los techos, según el trigger del tile donde se está.
     */
    public void setTecho() {
        MapTile tile = getMapa().getTile((int)pos.getX(), (int)pos.getY());
        this.techo = tile.getTrigger() == 1 || tile.getTrigger() == 2 || tile.getTrigger() == 4;
    }

    /**
     * Define cuál es el tile en donde está el mouse por encima
     */
    public void setMouseTile(Position pos) {
        // TODO: elegir la opción más adecuada

        // Elige el tile de forma precisa (teniendo en cuenta el offset de la pantalla)
        /*
        float of;
        of = offset.getX() == 0 ? 0 : (offset.getX() / Math.abs(offset.getX())) * (getConfig().getTilePixelWidth() - Math.abs(offset.getX()));
        mouseTile.setX((int)(this.pos.getX() + (pos.getX() + of) / getConfig().getTilePixelWidth() - getConfig().getWindowsTileWidth() / 2));

        of = offset.getY() == 0 ? 0 : (offset.getY() / Math.abs(offset.getY())) * (getConfig().getTilePixelHeight() - Math.abs(offset.getY()));
        mouseTile.setY((int)(this.pos.getY() + (getHeight() - pos.getY() + of) / getConfig().getTilePixelHeight() - getConfig().getWindowsTileHeight() / 2));
        */

        // Elige el tile como en cualquier AO...
        mouseTile.setX((int) (this.pos.getX() + pos.getX() / getConfig().getTilePixelWidth() - getConfig().getWindowsTileWidth() / 2));
        mouseTile.setY((int) (this.pos.getY() + (getHeight() - pos.getY()) / getConfig().getTilePixelHeight() - getConfig().getWindowsTileHeight() / 2));
    }

    /**
     * Acciones ejecutadas según lo que pasa
     */
    @Override
    public void act(float delta) {
        super.act(delta);

        if (listener.mousePosChanged())
            setMouseTile(listener.getLastMousePos());

        if (listener.longPressDone())
            getClPack().writeWarpChar("YO", Main.getInstance().getGameData().getCurrentUser().getMap(), mouseTile);

        if (!isMoving())
            if (Gdx.input.isKeyPressed(UP)) moveChar(Direccion.NORTE);
            else if (Gdx.input.isKeyPressed(RIGHT)) moveChar(Direccion.ESTE);
            else if (Gdx.input.isKeyPressed(DOWN)) moveChar(Direccion.SUR);
            else if (Gdx.input.isKeyPressed(LEFT)) moveChar(Direccion.OESTE);

        // Lo vuelvo a chequear, porque al mover el char, se activa el flag 'moving'
        if (isMoving()) {
            move();
            setMouseTile(listener.getLastMousePos());
        }
    }

    /**
     * Mueve o cambia la dirección del personaje
     */
    public void moveChar(Direccion dir) {
        if (Main.getInstance().getGameData().isPausa()) return;

        User u = Main.getInstance().getGameData().getCurrentUser();
        if (u.isComerciando() || u.isMirandoForo()) return;

        Position relPos = Position.dirToPos(dir);
        Position absPos = pos.getSuma(relPos);

        if (getMapa().isLegalPos(absPos) && !u.isParalizado() && !u.isDescansando() && !u.isMeditando()) {
            // TODO: si está DESCANSANDO (y considerar tmb meditando), se debería forzar el movimiento!!!
            u.setCambiandoDir(false);
            getClPack().writeWalk(dir);
            Main.getInstance().getGameData().getChars().moveChar(u.getIndexInServer(), dir);
            setMove(relPos, absPos);
        }
        else {
            Char c = Main.getInstance().getGameData().getChars().getChar(u.getIndexInServer());

            /* TODO: por qué el char a veces es nulo? esta bien?...
             con esta condición se arregla un NullPointerException, espero que no joda algo. */
            if (c != null )
                if (c.getHeading().ordinal() != dir.ordinal())
                    if (!u.isCambiandoDir()) {
                        getClPack().writeChangeHeading(dir);
                        u.setCambiandoDir(true);
                    }

        }
    }


    /**
     * Activa el movimiento de la pantalla hacia una dirección
     */
    public void setMove(Direccion dir) {
        Position relPos = Position.dirToPos(dir);
        Position absPos = pos.getSuma(relPos);

        setMove(relPos, absPos);
    }

    /**
     * Activa el movimiento de la pantalla según la posición absoluta final y la relativa.
     */
    public void setMove(Position relPos, Position absPos) {
        pos = absPos;
        addToPos = relPos;
        moving = true;
        setTecho();
    }

    /**
     * Si la pantalla se tiene que mover, esto se encarga de ir moviéndola
     * (se ejecuta constantemente)
     */
    public void move() {
            if (addToPos.getX() != 0) {
                offset.addX(-getConfig().getScrollPixelsPerFrame() * addToPos.getX() * Drawer.getDelta());
                if (Math.abs(offset.getX()) >= Math.abs(getConfig().getTilePixelWidth() * addToPos.getX())) {
                    offset.setX(0);
                    addToPos.setX(0);
                    moving = false;
                }
            }

            if (addToPos.getY() != 0) {
                offset.addY(-getConfig().getScrollPixelsPerFrame() * addToPos.getY() * Drawer.getDelta());
                if (Math.abs(offset.getY()) >= Math.abs(getConfig().getTilePixelHeight() * addToPos.getY())) {
                    offset.setY(0);
                    addToPos.setY(0);
                    moving = false;
                }
            }
    }

    /**
     * Dibuja todo el mundo!
     */
    @Override
    public void draw(Batch batch, float parentAlpha) {
        Drawer.pushScissors(getStage(), getRect());
        if (parentAlpha < 1) {
            Color c = Drawer.getDefColor();
            Drawer.setDefColor(new Color(c.r * parentAlpha, c.g * parentAlpha, c.b * parentAlpha, c.a));
        }
        int x, y;
        MapTile tile;
        Position tempPos = new Position();
        Position minOffset = new Position();
        Position screen = new Position();

        int halfWindowsTileWidth = getConfig().getWindowsTileWidth() / 2;
        int halfWindowsTileHeight = getConfig().getWindowsTileHeight() / 2;

        screenTile.setX1(pos.getX() - addToPos.getX() - halfWindowsTileWidth);
        screenTile.setY1(pos.getY() - addToPos.getY() - halfWindowsTileHeight);
        screenTile.setX2(pos.getX() - addToPos.getX() + halfWindowsTileWidth);
        screenTile.setY2(pos.getY() - addToPos.getY() + halfWindowsTileHeight);

        screenBigTile.setX1(screenTile.getX1() - getConfig().getTileBufferSizeX());
        screenBigTile.setY1(screenTile.getY1() - getConfig().getTileBufferSizeY());
        screenBigTile.setX2(screenTile.getX2() + getConfig().getTileBufferSizeX());
        screenBigTile.setY2(screenTile.getY2() + getConfig().getTileBufferSizeY());


        // Asegurarse de que screenBigTile está siempre dentro del mapa
        if (screenBigTile.getY1() < getMapa().getSize().getY1()) {
            minOffset.setY((int)(getMapa().getSize().getY1() - screenBigTile.getY1()));
            screenBigTile.setY1(getMapa().getSize().getY1());
        }
        if (screenBigTile.getY2() > getMapa().getSize().getY2()) {
            screenBigTile.setY2(getMapa().getSize().getY2());
        }
        if (screenBigTile.getX1() < getMapa().getSize().getX1()) {
            minOffset.setX((int)(getMapa().getSize().getX1() - screenBigTile.getX1()));
            screenBigTile.setX1(getMapa().getSize().getX1());
        }
        if (screenBigTile.getX2() > getMapa().getSize().getX2()) {
            screenBigTile.setX2(getMapa().getSize().getX2());
        }


        // En lo posible agrandar los bordes de renderizado a un tile para cada lado, para que se vea bien al moverse.
        if (screenTile.getY1() > getMapa().getSize().getY1()) {
            screenTile.addY1(-1);
            screenTile.addHeight(1);
        }
        else {
            screenTile.setY1(1);
            screen.setY(1);
        }
        if (screenTile.getY2() < getMapa().getSize().getY2())
            screenTile.addY2(1);

        if (screenTile.getX1() > getMapa().getSize().getX1()) {
            screenTile.addX1(-1);
            screenTile.addWidth(1);
        }
        else {
            screenTile.setX1(1);
            screen.setX(1);
        }
        if (screenTile.getX2() < getMapa().getSize().getX2())
            screenTile.addX2(1);

        screen.addX(-1);
        screen.addY(-1);


        DrawParameter dpA = new DrawParameter();
        dpA.setAnimated(true);

        DrawParameter dpAC = new DrawParameter();
        dpAC.setAnimated(true);
        dpAC.setCenter(true);

        for (y = (int)screenTile.getY1(); y <= (int)screenTile.getY2(); y++) {
            tempPos.setY(screen.getY() * getConfig().getTilePixelHeight() + Math.round(offset.getY()));
            for (x = (int)screenTile.getX1(); x <= (int)screenTile.getX2(); x++) {
                tempPos.setX(screen.getX() * getConfig().getTilePixelWidth() + Math.round(offset.getX()));
                tile = getMapa().getTile(x, y);
                if (tile == null) {
                    screen.addX(1);
                    continue;
                }

                // Capa 1
                Drawer.drawGrh(batch, tile.getCapa(0), tempPos.getX(), tempPos.getY(), dpA);

                // Capa 2
                if (tile.getCapa(1) != null)
                    Drawer.drawGrh(batch, tile.getCapa(1), tempPos.getX(), tempPos.getY(), dpAC);

                screen.addX(1);
            }

            screen.addX(-x + screenTile.getX1());
            screen.addY(1);
        }


        screen.setX(minOffset.getX() - getConfig().getTileBufferSizeX());
        screen.setY(minOffset.getY() - getConfig().getTileBufferSizeY());

        for (y = (int)screenBigTile.getY1(); y <= (int)screenBigTile.getY2(); y++) {
            tempPos.setY(screen.getY() * getConfig().getTilePixelHeight() + Math.round(offset.getY()));
            for (x = (int)screenBigTile.getX1(); x <= (int)screenBigTile.getX2(); x++) {
                tempPos.setX(screen.getX() * getConfig().getTilePixelWidth() + Math.round(offset.getX()));
                tile = getMapa().getTile(x, y);
                if (tile == null) {
                    screen.addX(1);
                    continue;
                }

                // Objetos
                if (tile.getObjeto() != null)
                    if (tile.getObjeto() != null)
                        Drawer.drawGrh(batch, tile.getObjeto(), tempPos.getX(), tempPos.getY(), dpAC);


                // Personajes
                if (tile.getCharIndex() != 0)
                    Main.getInstance().getGameData().getChars().getChar(tile.getCharIndex())
                            .draw(batch, tempPos.getX(), tempPos.getY(), dpAC);

                // Capa 3
                if (tile.getCapa(2) != null)
                    Drawer.drawGrh(batch, tile.getCapa(2), tempPos.getX(), tempPos.getY(), dpAC);

                screen.addX(1);
            }
            screen.addX(-x + screenBigTile.getX1());
            screen.addY(1);
        }


        if (!techo) {
            screen.setX(minOffset.getX() - getConfig().getTileBufferSizeX());
            screen.setY(minOffset.getY() - getConfig().getTileBufferSizeY());

            for (y = (int) screenBigTile.getY1(); y <= (int) screenBigTile.getY2(); y++) {
                tempPos.setY(screen.getY() * getConfig().getTilePixelHeight() + Math.round(offset.getY()));
                for (x = (int) screenBigTile.getX1(); x <= (int) screenBigTile.getX2(); x++) {
                    tempPos.setX(screen.getX() * getConfig().getTilePixelWidth() + Math.round(offset.getX()));
                    tile = getMapa().getTile(x, y);
                    if (tile == null) {
                        screen.addX(1);
                        continue;
                    }

                    // Capa 4
                    if (tile.getCapa(3) != null)
                        Drawer.drawGrh(batch, tile.getCapa(3), tempPos.getX(), tempPos.getY(), dpAC);

                    screen.addX(1);
                }
                screen.addX(-x + screenBigTile.getX1());
                screen.addY(1);
            }
        }


        screen.setX(minOffset.getX() - getConfig().getTileBufferSizeX());
        screen.setY(minOffset.getY() - getConfig().getTileBufferSizeY());

        for (y = (int)screenBigTile.getY1(); y <= (int)screenBigTile.getY2(); y++) {
            tempPos.setY(screen.getY() * getConfig().getTilePixelHeight() + Math.round(offset.getY()));
            for (x = (int)screenBigTile.getX1(); x <= (int)screenBigTile.getX2(); x++) {
                tempPos.setX(screen.getX() * getConfig().getTilePixelWidth() + Math.round(offset.getX()));
                tile = getMapa().getTile(x, y);
                if (tile == null) {
                    screen.addX(1);
                    continue;
                }

                if (tile.getCharIndex() != 0)
                    Main.getInstance().getGameData().getChars().getChar(tile.getCharIndex())
                            .drawDialog(batch, tempPos.getX(), tempPos.getY(), dpAC);

                screen.addX(1);
            }
            screen.addX(-x + screenBigTile.getX1());
            screen.addY(1);
        }


        Drawer.popScissors(getStage());
        Drawer.setDefColor(Color.WHITE);
    }

    public Config getConfig() { return Main.getInstance().getConfig(); }
    public ClientPackages getClPack() { return Main.getInstance().getConnection().getClPack(); }
}
