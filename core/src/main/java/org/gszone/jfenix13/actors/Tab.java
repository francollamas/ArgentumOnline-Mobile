package org.gszone.jfenix13.actors;


import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.kotcrab.vis.ui.widget.VisTable;

/**
 * Representa un Tab de un TabPane
 *
 * title: título del Tab
 * content: tabla con el contenido
 */
public class Tab extends com.kotcrab.vis.ui.widget.tabbedpane.Tab {
    private String title;
    private Table content;

    public Tab(String title) {
        this(title, false);
    }

    public Tab(String title, boolean closeable) {
        super(false, closeable);
        this.title = title;
        content = new VisTable(true);
    }

    @Override
    public String getTabTitle() {
        return title;
    }

    @Override
    public Table getContentTable() {
        return content;
    }
}
