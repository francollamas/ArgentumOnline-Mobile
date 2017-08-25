package org.gszone.jfenix13.objects;

import org.gszone.jfenix13.utils.Position;

/**
 * Representa a un Fx
 */
public class Fx {
    private int grhIndex;
    private Position offset;

    public Fx() {
        offset = new Position();
    }

    public int getGrhIndex() {
        return grhIndex;
    }

    public void setGrhIndex(int grhIndex) {
        this.grhIndex = grhIndex;
    }

    /**
     * Obtiene la posición exacta del offset
     */
    public Position getOffset() {
        return offset;
    }

    /**
     * Setea la posición exacta del offset
     */
    public void setOffset(Position offset) {
        this.offset = offset;
    }

    public void setOffsetX(int x) {
        offset.setX(x);
    }

    public void setOffsetY(int y) {
        offset.setY(y);
    }
}
