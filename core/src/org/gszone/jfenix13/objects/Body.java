package org.gszone.jfenix13.objects;

import org.gszone.jfenix13.utils.Position;

/**
 * Representa a un cuerpo
 *
 * headOffset: distancia desde el piso hasta donde debe comenzar la cabeza
 */
public class Body extends GrhDir {
    private Position headOffset;

    public Body() {
        super();
        headOffset = new Position();
    }

    /**
     * Obtiene la posición exacta de headOffset
     */
    public Position getHeadOffset() {
        return headOffset;
    }

    /**
     * Setea la posición exacta de headOffset
     */
    public void setHeadOffset(Position headOffset) {
        this.headOffset = headOffset;
    }

    public void setHeadOffsetX(int x) {
        headOffset.setX(x);
    }

    public void setHeadOffsetY(int y) {
        headOffset.setY(y);
    }
}
