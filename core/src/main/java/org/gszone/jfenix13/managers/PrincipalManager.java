package org.gszone.jfenix13.managers;

import org.gszone.jfenix13.actors.Consola;
import org.gszone.jfenix13.actors.World;

public class PrincipalManager extends ViewManager {

    public PrincipalManager() {

    }

    public Consola getConsola() {
        return getGD().getConsola();
    }

    public World getWorld() {
        return getGD().getWorld();
    }

    public void parseCommand(String command) {
        getGD().getCommands().parse(command);
    }
}
