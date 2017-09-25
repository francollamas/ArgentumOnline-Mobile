package org.gszone.jfenix13.objects;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import org.gszone.jfenix13.graphics.Drawer;
import org.gszone.jfenix13.utils.Rect;

/**
 * Contiene la información de un solo GrhData
 *
 * fileNum: número de textura
 * rect: contiene las coordenadas de los vértices del GrhData en la textura
 * frames: colección de índices de GrhData correspondientes a una animación
 * speed: velocidad de la animación
 * tr: porcion de textura que lo representa (solo para Grhs no animados)
 */

public class GrhData {
    private int fileNum;
    private Rect rect;
    private Array<Short> frames;
    private float speed;
    private TextureRegion tr;

    public GrhData() {
        frames = new Array();
        rect = new Rect();
        tr = null;
    }

    public int getFileNum() {
        return fileNum;
    }

    public void setFileNum(int fileNum) {
        if (fileNum >= 0)
            this.fileNum = fileNum;
    }

    public Rect getRect() {
        return rect;
    }

    public Short getFrame(short index) {
        if (index < frames.size)
            return frames.get(index);
        return -1;
    }

    public int getCantFrames() {
        return frames.size;
    }

    public void addFrame(short num) {
        if (num >= 0)
            frames.add(num);
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        if (speed >= 0)
            this.speed = speed;
    }

    public TextureRegion getTR() {
        if (tr == null)
            tr = Drawer.getTextureRegion(Drawer.TipoTex.PRINCIPAL, fileNum, rect);
        return tr;
    }

}
