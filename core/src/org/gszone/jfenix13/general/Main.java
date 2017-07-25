package org.gszone.jfenix13.general;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import org.gszone.jfenix13.containers.Assets;
import org.gszone.jfenix13.connection.Connection;
import org.gszone.jfenix13.screens.Carga;
import org.gszone.jfenix13.screens.Screen;

/**
 * Clase principal del juego
 *
 * game: referencia a la instancia actual del juego
 * assets: manejador de recursos
 * screens: conjunto de pantallas actuales del juego
 */
public class Main extends Game {
	private static Main game;

	private Assets assets;
	private Screen[] screens;

	private Connection connection;


	/**
	 * Inicialización del juego
	 */
	@Override
	public void create () {
		game = this;
		Gdx.graphics.setTitle("JFenix13");

		assets = new Assets();
		screens = new Screen[Screen.Scr.values().length];
		connection = new Connection();
		setScreen(new Carga());

	}

	/**
	 * Liberar recursos
	 */
	@Override
	public void dispose () {
		assets.dispose();
		connection.dispose();
	}

	public static Main getInstance() { return game; }
	public Assets getAssets() { return assets; }

	// Getter y setter sobre la lista de screens
	public Screen getLScreen(Screen.Scr scrType) { return screens[scrType.ordinal()]; }
	public void setLScreen(Screen screen) { screens[screen.getScrType().ordinal()] = screen; }

	/**
	 * Setea una screen ya cargada
	 */
	public void setScreen(Screen.Scr scrType) { setScreen(getLScreen(scrType)); }

	public Connection getConnection() { return connection; }
}
