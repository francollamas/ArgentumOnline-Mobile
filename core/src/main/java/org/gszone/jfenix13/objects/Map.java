package org.gszone.jfenix13.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import org.gszone.jfenix13.general.Config;
import org.gszone.jfenix13.Main;
import org.gszone.jfenix13.graphics.Grh;
import org.gszone.jfenix13.utils.BytesReader;
import org.gszone.jfenix13.utils.Dialogs;
import org.gszone.jfenix13.utils.Position;
import org.gszone.jfenix13.utils.Rect;


import static org.gszone.jfenix13.general.FileNames.*;
import static org.gszone.jfenix13.containers.GameData.*;

/**
 * Es el mapa actual del juego
 *
 * tiles: matriz de tiles, cada tile con sus correspondientes componentes (objetos, capas, luces, partículas, etc)
 * número: número de mapa
 * nombre: nombre del mapa
 * size: rectángulo que representa el tamaño del mapa
 */
public class Map {

    private MapTile[][] tiles;
    private int numero;
    private String nombre;
    private Rect size;

    public Map(int numero) {
        this.numero = numero;
        load();
    }

    /**
     * Carga un mapa
     */
    private void load() {
        FileHandle fh = Gdx.files.internal(getMapDir(numero));
        if (!fh.exists()) {
            // Si no existe, intento desconectar el usuario y aviso...
            Main.getInstance().getGameData().disconnect();
            // TODO: como desconecta el socket, debería desconetar el usuario del servidor, pero hay que chequearlo
            Dialogs.showOKDialog(Main.getInstance().getBundle().get("error"), "Falta el archivo '" + getMapDir(numero) + ".");
            return;
        }
        BytesReader r = new BytesReader(fh.readBytes(), true);

        this.nombre = r.readString();
        // Lee la cabecera (cantidades de cada tipo)
        int cantBloqueados = r.readInt();
        int[] cantTilesEnCapa = {r.readInt(), r.readInt(), r.readInt()};
        int cantTriggers = r.readInt();
        int cantLuces = r.readInt();
        int cantParticulas = r.readInt();

        // Lee el tamaño del mapa
        int xMax = r.readByte();
        int xMin = r.readByte();
        int yMax = r.readByte();
        int yMin = r.readByte();

        // Setea el tamaño del mapa
        size = new Rect();
        size.setX1(xMin);
        size.setY1(yMin);
        size.setX2(xMax);
        size.setY2(yMax);

        int cantXTiles = Math.abs(xMax - xMin) + 1;
        int cantYTiles = Math.abs(yMax - yMin) + 1;

        tiles = new MapTile[cantXTiles][cantYTiles];
        for (int j = 0; j < tiles.length; j++) {
            for (int k = 0; k < tiles[j].length; k++) {
                tiles[j][k] = new MapTile();
            }
        }

        for (int y = 0; y < cantYTiles; y++) {
            for (int x = 0; x < cantXTiles; x++) {
                tiles[x][y].setCapa(0, new Grh(r.readInt()));
            }
        }

        // Asigna bloqueos
        if (cantBloqueados > 0) {
            for (int i = 0; i < cantBloqueados; i++) {
                int x = r.readByte() - 1;
                int y = r.readByte() - 1;
                tiles[x][y].setBlocked(true);
            }
        }

        // Setea las capas en los tiles correspondientes
        for (int c = 0; c < cantTilesEnCapa.length; c++) {
            if (cantTilesEnCapa[c] > 0) {
                for (int i = 0; i < cantTilesEnCapa[c]; i++) {
                    int x = r.readByte() - 1;
                    int y = r.readByte() - 1;
                    tiles[x][y].setCapa(c + 1, new Grh(r.readInt()));
                }
            }
        }

        // Asigna triggers
        if (cantTriggers > 0) {
            for (int i = 0; i < cantTriggers; i++) {
                int x = r.readByte() - 1;
                int y = r.readByte() - 1;
                int trigger = r.readByte();
                tiles[x][y].setTrigger(trigger);
            }
        }

        // Asigna luces
        if (cantLuces > 0) {
            for (int i = 0; i < cantLuces; i++) {
                int x = r.readByte() - 1;
                int y = r.readByte() - 1;
                int rojo = r.readByte();
                int verde = r.readByte();
                int azul = r.readByte();

                /*Light light = new Light();
                light.setColor(rojo, verde, azul, 255);
                light.setRango(leReadByte(dis));

                tiles[x][y].setLight(light);*/
            }
        }

        // Asigna partículas
        if (cantParticulas > 0) {
            for (int i = 0; i < cantParticulas; i++) {
                int x = r.readByte() - 1;
                int y = r.readByte() - 1;
                tiles[x][y].setParticula(r.readInt());
            }
        }

    }

    public MapTile getTile(Position pos) {
        return getTile((int)pos.getX(), (int)pos.getY());
    }

    public MapTile getTile(int x, int y) {
        if (x >= size.getX1() && x <= size.getX2() && y >= size.getY1() && y <= size.getY2())
            return tiles[x - 1][y - 1];
        return null;
    }

    public int getNumero() {
        return numero;
    }

    public Rect getSize() {
        return size;
    }

    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene un rectángulo interno al mapa, que corresponden a los límites de posición del usuario.
     * Esto es para que lo último que se vea del mapa sean los límites, y no bordes negros.
     */
    public Rect getBorderRect() {
        int halfWindowsTileWidth = Main.getInstance().getConfig().getWindowsTileWidth() / 2;
        int halfWindowsTileHeight = Main.getInstance().getConfig().getWindowsTileHeight() / 2;

        Rect r = new Rect();
        r.setX1(size.getX1() + halfWindowsTileWidth);
        r.setY1(size.getY1() + halfWindowsTileHeight);
        r.setX2(size.getX2() - halfWindowsTileWidth);
        r.setY2(size.getY2() - halfWindowsTileHeight);

        return r;
    }


    /**
     * Indica si es posible moverse hacia una dirección estando en una posición determinada.
     */
    public boolean isLegalPos(Position pos, Config.Direccion dir) {
        return isLegalPos(pos.getSuma(Position.dirToPos(dir)));
    }

    public boolean isLegalPos(float x, float y) {
        return isLegalPos(new Position(x, y));
    }

    /**
     * Indica si es posible moverse a un tile final (pos)
     */
    public boolean isLegalPos(Position pos) {
        MapTile tile = getTile(pos);
        MapTile wTile = getTile(Main.getInstance().getGameData().getWorld().getPos());

        if (!getBorderRect().isPointIn(pos)) return false;
        if (tile.isBlocked()) return false;

        User u = Main.getInstance().getGameData().getCurrentUser();
        Char wC = Main.getInstance().getGameData().getChars().getChar(u.getIndexInServer());

        // Si hay un char
        if (tile.getCharIndex() > 0) {

            if (wTile.isBlocked()) return false;

            Char c = Main.getInstance().getGameData().getChars().getChar(tile.getCharIndex());
            // Si el char está muerto
            if (c.getHeadIndex() == MUERTO_HEAD || c.getBodyIndex() == MUERTO_NAV_BODY) {
                // Si el muerto esta en el agua y yo estoy en tierra (o viceversa), no lo dejo intercambiar.
                if (wTile.hayAgua() != tile.hayAgua()) return false;

                // Si soy admin y estoy invisible, no puedo intercambiar con el muerto
                if (wC.getPriv() > 0 && wC.getPriv() < 6 && wC.isInvisible()) return false;
            }
            // Si no está muerto
            else return false;
        }

        // Si quiere entrar al agua y no está navegando, o si quiere entrar a tierra y está navegando, no lo deja caminar
        if (u.isNavegando() != tile.hayAgua()) return false;

        return true;
    }



}
