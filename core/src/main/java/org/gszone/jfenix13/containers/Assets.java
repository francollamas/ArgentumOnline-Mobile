package org.gszone.jfenix13.containers;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import org.gszone.jfenix13.objects.Map;
import static org.gszone.jfenix13.containers.PartChar.*;

import static org.gszone.jfenix13.general.FileNames.*;

/**
 * Contiene todos los objetos cargados desde los assets
 *
 * gdxAssets: manejador de assets que libGDX sabe manejar (atlas, texturas, musica, sonidos, etc)
 * los demas corresponden a archivos de datos propios del juego.
 * audio: manejador de sonidos y música
 * grhs: manejador de grhs
 * fonts: manejador de fuentes
 * bodies: manejador de cuerpos
 * heads: manejador de cabezas
 * helmets: manejador de cascos
 * weapons: manejador de armas
 * shields: manejador de escudos
 * fxs: manejador de fxs
 * mapa: mapa actual
 */

public class Assets {
    private AssetManager gdxAssets;
    private Textures textures;
    private Audio audio;
    private Grhs grhs;
    private Bodies bodies;
    private PartChar heads;
    private PartChar helmets;
    private PartChar weapons;
    private PartChar shields;
    private Fxs fxs;
    private Map mapa;

    public Assets() {
        gdxAssets = new AssetManager();
        audio = new Audio();

        gdxAssets.load(getSkinDir(), Skin.class);

        gdxAssets.finishLoading();
    }

    public AssetManager getGDXAssets() { return gdxAssets; }

    /**
     * Carga al resto de los assets que sabe cargar libGDX
     * @return: proporcion que representa el progreso
     */
    public float loadNextAsset() {
        gdxAssets.update();
        return gdxAssets.getProgress();
    }

    /**
     * Termina de cargar el resto de assets propios del juego
     */
    public void loadRemaining() {
        textures = new Textures();
        grhs = new Grhs();
        bodies = new Bodies();
        heads = new PartChar(Tipo.HEAD);
        helmets = new PartChar(Tipo.HELMET);
        weapons = new PartChar(Tipo.WEAPON);
        shields = new PartChar(Tipo.SHIELD);
        fxs = new Fxs();
    }

    public Textures getTextures() { return textures; }
    public Grhs getGrhs() { return grhs; }
    public Audio getAudio() { return audio; }
    public Bodies getBodies() { return bodies; }
    public PartChar getHeads() { return heads; }
    public PartChar getHelmets() { return helmets; }
    public PartChar getWeapons() { return weapons; }
    public PartChar getShields() { return shields; }
    public Fxs getFxs() { return fxs; }
    public Map getMapa() {  return mapa; }

    public void changeMap(int num) { this.mapa = new Map(num); }

    /**
     * Elimina de memoria lo que no se elimina por defecto
     */
    public void dispose() {
        gdxAssets.dispose();
        textures.dispose();
        audio.dispose();
    }
}
