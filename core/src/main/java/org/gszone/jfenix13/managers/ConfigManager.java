package org.gszone.jfenix13.managers;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import org.gszone.jfenix13.Main;
import org.gszone.jfenix13.general.Config;
import org.gszone.jfenix13.general.DtConfig;
import org.gszone.jfenix13.views.screens.MenuView;

public class ConfigManager extends ViewManager {

    public ConfigManager() {

    }

    @Override
    public void back() {
        setScreen(new MenuView());
    }

    public void guardar(boolean vSync, boolean fullscreen, int res, boolean titleBar, boolean resizable) {

        // Configuraciones generales
        Config c = getConfig();
        // TODO: aplicar las configuraciones generales, en este caso, faltan las de AUDIO.
        c.saveConfigFile();

        // OnlyDesktop
        if (Gdx.app.getType() == Application.ApplicationType.Desktop) {
            // Guarda los valores actuales para luego verificar si se hizo un cambio en al menos una característica
            boolean oldVSync = DtConfig.vSync;
            boolean oldFullScreen = DtConfig.fullscreeen;
            int oldWidth = DtConfig.width;
            int oldHeight = DtConfig.height;
            boolean oldDecorated = DtConfig.decorated;
            boolean oldResizable = DtConfig.resizable;

            DtConfig.vSync = vSync;

            DtConfig.fullscreeen = fullscreen;
            if (DtConfig.fullscreeen) {
                DtConfig.width = 1024; DtConfig.height = 768;
            }
            else {
                switch (res) {
                    case 0:
                        DtConfig.width = 800; DtConfig.height = 600;
                        break;
                    case 1:
                        DtConfig.width = 1024; DtConfig.height = 768;
                        break;
                    case 2:
                        DtConfig.width = 1200; DtConfig.height = 900;
                        break;
                }
            }

            DtConfig.decorated = titleBar;
            DtConfig.resizable = resizable;

            DtConfig.saveConfig();

            // Si se hicieron cambios de video, se reinicia el cliente.
            if (oldVSync != DtConfig.vSync || oldFullScreen != DtConfig.fullscreeen
                    || oldWidth != DtConfig.width || oldHeight != DtConfig.height
                    || oldDecorated != DtConfig.decorated || oldResizable != DtConfig.resizable) {

                restartGame();
            }

        }

        back();
    }

    public Config getConfig() { return Main.getInstance().getConfig(); }
}
