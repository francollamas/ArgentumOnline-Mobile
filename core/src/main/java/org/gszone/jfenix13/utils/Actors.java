package org.gszone.jfenix13.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Align;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.*;
import com.kotcrab.vis.ui.widget.tabbedpane.TabbedPane;
import com.kotcrab.vis.ui.widget.tabbedpane.TabbedPaneAdapter;
import org.gszone.jfenix13.Main;
import org.gszone.jfenix13.actors.Tab;
import org.gszone.jfenix13.general.Config;
import org.gszone.jfenix13.views.View;

import static com.badlogic.gdx.Application.ApplicationType.Desktop;
import static com.badlogic.gdx.Application.ApplicationType.WebGL;

/**
 * Clase para facilitar la creación de ciertos actores
 */
public class Actors {
    /**
     * Ventana común (pequeña y permite moverla)
     */
    public static VisWindow newWindow(String title, Drawable background) {
        return newWindow(title, background, false, true);
    }

    /**
     * Ventana con título al centro
     */
    public static VisWindow newWindow(String title, Drawable background, boolean fillParent, boolean movable) {
        return newWindow(title, background, Align.center, fillParent, movable);
    }

    public static VisWindow newWindow(String title, Drawable background, int titleAlign, boolean fillParent, boolean movable) {
        VisWindow w = new VisWindow(title);
        if (background != null)
            w.setBackground(background);
        w.getTitleLabel().setAlignment(titleAlign);
        w.setMovable(movable);
        w.centerWindow();
        w.setResizable(false);
        w.setFillParent(fillParent);
        ((View)Main.getInstance().getScreen()).getStage().addActor(w);
        return w;
    }

    /**
     * Redimensiona la ventana al tamaño que debe ocupar, y la centra.
     */
    public static void fitWindow(final VisWindow w) {
        w.pack();
        w.centerWindow();
    }

    public static VisTable newFirstTable() {
        return newFirstTable(null);
    }

    public static VisTable newFirstTable(Drawable background) {
        return newFirstTable(background, false);
    }

    public static VisTable newFirstTable(boolean fillparent) {
        return newFirstTable(null, fillparent);
    }

    /**
     * Crea una nueva tabla sobre el Stage.
     * Es recomendable usarlo de tabla Base
     *
     * @param fillparent indica si ocupa toda la pantalla
     * @return la tabla en Sí.
     */
    public static VisTable newFirstTable(Drawable background, boolean fillparent) {
        VisTable t = new VisTable();
        t.setBackground(background);
        if (fillparent)
            t.setFillParent(true);
        else {
            if (background != null && background instanceof NinePatchDrawable) {
                NinePatch np = ((NinePatchDrawable) background).getPatch();
                t.setWidth(np.getTotalWidth());
                t.setHeight(np.getTotalHeight());
            }

            Config c = Main.getInstance().getConfig();
            t.setPosition((c.getVirtualWidth() - t.getWidth()) / 2, (c.getVirtualHeight() - t.getHeight()) / 2);
        }

        ((View)Main.getInstance().getScreen()).getStage().addActor(t);
        return t;
    }

    public static Cell<VisTable> newTable(Table table) {
        return newTable(table, (Drawable)null, true);
    }

    public static Cell<VisTable> newTable(Table table, boolean setVisDefaults) {
        return newTable(table, (Drawable)null, setVisDefaults);
    }

    public static Cell<VisTable> newTable(Table table, String background, boolean setVisDefaults) {
        return newTable(table, VisUI.getSkin().getDrawable(background), setVisDefaults);
    }

    /**
     * Crea una tabla dentro de otra
     * Cuyas celdas tienen paddings definidos por VisUI.
     *
     * @param table tabla padre
     * @return la Celda, para que sea fácilmente modificable. Se puede obtener la tabla con getActor() ).
     */
    public static Cell<VisTable> newTable(Table table, Drawable background, boolean setVisDefaults) {
        VisTable t = new VisTable(setVisDefaults); // le pasamos true para que use los Paddings por defecto
        t.setBackground(background);
        if (background != null && background instanceof NinePatchDrawable) {
            NinePatch np = ((NinePatchDrawable) background).getPatch();
            t.setWidth(np.getTotalWidth());
            t.setHeight(np.getTotalHeight());
        }
        if (table != null)
            return table.add(t);
        return null;
    }

    public static Cell<VisLabel> newLabel(Table table, String text) {
        return newLabel(table, text, Color.WHITE, "default");
    }

    public static Cell<VisLabel> newLabel(Table table, String text, Object color) {
        return newLabel(table, text, color, "default");
    }

    /**
     * Crea un label
     *
     * El color puede ser un String que haga referencia a un color de la skin, o una instancia de Color.
     */
    public static Cell<VisLabel> newLabel(Table table, String text, Object color, String style) {
        if (color instanceof String)
            color = VisUI.getSkin().getColor((String)color);

        VisLabel l = new VisLabel(text, (Color)color);
        l.setStyle(VisUI.getSkin().get(style, Label.LabelStyle.class));
        return table.add(l);
    }

    public static Cell<VisSelectBox<String>> newSelectBox(Table table, String... items) {
        VisSelectBox<String> sb = new VisSelectBox<>();
        sb.setItems(items);
        return table.add(sb);
    }

    public static Cell<VisTextButton> newTextButton(Table table, String text) {
        VisTextButton tb = new VisTextButton(text, "default");
        return table.add(tb);
    }

    /**
     * Crea un botón con texto dentro
     */
    public static Cell<VisTextButton> newTextButton(Table table, String text, String style) {
        VisTextButton tb = new VisTextButton(text, style);
        return table.add(tb);
    }

    /**
     * Crea un botón con una imagen dentro
     *
     * @param image tiene que coincidir con el nombre de un drawable guardado en la Skin.
     */
    public static Cell<VisImageButton> newImageButton(Table table, String image) {
        return newImageButton(table, VisUI.getSkin().getDrawable(image));
    }

    /**
     * Crea un botón con una imagen dentro
     */
    public static Cell<VisImageButton> newImageButton(Table table, Drawable image) {
        VisImageButton tb = new VisImageButton(image);
        return table.add(tb);
    }


    public static Cell<VisTextField> newTextField(Table table, String text, String message, boolean password) {
        return newTextField(table, text, message, "default", password);
    }

    public static Cell<VisTextField> newTextField(Table table, String text, String message) {
        return newTextField(table, text, message, "default", false);
    }

    public static Cell<VisTextField> newTextField(Table table, String text, String message, String style) {
        return newTextField(table, text, message, style, false);
    }

    /**
     * Crea un textfield
     */
    public static Cell<VisTextField> newTextField(Table table, String text, String message, String style, boolean password) {
        VisTextField tf = new VisTextField(text, style);
        tf.setFocusBorderEnabled(true);
        tf.setMessageText(message);
        if (password) {
            tf.setPasswordMode(true);
            tf.setPasswordCharacter('*');
        }
        return table.add(tf);
    }

    public static Cell<Container> newContainer(Table table) {
        return newContainer(table, null);
    }

    /**
     * Crea un contenedor
     */
    public static Cell<Container> newContainer(Table table, Actor actor) {
        Container c = new Container();
        if (actor != null) {
            c.setActor(actor);
            return table.add(c);
        }
        return null;
    }

    public static Cell<TabbedPane.TabbedPaneTable> newTabbedPane(Table table) {
        return newTabbedPane(table, -1, -1);
    }

    /**
     * Crea un TabbedPane
     */
    public static Cell<TabbedPane.TabbedPaneTable> newTabbedPane(Table table, float width, float height) {
        TabbedPane tp = new TabbedPane();
        tp.getTable().getTabsPaneCell().row();

        Cell<VisTable> celda = newTable(tp.getTable(), false);
        if (width >= 0) celda.width(width);
        if (height >= 0) celda.height(height);

        Table t = celda.getActor();

        tp.addListener(new TabbedPaneAdapter() {
            @Override
            public void switchedTab(com.kotcrab.vis.ui.widget.tabbedpane.Tab tab) {
                t.clearChildren();
                t.add(tab.getContentTable()).expand().fill();
            }
        });

        return table.add(tp.getTable());
    }

    /**
     * Agrega un nuevo Tab al TabbedPane y lo devuelve
     */
    public static Tab newTab(TabbedPane tp, String title) {
        Tab t = new Tab(title);
        tp.add(t);
        tp.switchTab(0);
        return t;
    }

    public static Cell<VisCheckBox> newCheckBox(Table table, String text) {
        VisCheckBox c = new VisCheckBox(text);
        return table.add(c);
    }

    public static Cell<VisRadioButton> newRadioButton(Table table, String text) {
        VisRadioButton c = new VisRadioButton(text);
        return table.add(c);
    }

    /**
     * Hace foco en un textfield (excepto en Android/iOS)
     */
    public static void setTfFocus(VisTextField tf) {
        setTfFocus(tf, false);
    }

    /**
     * Hace foco en un textfield (se puede o no excluir móbiles)
     */
    public static void setTfFocus(VisTextField tf, boolean forceInMobile) {
        // Si se está en dispositivos móviles, no hacemos foco (para que no salga el teclado de repente)
        if (!forceInMobile && Gdx.app.getType() != Desktop && Gdx.app.getType() != WebGL) return;
        tf.focusField();
        ((View)Main.getInstance().getScreen()).getStage().setKeyboardFocus(tf);
    }
}
