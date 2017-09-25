package org.gszone.jfenix13.objects;

import org.gszone.jfenix13.utils.Rect;

/**
 * Representa al usuario actual
 *
 * indexInServer: es el número de index del pj actual (sirve para buscarlo entre los chars).
 * cambiandoDir: indica si el usuario inició la petición de cambiar la dirección. (Si no se chequea de esta forma,
 *               desde que se hace la petición hasta que se recibe el cambio, se harían varias peticiones y esto
 *               generaría lag).
 * area: contiene info de los límites del área actual, (y permite cambiar de área)
 *
 * min????: valor actual de una característica (HP, MANA, etc).
 * max????: valor máximo de una característica (HP, MANA, etc).
 *
 * los demás atributos se entienden a simple vista:
 */
public class User {
    private int index;
    private int indexInServer;
    private int map;
    private boolean paralizado;
    private boolean descansando;
    private boolean meditando;
    private boolean navegando;
    private boolean cambiandoDir;

    private int minSta;
    private int maxSta;
    private int minMana;
    private int maxMana;
    private int minHP;
    private int maxHP;
    private int minExp;
    private int maxExp;
    private int oro;

    private Rect area;

    public User() {
        area = new Rect();
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

    public boolean isParalizado() {
        return paralizado;
    }

    public void setParalizado(boolean paralizado) {
        this.paralizado = paralizado;
    }

    public boolean isDescansando() {
        return descansando;
    }

    public void setDescansando(boolean descansando) {
        this.descansando = descansando;
    }

    public boolean isMeditando() {
        return meditando;
    }

    public void setMeditando(boolean meditando) {
        this.meditando = meditando;
    }

    public boolean isNavegando() {
        return navegando;
    }

    public void setNavegando(boolean navegando) {
        this.navegando = navegando;
    }

    public boolean isCambiandoDir() {
        return cambiandoDir;
    }

    public void setCambiandoDir(boolean cambiandoDir) {
        this.cambiandoDir = cambiandoDir;
    }

    public int getMinSta() {
        return minSta;
    }

    public void setMinSta(int minSta) {
        this.minSta = minSta;
    }

    public int getMaxSta() {
        return maxSta;
    }

    public void setMaxSta(int maxSta) {
        this.maxSta = maxSta;
    }

    public int getMinMana() {
        return minMana;
    }

    public void setMinMana(int minMana) {
        this.minMana = minMana;
    }

    public int getMaxMana() {
        return maxMana;
    }

    public void setMaxMana(int maxMana) {
        this.maxMana = maxMana;
    }

    public int getMinHP() {
        return minHP;
    }

    public void setMinHP(int minHP) {
        this.minHP = minHP;
    }

    public int getMaxHP() {
        return maxHP;
    }

    public void setMaxHP(int maxHP) {
        this.maxHP = maxHP;
    }

    public int getMinExp() {
        return minExp;
    }

    public void setMinExp(int minExp) {
        this.minExp = minExp;
    }

    public int getMaxExp() {
        return maxExp;
    }

    public void setMaxExp(int maxExp) {
        this.maxExp = maxExp;
    }

    public int getOro() {
        return oro;
    }

    public void setOro(int oro) {
        this.oro = oro;
    }

    public Rect getArea() { return area; }
}
