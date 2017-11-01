package org.gszone.jfenix13.containers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import org.gszone.jfenix13.objects.Fx;
import org.gszone.jfenix13.utils.BytesReader;
import java.io.IOException;

import static org.gszone.jfenix13.general.FileNames.*;

/**
 * Manejador de Fxs
 *
 * fxs: array de Fxs
 */
public class Fxs {
    private Fx[] fxs;

    public Fxs() {
        load();
    }

    /**
     * Carga todos los Fxs
     */
    private void load() {

        FileHandle fh = Gdx.files.internal(getFxsIndDir());
        BytesReader r = new BytesReader(fh.readBytes(), true);

        // Omite los primeros bytes que no interesan
        r.skipBytes(263);

        int cant = r.readShort();
        fxs = new Fx[cant];

        for (int i = 0; i < cant; i++) {
            Fx fx = new Fx();

            fx.setGrhIndex(r.readShort());
            fx.setOffsetX(r.readShort());
            fx.setOffsetY(r.readShort());

            fxs[i] = fx;
        }
    }

    public Fx[] getFxs() { return fxs; }

    /**
     * Obtiene un fx (verificar a la hora de usarlo que no sea null)
     */
    public Fx getFx(int index) {
        if (index - 1 < 0 || index - 1 >= fxs.length) return null;
        return fxs[index - 1];
    }
}
