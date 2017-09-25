package org.gszone.jfenix13.utils;

import org.gszone.jfenix13.general.General;
import org.gszone.jfenix13.general.General.Direccion;

/**
 * Clase que representa un punto
 *
 * x: coordenada en el eje horizontal
 * y: coordenada en el eje vertical
 */
public class Position {
    private float x;
    private float y;

    public Position() {

    }

    public Position(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void addX(float x) {
        this.x += x;
    }

    public float getY() {
        return y;
    }

    public void addY(float y) {
        this.y += y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void set(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Obtener la suma de la posición actual y una pasada por parámetro (no modifica la pos original)
     */
    public Position getSuma(Position pos) {
        Position p = new Position(this.getX(), this.getY());
        p.addX(pos.getX());
        p.addY(pos.getY());

        return p;
    }

    /**
     * A la posición actual se le agrega una pasada por parámetro
     */
    public void sumar(Position pos) {
        addX(pos.getX());
        addY(pos.getY());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Position)
            if (getX() == ((Position)obj).getX() && getY() == ((Position)obj).getY())
                return true;
        return false;
    }

    /**
     * Obtiene una posición relativa según una dirección
     */
    public static Position dirToPos(General.Direccion dir) {
        Position pos = new Position();

        switch (dir) {
            case NORTE:
                pos.setY(-1);
                break;
            case ESTE:
                pos.setX(1);
                break;
            case SUR:
                pos.setY(1);
                break;
            case OESTE:
                pos.setX(-1);
                break;
        }
        return pos;
    }

    /**
     * Obtiene una dirección según una posición relativa
     */
    public static Direccion posToDir(Position pos) {
        Direccion dir = null;

        if (pos.getX() == 1) dir = General.Direccion.ESTE;
        else if (pos.getX() == -1) dir = General.Direccion.OESTE;
        else if (pos.getY() == 1) dir = General.Direccion.SUR;
        else if (pos.getY() == -1) dir = General.Direccion.NORTE;

        return dir;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
