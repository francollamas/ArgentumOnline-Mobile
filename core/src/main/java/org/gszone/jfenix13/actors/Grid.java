package org.gszone.jfenix13.actors;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.utils.Array;
import com.kotcrab.vis.ui.widget.VisTable;

/**
 * Grilla de Slots...
 * los Slots son contenedores, y ellos tienen un contenido.
 * Ese contenido (T) es un objeto que hereda de Actor, puede ser por ejemplo un Item o un Hechizo
 */
public class Grid<T extends Actor> extends VisTable {

    private int slotsPerRow;
    private int selected;

    private Array<Slot> slots;

    public Grid(int cantSlots) {
        this(cantSlots, 5);
    }

    /**
     * Construye la grilla.
     * @param cantSlots: cantidad de slots que tiene
     * @param slotsPerRow: cantidad de slots admitidos por fila.
     */
    public Grid(int cantSlots, int slotsPerRow) {
        super(true);
        background("transparente3");

        slots = new Array<>();
        this.slotsPerRow = slotsPerRow;

        for (int i = 0; i < cantSlots; i++) {
            // Creo el contenido y lo agrego al nuevo Slot, que se agrega en esta Grilla.
            SlotContent a = new SlotContent();
            a.setSize(32, 32); // tamaño por defecto para un slot vacío...

            // Creo el slot y lo agrego al Array y a la Tabla...
            Slot s = new Slot(a);
            slots.add(s);
            add(s);
        }
    }

    /**
     * Obtiene un contenido (ej: item o hechizo) de un slot determinado del arreglo
     */
    public T getContent(int index) {
        index--;
        if (index < 0 || index >= slots.size) return null;
        if (slots.get(index).isEmpty()) return null;

        return (T)slots.get(index).getActor();
    }

    /**
     * Cambia el contenido de un Slot.
     */
    public void setContent(T content, int index) {
        index--;
        if (index < 0 || index >= slots.size) return;

        slots.get(index).setActor(content);
    }

    /**
     * Misma función ADD de la VisTable, pero si pasa un cierto ancho de elementos sigue abajo
     */
    @Override
    public <T extends Actor> Cell<T> add(T actor) {
        Cell<T> c = super.add(actor);

        if ((getCells().size + 1) % slotsPerRow == 1) row();
        return c;
    }

    /**
     * Indica si la grilla está vacía
     */
    public boolean isEmpty() {
        for (int i = 0; i < slots.size; i++)
            if (!slots.get(i).isEmpty()) return false;
        return true;
    }

}
