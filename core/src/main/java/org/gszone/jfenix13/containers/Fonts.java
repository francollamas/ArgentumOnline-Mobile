package org.gszone.jfenix13.containers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import org.gszone.jfenix13.objects.Font;
import org.gszone.jfenix13.utils.BytesReader;

import java.io.IOException;

import static org.gszone.jfenix13.general.FileNames.*;

/**
 * Manejador de Fuentes
 *
 * fonts: conjunto de fuentes
 */
public class Fonts {
    private Font[] fonts;

    public Fonts() {
        try {
            load();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Carga todas las Fuentes
     * @throws IOException
     */
    public void load() throws IOException {

        FileHandle fh = Gdx.files.internal(getFontsIndicesDir());
        BytesReader r = new BytesReader(fh.readBytes());

        fonts = new Font[r.readInt()];
        for (int i = 0; i < fonts.length; i++) {
            fonts[i] = new Font();
            fonts[i].setName(r.readString());
            fonts[i].setTex(r.readInt());
            fonts[i].setOffset(r.readInt());
            for (int j = 0; j < fonts[i].getChars().length; j++) {
                fonts[i].getChars()[j].setX1(r.readInt());
                fonts[i].getChars()[j].setY1(r.readInt());
                fonts[i].getChars()[j].setWidth(r.readInt());
                fonts[i].getChars()[j].setHeight(r.readInt());
                fonts[i].getChars()[j].updateTR(i);
            }
        }
    }

    public Font[] getFonts() {
        return fonts;
    }

}