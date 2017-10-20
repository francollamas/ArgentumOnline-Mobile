package org.gszone.jfenix13.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.github.czyzby.lml.annotation.LmlActor;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextField;

import static org.gszone.jfenix13.general.FileNames.getViewDir;

public class PrincipalView extends View {
    public static final String ID = "principal";

    @LmlActor("fondo") private VisTable fondo;
    @LmlActor("consola") private Container ctConsola;
    @LmlActor("send") private VisTextField tfSend;
    @LmlActor("world") private Container ctWorld;

    @Override
    public String getViewId() { return ID; }

    @Override
    public FileHandle getTemplateFile() {
        return Gdx.files.internal(getViewDir(ID));
    }

    @Override
    public void show() {
        fondo.setBackground(getBackground()); // TODO: descomentar y agregar un fondo!

        getStage().addListener(new InputListener() {
            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                if (keycode == Input.Keys.ENTER) {
                    if (tfSend.isDisabled()) {
                        tfSend.setDisabled(false);
                        setTfFocus(tfSend);
                    }
                    else if (getStage().getKeyboardFocus() != tfSend) {
                        setTfFocus(tfSend);
                        tfSend.setCursorAtTextEnd();
                    }
                    else {
                        // TODO: acción de enviar!!!
                        getGD().getConsola().addMessage(tfSend.getText());
                        tfSend.setText("");
                        tfSend.setDisabled(true);
                    }
                }
                return super.keyUp(event, keycode);
            }

            });

        //getStage().setDebugAll(true);

        // Agrego el mundo y consola al Stage
        ctConsola.setActor(getGD().getConsola());
        ctWorld.setActor(getGD().getWorld());

    }

    @Override
    public void render(float delta) {
        super.render(delta);
    }

}