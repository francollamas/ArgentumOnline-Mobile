package org.gszone.jfenix13.screens.mobile;

import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import org.gszone.jfenix13.screens.Screen;

/**
 * Created by llama on 9/8/2017.
 */
public class MbMenu extends Screen {
    TextField tfNombre;
    TextField tfContraseña;
    TextButton tbEntrar;

    public MbMenu() {
        super(Screen.Scr.MENU);
    }

    @Override
    public void show() {
        super.show();

        tfNombre = new TextField("", getSkin());
        tfNombre.setPosition(40, 100);
        tfNombre.setSize(200, 20);

        tfContraseña = new TextField("", getSkin());
        tfContraseña.setPosition(40, 75);
        tfContraseña.setSize(200, 20);
        tfContraseña.setPasswordCharacter('*');
        tfContraseña.setPasswordMode(true);

        tbEntrar = new TextButton("Entrar", getSkin());
        tbEntrar.setPosition(170, 30);
        tbEntrar.setSize(70, 25);

        stage.addActor(tfNombre);
        stage.addActor(tfContraseña);
        stage.addActor(tbEntrar);

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        stage.draw();
    }
}
