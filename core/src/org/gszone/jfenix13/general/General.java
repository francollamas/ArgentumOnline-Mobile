package org.gszone.jfenix13.general;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;

import static com.badlogic.gdx.Application.ApplicationType.*;

public class General {
    public enum Direccion {NORTE, ESTE, SUR, OESTE}
    private int scrWidth, scrHeight;
    private int windowsTileWidth, windowsTileHeight;
    private int tilePixelWidth, tilePixelHeight;
    private int TileBufferSizeX, TileBufferSizeY;
    private int ScrollPixelsPerFrame;
    private int BaseSpeed;

    public General() {
        tilePixelWidth = 32;
        tilePixelHeight = 32;
        ScrollPixelsPerFrame = 8;
        BaseSpeed = 17;

        if (Gdx.app.getType() == Desktop) {
            scrWidth = Gdx.graphics.getWidth();
            scrHeight = Gdx.graphics.getHeight();
            windowsTileWidth = 23;
            windowsTileHeight = 17;
            TileBufferSizeX = 9;
            TileBufferSizeY = 9;
        }
        else {
            // Escala 16:9
            scrWidth = 640;
            scrHeight = 360;

            windowsTileWidth = 13;
            windowsTileHeight = 11;
            TileBufferSizeX = 9;
            TileBufferSizeY = 9;

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
        return TileBufferSizeX;
    }

    public int getTileBufferSizeY() {
        return TileBufferSizeY;
    }

    public int getScrollPixelsPerFrame() {
        return ScrollPixelsPerFrame;
    }

    public int getBaseSpeed() {
        return BaseSpeed;
    }
}
