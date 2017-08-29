package org.gszone.jfenix13.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import org.gszone.jfenix13.general.General;
import org.gszone.jfenix13.general.Main;
import org.gszone.jfenix13.graphics.DrawParameter;
import org.gszone.jfenix13.graphics.Drawer;
import org.gszone.jfenix13.handlers.WorldHandler;
import org.gszone.jfenix13.listeners.WorldListener;
import org.gszone.jfenix13.objects.*;
import org.gszone.jfenix13.utils.Position;
import org.gszone.jfenix13.utils.Rect;

import static com.badlogic.gdx.Input.Keys.*;
import static org.gszone.jfenix13.general.General.*;

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
 * mapa: hace referencia al mapa actual.
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

    private WorldHandler h;

    public World() {
        setSize(getGeneral().getWindowsTileWidth() * getGeneral().getTilePixelWidth(),
                getGeneral().getWindowsTileHeight() * getGeneral().getTilePixelHeight());

        h = new WorldHandler();
        addListener(new WorldListener(h));

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
     * Inicia el movimiento del pj y mundo hacia una dirección
     */
    public void checkKeys() {
        if (!isMoving()) {
            if (Gdx.input.isKeyPressed(UP)) moveChar(Direccion.NORTE);
            else if (Gdx.input.isKeyPressed(RIGHT)) moveChar(Direccion.ESTE);
            else if (Gdx.input.isKeyPressed(DOWN)) moveChar(Direccion.SUR);
            else if (Gdx.input.isKeyPressed(LEFT)) moveChar(Direccion.OESTE);
        }
    }

    public void moveChar(Direccion dir) {
        Position relPos = Position.dirToPos(dir);
        Position absPos = pos.getSuma(relPos);
        if (!getMapa().isLegalPos(absPos)) return;

        int index = Main.getInstance().getGameData().getCurrentUser().getIndexInServer();
        Main.getInstance().getConnection().getClPack().writeWalk(dir);
        Main.getInstance().getGameData().getChars().moveChar(index, dir);
        setMove(relPos, absPos);
    }


    /**
     * Mueve la pantalla a una dirección
     */
    private void setMove(Position relPos, Position absPos) {
        pos = absPos;
        addToPos = relPos;
        moving = true;
        setTecho();
    }


    /**
     * Si la pantalla se tiene que mover, esto se encarga de ir moviendola.
     * (se ejecuta constantemente)
     */
    public void move() {

        if (isMoving()) {
            if (addToPos.getX() != 0) {
                offset.addX(-getGeneral().getScrollPixelsPerFrame() * addToPos.getX() * Drawer.getDelta());
                if (Math.abs(offset.getX()) >= Math.abs(getGeneral().getTilePixelWidth() * addToPos.getX())) {
                    offset.setX(0);
                    addToPos.setX(0);
                    moving = false;
                }
            }

            if (addToPos.getY() != 0) {
                offset.addY(-getGeneral().getScrollPixelsPerFrame() * addToPos.getY() * Drawer.getDelta());
                if (Math.abs(offset.getY()) >= Math.abs(getGeneral().getTilePixelHeight() * addToPos.getY())) {
                    offset.setY(0);
                    addToPos.setY(0);
                    moving = false;
                }
            }

            setMouseTile(h.getPos());
        }
    }

    public void render(Stage stage) {

        int x, y;
        MapTile tile;
        Position tempPos = new Position();
        Position minOffset = new Position();
        Position screen = new Position();

        int halfWindowsTileWidth = getGeneral().getWindowsTileWidth() / 2;
        int halfWindowsTileHeight = getGeneral().getWindowsTileHeight() / 2;

        screenTile.setX1(pos.getX() - addToPos.getX() - halfWindowsTileWidth);
        screenTile.setY1(pos.getY() - addToPos.getY() - halfWindowsTileHeight);
        screenTile.setX2(pos.getX() - addToPos.getX() + halfWindowsTileWidth);
        screenTile.setY2(pos.getY() - addToPos.getY() + halfWindowsTileHeight);

        screenBigTile.setX1(screenTile.getX1() - getGeneral().getTileBufferSizeX());
        screenBigTile.setY1(screenTile.getY1() - getGeneral().getTileBufferSizeY());
        screenBigTile.setX2(screenTile.getX2() + getGeneral().getTileBufferSizeX());
        screenBigTile.setY2(screenTile.getY2() + getGeneral().getTileBufferSizeY());


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
            tempPos.setY(screen.getY() * getGeneral().getTilePixelHeight() + offset.getY());
            for (x = (int)screenTile.getX1(); x <= (int)screenTile.getX2(); x++) {
                tempPos.setX(screen.getX() * getGeneral().getTilePixelWidth() + offset.getX());
                tile = getMapa().getTile(x, y);

                // Capa 1
                Drawer.drawGrh(stage.getBatch(), tile.getCapa(0), tempPos.getX(), tempPos.getY(), dpA);

                // Capa 2
                if (tile.getCapa(1) != null)
                    Drawer.drawGrh(stage.getBatch(), tile.getCapa(1), tempPos.getX(), tempPos.getY(), dpAC);

                screen.addX(1);
            }

            screen.addX(-x + screenTile.getX1());
            screen.addY(1);
        }


        screen.setX(minOffset.getX() - getGeneral().getTileBufferSizeX());
        screen.setY(minOffset.getY() - getGeneral().getTileBufferSizeY());

        for (y = (int)screenBigTile.getY1(); y <= (int)screenBigTile.getY2(); y++) {
            tempPos.setY(screen.getY() * getGeneral().getTilePixelHeight() + offset.getY());
            for (x = (int)screenBigTile.getX1(); x <= (int)screenBigTile.getX2(); x++) {
                tempPos.setX(screen.getX() * getGeneral().getTilePixelWidth() + offset.getX());
                tile = getMapa().getTile(x, y);

                // Objetos
                if (tile.getObjeto() != null)
                    if (tile.getObjeto() != null)
                        Drawer.drawGrh(stage.getBatch(), tile.getObjeto(), tempPos.getX(), tempPos.getY(), dpAC);

                // Personajes
                if (tile.getCharIndex() != 0)
                    drawChar(stage, tile.getCharIndex(), tempPos.getX(), tempPos.getY(), dpAC);

                // Capa 3
                if (tile.getCapa(2) != null)
                    Drawer.drawGrh(stage.getBatch(), tile.getCapa(2), tempPos.getX(), tempPos.getY(), dpAC);

                screen.addX(1);
            }
            screen.addX(-x + screenBigTile.getX1());
            screen.addY(1);
        }


        if (!techo) {
            screen.setX(minOffset.getX() - getGeneral().getTileBufferSizeX());
            screen.setY(minOffset.getY() - getGeneral().getTileBufferSizeY());

            for (y = (int) screenBigTile.getY1(); y <= (int) screenBigTile.getY2(); y++) {
                tempPos.setY(screen.getY() * getGeneral().getTilePixelHeight() + offset.getY());
                for (x = (int) screenBigTile.getX1(); x <= (int) screenBigTile.getX2(); x++) {
                    tempPos.setX(screen.getX() * getGeneral().getTilePixelWidth() + offset.getX());
                    tile = getMapa().getTile(x, y);

                    // Capa 4
                    if (tile.getCapa(3) != null)
                        Drawer.drawGrh(stage.getBatch(), tile.getCapa(3), tempPos.getX(), tempPos.getY(), dpAC);

                    screen.addX(1);
                }
                screen.addX(-x + screenBigTile.getX1());
                screen.addY(1);
            }
        }

    }

    /**
     * Dibuja a un char. (es llamado por el método render())
     */
    private void drawChar(Stage stage, int charIndex, float x, float y, DrawParameter dp) {
        Char c = Main.getInstance().getGameData().getChars().getChar(charIndex);
        int heading = c.getHeading().ordinal();
        boolean moved = false;

        // Movimiento del char
        if (c.isMoving()) {

            if (c.getMoveDir().getX() != 0 || c.getMoveDir().getY() != 0) {
                // Arranco las animaciones
                if (c.getBody()[heading].getSpeed() > 0) c.getBody()[heading].setStarted((byte)1);
                c.getWeapon()[heading].setStarted((byte)1);
                c.getShield()[heading].setStarted((byte)1);
                moved = true;


                // Muevo en X
                if (c.getMoveDir().getX() != 0) {
                    c.getMoveOffset().setX(c.getMoveOffset().getX()
                            + Main.getInstance().getGeneral().getScrollPixelsPerFrame() * c.getMoveDir().getX() * Drawer.getDelta());

                    if ((c.getMoveDir().getX() == 1 && c.getMoveOffset().getX() >= 0)
                            || c.getMoveDir().getX() == -1 && c.getMoveOffset().getX() <= 0) {
                        c.getMoveOffset().setX(0);
                        c.getMoveDir().setX(0);
                    }
                }

                // Muevo en Y
                if (c.getMoveDir().getY() != 0) {
                    c.getMoveOffset().setY(c.getMoveOffset().getY()
                            + Main.getInstance().getGeneral().getScrollPixelsPerFrame() * c.getMoveDir().getY() * Drawer.getDelta());

                    if ((c.getMoveDir().getY() == 1 && c.getMoveOffset().getY() >= 0)
                            || c.getMoveDir().getY() == -1 && c.getMoveOffset().getY() <= 0) {
                        c.getMoveOffset().setY(0);
                        c.getMoveDir().setY(0);
                    }
                }

            }
        }

        // Si no se movió (o sea, si no pasó por el trozo de código de arriba)
        if (!moved) {
            c.getBody()[heading].setStarted((byte)0);
            c.getBody()[heading].setFrame(1);

            c.getWeapon()[heading].setStarted((byte)0);
            c.getWeapon()[heading].setFrame(1);

            c.getShield()[heading].setStarted((byte)0);
            c.getShield()[heading].setFrame(1);

            c.setMoving(false);
        }

        x += c.getMoveOffset().getX();
        y += c.getMoveOffset().getY();


        if (!c.isInvisible()) {
            Body bodyData = Main.getInstance().getAssets().getBodies().getBody(c.getBodyIndex());
            if (c.getBody() != null)
                Drawer.drawGrh(stage.getBatch(), c.getBody()[heading], x, y, dp);

            if (c.getHead() != null)
                Drawer.drawGrh(stage.getBatch(), c.getHead()[heading], x + bodyData.getHeadOffset().getX(), y + bodyData.getHeadOffset().getY(), dp);

            if (c.getHelmet() != null)
                Drawer.drawGrh(stage.getBatch(), c.getHelmet()[heading], x + bodyData.getHeadOffset().getX(), y + bodyData.getHeadOffset().getY(), dp);

            if (c.getWeapon() != null)
                Drawer.drawGrh(stage.getBatch(), c.getWeapon()[heading], x, y, dp);

            if (c.getShield() != null)
                Drawer.drawGrh(stage.getBatch(), c.getShield()[heading], x, y, dp);

            DrawParameter dpp = new DrawParameter();
            dpp.setColor(new Color(0, 0.5f, 1f, 1f));

            if (c.getNombre().length() > 0)
                Drawer.drawText(stage.getBatch(), 3, c.getNombre(), x + c.getNombreOffset(), y + 30, dpp);
        }



        if (c.getFxIndex() != 0) {
            Fx fxData = Main.getInstance().getAssets().getFxs().getFx(c.getFxIndex());
            Drawer.drawGrh(stage.getBatch(), c.getFx(), x + fxData.getOffset().getX(), y + fxData.getOffset().getY(), dp);
            if (c.getFx().getStarted() == 0) {
                c.setFxIndex(0);
                c.setFx(null);
            }
        }

    }


    /**
     * Acciones ejecutadas según lo que pasa
     */
    @Override
    public void act(float delta) {
        super.act(delta);

        if (h.isMoved()) {
            setMouseTile(h.getPos());
            h.setMoved(false);
        }
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
        mouseTile.setX((int)(this.pos.getX() + pos.getX() / getGeneral().getTilePixelWidth() - getGeneral().getWindowsTileWidth() / 2));
        mouseTile.setY((int)(this.pos.getY() + (getHeight() - pos.getY()) / getGeneral().getTilePixelHeight() - getGeneral().getWindowsTileHeight() / 2));
    }

    public static General getGeneral() { return Main.getInstance().getGeneral(); }
}
