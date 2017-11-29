package org.gszone.jfenix13.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.kotcrab.vis.ui.FocusManager;
import com.kotcrab.vis.ui.widget.*;
import org.gszone.jfenix13.managers.PrincipalManager;

import static org.gszone.jfenix13.utils.Actors.*;

public class PrincipalView extends View {

    public PrincipalView() {
        super(new PrincipalManager());
    }
    public PrincipalManager getGestor() { return (PrincipalManager) gestor; }

    private VisLabel lbFps;
    private VisTextField tfSend;

    @Override
    public void show() {
        super.show();

        //stage.setDebugAll(true);

        // Definición de los elementos de la pantalla
        Table first = newFirstTable(true);
            // Titulo, consola, habla y world.
            Table izq = newTable(first).padLeft(13).padTop(13).padBottom(13).padRight(4).getActor();
                Table titulo = newTable(izq).colspan(2).prefHeight(25).left().getActor(); izq.row();
                    lbFps = newLabel(titulo, "FPS: ", Color.WHITE, "bold").left().getActor();
                izq.add(getGestor().getConsola()).colspan(2).width(572).height(99).left().row();
                newSelectBox(izq, bu("pp.t-normal"), bu("pp.t-whisper"), bu("pp.t-clan"), bu("pp.t-shout"), bu("pp.t-role"), bu("pp.t-team"));
                tfSend = newTextField(izq, "", "").fill().expand().getActor(); izq.row();
                tfSend.setMaxLength(160); tfSend.setDisabled(true);
                izq.add(getGestor().getWorld()).colspan(2).row();
                newTable(izq).colspan(2).prefHeight(40);

            Table der = newTable(first).padLeft(4).padTop(13).padBottom(13).padRight(13).expandX().getActor();
                newTable(der).width(240).height(130).top().row();
                newTable(der).width(240).height(285).row();
                newTable(der).width(240).height(285).row();


        // Eventos generales de la pantalla
        stage.addListener(new InputListener() {
            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                // Si toca ENTER y no hay ninguna ventana por encima...
                if (keycode == Input.Keys.ENTER &&
                        !(stage.getActors().get(stage.getActors().size - 1) instanceof VisWindow)) {
                    if (tfSend.isDisabled()) {
                        // Habilito el tfSend
                        tfSend.setDisabled(false);
                        setFocus(tfSend);
                    }
                    else if (FocusManager.getFocusedWidget() != tfSend) {
                        // Si está habilitado pero no en foco, pongo el foco en el tfSend
                        setFocus(tfSend);
                    }
                    else {
                        // Si está habilitado y en foco

                        getGestor().parseCommand(tfSend.getText());
                        tfSend.setText("");
                        tfSend.setDisabled(true);
                    }
                }
                return super.keyUp(event, keycode);
            }

        });
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        lbFps.setText("FPS: " + Gdx.graphics.getFramesPerSecond());
    }
}
