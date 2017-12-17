package org.gszone.jfenix13.actors;

import static org.gszone.jfenix13.containers.GameData.ObjTypes.*;

/**
 * Inventario del usuario
 */
public class UserInventory extends Grid<Item> {
    public static final int MAX_INVENTORY_SLOTS = 30;

    public UserInventory() {
        super(MAX_INVENTORY_SLOTS, 5, 18, true);
    }

    @Override
    protected void onDDAction(Item s1, Slot s2) {
        Integer[] arr = getIndexs(s1, s2);

        // Solicito intercambiarlos...
        if (arr != null)
            getClPack().writeMoveItem(arr[0], arr[1]);
    }

    @Override
    protected void onDblClick(Item i) {
        // TODO: verificar que REALMENTE estén TODOS los objetos equipables, y que no haya ninguno de otro tipo
        if (i.getTipo() == Armadura || i.getTipo() == Casco || i.getTipo() == Escudo || i.getTipo() == Weapon
                || i.getTipo() == Anillo || i.getTipo() == Instrumentos || i.getTipo() == Mochilas)
            equipItem(i);
        else
            usarItem(i);
    }

    private void usarItem(Item i) {
        // TODO: muchas comprobaciones (intervalos, etc, etc).
        getClPack().writeUseItem(indexOf(i));
    }

    private void equipItem(Item i) {
        // TODO: muchas comprobaciones (intervalos, etc, etc).
        getClPack().writeEquipItem(indexOf(i));
    }
}
