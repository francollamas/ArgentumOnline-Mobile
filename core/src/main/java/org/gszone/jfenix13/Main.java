package org.gszone.jfenix13;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.kotcrab.vis.ui.VisUI;
import org.gszone.jfenix13.connection.Connection;
import org.gszone.jfenix13.connection.GnConnection;
import org.gszone.jfenix13.connection.WebConnection;
import org.gszone.jfenix13.containers.Assets;
import org.gszone.jfenix13.containers.GameData;
import org.gszone.jfenix13.general.Config;
import org.gszone.jfenix13.views.CargaView;
import org.gszone.jfenix13.views.View;

import static org.gszone.jfenix13.general.FileNames.*;
import static com.badlogic.gdx.Application.ApplicationType.*;

/**
 * Clase principal del juego
 *
 * rebootable: contiene un Runnable (código para ejecutar) que se encarga de reiniciar el juego.
 * bundle: maneja los textos según el idioma.
 * config: ajustes iniciales (tamaño de pantalla, del world, etc).
 * assets: manejador de recursos
 * connection: permite la conexión con el servidor y el envío y recepción de paquetes.
 * gameData: contiene toda estructura y estado del juego
 */
public class Main extends Game {

	/**
	 * Constructor general
	 */
	public Main() {

	}

	/**
	 * Constructor usado en Desktop
	 * @param rebootable trozo de código que reinicia el juego
	 */
	public Main(Runnable rebootable) {
		this.rebootable = rebootable;
	}


	private Runnable rebootable;

	private I18NBundle bundle;
	private Batch batch;

	private Config config;
	private Assets assets;
	private Connection connection;
	private GameData gameData;

	/**
	 * Sale del juego
	 */
	public void salir() {
		Gdx.app.exit();
	}

	/**
	 * Reinicia el juego
	 */
	public void reiniciar() {
		// Inserta y ejecuta el runnable en la aplicación
		Gdx.app.postRunnable(rebootable);
	}

	/**
	 * Inicialización del juego
	 */
	@Override
	public void create () {
		// Config global
		Gdx.graphics.setTitle("JFenix13");
		Gdx.app.setLogLevel(Application.LOG_DEBUG);

		// TODO: ver el tema del cursor
		/*Pixmap pm = new Pixmap(Gdx.files.internal(getCursorDir()));
		Gdx.graphics.setCursor(Gdx.graphics.newCursor(pm, 0, 0));
		pm.dispose();*/

		// Config propia del juego
		bundle = I18NBundle.createBundle(Gdx.files.internal(getBundleDir()));
		batch = new SpriteBatch();
		config = new Config();
		assets = new Assets();
		VisUI.load(assets.getGDXAssets().get(getSkinDir(), Skin.class));
		gameData = new GameData();

		// Conexión
		if (Gdx.app.getType() == WebGL)
			connection = new WebConnection();
		else
            connection = new GnConnection();

		setScreen(new CargaView());
	}

	@Override
	public void render() {
		// Procesamos los paquetes recibidos al socket
		connection.getSvPack().doActions();

		// Uso esta llamada para que se siga renderizando la pantalla actual normalmente
		super.render();

		// Escribe agrega los bytes pendientes a la cola.
		connection.getClPack().write();

		// Si es Web, le avisa al socket que envíe las acciones registradas anteriormente
		// (ya que no hay un thread en la conexión que se encargue de ésto)
		if (Gdx.app.getType() == WebGL)
			connection.write();
	}

	/**
	 * Liberar recursos
	 */
	@Override
	public void dispose () {
		VisUI.dispose();
		screen.dispose();
		assets.dispose();
		connection.dispose();
	}

	/**
	 * Obtiene la instancia del juego
	 */
	public static Main getInstance() { return (Main)Gdx.app.getApplicationListener(); }

	/**
	 * Devuelve un nuevo escenario usando el mismo Batch.
	 */
	public static Stage newStage() {
		Config c = Main.getInstance().getConfig();
		return new Stage(new FitViewport(c.getVirtualWidth(), c.getVirtualHeight()), Main.getInstance().getBatch());
	}

	/**
	 * Cambia de Screen. (desecha la anterior y usa la nueva)
	 *
	 * @param screen
	 */
	@Override
	public void setScreen(Screen screen) {
		if (this.screen != null) this.screen.dispose();
		this.screen = screen;
		if (this.screen != null) {
			this.screen.show();
			this.screen.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			Action a = Actions.sequence(Actions.alpha(0f), Actions.fadeIn(0.3f, Interpolation.fade));
			((View)this.screen).getStage().addAction(a);
		}
	}

	public I18NBundle getBundle() { return bundle; }
	public Batch getBatch() { return batch; }
	public Config getConfig() { return config; }
	public Assets getAssets() { return assets; }
	public Connection getConnection() { return connection; }
	public GameData getGameData() { return gameData; }
}
