package org.gszone.jfenix13.objects;

import org.gszone.jfenix13.general.General;
import org.gszone.jfenix13.graphics.Grh;

/**
 * Representa a un objeto con Grh en las 4 direcciones. (se utiliza para cabezas, cascos, escudos y armas)
 *
 * grhsIndex: conjunto de los 4 grhs correspondientes a un GrhDir.
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
     * Retorna un nuevo array de Grhs, según los grhIndex.
     */
    public Grh[] getGrhs() {
        Grh[] grhs;
        grhs = new Grh[General.Direccion.values().length];
        for (int i = 0; i < grhs.length; i++) {
            Grh grh = new Grh(getGrhIndex(General.Direccion.values()[i]));
            grhs[i] = grh;
        }
        return grhs;
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
