package org.gszone.jfenix13.screens.desktop;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.kotcrab.vis.ui.util.dialog.Dialogs;
import com.kotcrab.vis.ui.widget.*;
import org.gszone.jfenix13.general.Main;
import org.gszone.jfenix13.screens.Screen;

/**
 * Created by llama on 21/7/2017.
 */
public class DtMenu extends Screen {

    private VisTextField tfNombre;
    private VisTextField tfContraseña;
    private VisTextButton tbEntrar;

    public DtMenu() {
        super(Scr.MENU);
    }

    @Override
    public void show() {
        super.show();

        tfNombre = new VisTextField("");
        tfNombre.setPosition(40, 300);
        tfNombre.setSize(200, 25);

        tfContraseña = new VisTextField("");
        tfContraseña.setPosition(40, 270);
        tfContraseña.setSize(200, 25);
        tfContraseña.setPasswordCharacter('*');
        tfContraseña.setPasswordMode(true);

        tbEntrar = new VisTextButton("Entrar");
        tbEntrar.setPosition(170, 225);
        tbEntrar.setSize(70, 25);

        stage.addActor(tfNombre);
        stage.addActor(tfContraseña);
        stage.addActor(tbEntrar);

        tbEntrar.addListener(new DragListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (tfNombre.getText().length() == 0) {
                    Dialogs.showOKDialog(stage, "Error...", "El nombre no puede estar vacío.");
                }
                else {
                    Main.getInstance().getConnection().connect();
                    getClPack().writeLoginExistingChar(tfNombre.getText(), tfContraseña.getText());
                    getClPack().write();
                }
                return super.touchDown(event, x, y, pointer, button);
            }
        });

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        stage.draw();
    }
}
