package org.gszone.jfenix13.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.utils.Array;
import com.kotcrab.vis.ui.widget.VisTable;
import org.gszone.jfenix13.Main;
import org.gszone.jfenix13.connection.ClientPackages;

import static com.badlogic.gdx.Application.ApplicationType.*;

/**
 * Grilla de Slots...
 * (T) es un objeto que hereda de Slot, puede ser por ejemplo un Item o un Hechizo
 *
 * NOTAS:
 * 1. todos los métodos que requieren un índice de Slot, obtienen y/o devuelven un número a partir de 1.
 * (internamente se trabaja a partir del 0).
 * 2. La Tabla contiene SOLAMENTE slots.. ya que para trabajar con ellos se recorre el contenido de ésta.
 * No intentar agregarle otro tipo de Actor...
 *
 * slotsPerRow: cantidad de slots por fila.
 * selected: índice del Slot seleccionado (comenzando desde 1)
 * dd: objeto que controla el Drag-Drop de los Slots.
 */
public abstract class Grid<T extends Slot> extends VisTable {

    private int slotsPerRow;
    private int selected;

    private DragAndDrop dd;

    public Grid(int cantSlots) {
        this(cantSlots, 5, 18, false);
    }

    /**
     * Construye la grilla.
     *
     * @param cantSlots cantidad de slots que tiene
     * @param slotsPerRow cantidad de slots admitidos por fila.
     * @param ddTapSquareSize cantidad de pixeles que tiene que recorrer el mouse cuando arrastra para activar el DD.
     * @param allowDD indica si debe hacer Drag-Drop interno
     */
    public Grid(int cantSlots, int slotsPerRow, int ddTapSquareSize, boolean allowDD) {
        super(Gdx.app.getType() == Desktop || Gdx.app.getType() == WebGL);
        // En Escritorio y Web los slots tienen una separación, en móviles no.

        this.slotsPerRow = slotsPerRow;
        selected = -1;

        if (allowDD) {
            dd = new DragAndDrop();
            dd.setTapSquareSize(ddTapSquareSize);

            /* Seteando este valor en 0 podemos hacer drag-drop en un instante..
            (quizás no es lo deseado, ya que podríamos hacer drag-drop accidentamnete cuando agitamos,
            de tan rápido que se mueve el mouse).
            */
            dd.setDragTime(25);
        }

        for (int i = 0; i < cantSlots; i++) {
            /* Creo un nuevo Slot vacío (probablemente inutilizable), que se agrega en esta Grilla.
               Este paso es necesario para que se vean rectángulos de Slots sin ocupar
             */
            Slot s = new Slot(this);
            s.setDisabled(true);

            if (dd != null) {
                // Como es inutilizable, simplemente lo definimos como Target (para dropear en ese Slot)
                // No lo definimos como source porque no se debe draguear si no es nada.
                dd.addTarget(new SlotTarget(s));
            }

            // Lo agregamos a la Grilla.
            add(s);
        }
    }

    /**
     * Obtiene un slot
     *
     * Lo devuelve solo si no está vacío
     */
    public T getSlot(int index) {
        index--;
        if (index < 0 || index >= getCells().size) return null;
        Slot s = (Slot)getCells().get(index).getActor();
        if (s.isEmpty()) return null;

        return (T)s;
    }

    /**
     * Coloca un Slot pasado por parámetro en una posición determinada (reemplazando al que había antes)
     */
    public void setSlot(T slot, int index) {
        index--;
        if (index < 0 || index >= getCells().size) return;

        slot.setGrid(this);

        // Agrego al slot a la lista de orígenes y destinos para hacer Drag-Drop
        if (dd != null) {
            dd.addSource(new SlotSource(slot));
            dd.addTarget(new SlotTarget(slot));
        }

        // Hago el cambio de Slot...
        getCells().get(index).setActor(slot);

        // Si el anterior estaba seleccionado, selecciono éste.
        if (selected == index + 1)
            setSelected(slot);
    }

    /**
     * Devuelve el índice de un Slot
     */
    public int indexOf(Slot slot) {
        Array<Cell> cells = getCells();
        for (int i = 0; i < cells.size; i++)
            if (cells.get(i).getActor() == slot)
                return i + 1;
        return -1;
    }

    /**
     * Comprueba que los dos slots existen, y obtiene sus índices
     * Si alguno de los dos no existe devuelve null
     */
    public Integer[] getIndexs(Slot slot1, Slot slot2) {
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
     * Indica si la grilla está vacía (aunque tenga muchos slots pero todos con cantidad = 0, está vacía)
     */
    public boolean isEmpty() {
       for (int i = 0; i < getCells().size; i++)
           if (!((Slot)getCells().get(i).getActor()).isEmpty()) return false;
       return true;
    }

    /**
     * Devuelve la cantidad máxima de slots que pueden haber
     */
    public int getCapacity() {
        return getCells().size;
    }

    /**
     * Devuelve la cantidad de Slots utilizables (con cantidad >= 1)
     */
    public int getCantSlots() {
        int cant = 0;
        for (int i = 0; i < getCells().size; i++) {
            Slot s = (Slot)getCells().get(i).getActor();
            if (!s.isEmpty())
                cant++;
        }

        return cant;
    }

    /**
     * Devuelve el elemento seleccionado
     */
    public T getSelected() {
        return getSlot(selected);
    }

    /**
     * Selecciona un Slot si es que está en la lista...
     *
     * Si se pasa un null, desselecciona lo que haya
     */
    public void setSelected(T slot) {
        if (slot == null) {
            if (getSelected() != null) slot.setChecked(false);
            selected = -1;
            return;
        }
        setSelected(indexOf(slot));
    }

    /**
     * Define el elemento seleccionado
     * @param index índice del elemento a seleccionar
     */
    public void setSelected(int index) {
        index--;
        if (index < 0 || index >= getCells().size) return;

        Slot s;
        // Si hay un elemento seleccionado, le quitamos la selección...
        s = getSelected();
        if (selected != -1 && s != null)
            s.setChecked(false);

        // Seleccionamos el nuevo elemento (si está vacío o inutilizable, quita la selección)
        selected = index + 1;
        s = getSelected();
        if (s != null)
            s.setChecked(true);
        else
            selected = -1;
    }

    /**
     * Acciones al hacer click sobre el slot
     */
    protected void onClick(T s) {

    }

    /**
     * Acciones al hacer doble click sobre el slot
     */
    protected void onDblClick(T s) {

    }

    /**
     * Acciones al hacer click derecho sobre el slot
     */
    protected void onRightClick(T s) {

    }

    /**
     * Acciones al hacer doble click derecho sobre el slot
     */
    protected void onRightDblClick(T s) {

    }

    /**
     * Acciones al haberse realizado el Drag-Drop
     *
     * @param s1 slot de origen (debe ser del mismo tipo que T)
     * @param s2 slot de destino (puede ser cualquier tipo de slot, por ejemplo, es de tipo Slot si es uno inicial)
     */
    protected void onDDAction(T s1, Slot s2) {

    }

    protected ClientPackages getClPack() { return Main.getInstance().getConnection().getClPack(); }

}
