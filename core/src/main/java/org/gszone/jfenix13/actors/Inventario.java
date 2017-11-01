package org.gszone.jfenix13.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.kotcrab.vis.ui.widget.VisTable;

public class Inventario extends VisTable {
    public Inventario() {

        defaults().pad(1);
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                add(new ItemSlot());
            }
            row();
        }

        layout();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }
}
