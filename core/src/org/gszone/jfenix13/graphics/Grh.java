package org.gszone.jfenix13.graphics;

import org.gszone.jfenix13.general.Main;
import org.gszone.jfenix13.objects.GrhData;

/**
 * Contiene la información de una animación
 *
 * index: número que lo identifica
 * frame: en caso de ser animación, es el número de fotograma de un momento determinado.
 * speed: es la velociad que tiene la animación (en caso de ser animación).
 * started: indica si se tiene que animar o no. (independientemente de que sea animación o no).
 * loops: cantidad de veces que se repite una animación (en caso de serla).
 */
public class Grh {
    public static final int INF_LOOPS = -1;

    private short index;
    private float frame;
    private float speed;
    private byte started;
    private short loops;

    public Grh(int index) {
        this((short)index, 2);
    }

    public Grh(int index, int started) {
        if (index == 0) return;
        GrhData grhData = Main.getInstance().getAssets().getGrhs().getGrhData(index);

        if (grhData == null) return;
        this.index = (short)index;

        if (started == 2) {
            if (grhData.getCantFrames() > 1)
                this.started = 1;
            else
                this.started = 0;
        }
        else {
            if (grhData.getCantFrames() == 1)
                this.started = 0;
        }

        if (this.started == 1)
            this.loops = INF_LOOPS;
        else
            this.loops = 0;

        this.frame = 1;
        this.speed = grhData.getSpeed();
    }

    public short getIndex() {
        return index;
    }

    public void setIndex(short index) {
        this.index = index;
    }

    public float getFrame() {
        return frame;
    }

    public void setFrame(float frame) {
        this.frame = frame;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public byte getStarted() {
        return started;
    }

    public void setStarted(byte started) {
        this.started = started;
    }

    public short getLoops() {
        return loops;
    }

    public void setLoops(short loops) {
        this.loops = loops;
    }
}
