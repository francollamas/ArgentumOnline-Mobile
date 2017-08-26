package org.gszone.jfenix13.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import org.gszone.jfenix13.general.Main;
import com.badlogic.gdx.Files.FileType;

import static org.gszone.jfenix13.general.FileNames.*;

/**
 * Clase que inicia el juego en escritorio
 */
public class DesktopLauncher {
	public static void main (String[] arg) {
		System.setProperty("org.lwjgl.opengl.Window.undecorated", "true");
		System.setProperty("org.lwjgl.opengl.Display.allowSoftwareOpenGL", "true");

		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1024;
		config.height = 768;
		//config.fullscreen = true;
		config.resizable = false;
		config.addIcon(getIconDir(), FileType.Internal);

		// Descomentar para liberar FPS!
		//config.vSyncEnabled = false;
		//config.foregroundFPS = 0;
		//config.backgroundFPS = 0;

		new LwjglApplication(new Main(), config);
	}
}
