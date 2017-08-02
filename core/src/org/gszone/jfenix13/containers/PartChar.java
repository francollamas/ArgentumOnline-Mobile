package org.gszone.jfenix13.containers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import org.gszone.jfenix13.general.General;
import org.gszone.jfenix13.graphics.Grh;
import org.gszone.jfenix13.objects.GrhDir;

import java.io.DataInputStream;
import java.io.IOException;

import static org.gszone.jfenix13.general.FileNames.*;
import static org.gszone.jfenix13.utils.Bytes.*;

/**
 * Manejador de una parte del personaje.
 *
 * grhDirs: conjunto de muchos grupos de grhs (según dirección).
 * tipo: tipo de agrupación (cabezas, cascos, armas o escudos)
 */
public class PartChar {
    public enum Tipo {HEAD, HELMET, WEAPON, SHIELD}

    private GrhDir[] grhDirs;
    private Tipo tipo;

    public PartChar(Tipo tipo) {
        try {
            this.tipo = tipo;
            load();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Carga todos los PartChar
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
            case WEAPON:
                d = getWeaponsIndDir();
                break;
            case SHIELD:
                d = getShieldsIndDir();
                break;
        }

        FileHandle fh = Gdx.files.internal(d);
        DataInputStream dis = new DataInputStream(fh.read());

        // Omite los primeros bytes que no interesan
        dis.skipBytes(263);

        int cant = leReadShort(dis);
        grhDirs = new GrhDir[cant];
        for (int i = 0; i < cant; i++) {
            GrhDir grhdir = new GrhDir();

            // Leo los valores de los Grh (índices)
            short[] grhIndex = new short[General.Direccion.values().length];
            for (int j = 0; j < grhIndex.length; j++) {
                grhIndex[j] = leReadShort(dis);
            }

            // Los seteo en la parte del personaje
            if (grhIndex[0] > 0 || tipo == Tipo.WEAPON || tipo == Tipo.SHIELD) {
                for (General.Direccion dir : General.Direccion.values())
                    grhdir.setGrh(dir, new Grh(grhIndex[dir.ordinal()], 0));
            }

            grhDirs[i] = grhdir;
        }
        dis.close();
    }

    public GrhDir[] getGrhDirs() { return grhDirs; }

    /**
     * Obtiene un objeto GrhDir (verificar a la hora de usarlo que no sea null)
     * @param index: número de cabeza, casco, arma o escudo
     */
    public GrhDir getGrhDir(int index) {
        if (index - 1 < 0 || index - 1 >= grhDirs.length) return null;
        return grhDirs[index - 1];
    }
}
