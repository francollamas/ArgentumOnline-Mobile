package org.gszone.jfenix13.actors;

public class Inventory extends Grid {

    public Inventory(int cantSlots, int slotsPerRow) {
        super(Item.class, cantSlots, slotsPerRow);
    }
}
