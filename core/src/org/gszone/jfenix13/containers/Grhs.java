package org.gszone.jfenix13.containers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import org.gszone.jfenix13.objects.GrhData;
import org.gszone.jfenix13.utils.BytesReader;
import org.gszone.jfenix13.utils.NotEnoughDataException;

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
        catch (NotEnoughDataException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Carga todos los GRHs
     */
    private void load() throws NotEnoughDataException {
        int index;
        int cantFrames;
        short frame;
        GrhData grhData;

        FileHandle fh = Gdx.files.internal(getGrhsIndDir());
        BytesReader r = new BytesReader(fh.readBytes(), true);

        // Omite los primeros bytes que no interesan
        r.skipBytes(4);

        // Lee la cantidad de grhs y le da el tamaño al array
        grhsData = new GrhData[r.readInt()];

        // Mientras haya datos para leer...
        while (r.getAvailable() > 0)  {
            // Número de grh
            index = r.readInt();
            grhData = new GrhData();
            setGrhData(index, grhData);

            // Cantidad de frames
            cantFrames = r.readShort();

            // Si es un grh simple
            if (cantFrames == 1) {
                if (index > 0 && index <= grhsData.length)
                    // Agrega el único frame
                    grhData.addFrame((short)index);

                // Propiedades del Grh
                grhData.setFileNum(r.readInt());
                grhData.getRect().setX1(r.readShort());
                grhData.getRect().setY1(r.readShort());
                grhData.getRect().setWidth(r.readShort());
                grhData.getRect().setHeight(r.readShort());
            }

            //Si es una animación
            else {
                // Agrega todos los frames, indicándole cada número de Grh simple.
                for (int i = 0; i < cantFrames; i++) {
                    frame = r.readShort();
                    if (frame > 0 && frame <= grhsData.length)
                        grhData.addFrame(frame);
                }

                // Propiedades de la animación
                grhData.setSpeed(r.readFloat() / 45f);

                grhData.getRect().setWidth(getGrhData(grhData.getFrame((short) 0)).getRect().getWidth());
                grhData.getRect().setHeight(getGrhData(grhData.getFrame((short) 0)).getRect().getHeight());
            }
        }
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