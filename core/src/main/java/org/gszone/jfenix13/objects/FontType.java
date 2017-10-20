package org.gszone.jfenix13.objects;

import com.badlogic.gdx.graphics.Color;

/**
 * Define un tipo de fuente
 *
 * color: color de la fuente
 * style: nombre del estilo que va a tener el label. (Se puede consultar los distintos estilos
 *        en la propiedad 'style' del Label, en el Skin Composer).
 */
public class FontType {
    private Color color;
    private String style;

    public FontType() {
        this(Color.WHITE);
    }

    public FontType(Color c) {
        color = c;
        style = "normal";
    }

    public FontType(Color c, String style) {
        color = c;
        this.style = style;
    }

    public Color getColor() {
        return color;
    }

    public String getStyle() {
        return style;
    }
}
