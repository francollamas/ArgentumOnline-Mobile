package org.gszone.jfenix13.containers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import org.gszone.jfenix13.general.General;
import org.gszone.jfenix13.graphics.Grh;
import org.gszone.jfenix13.objects.Body;

import java.io.DataInputStream;
import java.io.IOException;

import static org.gszone.jfenix13.general.FileNames.*;
import static org.gszone.jfenix13.utils.Bytes.*;

/**
 * Manejador de cuerpos
 *
 */
public class Bodies {
    private Body[] bodies;

    public Bodies() {
        try {
            load();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Carga todos los Bodies
     * @throws IOException
     */
    private void load() throws IOException {

        FileHandle fh = Gdx.files.internal(getBodiesIndDir());
        DataInputStream dis = new DataInputStream(fh.read());

        // Omite los primeros bytes que no interesan
        dis.skipBytes(263);

        int cant = leReadShort(dis);
        bodies = new Body[cant];

        for (int i = 0; i < cant; i++) {
            Body body = new Body();

            // Leo los valores de los Grh (índices)
            short[] grhIndex = new short[General.Direccion.values().length];
            for (int j = 0; i < grhIndex.length; i++)
                grhIndex[j] = leReadShort(dis);
            short x = leReadShort(dis);
            short y = leReadShort(dis);

            // Los seteo en el cuerpo
            if (grhIndex[0] > 0) {
                for (General.Direccion dir : General.Direccion.values())
                    body.setGrh(dir, new Grh(grhIndex[dir.ordinal()], 0));
                body.setHeadOffsetX(x);
                body.setHeadOffsetY(y);
            }

            bodies[i] = body;
        }
        dis.close();
    }

}