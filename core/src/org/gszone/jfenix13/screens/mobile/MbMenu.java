package org.gszone.jfenix13.screens.mobile;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.kotcrab.vis.ui.util.dialog.Dialogs;
import com.kotcrab.vis.ui.widget.VisTextButton;
import com.kotcrab.vis.ui.widget.VisTextField;
import org.gszone.jfenix13.general.Main;
import org.gszone.jfenix13.screens.Screen;

/**
 * Created by llama on 9/8/2017.
 */
public class MbMenu extends Screen {
    private VisTextField tfNombre;
    private VisTextField tfContraseña;
    private VisTextButton tbEntrar;

    public MbMenu() {
        super(Screen.Scr.MENU);
    }

    @Override
    public void show() {
        super.show();

        tfNombre = new VisTextField("");
        tfNombre.setPosition(40, 200);
        tfNombre.setSize(200, 20);
        tfNombre.setFocusBorderEnabled(false);
        tfNombre.focusField();

        tfContraseña = new VisTextField("");
        tfContraseña.setPosition(40, 175);
        tfContraseña.setSize(200, 20);
        tfContraseña.setPasswordCharacter('*');
        tfContraseña.setPasswordMode(true);
        tfContraseña.setFocusBorderEnabled(false);

        tbEntrar = new VisTextButton("Entrar");
        tbEntrar.setPosition(170, 130);
        tbEntrar.setSize(70, 25);

        stage.addActor(tfNombre);
        stage.addActor(tfContraseña);
        stage.addActor(tbEntrar);

        stage.setKeyboardFocus(tfNombre);

        tbEntrar.addListener(new DragListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                conectar();
                return super.touchDown(event, x, y, pointer, button);
            }
        });


        InputListener ltConectar = new InputListener() {
            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                if (keycode == Input.Keys.ENTER)
                    conectar();
                else if (keycode == Input.Keys.ESCAPE)
                    Gdx.app.exit();
                return super.keyUp(event, keycode);
            }
        };

        tfNombre.addListener(ltConectar);
        tfContraseña.addListener(ltConectar);

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        stage.draw();

        Main.getInstance().getConnection().getSvPack().doActions();
    }

    private void conectar() {
        if (tfNombre.getText().length() == 0)
            Dialogs.showOKDialog(stage, "Error...", "El nombre no puede estar vacío.");
        else if (tfContraseña.getText().length() == 0)
            Dialogs.showOKDialog(stage, "Error...", "La contraseña no puede estar vacía");
        else {
            Main.getInstance().getConnection().connect();
            getClPack().writeLoginExistingChar(tfNombre.getText(), tfContraseña.getText());
        }
    }
}
