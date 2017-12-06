package org.gszone.jfenix13.containers;

import org.gszone.jfenix13.actors.Consola;
import org.gszone.jfenix13.actors.Inventory;
import org.gszone.jfenix13.general.Commands;
import org.gszone.jfenix13.objects.User;
import org.gszone.jfenix13.actors.World;

/**
 * Clase con el estado del juego
 * <p>
 * fontTypes: tipos de fuente
 * commands: manejador de comandos
 * colors: conjunto de colores de personajes
 * world: mundo del juego
 * inventory: inventario del personaje
 * currentUser: usuario actual que se está manejando
 * chars: manejador de todos los chars que el cliente recibe
 */
public class GameData {

    public static final int MAX_INVENTORY_SLOTS = 30;
    public static final int MAX_NPC_INVENTORY_SLOTS = 50; // TODO: usar
    public static final int MAX_HECHIZOS_SLOTS = 35; // TODO: usar

    public static final int MUERTO_HEAD = 500;
    public static final int MUERTO_NAV_BODY = 87;

    public enum ObjTypes {
        UseOnce, Weapon, Armadura, Arboles, Guita, Puertas, Contenedores, Carteles, Llaves, Foros, Pociones, Null1,
        Bebidas, Leña, Fogata, Escudo, Casco, Anillo, Teleport, Null2, Null3, Yacimiento, Minerales, Pergaminos,
        Null4, Instrumentos, Yunque, Fragua, Null5, Null6, Barcos, Flechas, BotellaVacia, BotellaLlena,
        Manchas, ArbolElfico, Mochilas, Cualquiera
    }

    private FontTypes fontTypes;
    private Colors colors;
    private Commands commands;
    private Consola consola;
    private World world;
    private Inventory inventario;
    private User currentUser;
    private Chars chars;
    private boolean pausa;

    public GameData() {

        fontTypes = new FontTypes();
        commands = new Commands();
        colors = new Colors();
        currentUser = new User();
        chars = new Chars();
        consola = new Consola();

        world = new World();
        inventario = new Inventory(MAX_INVENTORY_SLOTS, 5);

    }

    public FontTypes getFontTypes() {
        return fontTypes;
    }

    public Commands getCommands() {
        return commands;
    }

    public Colors getColors() {
        return colors;
    }

    public World getWorld() {
        return world;
    }

    public Inventory getInventario() {
        return inventario;
    }

    public Consola getConsola() {
        return consola;
    }

    public Chars getChars() {
        return chars;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public boolean isPausa() {
        return pausa;
    }

    public void setPausa(boolean pausa) {
        this.pausa = pausa;
    }

    /**
     * Vuelve al estado inicial del juego
     */
    public void resetGameData() {
        currentUser = new User();
        chars = new Chars();
        consola = new Consola();
        inventario = new Inventory(MAX_INVENTORY_SLOTS, 5);
        setPausa(false);
    }
}
