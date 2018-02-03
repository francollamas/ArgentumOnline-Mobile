package org.gszone.jfenix13.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.TimeUtils;

import static org.gszone.jfenix13.general.FileNames.*;

/**
 * Información de una textua
 *
 * tex: textura
 * num: número de gráfico que representa a la textura
 * lastAccess: ultimo momento en que se solicitó esta textura (en milisegundos)
 */
public class TextureData {
    private int num;
    private long lastAccess;
    private Texture tex;

    public TextureData(int num) {
        this.num = num;
        tex = new Texture(Gdx.files.internal(DIR_TEXTURAS + "/" + num + ".png"));
    }

    public int getNum() {
        return num;
    }

    public long getLastAccess() {
        return lastAccess;
    }

    public Texture getTex() {
        lastAccess = TimeUtils.millis();
        return tex;
    }

    public void setTex(Texture tex) {
        this.tex = tex;
    }

    public void dispose() {
        tex.dispose();
    }
}
