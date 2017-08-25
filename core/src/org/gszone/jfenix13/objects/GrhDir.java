package org.gszone.jfenix13.objects;

import org.gszone.jfenix13.general.General;

/**
 * Representa a un objeto con Grh en las 4 direcciones.
 */
public class GrhDir {
    protected int[] grhsIndex;

    public GrhDir() {
        grhsIndex = new int[General.Direccion.values().length];
    }

    /**
     * Obtiene todos los GrhIndex
     */
    public int[] getGrhsIndex() {
        return grhsIndex;
    }

    /**
     * Obtiene el GrhIndex de una dirección
     */
    public int getGrhIndex(General.Direccion dir) {
        return grhsIndex[dir.ordinal()];
    }

    /**
     * Setea un GrhIndex para una dirección.
     */
    public void setGrhIndex(General.Direccion dir, int grhIndex) {
        this.grhsIndex[dir.ordinal()] = grhIndex;
    }
}
