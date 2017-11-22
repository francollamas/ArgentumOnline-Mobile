package org.gszone.jfenix13.views;


import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Array;
import org.gszone.jfenix13.Main;
import org.gszone.jfenix13.connection.ClientPackages;
import org.gszone.jfenix13.connection.Connection;
import org.gszone.jfenix13.connection.ServerPackages;
import org.gszone.jfenix13.containers.Assets;
import org.gszone.jfenix13.containers.Audio;
import org.gszone.jfenix13.containers.GameData;

import static com.badlogic.gdx.Application.ApplicationType.*;
import static org.gszone.jfenix13.general.FileNames.getDtGuiDir;
import static org.gszone.jfenix13.general.FileNames.getMbGuiDir;

/**
 * Clase general para una Vista
 *
 * stage: escenario de la vista
 * texs: conjunto de texturas que se van cargando (generalmente usadas para el fondo de pantalla)
 *       se guarda su referencia para poder liberarlas de la memoria al cerrar la pantalla (método dispose() )
 */
public abstract class View implements Screen {

    protected Stage stage;
    private Array<Texture> texs;

    public View() {
        stage = Main.newStage();
        texs = new Array<>();
    }

    protected Drawable getBackground(String name) {
        return getBackground(name, true);
    }

    /**
     * Devuelve un fondo
     *
     * @param name nombre de la imagen (sin el "src_")
     * @param borders indica si tiene bordes (generalmente para poder redimensionar, mover, etc.)
     * @return ninepatchDrawable con el fondo
     */
    protected Drawable getBackground(String name, boolean borders) {

        name = "scr_" + name;
        Application.ApplicationType t = Gdx.app.getType();
        Texture tex = new Texture(t == Desktop || t == WebGL ? getDtGuiDir(name) : getMbGuiDir(name));
        texs.add(tex);

        /* Toma una imagen y la convierte en NinePatch, para poder definirle bordes y así permitir mover ventanas
         (si fuera éste el caso) */
        NinePatch n = new NinePatch(tex);
        if (borders)
            n.setPadding(14, 14, 20, 20); // TODO: poner valores convenientes para que funcione bien el resizado y mover.
        return new NinePatchDrawable(n);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        // Realiza las acciones pendientes de todos los actores agregados al escenario.
        stage.act();

        // Limpia la pantalla
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
    }

    /**
     * Actualiza el tamaño del viewport según el nuevo tamaño de la pantalla
     */
    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        Gdx.input.setInputProcessor(null);
        for (Texture tex : texs)
            tex.dispose();
    }

    // Accesos rápidos desde las pantallas...
    public void setScreen(Screen scr) { Main.getInstance().setScreen(scr); }
    public Stage getStage() { return stage; }
    public Assets getAssets() { return Main.getInstance().getAssets(); }
    public Audio getAudio() { return Main.getInstance().getAssets().getAudio(); }
    public ClientPackages getClPack() { return Main.getInstance().getConnection().getClPack(); }
    public ServerPackages getSvPack() { return Main.getInstance().getConnection().getSvPack(); }
    public Connection getConnection() { return Main.getInstance().getConnection(); }
    public GameData getGD() { return Main.getInstance().getGameData(); }
    public String bu(String key) { return Main.getInstance().getBundle().get(key); }
}
