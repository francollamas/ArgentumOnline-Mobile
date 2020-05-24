package org.gszone.jfenix13.objects;

import com.badlogic.gdx.graphics.Color;
import org.gszone.jfenix13.graphics.Grh;

/**
 * Características de 1 tile del mapa.
 *
 * capas: array de grhs (uno por cada capa).
 * charIndex: índice del char que esta ocupando el tile
 * objeto: grh que representa al objeto del tile
 * blocked: indica si está bloqueado
 * trigger: indica cómo se debe comportar el cliente si el usuario está parado sobre este tile
 * light: indica si se inicia una luz en esa posición
 * partícula: indica si hay una partícula
 * color: representa los colores del tile (en sus 4 vértices).
 */
public class MapTile {
    private Grh[] capas;
    private int charIndex;
    private Grh objeto;

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

    public Grh getObjeto() {
        return objeto;
    }

    public void setObjeto(Grh objeto) {
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

    /**
     * Determina si en el tile hay agua
     */
    public boolean hayAgua() {
        int index = capas[0].getIndex();
        return ((index >= 1505 && index <= 1520) || (index >= 5665 && index <= 5680) || (index >= 13547 && index <= 13562))
                && (capas[1] == null);
    }
}
