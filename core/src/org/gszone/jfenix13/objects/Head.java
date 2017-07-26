package org.gszone.jfenix13.objects;

import org.gszone.jfenix13.general.General;
import org.gszone.jfenix13.graphics.Grh;

/**
 * Representa a una cabeza o casco
 */
public class Head {
    private Grh[] grhs;

    public Head() {
        grhs = new Grh[General.Direccion.values().length];
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
    public Grh getGrh(General.Direccion dir) {
        return grhs[dir.ordinal()];
    }

    /**
     * Setea un grh para una dirección.
     */
    public void setGrh(General.Direccion dir, Grh grh) {
        this.grhs[dir.ordinal()] = grh;
    }
}
