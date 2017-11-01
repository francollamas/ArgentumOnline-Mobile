package org.gszone.jfenix13.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.kotcrab.vis.ui.VisUI;
import org.gszone.jfenix13.Main;

/**
 * Created by llama on 31/10/2017.
 */
public class ItemSlot extends Actor {

    private Drawable background;

    public ItemSlot() {
        background = VisUI.getSkin().getDrawable("item-fondo");
        setSize(32, 32);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        background.draw(batch, 0, 16, 32, 32);
    }
}
