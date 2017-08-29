package org.gszone.jfenix13.containers;

import org.gszone.jfenix13.objects.User;
import org.gszone.jfenix13.actors.World;
import org.gszone.jfenix13.utils.Rect;

/**
 * Clase con el estado del juego
 *
 * world: mundo del juego
 * currentUser: usuario actual que se está manejando
 * chars: manejador de todos los chars que el cliente recibe
 * area: contiene info de los límites del área actual, (y permite cambiar de área)
 */
public class GameData {
    private World world;
    private User currentUser;
    private Chars chars;
    private Rect area;


    public GameData() {
        currentUser = new User();
        chars = new Chars();
        area = new Rect();
        world = new World();
    }

    public World getWorld() { return world; }
    public Chars getChars() { return chars; }
    public Rect getArea() { return area; }
    public User getCurrentUser() { return currentUser; }
}
