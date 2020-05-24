package org.gszone.jfenix13.containers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import org.gszone.jfenix13.general.Config;
import org.gszone.jfenix13.objects.Body;
import org.gszone.jfenix13.utils.BytesReader;
import org.gszone.jfenix13.utils.NotEnoughDataException;

import java.io.IOException;

import static org.gszone.jfenix13.general.FileNames.*;

/**
 * Manejador de cuerpos
 *
 * bodies: conjunto de cuerpos
 */
public class Bodies {
    private Body[] bodies;

    public Bodies() {
        try {
            load();
        }
        catch (NotEnoughDataException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Carga todos los Bodies
     */
    private void load() throws NotEnoughDataException {

        FileHandle fh = Gdx.files.internal(getBodiesIndDir());
        BytesReader r = new BytesReader(fh.readBytes(), true);

        // Omite los primeros bytes que no interesan
        r.skipBytes(263);

        int cant = r.readShort();
        bodies = new Body[cant];

        for (int i = 0; i < cant; i++) {
            Body body = new Body();

            // Leo los valores de los Grh (índices)
            short[] grhIndex = new short[Config.Direccion.values().length];
            for (int j = 0; j < grhIndex.length; j++) {
                grhIndex[j] = r.readShort();
            }
            short x = r.readShort();
            short y = r.readShort();

            // Los seteo en el cuerpo
            if (grhIndex[0] > 0) {
                for (Config.Direccion dir : Config.Direccion.values()) {
                    body.setGrhIndex(dir, grhIndex[dir.ordinal()]);
                }
                body.setHeadOffsetX(x);
                body.setHeadOffsetY(y);
            }

            bodies[i] = body;
        }
    }

    public Body[] getBodies() { return bodies; }

    /**
     * Obtiene un cuerpo (verificar a la hora de usarlo que no sea null)
     */
    public Body getBody(int index) {
        if (index - 1 < 0 || index - 1 >= bodies.length) return null;
        return bodies[index - 1];
    }

}