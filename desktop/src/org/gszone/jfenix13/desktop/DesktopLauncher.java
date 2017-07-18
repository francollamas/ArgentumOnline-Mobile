package org.gszone.jfenix13.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import org.gszone.jfenix13.general.Main;
import com.badlogic.gdx.Files.FileType;

import static org.gszone.jfenix13.general.FileNames.*;
import static org.gszone.jfenix13.general.General.*;

/**
 * Clase que inicia el juego en escritorio
 */
public class DesktopLauncher {
	public static void main (String[] arg) {
		System.setProperty("org.lwjgl.opengl.Window.undecorated", "true");

		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = SCR_WIDTH;
		config.height = SCR_HEIGHT;
		config.fullscreen = false;
		config.resizable = false;
		config.addIcon(getIconDir(), FileType.Internal);

		// Descomentar para liberar FPS!
		//config.vSyncEnabled = false;
		//config.foregroundFPS = 0;
		//config.backgroundFPS = 0;

		new LwjglApplication(new Main(), config);
	}
}
