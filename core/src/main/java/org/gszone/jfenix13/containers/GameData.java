package org.gszone.jfenix13.containers;

import org.gszone.jfenix13.actors.Consola;
import org.gszone.jfenix13.objects.User;
import org.gszone.jfenix13.actors.World;

/**
 * Clase con el estado del juego
 *
 * colors: conjunto de colores de personajes
 * world: mundo del juego
 * currentUser: usuario actual que se está manejando
 * chars: manejador de todos los chars que el cliente recibe
 */
public class GameData {
    public static final int MUERTO_HEAD = 500;
    public static final int MUERTO_NAV_BODY = 87;

    private FontTypes fontTypes;
    private Colors colors;
    private Consola consola;
    private World world;
    private User currentUser;
    private Chars chars;

    public GameData() {

        fontTypes = new FontTypes();
        colors = new Colors();
        currentUser = new User();
        chars = new Chars();
        consola = new Consola();
        world = new World();

    }

    public FontTypes getFontTypes() { return fontTypes; }
    public Colors getColors() { return colors; }
    public World getWorld() { return world; }
    public Consola getConsola() { return consola; }
    public Chars getChars() { return chars; }
    public User getCurrentUser() { return currentUser; }

    /**
     * Vuelve al estado inicial del juego
     */
    public void resetGameData() {
        currentUser = new User();
        chars = new Chars();
        consola = new Consola();
    }
}
