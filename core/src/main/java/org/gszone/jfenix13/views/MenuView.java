package org.gszone.jfenix13.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.github.czyzby.lml.annotation.LmlActor;
import com.kotcrab.vis.ui.widget.VisTextField;

import static org.gszone.jfenix13.general.FileNames.getViewDir;

public class MenuView extends View {
    public static final String ID = "menu";

    @LmlActor("log-nombre") private VisTextField tfLogNombre;

    @Override
    public String getViewId() { return ID; }

    @Override
    public FileHandle getTemplateFile() {
        return Gdx.files.internal(getViewDir(ID));
    }

    @Override
    public void show() {

        // Eventos generales del teclado
        getStage().addListener(new InputListener() {
            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                if (keycode == Input.Keys.ESCAPE)
                    Gdx.app.exit();
                return super.keyUp(event, keycode);
            }
        });


        // Hago foco en el nombre del loggin
        tfLogNombre.focusField();
        getStage().setKeyboardFocus(tfLogNombre);
    }
}
