package org.gszone.jfenix13;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.github.czyzby.kiwi.util.gdx.asset.Disposables;
import com.github.czyzby.lml.parser.LmlParser;
import com.github.czyzby.lml.parser.impl.AbstractLmlView;
import com.github.czyzby.lml.util.LmlApplicationListener;
import com.github.czyzby.lml.vis.util.VisLml;
import com.kotcrab.vis.ui.VisUI;
import org.gszone.jfenix13.connection.Connection;
import org.gszone.jfenix13.connection.GnConnection;
import org.gszone.jfenix13.connection.WebConnection;
import org.gszone.jfenix13.containers.Assets;
import org.gszone.jfenix13.containers.GameData;
import org.gszone.jfenix13.general.Config;
import org.gszone.jfenix13.views.*;
import org.gszone.jfenix13.views.actions.GlobalActions;

import static org.gszone.jfenix13.general.FileNames.*;
import static com.badlogic.gdx.Application.ApplicationType.*;

/**
 * Clase principal del juego
 *
 * rebootable: contiene un Runnable (código para ejecutar) que se encarga de reiniciar el juego.
 * config: ajustes iniciales (tamaño de pantalla, del world, etc).
 * assets: manejador de recursos
 * connection: permite la conexión con el servidor y el envío y recepción de paquetes.
 * gameData: contiene toda estructura y estado del juego
 */
public class Main extends LmlApplicationListener {

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

		// Config de LML y propia del juego
		batch = new SpriteBatch();
		config = new Config();
		assets = new Assets();
		VisUI.load(assets.getGDXAssets().get(getSkinDir(), Skin.class));
		gameData = new GameData();
		super.create();

		// Conexión
		if (Gdx.app.getType() == WebGL)
			connection = new WebConnection();
		else
            connection = new GnConnection();

		// Asigna alias a todas las clases de las vistas para que sean reconocidas desde los archivos lml
		addClassAlias(CargaView.ID, CargaView.class);
		addClassAlias(MenuView.ID, MenuView.class);
		addClassAlias(ConfigView.ID, ConfigView.class);
		addClassAlias(PrincipalView.ID, PrincipalView.class);
		addClassAlias(CrearPjView.ID, CrearPjView.class);
		setView(CargaView.class);
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
		super.dispose();
		Disposables.disposeOf(batch);
		VisUI.dispose();
		assets.dispose();
		connection.dispose();
	}

	/**
	 * Configura Lml
	 */
	@Override
	protected LmlParser createParser() {
		return VisLml.parser()
				.actions("global", GlobalActions.class)
				.i18nBundle(I18NBundle.createBundle(Gdx.files.internal("i18n/bundle")))
				.build();
	}

	/**
	 * Obtiene la instancia del juego
	 */
	public static Main getInstance() { return (Main)Gdx.app.getApplicationListener(); }

	/**
	 * Devuelve un nuevo escenario usando el mismo Batch.
	 */
	public static Stage newStage() {
		Config g = Main.getInstance().getConfig();
		return new Stage(new FitViewport(g.getVirtualWidth(), g.getVirtualHeight()), Main.getInstance().getBatch());
	}

	@Override
	public void setView(final AbstractLmlView view, Action doAfterHide) {
		Runnable r = new Runnable() {
			@Override
			public void run() {
				AbstractLmlView curr = getCurrentView();
				setCurrentView(view);
				if (curr != null) clearView(curr);

					getCurrentView().resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), isCenteringCameraOnResize());
					Gdx.input.setInputProcessor(getCurrentView().getStage());
					getCurrentView().show();
					getCurrentView().getStage().addAction(getViewShowingAction(view));
			}
		};

		if (doAfterHide == null)
			r.run();
		else {
			Gdx.input.setInputProcessor(null);
			getCurrentView().hide();
			getCurrentView().getStage().addAction(Actions.sequence(doAfterHide, Actions.run(r)));
		}
	}

	@Override
	protected float getViewTransitionDuration() {
		return 0.25f;
	}

	public Batch getBatch() { return batch; }
	public Config getConfig() { return config; }
	public Assets getAssets() { return assets; }
	public Connection getConnection() { return connection; }
	public GameData getGameData() { return gameData; }
}
