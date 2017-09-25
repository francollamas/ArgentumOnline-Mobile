package org.gszone.jfenix13.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import org.gszone.jfenix13.connection.*;
import org.gszone.jfenix13.containers.Audio;
import org.gszone.jfenix13.Main;

import static org.gszone.jfenix13.general.FileNames.*;
import static com.badlogic.gdx.Application.ApplicationType.*;

/**
 * Clase abstracta con el principal funcionamiento de una Screen.
 * Todas las pantallas deben heredar de ésta
 *
 * scrType: tipo de pantalla
 * stage: escenario (lugar donde se ubican todos los objetos visuales como botones, ventanas, etc)
 * background: imagen de fondo
 */
public abstract class Screen implements com.badlogic.gdx.Screen {
    public enum Scr {CARGA, MENU, PRINCIPAL}

    protected Scr scrType;

    protected Stage stage;
    protected Image background;

    public Screen(Scr scr) {
        this(scr, null);
    }

    /**
     * Constructor
     */
    public Screen(Scr scrType, String background) {
        this.scrType = scrType;
        this.background = null;
        if (background != null) {
            setBackground(background);
        }

    }

    protected void setBackground(String background) {
        TextureAtlas atlas;
        if (Gdx.app.getType() == Desktop || Gdx.app.getType() == WebGL)
            atlas = Main.getInstance().getAssets().getGDXAssets().get(getAtlasDtGuiDir(), TextureAtlas.class);
        else
            atlas = Main.getInstance().getAssets().getGDXAssets().get(getAtlasMbGuiDir(), TextureAtlas.class);

        this.background = new Image(atlas.findRegion("scr_" + background));
    }

    public Scr getScrType() { return scrType; }
    public Stage getStage() { return stage; }
    public Batch getBatch() { return stage.getBatch(); }
    public Audio getAudio() { return Main.getInstance().getAssets().getAudio(); }
    public ClientPackages getClPack() { return Main.getInstance().getConnection().getClPack(); }
    public ServerPackages getSvPack() { return Main.getInstance().getConnection().getSvPack(); }
    public Connection getConnection() { return Main.getInstance().getConnection(); }
    public int getVirtualWidth() { return Main.getInstance().getGeneral().getScrWidth(); }
    public int getVirtualHeight() { return Main.getInstance().getGeneral().getScrHeight(); }

    /**
     * Se ejecuta al mostrarse la pantalla
     */
    @Override
    public void show() {
        // Crea un escenario para la pantalla
        stage = new Stage(new FitViewport(Main.getInstance().getGeneral().getScrWidth(),
                                    Main.getInstance().getGeneral().getScrHeight()));

        // Setea la posición del fondo
        if (background != null)
            background.setPosition((stage.getWidth() - background.getWidth()) / 2,
                                        (stage.getHeight() - background.getHeight()) / 2);

        // Le digo a libGDX qué entrada tiene que escuchar (la entrada del actual escenario en este caso)
        Gdx.input.setInputProcessor(stage);

        // Agrego esta nueva pantalla a la lista de pantallas del juego
        Main.getInstance().setLScreen(this);

        // Permite visualizar los bordes de los Actores.
        //stage.setDebugAll(true);
    }


    /**
     * Se ejecuta constantemente
     * Renderizado de todos los gráficos.
     *
     * @param delta: tiempo transcurrido entre el frame anterior y el actual
     */
    @Override
    public void render(float delta) {
        // Realiza las acciones recibidas del servidor, y registra las nuevas acciones.
        getSvPack().doActions();
        getClPack().write();

        // Si es Web, le avisa al socket que envíe las acciones registradas anteriormente
        // (ya que no hay un thread en la conexión que se encargue de ésto)
        if (Gdx.app.getType() == WebGL)
            getConnection().write();


        // Realiza las acciones pendientes de todos los actores agregados al escenario.
        stage.act();

        // Limpia la pantalla
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // El renderizado del escenario y de las demás cosas que se quieran agregar se hacen en las clases hijas.
    }

    /**
     * Se ejecuta cada vez que la pantala cambia de tamaño
     */
    @Override
    public void resize(int width, int height) {
        // Actualiza el tamaño del viewport según el nuevo tamaño de la pantalla
        stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    /**
     * La pantalla se deja de mostrar
     */
    @Override
    public void hide() {
        // Se deja de procesar la entrada para el stage
        Gdx.input.setInputProcessor(null);
    }

    /**
     * La pantalla se cierra
     */
    @Override
    public void dispose() {
        // Libera recursos
        stage.dispose();
    }
}