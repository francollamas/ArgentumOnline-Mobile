package org.gszone.jfenix13.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.kotcrab.vis.ui.widget.*;
import org.gszone.jfenix13.actors.Inventario;

import static org.gszone.jfenix13.utils.Actors.*;

public class PrincipalView extends View {

    private VisLabel lbFps;
    private VisTextField tfSend;

    @Override
    public void show() {
        super.show();

        //stage.setDebugAll(true);

        Table ft = newFirstTable(true);
            // Titulo, consola, habla y world.
            Table t1 = newTable(ft).padBottom(4).getActor();
            t1.align(Align.left);
                lbFps = newLabel(t1, "FPS: ", Color.WHITE, "bold").align(Align.left).colspan(2).getActor(); t1.row();
                t1.add(getGD().getConsola()).colspan(2).width(getGD().getWorld().getWidth()).height(100).row();
                newSelectBox(t1, bu("pp.t-normal"), bu("pp.t-whisper"), bu("pp.t-clan"), bu("pp.t-shout"), bu("pp.t-role"), bu("pp.t-team"));
                tfSend = newTextField(t1, "", "").fill().expand().getActor(); t1.row();
                tfSend.setMaxLength(160); tfSend.setDisabled(true);
                t1.add(getGD().getWorld()).colspan(2).row();


        // TODO: hacer que el evento del enter funcione solo si no hay otras pantallas encima
        getStage().addListener(new InputListener() {
            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                if (keycode == Input.Keys.ENTER) {
                    if (tfSend.isDisabled()) {
                        // Habilito el tfSend
                        tfSend.setDisabled(false);
                        setTfFocus(tfSend);
                    }
                    else if (getStage().getKeyboardFocus() != tfSend) {
                        // Si está habilitado pero no en foco, pongo el foco en el tfSend
                        setTfFocus(tfSend);
                        tfSend.setCursorAtTextEnd();
                    }
                    else {
                        // Si está habilitado y en foco
                        // TODO: acción de enviar!!!
                        getGD().getCommands().parse(tfSend.getText());
                        tfSend.setText("");
                        tfSend.setDisabled(true);

                        // TODO: acá en VB6, hace foco al inventario
                    }
                }
                return super.keyUp(event, keycode);
            }

        });

        // Agrego todos los elementos a la pantalla
        //ctInventario.setActor(new Inventario());
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        lbFps.setText("FPS: " + Gdx.graphics.getFramesPerSecond());
    }
}
