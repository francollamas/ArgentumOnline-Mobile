package org.gszone.jfenix13.actors;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.utils.Array;
import com.kotcrab.vis.ui.widget.VisTable;

/**
 * Grilla de Slots...
 * (T) es un objeto que hereda de Slot, puede ser por ejemplo un Item o un Hechizo
 */
public class Grid<T extends Slot> extends VisTable {

    private int slotsPerRow;
    private int selected;

    private DragAndDrop dd;

    public Grid(int cantSlots) {
        this(cantSlots, 5);
    }

    public Grid(int cantSlots, int slotsPerRow) {
        this(cantSlots, slotsPerRow, true);
    }

    /**
     * Construye la grilla.
     * @param cantSlots: cantidad de slots que tiene
     * @param slotsPerRow: cantidad de slots admitidos por fila.
     */
    public Grid(int cantSlots, int slotsPerRow, boolean padding) {
        super(padding);

        this.slotsPerRow = slotsPerRow;
        selected = -1;

        dd = new DragAndDrop();
        dd.setTapSquareSize(18);

        /* Seteando este valor en 0 podemos hacer drag-drop en un instante..
        (quizás no es lo deseado, ya que podríamos hacer drag-drop accidentamnete cuando agitamos,
        de tan rápido que se mueve el mouse).
        */
        dd.setDragTime(100);

        for (int i = 0; i < cantSlots; i++) {
            // Creo un nuevo Slot vacío, que se agrega en esta Grilla.
            Slot s = new Slot();
            s.setDisabled(true);
            s.setGrid(this);

            dd.addTarget(new SlotTarget(s));
            add(s);
        }
    }

    /**
     * Obtiene un slot (ej: item, hechizo, etc)
     */
    public T getSlot(int index) {
        index--;
        if (index < 0 || index >= getCells().size) return null;
        Slot s = (Slot)getCells().get(index).getActor();
        if (s.isEmpty()) return null;

        return (T)s;
    }

    /**
     * Cambia el contenido de un Slot.
     */
    public void setSlot(T content, int index) {
        index--;
        if (index < 0 || index >= getCells().size) return;

        content.setGrid(this);
        // Le agrego DragAndDrop a los contenidos nuevos.
        dd.addSource(new SlotSource(content));
        dd.addTarget(new SlotTarget(content));

        getCells().get(index).setActor(content);

        if (selected == index + 1)
            setSelected(content);
    }

    /**
     * Devuelve el índice de un Slot (indice del servidor, comenzando desde 1)
     */
    public int indexOf(T slot) {
        Array<Cell> cells = getCells();
        for (int i = 0; i < cells.size; i++) {
            if (cells.get(i).getActor() == slot) {
                return i + 1;
            }
        }
        return -1;
    }

    /**
     * Comprueba que los dos slots existen, y obtiene sus índices
     * Si alguno de los dos no existe devuelve null
     */
    public Integer[] getIndexs(T slot1, T slot2) {
        int index1 = indexOf(slot1);
        int index2 = indexOf(slot2);
        if (index1 == -1) return null;

        Integer[] arr = new Integer[2];
        arr[0] = index1;
        arr[1] = index2;

        return arr;
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
     * Indica si la grilla está vacía (puede tener muchos slots pero todos con cantidad = 0)
     */
    public boolean isEmpty() {
       for (int i = 0; i < getCells().size; i++)
           if (!((Slot)getCells().get(i).getActor()).isEmpty()) return false;
       return true;
    }

    public int getCapacity() {
        return getCells().size;
    }

    public int getCantSlots() {
        int cant = 0;
        for (int i = 0; i < getCells().size; i++) {
            Slot s = (Slot)getCells().get(i).getActor();
            if (!s.isEmpty())
                cant++;
        }

        return cant;
    }

    public void setSelected(T slot) {
        setSelected(indexOf(slot));
    }

    public void setSelected(int index) {
        index--;
        if (index < 0 || index >= getCells().size) return;

        Slot s;
        s = getSlot(selected);
        if (selected != -1 && s != null)
            s.setChecked(false);


        selected = index + 1;
        s = getSlot(selected);
        if (s != null)
            s.setChecked(true);
        else
            selected = -1;
    }

}
