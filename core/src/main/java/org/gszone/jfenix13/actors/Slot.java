package org.gszone.jfenix13.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.kotcrab.vis.ui.VisUI;

/**
 * Slot: elemento de una Grilla.
 *
 * Tiene un estado (se usa para un Item de inventario, o un Hechizo en particular)
 * Son solo de 32x32 pixeles
 */
public abstract class Slot extends Actor {
    private static final Color COL_BACKGROUND = new Color(0, 0, 0, 0.6f);
    private static final Color COL_OVER = new Color(0, 0, 0, 0.3f);
    private static final Color COL_CHECKED = new Color(1, 1, 1, 0.15f);

    protected String name;
    private Drawable drawable;
    private boolean disabled;
    private boolean checked;


    public Slot() {
        setSize(32, 32);
        drawable = VisUI.getSkin().getDrawable("slot-fondo");
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
        if (checked) {

        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Color c = batch.getColor();
        batch.setColor(checked ? COL_OVER : COL_BACKGROUND);
        drawable.draw(batch, getX(), getY(), getWidth(), getHeight());
        batch.setColor(c);
    }
}
