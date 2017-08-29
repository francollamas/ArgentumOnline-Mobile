package org.gszone.jfenix13.objects;

import org.gszone.jfenix13.graphics.Grh;

/**
 * Representa la info de un objeto
 * index: número que representa al objeto en los Dats.
 * cantidad: ...
 */
public class Objeto {
    private int index;
    private int cantidad;

    public Objeto() {
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
