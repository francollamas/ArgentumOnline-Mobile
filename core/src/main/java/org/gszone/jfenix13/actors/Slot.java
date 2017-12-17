package org.gszone.jfenix13.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.kotcrab.vis.ui.VisUI;
import org.gszone.jfenix13.Main;
import org.gszone.jfenix13.graphics.DrawParameter;
import org.gszone.jfenix13.graphics.Drawer;

/**
 * Slot (es un elemento de la Grilla)
 *
 * Estáticos:
 * -- colores
 * -- background (imagen de fondo del slot) y border (imagen cuadrada que rodea al slot)
 *
 * backgroundColor: color de fondo del Slot.
 * nombre: nombre del slot
 * TODO: completar
 */
public class Slot extends Actor {
    private static final Color COL_BACKGROUND = new Color(0, 0, 0, 0.4f);
    private static final Color COL_DISABLED = new Color(0.05f, 0, 0, 0.4f);
    private static final Color COL_CHECKED = new Color(1, 1, 0.5f, 1);

    private static Drawable background;
    private static Drawable border;

    private Color backgroundColor;

    private String nombre;
    private int cantidad;
    private int grh;
    private int grhWidth;
    private int grhHeight;

    private boolean checked;
    private boolean visible;
    private boolean disabled;
    private Grid grid;

    public Slot() {
        this(null);
    }

    public Slot(Grid grid) {
        this(false);
        backgroundColor = COL_BACKGROUND;
        visible = true;
        this.grid = grid;
    }

    public Slot(boolean big) {

        if (!big) {
            // Normales
            if (background == null || border == null) {
                background = VisUI.getSkin().getDrawable("slot38x38");
                border = VisUI.getSkin().getDrawable("slot-border38x38");
            }
            setSize(38, 38);
        }

        else {
            // Grandes
            // TODO: cargar drawables y configurar tamaño para slots más grandes.
        }

        addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                if (getCantidad() == 0 || grid == null || isDisabled()) return;

                if (button == 0) {
                    if (getTapCount() == 1) {
                        if (!isChecked())
                            getGrid().setSelected(Slot.this);
                        getGrid().onClick(Slot.this);
                    } else if (getTapCount() == 2) getGrid().onDblClick(Slot.this);
                }
                else if (button == 1) {
                    if (getTapCount() == 1)
                        getGrid().onRightClick(Slot.this);
                    else if (getTapCount() == 2)
                        getGrid().onRightDblClick(Slot.this);
                }

                if (getTapCount() >= 2) setTapCount(0);
            }
        });
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        float x = getX();
        float y = Main.getInstance().getConfig().getVirtualHeight() - getY() - getHeight();

        // Guardarmos el color que ya tenía el batch
        Color c = batch.getColor();

        // Dibujamos el fondo
        batch.setColor(backgroundColor);
        background.draw(batch, getX(), getY(), getWidth(), getHeight());
        batch.setColor(c);

        // Si está vacío o invisible no mostramos nada
        if (getCantidad() == 0 || !isVisible()) return;

        // Dibujamos el checked
        if (checked) {
            batch.setColor(COL_CHECKED);
            border.draw(batch, getX(), getY(), getWidth(), getHeight());
        }
        batch.setColor(c);

        DrawParameter dp = new DrawParameter();
        dp.setColor(getColor());
        Drawer.drawGrh(batch, getGrh(), x + getWidth() / 2 - getGrhWidth() / 2, y + getHeight() / 2 - getGrhHeight() / 2, dp);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public boolean isEmpty() {
        return cantidad == 0;
    }

    public int getGrh() {
        return grh;
    }

    public void setGrh(int grh) {
        this.grh = grh;
    }

    public void setGrhSize(int width, int height) {
        grhWidth = width;
        grhHeight = height;
    }

    public int getGrhWidth() {
        return grhWidth;
    }

    public void setGrhWidth(int grhWidth) {
        this.grhWidth = grhWidth;
    }

    public int getGrhHeight() {
        return grhHeight;
    }

    public void setGrhHeight(int grhHeight) {
        this.grhHeight = grhHeight;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public Grid getGrid() {
        return grid;
    }

    public void setGrid(Grid grid) {
        this.grid = grid;
    }

    public Color getColBackground() {
        return backgroundColor;
    }

    public void setColBackground(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public void removeColBackground() {
        this.backgroundColor = COL_BACKGROUND;
    }

    @Override
    public boolean isVisible() {
        return visible;
    }

    @Override
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
        if (disabled)
            setColBackground(COL_DISABLED);
        else
            removeColBackground();
    }
}
