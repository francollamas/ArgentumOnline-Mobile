package main;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

public class Fonts {

    private ArrayList<Font> font;


    public Fonts() {

        File f = new File("fonts.ind");
        if (f.exists()) {
            try {
                RandomAccessFile fonts = new RandomAccessFile(f, "r");

                font = new ArrayList<Font>();
                int quantFonts = Util.littleToBig_Int(fonts.readInt());

                for (int i = 0; i < quantFonts; i++) {
                    font.add(new Font());
                    // borro esto porque no se leer strings en VB6
                    //font.get(i).setName(fonts.readUTF());
                    font.get(i).setName("Font");
                    font.get(i).setTex(Util.littleToBig_Int(fonts.readInt()));
                    font.get(i).setOffset(Util.littleToBig_Int(fonts.readInt()));
                    for (int j = 0; j < font.get(i).getChar().length; j++) {
                        font.get(i).getChar()[j].setSrcX(Util.littleToBig_Int(fonts.readInt()));
                        font.get(i).getChar()[j].setSrcY(Util.littleToBig_Int(fonts.readInt()));
                        font.get(i).getChar()[j].setSrcWidth(Util.littleToBig_Int(fonts.readInt()));
                        font.get(i).getChar()[j].setSrcHeight(Util.littleToBig_Int(fonts.readInt()));
                    }
                }
                fonts.close();

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        else {
            try {
                RandomAccessFile fonts = new RandomAccessFile(f, "rw");

                font = new ArrayList<Font>();
                fonts.writeInt(Util.bigToLittle_Int(0));
                fonts.close();
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public ArrayList<Font> getFont() {
        return font;
    }

}
