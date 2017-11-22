package org.gszone.jfenix13.graphics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Align;
import com.kotcrab.vis.ui.VisUI;
import org.gszone.jfenix13.Main;

/**
 * Contiene los parámetros para renderizar una fuente
 *
 * color: color del texto
 * alpha: cantidad de transparencia que luego se combina con el color.
 * light: indica si brilla o no (si cambia el clima y está activo, el color no se altera)
 *
 */
public class FontParameter {
    private BitmapFont font;
    private Color color;
    private float alpha;
    private boolean light;
    private int align;

    public FontParameter() {
        this("tahoma11");
    }

    public FontParameter(String font) {
        setFont(font);
        setColor(Color.WHITE);
        setAlpha(1f);
        setAlign(Align.center);
    }

    public BitmapFont getFont() {
        return font;
    }

    public void setFont(String font) {
        BitmapFont bmf = VisUI.getSkin().getFont(font);
        this.font = bmf;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public float getAlpha() {
        return alpha;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

    public boolean isLight() {
        return light;
    }

    public void setLight(boolean light) {
        this.light = light;
    }

    public int getAlign() {
        return align;
    }

    public void setAlign(int align) {
        this.align = align;
    }
}
