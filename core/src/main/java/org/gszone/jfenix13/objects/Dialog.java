package org.gszone.jfenix13.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import org.gszone.jfenix13.graphics.Drawer;
import org.gszone.jfenix13.graphics.FontParameter;
import org.gszone.jfenix13.utils.StrUtils;

/**
 * Representa el díalogo de un PJ o NPC
 *
 * text: array de líneas de texto
 * startTime: momento en que se inició el diálogo
 * lifeTime: tiempo de vida del diálogo
 *
 */
public class Dialog {
    private static final int MAX_LENGTH = 18;

    // Tiempo base que dura un diálogo
    private static final int MS_BASE = 5000;

    // Tiempo adicional por un caracter
    private static final int MS_PER_CHAR = 100;

    private Array<String> text;
    private Color color;
    private long startTime;
    private long lifeTime;

    public Dialog(String text) {
        this(text, Color.WHITE);
    }

    public Dialog(String text, Color color) {
        this.text = StrUtils.getLineas(StrUtils.getFormattedText(text, 8, MAX_LENGTH));
        this.color = color;
        startTime = TimeUtils.millis();
        lifeTime = MS_BASE + MS_PER_CHAR * text.length();
    }

    /**
     * Indica si el dialogo todavía sigue vivo
     */
    public boolean isAlive() {
        return TimeUtils.millis() - startTime < lifeTime;
    }

    public void draw(Batch batch, float x, float y) {
        FontParameter fp = new FontParameter("tahoma13boldhborder");
        fp.setColor(color);
        //Drawer.drawText(batch, text, x, y - 11 * text.size + 12, fp);
        Drawer.drawText(batch, text, x, y - fp.getFont().getLineHeight() * text.size + 18, fp);
    }


}
