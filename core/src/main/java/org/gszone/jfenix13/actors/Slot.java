package org.gszone.jfenix13.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.kotcrab.vis.ui.VisUI;

public class Slot extends Container {
    // TODO: constantes viejas, borrarlas si no las uso
    private static final Color COL_BACKGROUND = new Color(0, 0, 0, 0.6f);
    private static final Color COL_OVER = new Color(0, 0, 0, 0.3f);
    private static final Color COL_CHECKED = new Color(1, 1, 1, 0.15f);

    public Slot(Actor a) {
        background(VisUI.getSkin().getDrawable("transparente1"));
        setActor(a);
    }

    public boolean isEmpty() {
        // Definimos como vacío al Slot, si contiene simplemente un Actor (y no un ? que herede de Actor)
        return getActor().getClass() == SlotContent.class;
    }
}
