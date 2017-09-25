package org.gszone.jfenix13.listeners;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import org.gszone.jfenix13.handlers.WorldHandler;

/**
 * Listener del mundo
 * Lo que pasa cuando se interactúa con el mundo (mouse y teclado)
 *
 * h: es el handler del mundo, conjunto de características que determinan que hacer según los eventos sucedidos
 */
public class WorldListener extends DragListener {

    private WorldHandler h;

    public WorldListener(WorldHandler h) {
        this.h = h;
    }

    @Override
    public boolean mouseMoved(InputEvent event, float x, float y) {
        h.getPos().setX(x);
        h.getPos().setY(y);
        h.setMoved(true);
        return super.mouseMoved(event, x, y);
    }
}
