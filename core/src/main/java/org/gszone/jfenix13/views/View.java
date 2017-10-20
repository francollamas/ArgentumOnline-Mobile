package org.gszone.jfenix13.views;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Array;
import com.github.czyzby.lml.parser.impl.AbstractLmlView;
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
import static org.gszone.jfenix13.general.FileNames.getDtGuiDir;
import static org.gszone.jfenix13.general.FileNames.getMbGuiDir;

/**
 * Clase general para una Vista
 *
 * texs: conjunto de texturas que se van cargando (generalmente usadas para el fondo de pantalla)
 *       se guarda su referencia para poder liberarlas de la memoria al cerrar la pantalla (método dispose() )
 */
public abstract class View extends AbstractLmlView {

    Array<Texture> texs;

    public View() {
        super(Main.newStage());
        texs = new Array<Texture>();
    }

    public Drawable getBackground() {
        return getBackground(getViewId());
    }

    /**
     * Devuelve un fondo
     */
    public Drawable getBackground(String name) {

        name = "scr_" + name;
        Application.ApplicationType t = Gdx.app.getType();
        Texture tex = new Texture(t == Desktop || t == WebGL ? getDtGuiDir(name) : getMbGuiDir(name));
        texs.add(tex);

        /* Toma una imagen y la convierte en NinePatch, para poder definirle bordes y así permitir mover ventanas
         (si fuera éste el caso) */
        NinePatch n = new NinePatch(tex);
        n.setPadding(10, 10, 20, 20); // TODO: poner valores convenientes para que funcione bien el resizado y mover.
        return new NinePatchDrawable(n);
    }

    @Override
    public void dispose() {
        super.dispose();
        for (Texture tex : texs)
            tex.dispose();
    }

    /**
     * Hace foco en un textfield (excepto en móbiles)
     * @param tf
     */
    protected void setTfFocus(VisTextField tf) {
        setTfFocus(tf, false);
    }

    /**
     * Hace foco en un textfield (se puede o no excluir móbiles)
     */
    protected void setTfFocus(VisTextField tf, boolean forceInMobile) {
        // Si se está en dispositivos móviles, no hacemos foco (para que no salga el teclado de repente)
        if (Gdx.app.getType() != Desktop && Gdx.app.getType() != WebGL && !forceInMobile) return;
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
