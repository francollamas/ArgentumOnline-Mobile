package org.gszone.jfenix13.containers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import org.gszone.jfenix13.general.Config;
import org.gszone.jfenix13.objects.GrhDir;
import org.gszone.jfenix13.utils.BytesReader;
import org.gszone.jfenix13.utils.NotEnoughDataException;

import java.io.IOException;

import static org.gszone.jfenix13.general.FileNames.*;

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
        this.tipo = tipo;

        try {
            load();
        }
        catch (NotEnoughDataException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Carga todos los PartChar
     */
    private void load() throws NotEnoughDataException {

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
        BytesReader r = new BytesReader(fh.readBytes(), true);

        // Omite los primeros bytes que no interesan
        r.skipBytes(263);

        int cant = r.readShort();
        grhDirs = new GrhDir[cant];
        for (int i = 0; i < cant; i++) {
            GrhDir grhdir = new GrhDir();

            // Leo los valores de los Grh (índices)
            short[] grhIndex = new short[Config.Direccion.values().length];
            for (int j = 0; j < grhIndex.length; j++) {
                grhIndex[j] = r.readShort();
            }

            // Los seteo en la parte del personaje
            if (grhIndex[0] > 0 || tipo == Tipo.WEAPON || tipo == Tipo.SHIELD) {
                for (Config.Direccion dir : Config.Direccion.values())
                    grhdir.setGrhIndex(dir, grhIndex[dir.ordinal()]);
            }

            grhDirs[i] = grhdir;
        }
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
