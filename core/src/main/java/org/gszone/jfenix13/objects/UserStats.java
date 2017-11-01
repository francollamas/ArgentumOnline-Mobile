package org.gszone.jfenix13.objects;

import org.gszone.jfenix13.Main;

/**
 * Estado del personaje (vida, mana, energía, etc)
 */
public class UserStats {
    private int energia;
    private int mana;
    private int vida;
    private int exp;
    private int hambre;
    private int sed;
    private int maxEnergia;
    private int maxMana;
    private int maxVida;
    private int maxExp;
    private int maxHambre;
    private int maxSed;
    private int nivel;
    private int oro;
    private int fuerza;
    private int agilidad;

    public int getEnergia() {
        return energia;
    }

    public void setEnergia(int energia) {
        this.energia = energia;
    }

    public int getMaxEnergia() {
        return maxEnergia;
    }

    public void setMaxEnergia(int maxEnergia) {
        this.maxEnergia = maxEnergia;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public int getMaxMana() {
        return maxMana;
    }

    public void setMaxMana(int maxMana) {
        this.maxMana = maxMana;
    }

    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;

        // Actualizo el estado del usuario según la vida (vivo o muerto)
        User u = Main.getInstance().getGameData().getCurrentUser();
        if (vida == 0)
            u.setMuerto(true);
        else
            u.setMuerto(false);
    }

    public int getMaxVida() {
        return maxVida;
    }

    public void setMaxVida(int maxVida) {
        this.maxVida = maxVida;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public int getMaxExp() {
        return maxExp;
    }

    public void setMaxExp(int maxExp) {
        this.maxExp = maxExp;
    }

    public int getHambre() {
        return hambre;
    }

    public void setHambre(int hambre) {
        this.hambre = hambre;
    }

    public int getSed() {
        return sed;
    }

    public void setSed(int sed) {
        this.sed = sed;
    }

    public int getMaxHambre() {
        return maxHambre;
    }

    public void setMaxHambre(int maxHambre) {
        this.maxHambre = maxHambre;
    }

    public int getMaxSed() {
        return maxSed;
    }

    public void setMaxSed(int maxSed) {
        this.maxSed = maxSed;
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

    public int getFuerza() {
        return fuerza;
    }

    public void setFuerza(int fuerza) {
        this.fuerza = fuerza;
    }

    public int getAgilidad() {
        return agilidad;
    }

    public void setAgilidad(int agilidad) {
        this.agilidad = agilidad;
    }
}
