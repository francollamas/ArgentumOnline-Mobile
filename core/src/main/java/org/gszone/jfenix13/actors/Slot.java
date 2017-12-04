package org.gszone.jfenix13.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Slot: elemento de una Grilla.
 *
 * Tiene un estado (se usa para un Item de inventario, o un Hechizo en particular)
 */
public abstract class Slot extends Actor {
    protected String name;

    public Slot(int width, int height) {
        setSize(width, height);
    }



    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        //Drawer.
    }
}
