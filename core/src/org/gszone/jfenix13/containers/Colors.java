package org.gszone.jfenix13.containers;


import com.badlogic.gdx.graphics.Color;

/**
 * Contiene los colores de los nicks de los PJs.
 *
 * colors: array de colores
 */
public class Colors {
    private Color[] colors;

    public Colors() {
        colors = new Color[11];
        colors[0] = newColor(0, 128, 255); // ciudadano
        colors[1] = newColor(255, 0, 0); // criminal
        colors[2] = newColor(0, 240, 0); // newbie
        colors[3] = newColor(190, 190, 190); // neutral

        colors[4] = newColor(255, 128, 64); // consejero
        colors[5] = newColor(255, 128, 64); // semidioses
        colors[6] = newColor(255, 128, 64); // dioses
        colors[7] = newColor(255, 128, 64); // admins

        colors[8] = newColor(180, 180, 180); // rol master
        colors[9] = newColor(255, 128, 0); // consejo del caos
        colors[10] = newColor(0, 195, 255); // consejo de bander
    }

    public static Color newColor(int r, int g, int b) {
        return newColor(r, g, b, 255);
    }

    public static Color newColor(int r, int g, int b, int a) {
        return new Color(r / 255.0f, g / 255.0f, b / 255.0f, a / 255.0f);
    }

    /**
     * Obtiene el color correspondiente según privilegios y bando
     */
    public Color getColor(int priv, int bando) {
        if (priv == 0)
            return colors[bando - 2];
        else
            return colors[priv + 3];
    }
}
