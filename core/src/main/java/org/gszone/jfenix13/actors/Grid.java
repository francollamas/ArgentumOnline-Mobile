package org.gszone.jfenix13.actors;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.kotcrab.vis.ui.widget.VisTable;

/**
 * Grilla abstracta que aloja slots.
 * Permite Drag-Drop interno y externo
 *
 * Se usa para Inventario, Hechizos, Banco, etc...
 */
public abstract class Grid extends VisTable {

    private int slotsPerRow;

    public Grid(int slotsPerRow) {
        super(true);
        background("transparente3");

        this.slotsPerRow = slotsPerRow;
    }

    /**
     * Agrega un slot en la fila correspondiente
     */
    @Override
    public <T extends Actor> Cell<T> add(T actor) {
        Cell<T> c = super.add(actor);

        if ((getCells().size + 1) % slotsPerRow == 1) row();
        return c;
    }

    /**
     * Busca en las celdas de la Tabla y devuelve un Slot específico
     */
    public Slot getSlot(int index) {
        return (Slot)getCells().get(index).getActor();
    }
}
