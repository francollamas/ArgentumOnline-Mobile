package org.gszone.jfenix13.actors;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.utils.Array;
import com.kotcrab.vis.ui.widget.VisTable;

/**
 * Grilla abstracta que aloja slots.
 * Permite Drag-Drop interno y externo
 *
 * Se usa para Inventario, Hechizos, Banco, etc...
 */
public abstract class Grid extends VisTable {

    private int slotsPerRow;
    private Slot selected;

    public Grid(Class<? extends Slot> clase, int cantSlots, int slotsPerRow) {
        super(true);
        background("transparente3");

        this.slotsPerRow = slotsPerRow;

        for (int i = 0; i < cantSlots; i++) {
            try {
                add(clase.newInstance());
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Agrega un nuevo slot en la fila correspondiente
     * Esta funcion debe ser llamada únicamente por el constructor de la Grilla.
     */
    @Override
    public <T extends Actor> Cell<T> add(T actor) {
        Cell<T> c = super.add(actor);

        if ((getCells().size + 1) % slotsPerRow == 1) row();
        return c;
    }

    public void setSlot(int index, Slot s) {
        index--;
        Array<Cell> arr = getCells();
        if (index < 0 || index >= arr.size) return;

        arr.get(index).setActor(s);
    }

    /**
     * Busca en las celdas de la Tabla y devuelve un Slot específico
     */
    public Slot getSlot(int index) {
        index--;
        Array<Cell> arr = getCells();
        if (index < 0 || index >= arr.size) return null;
        return (Slot)arr.get(index).getActor();
    }

    public Slot getSelected() {
        return selected;
    }
}
