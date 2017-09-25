package org.gszone.jfenix13.desktop;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import org.gszone.jfenix13.Main;

/** Launches the desktop (LWJGL) application. */
public class DesktopLauncher {
    public static void main(String[] args) {
        createApplication();
    }

    private static LwjglApplication createApplication() {
        System.setProperty("org.lwjgl.opengl.Window.undecorated", "true");
        System.setProperty("org.lwjgl.opengl.Display.allowSoftwareOpenGL", "true");
        return new LwjglApplication(new Main(), getDefaultConfiguration());
    }

    private static LwjglApplicationConfiguration getDefaultConfiguration() {
        LwjglApplicationConfiguration configuration = new LwjglApplicationConfiguration();
        configuration.width = 800;
        configuration.height = 600;
        configuration.resizable = false;
        //configuration.fullscreen = true;
        for (int size : new int[] { 128, 64, 32, 16 }) {
            configuration.addIcon("icon" + size + ".png", FileType.Internal);
        }

        // Descomentar para liberar FPS!
        //configuration.vSyncEnabled = false;
        //configuration.foregroundFPS = 0;
        //configuration.backgroundFPS = 0;

        return configuration;
    }
}