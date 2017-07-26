package org.gszone.jfenix13.containers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import org.gszone.jfenix13.general.General;
import org.gszone.jfenix13.graphics.Grh;
import org.gszone.jfenix13.objects.Head;

import java.io.DataInputStream;
import java.io.IOException;

import static org.gszone.jfenix13.general.FileNames.*;
import static org.gszone.jfenix13.utils.Bytes.*;

/**
 * Manejador de cabezas
 *
 * heads: cabezas
 * tipo: tipo de cabeza (normal o casco)
 */
public class Heads {
    public enum Tipo {HEAD, HELMET}

    private Head[] heads;
    private Tipo tipo;

    public Heads(Tipo tipo) {
        try {
            this.tipo = tipo;
            load();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Carga todos los Heads
     * @throws IOException
     */
    private void load() throws IOException {

        // Defino de donde obtengo el archivo según lo que sea (cabezas o cascos)
        String d = "";
        switch (tipo) {
            case HEAD:
                d = getHeadsIndDir();
                break;
            case HELMET:
                d = getHelmetsIndDir();
                break;
        }

        FileHandle fh = Gdx.files.internal(d);
        DataInputStream dis = new DataInputStream(fh.read());

        // Omite los primeros bytes que no interesan
        dis.skipBytes(263);

        int cant = leReadShort(dis);
        heads = new Head[cant];

        for (int i = 0; i < cant; i++) {
            Head head = new Head();

            // Leo los valores de los Grh (índices)
            short[] grhIndex = new short[General.Direccion.values().length];
            for (int j = 0; i < grhIndex.length; i++)
                grhIndex[j] = leReadShort(dis);

            // Los seteo en la cabeza
            if (grhIndex[0] > 0) {
                for (General.Direccion dir : General.Direccion.values())
                    head.setGrh(dir, new Grh(grhIndex[dir.ordinal()], 0));
            }

            heads[i] = head;
        }
        dis.close();
    }
}
