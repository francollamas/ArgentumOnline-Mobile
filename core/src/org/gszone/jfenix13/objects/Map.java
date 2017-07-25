package org.gszone.jfenix13.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import org.gszone.jfenix13.general.General;
import org.gszone.jfenix13.graphics.Grh;
import org.gszone.jfenix13.utils.Position;
import org.gszone.jfenix13.utils.Rect;

import java.io.DataInputStream;
import java.io.IOException;

import static org.gszone.jfenix13.general.FileNames.*;
import static org.gszone.jfenix13.utils.Bytes.*;
import static org.gszone.jfenix13.general.General.*;

/**
 * Es el mapa actual del juego
 *
 * tiles: matriz de tiles, cada tile con sus correspondientes componentes (objetos, capas, luces, partículas, etc)
 * número: número de mapa
 * size: rectángulo que representa el tamaño del mapa
 */
public class Map {

    private MapTile[][] tiles;
    private int numero;
    private Rect size;

    public Map(int numero) {
        this.numero = numero;

        try {
            load();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Carga un mapa
     */
    private void load() throws IOException {
        FileHandle fh = Gdx.files.internal(DIR_MAPS + "/Mapa" + numero + ".mcl");
        DataInputStream dis = new DataInputStream(fh.read());

        // Lee la cabecera (cantidades de cada tipo)
        int cantBloqueados = leReadInt(dis);
        int[] cantTilesEnCapa = {leReadInt(dis), leReadInt(dis), leReadInt(dis)};
        int cantTriggers = leReadInt(dis);
        int cantLuces = leReadInt(dis);
        int cantParticulas = leReadInt(dis);

        // Lee el tamaño del mapa
        int xMax = leReadByte(dis);
        int xMin = leReadByte(dis);
        int yMax = leReadByte(dis);
        int yMin = leReadByte(dis);

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
                tiles[x][y].setCapa(0, new Grh(leReadInt(dis)));
            }
        }

        // Asigna bloqueos
        if (cantBloqueados > 0) {
            for (int i = 0; i < cantBloqueados; i++) {
                int x = leReadByte(dis) - 1;
                int y = leReadByte(dis) - 1;
                tiles[x][y].setBlocked(true);
            }
        }

        // Setea las capas en los tiles correspondientes
        for (int c = 0; c < cantTilesEnCapa.length; c++) {
            if (cantTilesEnCapa[c] > 0) {
                for (int i = 0; i < cantTilesEnCapa[c]; i++) {
                    int x = leReadByte(dis) - 1;
                    int y = leReadByte(dis) - 1;
                    tiles[x][y].setCapa(c + 1, new Grh(leReadInt(dis)));
                }
            }
        }

        // Asigna triggers
        if (cantTriggers > 0) {
            for (int i = 0; i < cantTriggers; i++) {
                int x = leReadByte(dis) - 1;
                int y = leReadByte(dis) - 1;
                int trigger = leReadByte(dis);
                tiles[x][y].setTrigger(trigger);
            }
        }

        // Asigna luces
        if (cantLuces > 0) {
            for (int i = 0; i < cantLuces; i++) {
                int x = leReadByte(dis) - 1;
                int y = leReadByte(dis) - 1;
                int rojo = leReadByte(dis);
                int verde = leReadByte(dis);
                int azul = leReadByte(dis);

                /*Light light = new Light();
                light.setColor(rojo, verde, azul, 255);
                light.setRango(leReadByte(dis));

                tiles[x][y].setLight(light);*/
            }
        }

        // Asigna partículas
        if (cantParticulas > 0) {
            for (int i = 0; i < cantParticulas; i++) {
                int x = leReadByte(dis) - 1;
                int y = leReadByte(dis) - 1;
                tiles[x][y].setParticula(leReadInt(dis));
            }
        }

        dis.close();
    }

    public MapTile getTile(int x, int y) {
        return tiles[x][y];
    }

    public int getNumero() {
        return numero;
    }

    public Rect getSize() {
        return size;
    }

    /**
     * Obtiene un rectángulo interno al mapa, que corresponden a los límites de posición del usuario.
     * Esto es para que lo último que se vea del mapa sean los límites, y no bordes negros.
     */
    public Rect getBorderRect() {
        int halfWindowsTileWidth = WINDOWS_TILE_WIDTH / 2;
        int halfWindowsTileHeight = WINDOWS_TILE_HEIGHT / 2;

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
    public boolean isLegalPos(Position pos, General.Direccion dir) {
        return isLegalPos(pos.getSuma(Position.dirToPos(dir)));
    }

    public boolean isLegalPos(float x, float y) {
        return isLegalPos(new Position(x, y));
    }

    /**
     * Indica si es posible moverse a un tile final (pos)
     */
    public boolean isLegalPos(Position pos) {
        MapTile tile = tiles[(int) pos.getX() - 1][(int) pos.getY() - 1];

        // Verificamos que no se pase ciertos límites (porque sino intentaría renderizar tiles que no existen)
        if (!getBorderRect().isPointIn(pos)) return false;

        if (tile.isBlocked()) return false;

        return true;
    }

}
