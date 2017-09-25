package org.gszone.jfenix13.containers;

import org.gszone.jfenix13.objects.User;
import org.gszone.jfenix13.actors.World;

/**
 * Clase con el estado del juego
 *
 * world: mundo del juego
 * currentUser: usuario actual que se está manejando
 * chars: manejador de todos los chars que el cliente recibe
 */
public class GameData {
    public static final int MUERTO_HEAD = 500;
    public static final int MUERTO_NAV_BODY = 87;


    private World world;
    private User currentUser;
    private Chars chars;


    public GameData() {
        currentUser = new User();
        chars = new Chars();
        world = new World();
    }

    public World getWorld() { return world; }
    public Chars getChars() { return chars; }
    public User getCurrentUser() { return currentUser; }

    /**
     * Vuelve al estado inicial del juego
     */
    public void resetGameData() {
        currentUser = new User();
        chars = new Chars();
    }
}
