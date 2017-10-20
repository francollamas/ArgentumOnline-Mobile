package org.gszone.jfenix13.objects;

/**
 * Estado del personaje (vida, mana, energía, etc)
 */
public class UserStats {
    private int minEnergia;
    private int maxEnergia;
    private int minMana;
    private int maxMana;
    private int minVida;
    private int maxVida;
    private int minExp;
    private int maxExp;
    private int nivel;
    private int oro;

    public int getMinEnergia() {
        return minEnergia;
    }

    public void setMinEnergia(int minEnergia) {
        this.minEnergia = minEnergia;
    }

    public int getMaxEnergia() {
        return maxEnergia;
    }

    public void setMaxEnergia(int maxEnergia) {
        this.maxEnergia = maxEnergia;
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

    public int getMinVida() {
        return minVida;
    }

    public void setMinVida(int minVida) {
        this.minVida = minVida;
    }

    public int getMaxVida() {
        return maxVida;
    }

    public void setMaxVida(int maxVida) {
        this.maxVida = maxVida;
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

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public int getOro() {
        return oro;
    }

    public void setOro(int oro) {
        this.oro = oro;
    }
}
