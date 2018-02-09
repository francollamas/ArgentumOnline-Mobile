package org.gszone.jfenix13.general;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import static com.badlogic.gdx.Application.ApplicationType.*;

/**
 * Contiene valores generales del juego, como tamaño de pantalla, velocidades, etc.

 * virtualWidth, virtualHeight: ancho y alto virtual (independiente del tamaño de pantalla del dispositivo)
 * windowsTileWidth, windowsTileHeight: cantidad de tiles que tiene el mundo a lo largo del eje X e Y respectivamente.
 * tilePixelWidth, tilePixelHeight: cantidad de pixeles (a lo ancho y alto) que ocupa un tile.
 * scrollPixelsPerFrame: multiplicador, mientras más grande es el número, mas rápido se mueve el personaje y la pantalla.
 * baseSpeed: velocidad general del juego. De esto depende la velocidad de los Grhs, del movimiento de la pantalla, etc.
 *
 */
public class Config {
    public enum Direccion {NORTE, ESTE, SUR, OESTE}

    private boolean musicActive;
    private float musicVol;
    private boolean soundActive;
    private float soundVol;

    private boolean hideRoofs;

    // Ajustes definidos
    private int virtualWidth, virtualHeight;
    private int windowsTileWidth, windowsTileHeight;
    private int tilePixelWidth, tilePixelHeight;
    private int tileBufferSizeX, tileBufferSizeY;
    private int scrollPixelsPerFrame;
    private float baseSpeed;

    public Config() {

        Preferences pref = getPref();
        musicActive = pref.getBoolean("music", true);
        musicVol = pref.getFloat("musicVol", 1);
        soundActive = pref.getBoolean("sound", true);
        soundVol = pref.getFloat("soundVol", 1);
        hideRoofs = pref.getBoolean("hideRoofs", false);

        setOtherConfigs();
    }

    public void saveConfigFile() {

        Preferences pref = getPref();
        pref.putBoolean("music", musicActive);
        pref.putFloat("musicVol", musicVol);
        pref.putBoolean("sound", soundActive);
        pref.putFloat("soundVol", soundVol);
        pref.putBoolean("hideRoofs", hideRoofs);

        pref.flush();
    }

    /**
     * Setea ajustes totalmente definidos, sin posibilidad a cambio
     */
    private void setOtherConfigs() {
        tilePixelWidth = 32;
        tilePixelHeight = 32;
        scrollPixelsPerFrame = 7;
        //baseSpeed = 18.475f;
        baseSpeed = 17.5f;

        // Configuraciones específicas para Escritorio y Web
        if (Gdx.app.getType() == Desktop || Gdx.app.getType() == WebGL) {
            // Pantalla virtual 4:3
            virtualWidth = 1024;
            virtualHeight = 768;
            windowsTileWidth = 23;
            windowsTileHeight = 17;
            tileBufferSizeX = 11;
            tileBufferSizeY = 11;
        }

        // Configuraciones específicas para Android e iOS
        else {
            // Pantalla virtual 16:9
            virtualWidth = 512;
            virtualHeight = 288;
            windowsTileWidth = 11;
            windowsTileHeight = 9;
            tileBufferSizeX = 9;
            tileBufferSizeY = 9;
        }
    }

    public boolean isMusicActive() {
        return musicActive;
    }

    public float getMusicVol() {
        return musicVol;
    }

    public boolean isSoundActive() {
        return soundActive;
    }

    public float getSoundVol() {
        return soundVol;
    }

    public void setMusicActive(boolean musicActive) {
        this.musicActive = musicActive;
    }

    public void setMusicVol(float musicVol) {
        this.musicVol = musicVol;
    }

    public void setSoundActive(boolean soundActive) {
        this.soundActive = soundActive;
    }

    public void setSoundVol(float soundVol) {
        this.soundVol = soundVol;
    }

    public boolean isHideRoofs() {
        return hideRoofs;
    }

    public void setHideRoofs(boolean hideRoofs) {
        this.hideRoofs = hideRoofs;
    }

    public int getVirtualWidth() {
        return virtualWidth;
    }

    public int getVirtualHeight() {
        return virtualHeight;
    }

    public int getWindowsTileWidth() {
        return windowsTileWidth;
    }

    public int getWindowsTileHeight() {
        return windowsTileHeight;
    }

    public int getTilePixelWidth() {
        return tilePixelWidth;
    }

    public int getTilePixelHeight() {
        return tilePixelHeight;
    }

    public int getTileBufferSizeX() {
        return tileBufferSizeX;
    }

    public int getTileBufferSizeY() {
        return tileBufferSizeY;
    }

    public int getScrollPixelsPerFrame() {
        return scrollPixelsPerFrame;
    }

    public float getBaseSpeed() {
        return baseSpeed;
    }

    public Preferences getPref() { return Gdx.app.getPreferences("jfenix13"); }
}
