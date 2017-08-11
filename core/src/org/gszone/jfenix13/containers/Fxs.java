package org.gszone.jfenix13.containers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import org.gszone.jfenix13.objects.Fx;

import java.io.DataInputStream;
import java.io.IOException;

import static org.gszone.jfenix13.general.FileNames.*;
import static org.gszone.jfenix13.utils.Bytes.*;

/**
 * Manejador de Fxs
 */
public class Fxs {
    private Fx[] fxs;

    public Fxs() {
        try {
            load();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * DtCarga todos los Fxs
     * @throws IOException
     */
    private void load() throws IOException {

        FileHandle fh = Gdx.files.internal(getFxsIndDir());
        DataInputStream dis = new DataInputStream(fh.read());

        // Omite los primeros bytes que no interesan
        dis.skipBytes(263);

        int cant = leReadShort(dis);
        fxs = new Fx[cant];

        for (int i = 0; i < cant; i++) {
            Fx fx = new Fx();

            fx.setGrhIndex(leReadShort(dis));
            fx.setOffsetX(leReadShort(dis));
            fx.setOffsetY(leReadShort(dis));

            fxs[i] = fx;
        }
        dis.close();
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
