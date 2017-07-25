package org.gszone.jfenix13.objects;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import org.gszone.jfenix13.general.Main;
import org.gszone.jfenix13.graphics.DrawParameter;
import org.gszone.jfenix13.graphics.Drawer;
import org.gszone.jfenix13.handlers.WorldHandler;
import org.gszone.jfenix13.listeners.WorldListener;
import org.gszone.jfenix13.utils.Position;
import org.gszone.jfenix13.utils.Rect;

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
        setSize(WINDOWS_TILE_WIDTH * TILE_PIXEL_WIDTH, WINDOWS_TILE_HEIGHT * TILE_PIXEL_HEIGHT);

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
     * Determina si la pantalla se tiene que mover hacia alguna dirección.
     */
    public void setMove(Direccion dir) {
        Position relPos = Position.dirToPos(dir);
        Position absPos = pos.getSuma(relPos);

        if (!getMapa().isLegalPos(absPos)) return;

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
        if (moving) {
            if (addToPos.getX() != 0) {
                offset.addX(-SCROLL_PIXELS_PER_FRAME * addToPos.getX() * Drawer.getDelta());
                if (Math.abs(offset.getX()) >= Math.abs(TILE_PIXEL_WIDTH * addToPos.getX())) {
                    offset.setX(0);
                    addToPos.setX(0);
                    moving = false;
                }
            }

            if (addToPos.getY() != 0) {
                offset.addY(-SCROLL_PIXELS_PER_FRAME * addToPos.getY() * Drawer.getDelta());
                if (Math.abs(offset.getY()) >= Math.abs(TILE_PIXEL_HEIGHT * addToPos.getY())) {
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

        int halfWindowsTileWidth = WINDOWS_TILE_WIDTH / 2;
        int halfWindowsTileHeight = WINDOWS_TILE_HEIGHT / 2;

        screenTile.setX1(pos.getX() - addToPos.getX() - halfWindowsTileWidth);
        screenTile.setY1(pos.getY() - addToPos.getY() - halfWindowsTileHeight);
        screenTile.setX2(pos.getX() - addToPos.getX() + halfWindowsTileWidth);
        screenTile.setY2(pos.getY() - addToPos.getY() + halfWindowsTileHeight);

        screenBigTile.setX1(screenTile.getX1() - TILE_BUFFER_SIZE_X);
        screenBigTile.setY1(screenTile.getY1() - TILE_BUFFER_SIZE_Y);
        screenBigTile.setX2(screenTile.getX2() + TILE_BUFFER_SIZE_X);
        screenBigTile.setY2(screenTile.getY2() + TILE_BUFFER_SIZE_Y);


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

        for (x = (int)screenTile.getX1(); x <= (int)screenTile.getX2(); x++) {
            tempPos.setX(screen.getX() * TILE_PIXEL_WIDTH + offset.getX());
            for (y = (int)screenTile.getY1(); y <= (int)screenTile.getY2(); y++) {
                tempPos.setY(screen.getY() * TILE_PIXEL_HEIGHT + offset.getY());
                tile = getMapa().getTile(x - 1, y - 1);

                // Capa 1
                Drawer.drawGrh(stage.getBatch(), tile.getCapa(0), tempPos.getX(), tempPos.getY(), dpA);

                // Capa 2
                if (tile.getCapa(1) != null)
                    Drawer.drawGrh(stage.getBatch(), tile.getCapa(1), tempPos.getX(), tempPos.getY(), dpAC);

                screen.addY(1);
            }

            screen.addY(-y + screenTile.getY1());
            screen.addX(1);
        }


        screen.setX(minOffset.getX() - TILE_BUFFER_SIZE_X);
        screen.setY(minOffset.getY() - TILE_BUFFER_SIZE_Y);

        for (x = (int)screenBigTile.getX1(); x <= (int)screenBigTile.getX2(); x++) {
            tempPos.setX(screen.getX() * TILE_PIXEL_WIDTH + offset.getX());
            for (y = (int)screenBigTile.getY1(); y <= (int)screenBigTile.getY2(); y++) {
                tempPos.setY(screen.getY() * TILE_PIXEL_HEIGHT + offset.getY());
                tile = getMapa().getTile(x - 1, y - 1);

                // Objetos
                if (tile.getObjeto() != null)
                    if (tile.getObjeto().getGrh() != null)
                        Drawer.drawGrh(stage.getBatch(), tile.getObjeto().getGrh(), tempPos.getX(), tempPos.getY(), dpAC);

                // Personajes


                // Capa 3
                if (tile.getCapa(2) != null)
                    Drawer.drawGrh(stage.getBatch(), tile.getCapa(2), tempPos.getX(), tempPos.getY(), dpAC);

                screen.addY(1);
            }
            screen.addY(-y + screenBigTile.getY1());
            screen.addX(1);
        }


        if (!techo) {
            screen.setX(minOffset.getX() - TILE_BUFFER_SIZE_X);
            screen.setY(minOffset.getY() - TILE_BUFFER_SIZE_Y);

            for (x = (int) screenBigTile.getX1(); x <= (int) screenBigTile.getX2(); x++) {
                tempPos.setX(screen.getX() * TILE_PIXEL_WIDTH + offset.getX());
                for (y = (int) screenBigTile.getY1(); y <= (int) screenBigTile.getY2(); y++) {
                    tempPos.setY(screen.getY() * TILE_PIXEL_HEIGHT + offset.getY());
                    tile = getMapa().getTile(x - 1, y - 1);

                    // Capa 4
                    if (tile.getCapa(3) != null)
                        Drawer.drawGrh(stage.getBatch(), tile.getCapa(3), tempPos.getX(), tempPos.getY(), dpAC);

                    screen.addY(1);
                }
                screen.addY(-y + screenBigTile.getY1());
                screen.addX(1);
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
        MapTile tile = getMapa().getTile((int)pos.getX() - 1, (int)pos.getY() - 1);
        this.techo = tile.getTrigger() == 1 || tile.getTrigger() == 2 || tile.getTrigger() == 4;
    }


    /**
     * Define cuál es el tile en donde está el mouse por encima
     */
    public void setMouseTile(Position pos) {
        mouseTile.setX((int)(this.pos.getX() + pos.getX() / TILE_PIXEL_WIDTH - WINDOWS_TILE_WIDTH / 2));
        mouseTile.setY((int)(this.pos.getY() + (getHeight() - pos.getY()) / TILE_PIXEL_HEIGHT - WINDOWS_TILE_HEIGHT / 2));
    }
}
