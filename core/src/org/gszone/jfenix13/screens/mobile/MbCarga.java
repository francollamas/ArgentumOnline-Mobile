package org.gszone.jfenix13.screens.mobile;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.kotcrab.vis.ui.widget.VisLabel;
import org.gszone.jfenix13.general.Main;
import org.gszone.jfenix13.screens.Screen;
import org.gszone.jfenix13.screens.desktop.DtPrincipal;

public class MbCarga extends Screen {
    VisLabel lb;

    public MbCarga() { super(Screen.Scr.CARGA, "carga"); }

    @Override
    public void show() {
        super.show();

        lb = new VisLabel("0 %");
        lb.setPosition(580, 20);
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
            Main.getInstance().setScreen(new MbMenu());
        }
    }
}
