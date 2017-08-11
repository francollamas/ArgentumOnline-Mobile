package org.gszone.jfenix13.screens.desktop;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import org.gszone.jfenix13.general.Main;
import org.gszone.jfenix13.screens.Screen;

/**
 * Pantalla de carga
 */
public class DtCarga extends Screen {
    Label lb;

    public DtCarga() { super(Scr.CARGA, "dt_carga"); }

    @Override
    public void show() {
        super.show();

        lb = new Label("0 %", getSkin());
        lb.setPosition(950, 30);
        lb.setFontScale(1.2f);
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
            Main.getInstance().setScreen(new DtPrincipal());
        }
    }
}