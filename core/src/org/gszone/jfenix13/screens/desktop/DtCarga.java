package org.gszone.jfenix13.screens.desktop;

import com.kotcrab.vis.ui.widget.*;
import org.gszone.jfenix13.general.Main;
import org.gszone.jfenix13.screens.Screen;

/**
 * Pantalla de carga
 */
public class DtCarga extends Screen {
    private VisLabel lb;

    public DtCarga() { super(Scr.CARGA, "carga"); }

    @Override
    public void show() {
        super.show();

        lb = new VisLabel("0 %");
        lb.setPosition(950, 30);
        lb.setColor(0.7f, 0.7f, 0.7f, 1);
        stage.addActor(lb);
        Main.getInstance().getAssets().getAudio().playMusic(6);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        getBatch().begin();
        background.draw(getBatch(), 1);
        getBatch().end();

        stage.draw();

        float value;
        value = Main.getInstance().getAssets().loadNextAsset();

        lb.setText((int) (value * 100) + " %");

        if (value == 1f) {
            Main.getInstance().getAssets().loadRemaining();
            Main.getInstance().setScreen(new DtMenu());
        }
    }
}