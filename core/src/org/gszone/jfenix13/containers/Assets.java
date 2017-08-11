package org.gszone.jfenix13.containers;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import org.gszone.jfenix13.general.Main;
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
 *
 * mapa: mapa actual
 */

public class Assets {
    private AssetManager gdxAssets;
    private Audio audio;
    private Grhs grhs;
    private Fonts fonts;
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

        preloadGdxAssets();

        // DtCarga desde el comienzo hasta la GUI. El resto va acompañado con la pantalla de carga.
        gdxAssets.finishLoadingAsset(getAtlasGuiDir());
    }

    public AssetManager getGDXAssets() { return gdxAssets; }

    /**
     * Precarga los assets (indica cuáles y de que tipo son los assets a cargar)
     */
    private void preloadGdxAssets() {
        // Skin de la GUI
        gdxAssets.load(getSkinFlat(), Skin.class);

        // Atlas de las fuentes
        gdxAssets.load(getAtlasFontTexDir(), TextureAtlas.class);

        // Atlas de la GUI del juego
        gdxAssets.load(getAtlasGuiDir(), TextureAtlas.class);

        // Sonidos
        String[] soundDirs = Audio.getSoundDirs();
        for(String dir: soundDirs) {
            gdxAssets.load(dir, Sound.class);
        }

        // Atlas de texturas normales
        gdxAssets.load(getAtlasNormTexDir(), TextureAtlas.class);

        // Atlas de texturas grandes
        gdxAssets.load(getAtlasBigTexDir(), TextureAtlas.class);
    }


    /**
     * DtCarga al resto de los assets que sabe cargar libGDX
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
        grhs = new Grhs();
        fonts = new Fonts();
        bodies = new Bodies();
        heads = new PartChar(Tipo.HEAD);
        helmets = new PartChar(Tipo.HELMET);
        weapons = new PartChar(Tipo.WEAPON);
        shields = new PartChar(Tipo.SHIELD);
        fxs = new Fxs();
        mapa = new Map(1);
    }

    public Grhs getGrhs() { return grhs; }

    public Fonts getFonts() { return fonts; }

    public Audio getAudio() { return audio; }

    public Bodies getBodies() { return bodies; }

    public PartChar getHeads() { return heads; }

    public PartChar getHelmets() { return helmets; }

    public PartChar getWeapons() { return weapons; }

    public PartChar getShields() { return shields; }

    public Fxs getFxs() { return fxs; }

    public Map getMapa() {  return mapa; }

    public void setMapa(Map mapa) { this.mapa = mapa; }

    /**
     * Elimina de memoria lo que no se elimina por defecto
     */
    public void dispose() {
        gdxAssets.dispose();
        audio.dispose();
    }
}
