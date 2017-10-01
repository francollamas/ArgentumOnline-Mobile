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
import org.gszone.jfenix13.general.General;
import org.gszone.jfenix13.views.*;
import org.gszone.jfenix13.views.actions.GlobalActions;

import static org.gszone.jfenix13.general.FileNames.*;
import static com.badlogic.gdx.Application.ApplicationType.*;

/**
 * Clase principal del juego
 *
 * general: ajustes iniciales (tamaño de pantalla, del world, etc).
 * assets: manejador de recursos
 * connection: permite la conexión con el servidor y el envío y recepción de paquetes.
 * gameData: contiene toda estructura y estado del juego
 */
public class Main extends LmlApplicationListener {
	public static final int[] WIDTH = {800, 512}, HEIGHT = {600, 288};

	private Batch batch;

	private General general;
	private Assets assets;
	private Connection connection;
	private GameData gameData;

	/**
	 * Inicialización del juego
	 */
	@Override
	public void create () {
		// Config global
		Gdx.graphics.setTitle("JFenix13");
		Gdx.app.setLogLevel(Application.LOG_DEBUG);

		// Config de LML y propia del juego
		batch = new SpriteBatch();
		general = new General();
		assets = new Assets();
		gameData = new GameData();
		VisUI.load(assets.getGDXAssets().get(getSkinDir(), Skin.class));
		super.create();

		// Conexión
		if (Gdx.app.getType() == WebGL)
			connection = new WebConnection();
		else
            connection = new GnConnection();

		// Asigna alias a todas las clases de las vistas para que sean reconocidas desde los archivos lml
		addClassAlias(CargaView.ID, CargaView.class);
		addClassAlias(MenuView.ID, MenuView.class);


		setView(CargaView.class);
	}

	@Override
	public void render() {
		// Realiza las acciones recibidas del servidor, y registra las nuevas acciones.
		connection.getSvPack().doActions();
		connection.getClPack().write();

		// Si es Web, le avisa al socket que envíe las acciones registradas anteriormente
		// (ya que no hay un thread en la conexión que se encargue de ésto)
		if (Gdx.app.getType() == WebGL)
			connection.write();

		// Uso esta llamada para que se siga renderizando la pantalla actual normalmente
		super.render();
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
		General g = Main.getInstance().getGeneral();
		return new Stage(new FitViewport(g.getScrWidth(), g.getScrHeight()), Main.getInstance().getBatch());
	}

	@Override
	protected Action getViewHidingAction(AbstractLmlView view) {
		// Redefino el efecto de ocultar pantala por uno que no hace nada.
		return Actions.visible(true);
	}

	@Override
	protected Action getViewShowingAction(AbstractLmlView view) {
		// Redefino el efecto de mostrar pantala por uno que no hace nada.
		return Actions.visible(true);
	}

	public Batch getBatch() { return batch; }
	public General getGeneral() { return general; }
	public Assets getAssets() { return assets; }
	public Connection getConnection() { return connection; }
	public GameData getGameData() { return gameData; }
}
