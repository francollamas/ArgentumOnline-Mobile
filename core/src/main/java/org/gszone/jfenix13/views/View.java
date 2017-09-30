package org.gszone.jfenix13.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.github.czyzby.lml.parser.impl.AbstractLmlView;
import com.kotcrab.vis.ui.widget.VisImage;
import org.gszone.jfenix13.Main;
import org.gszone.jfenix13.connection.ClientPackages;
import org.gszone.jfenix13.connection.Connection;
import org.gszone.jfenix13.connection.ServerPackages;
import org.gszone.jfenix13.containers.Assets;
import org.gszone.jfenix13.containers.Audio;

import static com.badlogic.gdx.Application.ApplicationType.Desktop;
import static com.badlogic.gdx.Application.ApplicationType.WebGL;
import static org.gszone.jfenix13.general.FileNames.getAtlasDtGuiDir;
import static org.gszone.jfenix13.general.FileNames.getAtlasMbGuiDir;

/**
 * Clase general para una Vista
 *
 * background: imagen de fondo (opcional)
 */
public abstract class View extends AbstractLmlView {
    protected VisImage background;

    public View() {
        super(Main.newStage());
    }

    /**
     * Define activa la imagen de fondo de la pantalla
     * Debe estar guardada como: 'scr_ID.png', en el atlas correspondiente
     */
    protected void setBackground() {
        TextureAtlas atlas;
        if (Gdx.app.getType() == Desktop || Gdx.app.getType() == WebGL)
            atlas = getAssets().getGDXAssets().get(getAtlasDtGuiDir(), TextureAtlas.class);
        else
            atlas = getAssets().getGDXAssets().get(getAtlasMbGuiDir(), TextureAtlas.class);

        this.background = new VisImage(atlas.findRegion("scr_" + getViewId()));
    }

    @Override
    public void show() {
        // Setea la posicion del fondo
        if (background != null)
            background.setPosition((getStage().getWidth() - background.getWidth()) / 2,
                    (getStage().getHeight() - background.getHeight()) / 2);
    }

    @Override
    public void render() {
        renderBackground();
        super.render();
    }

    @Override
    public void render(float delta) {
        renderBackground();
        super.render();
    }

    /**
     * Renderiza el fondo
     */
    private void renderBackground() {
        getStage().getBatch().begin();
        if (background != null) background.draw(getStage().getBatch(), 1);
        getStage().getBatch().end();
    }


    // Accesos rápidos desde las pantallas...
    public Assets getAssets() { return Main.getInstance().getAssets(); }
    public Audio getAudio() { return Main.getInstance().getAssets().getAudio(); }
    public ClientPackages getClPack() { return Main.getInstance().getConnection().getClPack(); }
    public ServerPackages getSvPack() { return Main.getInstance().getConnection().getSvPack(); }
    public Connection getConnection() { return Main.getInstance().getConnection(); }
}
