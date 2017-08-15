package org.gszone.jfenix13.screens.mobile;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
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

        tfContraseña = new VisTextField("");
        tfContraseña.setPosition(40, 175);
        tfContraseña.setSize(200, 20);
        tfContraseña.setPasswordCharacter('*');
        tfContraseña.setPasswordMode(true);

        tbEntrar = new VisTextButton("Entrar");
        tbEntrar.setPosition(170, 130);
        tbEntrar.setSize(70, 25);

        stage.addActor(tfNombre);
        stage.addActor(tfContraseña);
        stage.addActor(tbEntrar);

        tbEntrar.addListener(new DragListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Main.getInstance().getConnection().connect();
                getClPack().writeLoginExistingChar(tfNombre.getText(), tfContraseña.getText());
                getClPack().write();
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
