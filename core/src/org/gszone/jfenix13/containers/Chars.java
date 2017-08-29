package org.gszone.jfenix13.containers;

import org.gszone.jfenix13.general.General;
import org.gszone.jfenix13.general.Main;
import org.gszone.jfenix13.objects.Char;
import org.gszone.jfenix13.objects.MapTile;
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

    public void moveChar(int index, int x, int y) {
        General.Direccion dir = null;

        Char c = chars[index];
        Main.getInstance().getAssets().getMapa().getTile(x, y).setCharIndex(index);
        Main.getInstance().getAssets().getMapa().getTile((int)c.getPos().getX(), (int)c.getPos().getY()).setCharIndex(0);

        int xDif = x - (int)c.getPos().getX();
        int yDif = y - (int)c.getPos().getY();

        if (xDif != 0) xDif /= Math.abs(xDif);
        if (yDif != 0) yDif /= Math.abs(yDif);

        // Determinamos dirección.
        if (xDif == 1) dir = General.Direccion.ESTE;
        else if (xDif == -1) dir = General.Direccion.OESTE;
        else if (yDif == 1) dir = General.Direccion.SUR;
        else if (yDif == -1) dir = General.Direccion.NORTE;

        // Si no se movió, no se hace nada...
        if (dir == null) return;


        c.getPos().setX(x);
        c.getPos().setY(y);

        c.setHeading(dir);
        c.getMoveOffset().setX(-1 * Main.getInstance().getGeneral().getTilePixelWidth() * xDif);
        c.getMoveOffset().setY(-1 * Main.getInstance().getGeneral().getTilePixelHeight() * yDif);

        c.getMoveDir().setX(xDif);
        c.getMoveDir().setY(yDif);

        c.setMoving(true);

    }

    /**
     * Mueve al char hacia una dirección
     */
    public void moveChar(int index, General.Direccion dir) {
        Char c = chars[index];
        Position relPos = Position.dirToPos(dir);
        Position absPos = c.getPos().getSuma(relPos);

        Main.getInstance().getAssets().getMapa().getTile((int)absPos.getX(), (int)absPos.getY()).setCharIndex(index);
        Main.getInstance().getAssets().getMapa().getTile((int)c.getPos().getX(), (int)c.getPos().getY()).setCharIndex(0);
        c.setPos(absPos);

        c.setHeading(dir);
        c.getMoveOffset().setX(-1 * Main.getInstance().getGeneral().getTilePixelWidth() * relPos.getX());
        c.getMoveOffset().setY(-1 * Main.getInstance().getGeneral().getTilePixelHeight() * relPos.getY());

        c.setMoveDir(relPos);
        c.setMoving(true);
    }

    /**
     * Borra un char según su index
     */
    public void deleteChar(int index) {
        Char c = chars[index];
        c.setActive(false);

        // Actualizo el valor que indica el index del último char.
        if (index == lastChar) {
            while (chars[lastChar] != null && !chars[lastChar].isActive()) {
                lastChar--;
                if (lastChar == 0) break;
            }
        }

        // Lo saco del mapa
        MapTile tile = Main.getInstance().getAssets().getMapa().getTile((int)c.getPos().getX(), (int)c.getPos().getY());
        tile.setCharIndex(0);

        // TODO: borrar diálogos del pj en cuestión...

        chars[index] = null;

        numChars--;

    }

    public Char getChar(int index) {
        /* Si quiero obtener un char que no existe, lo creo (ya que se va a estar usando).
        Con ésto me aseguro de crear solo los chars necesarios. */
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
