package org.gszone.jfenix13.containers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import org.gszone.jfenix13.objects.GrhData;

import java.io.DataInputStream;
import java.io.IOException;

import static org.gszone.jfenix13.utils.Bytes.*;
import static org.gszone.jfenix13.general.FileNames.*;

/**
 * Manejador de Grhs
 *
 * grhsData: conjunto de todos los distintos grhs.
 */
public class Grhs {
    private GrhData[] grhsData;

    public Grhs() {
        try {
            load();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * DtCarga todos los GRHs
     * @throws IOException
     */
    private void load() throws IOException {
        int index;
        int cantFrames;
        short frame;
        GrhData grhData;

        FileHandle fh = Gdx.files.internal(getGrhsIndDir());
        DataInputStream dis = new DataInputStream(fh.read());

        // Omite los primeros bytes que no interesan
        dis.skipBytes(4);

        // Lee la cantidad de grhs y le da el tamaño al array
        grhsData = new GrhData[leReadInt(dis)];

        // Mientras haya datos para leer...
        while (dis.available() > 0) {
            // Número de grh
            index = leReadInt(dis);
            grhData = new GrhData();
            setGrhData(index, grhData);

            // Cantidad de frames
            cantFrames = leReadShort(dis);

            // Si es un grh simple
            if (cantFrames == 1) {
                if (index > 0 && index <= grhsData.length)
                    // Agrega el único frame
                    grhData.addFrame((short)index);

                // Propiedades del Grh
                grhData.setFileNum(leReadInt(dis));
                grhData.getRect().setX1(leReadShort(dis));
                grhData.getRect().setY1(leReadShort(dis));
                grhData.getRect().setWidth(leReadShort(dis));
                grhData.getRect().setHeight(leReadShort(dis));
            }

            //Si es una animación
            else {
                // Agrega todos los frames, indicándole cada número de Grh simple.
                for (int i = 0; i < cantFrames; i++) {
                    frame = leReadShort(dis);
                    if (frame > 0 && frame <= grhsData.length)
                        grhData.addFrame(frame);
                }

                // Propiedades de la animación
                grhData.setSpeed(leReadFloat(dis) / 45f);
                grhData.getRect().setWidth(getGrhData(grhData.getFrame((short)0)).getRect().getWidth());
                grhData.getRect().setHeight(getGrhData(grhData.getFrame((short)0)).getRect().getHeight());
            }
        }
        dis.close();
    }

    /**
     * Obtiene un GrhData en particular
     */
    public GrhData getGrhData(int index) {
        if (index - 1 < 0 || index - 1 >= grhsData.length) return null;
        return grhsData[index - 1];
    }

    /**
     * Cambia un GrhData por otro.
     * @param index: posición en el array
     * @param grhData
     */
    public void setGrhData(int index, GrhData grhData) {
        grhsData[index - 1] = grhData;
    }

}