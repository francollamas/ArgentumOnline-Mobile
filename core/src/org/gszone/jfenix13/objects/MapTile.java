package org.gszone.jfenix13.objects;

import com.badlogic.gdx.graphics.Color;
import org.gszone.jfenix13.graphics.Grh;

/**
 * Características de 1 tile del mapa.
 *
 *
 */
public class MapTile {
    private Grh[] capas;
    private int charIndex;
    private int npcIndex;
    private Objeto objeto;

    private boolean blocked;
    private int trigger;
    //private Light light;
    private int particula;
    private Color[] color;

    public MapTile() {
        capas = new Grh[4];
        objeto = null;
        //light = null;
        color = new Color[4];
    }

    public Grh[] getCapas() {
        return capas;
    }

    public void setCapas(Grh[] capa) {
        this.capas = capa;
    }

    public Grh getCapa(int index) {
        return capas[index];
    }

    public void setCapa(int index, Grh grh) {
        this.capas[index] = grh;
    }

    public int getCharIndex() {
        return charIndex;
    }

    public void setCharIndex(int charIndex) {
        this.charIndex = charIndex;
    }

    public int getNpcIndex() {
        return npcIndex;
    }

    public void setNpcIndex(int npcIndex) {
        this.npcIndex = npcIndex;
    }

    public Objeto getObjeto() {
        return objeto;
    }

    public void setObjeto(Objeto objeto) {
        this.objeto = objeto;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public int getTrigger() {
        return trigger;
    }

    public void setTrigger(int trigger) {
        this.trigger = trigger;
    }

    /*public Light getLight() {
        return light;
    }

    public void setLight(Light light) {
        this.light = light;
    }*/

    public int getParticula() {
        return particula;
    }

    public void setParticula(int particula) {
        this.particula = particula;
    }

    public Color[] getColors() {
        return color;
    }

    public void setColors(Color[] color) {
        this.color = color;
    }
}
