package org.gszone.jfenix13.containers;

import org.gszone.jfenix13.general.General;
import org.gszone.jfenix13.Main;
import org.gszone.jfenix13.objects.Char;
import org.gszone.jfenix13.objects.MapTile;
import org.gszone.jfenix13.utils.Position;
import org.gszone.jfenix13.utils.Rect;
import org.gszone.jfenix13.general.General.Direccion;

/**
 * Contiene a los usuarios que el cliente va mostrando
 *
 * chars: array de chars
 * numChars: cantidad de chars
 * lastChar: index del último char
 */
public class Chars {
    private Char[] chars;
    private int numChars;
    private int lastChar;

    public Chars() {
        chars = new Char[10000];
    }

    /**
     * Mueve a un PJ o NPC hacia una dirección (cualquiera excepto al que estamos manejando)
     */
    public void moveChar(int index, int x, int y) {
        Char c = chars[index];
        Main.getInstance().getAssets().getMapa().getTile((int)c.getPos().getX(), (int)c.getPos().getY()).setCharIndex(0);
        Main.getInstance().getAssets().getMapa().getTile(x, y).setCharIndex(index);

        int xDif = x - (int)c.getPos().getX();
        int yDif = y - (int)c.getPos().getY();

        if (xDif != 0) xDif /= Math.abs(xDif);
        if (yDif != 0) yDif /= Math.abs(yDif);

        Direccion dir = Position.posToDir(new Position(xDif, yDif));
        if (dir == null) return;

        c.getPos().setX(x);
        c.getPos().setY(y);

        c.setHeading(dir);
        c.getMoveOffset().setX(-1 * Main.getInstance().getGeneral().getTilePixelWidth() * xDif);
        c.getMoveOffset().setY(-1 * Main.getInstance().getGeneral().getTilePixelHeight() * yDif);

        c.getMoveDir().setX(xDif);
        c.getMoveDir().setY(yDif);

        c.setMoving(true);

        Rect area = Main.getInstance().getGameData().getCurrentUser().getArea();
        if (!area.isPointIn(new Position(x, y)))
            deleteChar(index);
    }

    /**
     * Mueve al char actual hacia una dirección
     */
    public void moveChar(int index, General.Direccion dir) {
        Char c = chars[index];
        Position relPos = Position.dirToPos(dir);
        Position absPos = c.getPos().getSuma(relPos);

        Main.getInstance().getAssets().getMapa().getTile((int)c.getPos().getX(), (int)c.getPos().getY()).setCharIndex(0);
        Main.getInstance().getAssets().getMapa().getTile((int)absPos.getX(), (int)absPos.getY()).setCharIndex(index);
        c.setPos(absPos);

        c.setHeading(dir);
        c.getMoveOffset().setX(-1 * Main.getInstance().getGeneral().getTilePixelWidth() * relPos.getX());
        c.getMoveOffset().setY(-1 * Main.getInstance().getGeneral().getTilePixelHeight() * relPos.getY());

        c.setMoveDir(relPos);
        c.setMoving(true);

        /* TODO: considerar sacar este chequeo, parece estar de más, xq esto mueve solo al user que estamos usando
         por lo que este trozo no tiene sentido, no se cumple nunca supuestamente */
        Rect area = Main.getInstance().getGameData().getCurrentUser().getArea();
        if (!area.isPointIn(absPos))
            if (index != Main.getInstance().getGameData().getCurrentUser().getIndexInServer())
                deleteChar(index);
    }

    /**
     * Borra un char según su index
     */
    public void deleteChar(int index) {
        Char c = chars[index];
        c.setActive(false);

        // TODO: lastChar no está actualizándose correctamente!!!
        // Actualizo el valor que indica el index del último char.
        if (index == lastChar) {
            while (lastChar > 0 && ((chars[lastChar] != null && !chars[lastChar].isActive()) || chars[lastChar] == null))
                lastChar--;
        }

        // Lo saco del mapa
        MapTile tile = Main.getInstance().getAssets().getMapa().getTile((int)c.getPos().getX(), (int)c.getPos().getY());
        tile.setCharIndex(0);

        // TODO: borrar diálogos del pj en cuestión...

        chars[index] = null;
        numChars--;
    }

    /**
     * Borra todos los chars existentes.
     */
    public void clear() {
        int lastChar = Main.getInstance().getGameData().getChars().getLastChar();
        for (int i = 1; i <=lastChar; i++) {
            if (Main.getInstance().getGameData().getChars().getChar(i) != null)
                deleteChar(i);
        }
    }

    /**
     * Se asegura de que todos los chars del mapa realmente se estén mostrando.
     * Es necesario ya que por lag el cliente podría hacer que se pisen dos personajes, y uno de los dos
     * no se actualizaría hasta que no se mueva.
     */
    public void refresh() {
        int lastChar = Main.getInstance().getGameData().getChars().getLastChar();
        for (int i = 1; i <=lastChar; i++) {
            Char c = Main.getInstance().getGameData().getChars().getChar(i);
            if (c != null && c.isActive()) {
                Main.getInstance().getAssets().getMapa().getTile((int)c.getPos().getX(), (int)c.getPos().getY()).setCharIndex(i);
            }
        }
    }

    /**
     * Obtiene un char, si no existe devuelve null
     */
    public Char getChar(int index) {
        return getChar(index, false);
    }

    /**
     * Obtiene un char
     * @param index: número asociado al char
     * @param crear: indica si debe crear un nuevo char en caso que no exista
     * @return: char
     */
    public Char getChar(int index, boolean crear) {
        /* Si quiero obtener un char que no existe, lo creo (ya que se va a estar usando).
        Con ésto me aseguro de crear solo los chars necesarios. */
        if (chars[index] == null && crear)
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
