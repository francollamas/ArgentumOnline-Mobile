package org.gszone.jfenix13.objects;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import org.gszone.jfenix13.graphics.Drawer;
import org.gszone.jfenix13.utils.Rect;

/**
 * Es un caracter perteneciente a una fuente
 *
 * tr: porcion de textura que le corresponde al caracter
 */
public class Char extends Rect {
    private TextureRegion tr;

    public Char() {
        tr = null;
    }

    public TextureRegion getTR() {
        return tr;
    }

    public void updateTR(int index) {
        tr = Drawer.getTextureRegion(Drawer.TipoTex.FUENTE, index, this);
    }
}
