package org.gszone.jfenix13.managers;

import com.badlogic.gdx.Screen;
import org.gszone.jfenix13.Main;
import org.gszone.jfenix13.connection.ClientPackages;
import org.gszone.jfenix13.connection.Connection;
import org.gszone.jfenix13.connection.ServerPackages;
import org.gszone.jfenix13.containers.GameData;
import org.gszone.jfenix13.views.screens.View;

public abstract class ViewManager {

    public ViewManager() {

    }

    /**
     * Realiza actualizaciones constantemente
     *
     * No debe ser utilizado, ya es llamado desde la clase abstracta {@link View}
     */
    public void update() {

    }

    public void playMusic(int num) {
        Main.getInstance().getAssets().getAudio().playMusic(num);
    }

    public void exitGame() {
        Main.getInstance().salir();
    }

    public void restartGame() {
        Main.getInstance().reiniciar();
    }

    public void back() {

    }

    protected void setScreen(Screen scr) { Main.getInstance().setScreen(scr); }
    protected ClientPackages getClPack() { return Main.getInstance().getConnection().getClPack(); }
    protected ServerPackages getSvPack() { return Main.getInstance().getConnection().getSvPack(); }
    protected Connection getConnection() { return Main.getInstance().getConnection(); }
    protected GameData getGD() { return Main.getInstance().getGameData(); }
    protected String bu(String key) { return Main.getInstance().getBundle().get(key); }
}
