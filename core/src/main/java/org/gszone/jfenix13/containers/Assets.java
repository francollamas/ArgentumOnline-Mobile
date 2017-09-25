package org.gszone.jfenix13.containers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.kotcrab.vis.ui.VisUI;
import org.gszone.jfenix13.actors.Controller;
import org.gszone.jfenix13.objects.Map;
import static org.gszone.jfenix13.containers.PartChar.*;

import static org.gszone.jfenix13.general.FileNames.*;
import static com.badlogic.gdx.Application.ApplicationType.*;

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
 * colors: conjunto de colores de personajes
 */

public class Assets {
    private AssetManager gdxAssets;
    private Textures textures;
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
    private Colors colors;

    public Assets() {
        gdxAssets = new AssetManager();
        audio = new Audio();

        VisUI.load();
        preloadGdxAssets();

        // Cargar todos los elementos necesarios para poder visualizar la pantalla de carga.
        //gdxAssets.finishLoadingAsset(getAtlasGralGuiDir());
        gdxAssets.finishLoading(); // TODO: dejar la linea de arriba cuando pueda
    }

    public AssetManager getGDXAssets() { return gdxAssets; }

    /**
     * Precarga los assets (indica cuáles y de que tipo son los assets a cargar)
     */
    private void preloadGdxAssets() {
        // Atlas de las fuentes
        gdxAssets.load(getAtlasFontTexDir(), TextureAtlas.class);

        // Atlas de la GUI del juego (ya sea para MOBILE o DESKTOP)
        if (Gdx.app.getType() == Desktop || Gdx.app.getType() == WebGL)
            gdxAssets.load(getAtlasDtGuiDir(), TextureAtlas.class);
        else
            gdxAssets.load(getAtlasMbGuiDir(), TextureAtlas.class);

        // Atlas de la GUI general para cualquier plataforma
        //gdxAssets.load(getAtlasGralGuiDir(), TextureAtlas.class); // TODO: descomentar esto cuando tenga algun GUI general

        // Sonidos
        // TODO: cargarlos sobre la marcha y liberarlos al terminar c/u.
        /*String[] soundDirs = Audio.getSoundDirs();
        for(String dir: soundDirs) {
            gdxAssets.load(dir, Sound.class);
        }*/
    }


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
        fonts = new Fonts();
        bodies = new Bodies();
        heads = new PartChar(Tipo.HEAD);
        helmets = new PartChar(Tipo.HELMET);
        weapons = new PartChar(Tipo.WEAPON);
        shields = new PartChar(Tipo.SHIELD);
        fxs = new Fxs();
        colors = new Colors();
    }

    public Textures getTextures() { return textures; }
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
    public Colors getColors() { return colors; }

    public void changeMap(int num) { this.mapa = new Map(num); }

    /**
     * Elimina de memoria lo que no se elimina por defecto
     */
    public void dispose() {
        gdxAssets.dispose();
        textures.dispose();
        VisUI.dispose();
        Controller.dispose();
        audio.dispose();
    }
}
