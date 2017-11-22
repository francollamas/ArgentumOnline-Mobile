package org.gszone.jfenix13.general;

import org.gszone.jfenix13.Main;
import org.gszone.jfenix13.actors.Consola;
import org.gszone.jfenix13.connection.ClientPackages;
import org.gszone.jfenix13.containers.GameData;
import org.gszone.jfenix13.objects.User;

import static org.gszone.jfenix13.containers.FontTypes.FontTypeName.*;

/**
 * Analiza los comandos para enviar al servidor
 */
public class Commands {
    public Commands() {

    }

    /**
     * Analiza y ejecuta un comando
     */
    public void parse(String cmd) {
        if (cmd.length() == 0) return;

        ClientPackages c = getClPack();

        if (cmd.substring(0, 1).equals("/")) {
            switch (cmd.toUpperCase().trim()) {
                case "/ONLINE":
                    c.writeOnline();
                    break;
                case "/SALIR":
                    cmdQuit();
                    break;
                case "/MEDITAR":
                    cmdMeditate();
                    break;
                case "/PING":
                    c.writePing();
                    break;
            }
        } else
            c.writeTalk(cmd);
    }

    private void cmdQuit() {
        if (getU().isParalizado()) {
            getC().addMessage(bu("msg.paralizado"), Warning);
            return;
        }
        getClPack().writeQuit();
    }

    private void cmdMeditate() {
        if (isMuerto()) return;
        if (getU().getStats().getMana() == getU().getStats().getMaxMana()) return;
        getClPack().writeMeditate();
    }


    /**
     * Muestra un mensaje en consola si el usuario está muerto.
     * (se puso en un método separado para no escribir el código de mostrar mensaje tantas veces)
     *
     * @return indica si está muerto
     */
    private boolean isMuerto() {
        if (getU().isMuerto()) {
            getC().addMessage(bu("msg.muerto"), Info);
            return true;
        }
        return false;
    }

    public String bu(String key) { return Main.getInstance().getBundle().get(key); }
    public ClientPackages getClPack() { return Main.getInstance().getConnection().getClPack(); }
    public GameData getGD() { return Main.getInstance().getGameData(); }
    public User getU() { return getGD().getCurrentUser(); }
    public Consola getC() { return getGD().getConsola(); }
}
