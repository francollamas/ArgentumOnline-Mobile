package org.gszone.jfenix13.objects;


import org.gszone.jfenix13.general.General.Direccion;
import org.gszone.jfenix13.graphics.Grh;
import org.gszone.jfenix13.utils.Position;

/**
 * Representa a un cuerpo
 */
public class Body {
    private Grh[] grhs;
    private Position headOffset;

    public Body() {
        grhs = new Grh[Direccion.values().length];
        headOffset = new Position();
    }

    /**
     * Obtiene todos los Grh
     */
    public Grh[] getGrhs() {
        return grhs;
    }

    /**
     * Obtiene el Grh de una dirección
     */
    public Grh getGrh(Direccion dir) {
        return grhs[dir.ordinal()];
    }

    /**
     * Setea un grh para una dirección.
     */
    public void setGrh(Direccion dir, Grh grh) {
        this.grhs[dir.ordinal()] = grh;
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
