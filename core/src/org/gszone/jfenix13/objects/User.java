package org.gszone.jfenix13.objects;

/**
 * Representa al usuario actual
 * indexInServer: es el número de index del pj actual (sirve para buscarlo entre los chars).
 */
public class User {
    private int index;
    private int indexInServer;
    private int map;


    public User() {

    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndexInServer() {
        return indexInServer;
    }

    public void setIndexInServer(int indexInServer) {
        this.indexInServer = indexInServer;
    }

    public int getMap() {
        return map;
    }

    public void setMap(int map) {
        this.map = map;
    }
}
