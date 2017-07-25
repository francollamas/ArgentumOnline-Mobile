package org.gszone.jfenix13.handlers;


import org.gszone.jfenix13.utils.Position;

/**
 * Variables que manejan al mundo
 *
 * pos: posición del nuevo tile en donde el mouse está por encima
 * moved: indica que se movió el mouse a la posición 'pos' (del mapa)
 */
public class WorldHandler {
    private Position pos;
    private boolean moved;

    public WorldHandler() {
        pos = new Position();
    }

    public Position getPos() {
        return pos;
    }

    public void setPos(Position pos) {
        this.pos = pos;
    }

    public boolean isMoved() {
        return moved;
    }

    public void setMoved(boolean moved) {
        this.moved = moved;
    }
}
