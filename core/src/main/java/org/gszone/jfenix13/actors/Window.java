package org.gszone.jfenix13.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Array;
import com.kotcrab.vis.ui.FocusManager;
import com.kotcrab.vis.ui.widget.VisWindow;
import org.gszone.jfenix13.Main;
import org.gszone.jfenix13.connection.ClientPackages;

import static org.gszone.jfenix13.general.FileNames.getGuiDir;

/**
 * Ventana del juego
 *
 * texts: array de texturas utilizadas en la ventana
 */
public class Window extends VisWindow {

    private Array<Texture> texs;
    private boolean fadeOutActionRunning;

    public Window(String title) {
        this(title, true);
    }

    public Window(String title, boolean closable) {
        super(title);
        centerWindow();

        if (closable) {
            closeOnEscape();
            addCloseButton();
        }
        texs = new Array();
    }

    @Override
    protected void close() {
        super.close();
    }

    /**
     * El siguiente método es IGUAL al de la clase VisWindows, a excepción de que libera
     * las texturas utilizadas en la ventana
     */
    @Override
    public void fadeOut (float time) {
        if (fadeOutActionRunning) return;
        fadeOutActionRunning = true;
        final Touchable previousTouchable = getTouchable();
        setTouchable(Touchable.disabled);
        Stage stage = getStage();
        if (stage != null && stage.getKeyboardFocus() != null && stage.getKeyboardFocus().isDescendantOf(this)) {
            FocusManager.resetFocus(stage);
        }
        addAction(Actions.sequence(Actions.fadeOut(time, Interpolation.fade), new Action() {
            @Override
            public boolean act (float delta) {
                setTouchable(previousTouchable);
                remove();
                getColor().a = 1f;

                // Libera texturas
                for (Texture tex : texs)
                    tex.dispose();

                fadeOutActionRunning = false;

                return true;
            }
        }));
    }

    protected Drawable getDrawable(String name) {
        return getDrawable(name, true);
    }

    /**
     * Devuelve una textura contenida en el directorio de la GUI
     * (mismo método que el de la clase View)
     *
     * @param name nombre de la imagen
     * @param borders indica si tiene bordes (generalmente para poder redimensionar, mover, etc.)
     * @return ninepatchDrawable con el fondo
     */
    protected Drawable getDrawable(String name, boolean borders) {
        Texture tex = new Texture(getGuiDir(name));
        texs.add(tex);

        /* Toma una imagen y la convierte en NinePatch, para poder definirle bordes y así permitir mover ventanas
         (si fuera éste el caso) */
        NinePatch n = new NinePatch(tex);
        if (borders)
            n.setPadding(14, 14, 20, 20); // TODO: poner valores convenientes para que funcione bien el resizado y mover.
        return new NinePatchDrawable(n);
    }

    protected ClientPackages getClPack() { return Main.getInstance().getConnection().getClPack(); }
}
