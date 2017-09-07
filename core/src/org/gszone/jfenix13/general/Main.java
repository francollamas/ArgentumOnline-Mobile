package org.gszone.jfenix13.general;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import org.gszone.jfenix13.containers.Assets;
import org.gszone.jfenix13.connection.Connection;
import org.gszone.jfenix13.containers.GameData;
import org.gszone.jfenix13.screens.desktop.DtCarga;
import org.gszone.jfenix13.screens.Screen;
import org.gszone.jfenix13.screens.mobile.MbCarga;

import static com.badlogic.gdx.Application.ApplicationType.*;

/**
 * Clase principal del juego
 *
 * game: referencia a la instancia actual del juego
 * general: ajustes iniciales (tamaño de pantalla, del world, etc).
 * assets: manejador de recursos
 * screens: conjunto de pantallas actuales del juego
 * connection: permite la conexión con el servidor y el envío y recepción de paquetes.
 * gameData: contiene toda estructura y estado del juego
 */
public class Main extends Game {
	private static Main game;

	private General general;
	private Assets assets;
	private Screen[] screens;
	private Connection connection;

	private GameData gameData;

	/**
	 * Inicialización del juego
	 */
	@Override
	public void create () {
		game = this;
		Gdx.graphics.setTitle("JFenix13");
		general = new General();

		assets = new Assets();
		screens = new Screen[Screen.Scr.values().length];
		connection = new Connection();
		gameData = new GameData();


		if (Gdx.app.getType() == Desktop)
			setScreen(new DtCarga());
		else
			setScreen(new MbCarga());

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
	public General getGeneral() { return general; }
	public Assets getAssets() { return assets; }

	// Getter y setter sobre la lista de screens
	public Screen getLScreen(Screen.Scr scrType) { return screens[scrType.ordinal()]; }
	public void setLScreen(Screen screen) { screens[screen.getScrType().ordinal()] = screen; }

	/**
	 * Setea una screen ya cargada
	 */
	public void setScreen(Screen.Scr scrType) { setScreen(getLScreen(scrType)); }

	public Connection getConnection() { return connection; }
	public GameData getGameData() { return gameData; }
}
