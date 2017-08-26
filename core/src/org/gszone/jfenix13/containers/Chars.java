package org.gszone.jfenix13.containers;

import org.gszone.jfenix13.general.General;
import org.gszone.jfenix13.general.Main;
import org.gszone.jfenix13.objects.Char;
import org.gszone.jfenix13.utils.Position;

/**
 * Contiene a los usuarios que el cliente va mostrando
 */
public class Chars {
    private Char[] chars;
    private int numChars;
    private int lastChar;

    public Chars() {
        chars = new Char[10000];
    }

    public void moveChar(int index, General.Direccion dir, Position relPos, Position absPos) {
        Char c = chars[index];

        Main.getInstance().getAssets().getMapa().getTile((int)absPos.getX(), (int)absPos.getY()).setCharIndex(index);
        Main.getInstance().getAssets().getMapa().getTile((int)c.getPos().getX(), (int)c.getPos().getY()).setCharIndex(0);
        c.setPos(absPos);

        c.setHeading(dir);
        c.getMoveOffset().setX(-1 * Main.getInstance().getGeneral().getTilePixelWidth() * relPos.getX());
        c.getMoveOffset().setY(-1 * Main.getInstance().getGeneral().getTilePixelHeight() * relPos.getY());

        c.setMoveDir(relPos);
        c.setMoving(true);
    }

    public Char getChar(int index) {
        // Si quiero obtener un char que no existe, lo creo (ya que se va a estar usando).
        if (chars[index] == null)
            chars[index] = new Char();
        return chars[index];
    }

    public int getNumChars() {
        return numChars;
    }

    public void setNumChars(int numChars) {
        this.numChars = numChars;
    }

    public int getLastChar() {
        return lastChar;
    }

    public void setLastChar(int lastChar) {
        this.lastChar = lastChar;
    }
}
