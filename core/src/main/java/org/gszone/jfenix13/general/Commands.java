package org.gszone.jfenix13.general;

import org.gszone.jfenix13.Main;
import org.gszone.jfenix13.actors.Consola;
import org.gszone.jfenix13.connection.ClientPackages;
import org.gszone.jfenix13.containers.FontTypes;
import org.gszone.jfenix13.containers.GameData;

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

        if (cmd.substring(0, 1).equals("/")) {
            switch (cmd.toUpperCase().trim()) {
                case "/ONLINE":
                    getClPack().writeOnline();
                    break;
                case "/SALIR":
                    handleQuit();
                    break;
            }
        }
        else
            getClPack().writeTalk(cmd.trim());
    }

    public void handleQuit() {
        if (getGD().getCurrentUser().isParalizado()) {
            getC().addMessage(getMsg("msg-paralizado"), FontTypes.FontTypeName.Warning);
            return;
        }
        getClPack().writeQuit();
    }

    public ClientPackages getClPack() { return Main.getInstance().getConnection().getClPack(); }
    public GameData getGD() { return Main.getInstance().getGameData(); }
    public Consola getC() { return getGD().getConsola(); }

    public String getMsg(String key) {
        return Main.getInstance().getParser().getData().getDefaultI18nBundle().get(key);
    }
}
