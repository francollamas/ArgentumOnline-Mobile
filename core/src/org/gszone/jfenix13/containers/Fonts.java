package org.gszone.jfenix13.containers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import org.gszone.jfenix13.objects.Font;

import java.io.DataInputStream;
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
        DataInputStream dis = new DataInputStream(fh.read());

        fonts = new Font[dis.readInt()];
        for (int i = 0; i < fonts.length; i++) {
            fonts[i] = new Font();
            fonts[i].setName(dis.readUTF());
            fonts[i].setTex(dis.readInt());
            fonts[i].setOffset(dis.readInt());
            for (int j = 0; j < fonts[i].getChars().length; j++) {
                fonts[i].getChars()[j].setX1(dis.readInt());
                fonts[i].getChars()[j].setY1(dis.readInt());
                fonts[i].getChars()[j].setWidth(dis.readInt());
                fonts[i].getChars()[j].setHeight(dis.readInt());
                fonts[i].getChars()[j].updateTR(i);
            }
        }
        dis.close();
    }

    public Font[] getFonts() {
        return fonts;
    }

}