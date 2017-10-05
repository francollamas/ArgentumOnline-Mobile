package org.gszone.jfenix13.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.github.czyzby.lml.parser.impl.AbstractLmlView;
import com.kotcrab.vis.ui.widget.VisImage;
import com.kotcrab.vis.ui.widget.VisTextField;
import org.gszone.jfenix13.Main;
import org.gszone.jfenix13.connection.ClientPackages;
import org.gszone.jfenix13.connection.Connection;
import org.gszone.jfenix13.connection.ServerPackages;
import org.gszone.jfenix13.containers.Assets;
import org.gszone.jfenix13.containers.Audio;
import org.gszone.jfenix13.containers.GameData;

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

        background.setPosition((getStage().getWidth() - background.getWidth()) / 2,
                (getStage().getHeight() - background.getHeight()) / 2);
        getStage().addActor(background);
        background.toBack();
    }

    /**
     * Renderiza el fondo
     */
    private void renderBackground() {
        getStage().getBatch().begin();
        if (background != null) background.draw(getStage().getBatch(), 1);
        getStage().getBatch().end();
    }

    /**
     * Hace foco en un textfield
     */
    protected void setTfFocus(VisTextField tf) {
        // Si se está en dispositivos móviles, no hacemos foco (para que no salga el teclado de repente)
        if (Gdx.app.getType() != Desktop && Gdx.app.getType() != WebGL) return;
        tf.focusField();
        getStage().setKeyboardFocus(tf);
    }

    // Accesos rápidos desde las pantallas...
    public void setView(final Class<? extends AbstractLmlView> viewClass) { Main.getInstance().setView(viewClass);}
    public Assets getAssets() { return Main.getInstance().getAssets(); }
    public Audio getAudio() { return Main.getInstance().getAssets().getAudio(); }
    public ClientPackages getClPack() { return Main.getInstance().getConnection().getClPack(); }
    public ServerPackages getSvPack() { return Main.getInstance().getConnection().getSvPack(); }
    public Connection getConnection() { return Main.getInstance().getConnection(); }
    public GameData getGD() { return Main.getInstance().getGameData(); }
}
