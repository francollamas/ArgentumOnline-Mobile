package org.gszone.jfenix13.screens;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import org.gszone.jfenix13.general.Main;

/**
 * Pantalla de carga
 */
public class Carga extends Screen {
    ProgressBar pb;
    Label lb1;
    Label lb2;

    public Carga() { super(Scr.CARGA); }

    @Override
    public void show() {
        super.show();
        pb = new ProgressBar(0, 1, 0.0625f, false, getSkin());
        pb.setPosition(0, 20);
        pb.setAnimateDuration(0.15f);
        pb.setSize(stage.getWidth(), 40);

        lb1 = new Label("Cargando...", getSkin());
        lb1.setPosition(pb.getX() + 10, pb.getY() + pb.getHeight() + 20);
        lb1.setFontScale(1.2f);

        lb2 = new Label("0 %", getSkin());
        lb2.setPosition(pb.getX() + pb.getWidth() - 50, pb.getY() + pb.getHeight() + 20);


        stage.addActor(pb);
        stage.addActor(lb1);
        stage.addActor(lb2);

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        stage.draw();

        float value;

        lb2.setText((int) (pb.getVisualValue() * 100) + " %");
        lb2.setFontScale(1.2f);

        if (pb.getVisualValue() == pb.getValue()) {
            if (pb.getValue() == pb.getMaxValue()) {
                /* Hacemos finishloading porque al trabajar con el progressbar redondea los valores
                haciendo que muestre que terminó la carga cuando en realizad falta muy poco */
                Main.getInstance().getAssets().getGDXAssets().finishLoading();

                // Cargamos todos los demás assets
                Main.getInstance().getAssets().loadRemaining();
                Main.getInstance().setScreen(new Principal());
            }
            value = Main.getInstance().getAssets().loadNextAsset();
            pb.setValue(value);
        }
    }
}