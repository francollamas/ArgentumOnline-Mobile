package org.gszone.jfenix13.containers;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import org.gszone.jfenix13.objects.Map;
import static org.gszone.jfenix13.containers.Heads.*;

import static org.gszone.jfenix13.general.FileNames.*;

/**
 * Contiene todos los objetos cargados desde los assets
 *
 * gdxAssets: manejador de assets que libGDX sabe manejar (atlas, texturas, musica, sonidos, etc)
 * los demas corresponden a archivos de datos propios del juego.
 * grhs: manejador de grhs
 * fonts: manejador de fuentes
 * bodies: manejador de cuerpos
 * heads: manejador de cabezas
 * helmets: manejador de cascos
 * fxs: manejador de fxs
 *
 * mapa: mapa actual
 */

public class Assets {
    private AssetManager gdxAssets;
    private Grhs grhs;
    private Fonts fonts;
    private Bodies bodies;
    private Heads heads;
    private Heads helmets;
    private Fxs fxs;

    private Map mapa;

    public Assets() {
        gdxAssets = new AssetManager();
        preloadGdxAssets();

        // Carga desde el comienzo hasta la GUI. El resto va acompañado con la pantalla de carga.
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

        // Atlas de texturas normales
        gdxAssets.load(getAtlasNormTexDir(), TextureAtlas.class);

        // Atlas de texturas grandes
        gdxAssets.load(getAtlasBigTexDir(), TextureAtlas.class);
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
        grhs = new Grhs();
        fonts = new Fonts();
        bodies = new Bodies();
        heads = new Heads(Tipo.HEAD);
        helmets = new Heads(Tipo.HELMET);
        fxs = new Fxs();
        mapa = new Map(1);
    }

    public Grhs getGrhs() { return grhs; }

    public Fonts getFonts() { return fonts; }

    public Bodies getBodies() { return bodies; }

    public Heads getHeads() { return heads; }

    public Heads getHelmets() { return helmets; }

    public Fxs getFxs() { return fxs; }

    public Map getMapa() {  return mapa; }

    public void setMapa(Map mapa) { this.mapa = mapa; }

    /**
     * Elimina de memoria lo que no se elimina por defecto
     */
    public void dispose() {
        gdxAssets.dispose();
    }
}
