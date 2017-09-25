package org.gszone.jfenix13.general;

import com.badlogic.gdx.Gdx;

import static com.badlogic.gdx.Application.ApplicationType.*;

/**
 * Contiene valores generales del juego, como tamaño de pantalla, velocidades, etc.
 *
 * scrWidth, scrHeight: ancho y alto del juego (virtual, independiente de la pantalla en donde se ejecuta).
 * windowsTileWidth, windowsTileHeight: cantidad de tiles que tiene el mundo a lo largo del eje X e Y respectivamente.
 * tilePixelWidth, tilePixelHeight: cantidad de pixeles (a lo ancho y alto) que ocupa un tile.
 * scrollPixelsPerFrame: multiplicador, mientras más grande es el número, mas rápido se mueve el personaje y la pantalla.
 * baseSpeed: velocidad general del juego. De esto depende la velocidad de los Grhs, del movimiento de la pantalla, etc.
 *
 */
public class General {
    public enum Direccion {NORTE, ESTE, SUR, OESTE}
    private int scrWidth, scrHeight;
    private int windowsTileWidth, windowsTileHeight;
    private int tilePixelWidth, tilePixelHeight;
    private int tileBufferSizeX, tileBufferSizeY;
    private int scrollPixelsPerFrame;
    private int baseSpeed;

    public General() {
        tilePixelWidth = 32;
        tilePixelHeight = 32;
        scrollPixelsPerFrame = 8;
        baseSpeed = 17;

        // Configuraciones específicas para escritorio
        if (Gdx.app.getType() == Desktop || Gdx.app.getType() == WebGL) {
            scrWidth = 800;
            scrHeight = 600;
            windowsTileWidth = 17;
            windowsTileHeight = 13;
            tileBufferSizeX = 9;
            tileBufferSizeY = 9;
        }

        // Configuraciones específicas para Android e iOS
        else {
            // Escala 16:9
            scrWidth = 512; // 576
            scrHeight = 288; // 296

            windowsTileWidth = 11;
            windowsTileHeight = 9;
            tileBufferSizeX = 9;
            tileBufferSizeY = 9;
        }
    }

    public int getScrWidth() {
        return scrWidth;
    }

    public int getScrHeight() {
        return scrHeight;
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

    public int getBaseSpeed() {
        return baseSpeed;
    }
}
