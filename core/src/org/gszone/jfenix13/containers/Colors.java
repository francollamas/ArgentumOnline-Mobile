package org.gszone.jfenix13.containers;


import com.badlogic.gdx.graphics.Color;

/**
 * Contiene los colores de los nicks de los PJs.
 * (está hardcodeado)
 *
 * colors: array de colores
 */
public class Colors {
    private Color[] colors;

    public Colors() {
        colors = new Color[11];
        colors[0] = new Color(0, 0.5f, 1, 1); // ciudadano
        colors[1] = new Color(1, 0, 0, 1); // criminal
        colors[2] = new Color(0, 0.9411f, 0, 1); // newbie
        colors[3] = new Color(0.745f, 0.745f, 0.745f, 1); // neutral

        colors[4] = new Color(1, 0.5f, 0.2509f, 1); // consejero
        colors[5] = new Color(1, 0.5f, 0.2509f, 1); // semidioses
        colors[6] = new Color(1, 0.5f, 0.2509f, 1); // dioses
        colors[7] = new Color(1, 0.5f, 0.2509f, 1); // admins

        colors[8] = new Color(0.7058f, 0.7058f, 0.7058f, 1); // rol master
        colors[9] = new Color(1, 0.5f, 0, 1); // consejo del caos
        colors[10] = new Color(0, 0.7647f, 1, 1); // consejo de bander
    }

    /**
     * Obtiene el color correspondiente según privilegios y bando
     */
    public Color getColor(int priv, byte bando) {
        if (priv == 0)
            return colors[bando - 2];
        else
            return colors[priv + 3];
    }
}
