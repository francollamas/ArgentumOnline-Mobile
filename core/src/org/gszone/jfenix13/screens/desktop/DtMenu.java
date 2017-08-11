package org.gszone.jfenix13.screens.desktop;

import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import org.gszone.jfenix13.screens.Screen;

/**
 * Created by llama on 21/7/2017.
 */
public class DtMenu extends Screen {

    TextField tfNombre;
    TextField tfContraseña;
    TextButton tbEntrar;

    public DtMenu() {
        super(Scr.MENU);
    }

    @Override
    public void show() {
        super.show();

        tfNombre = new TextField("", getSkin());
        tfNombre.setPosition(40, 300);
        tfNombre.setSize(200, 20);

        tfContraseña = new TextField("", getSkin());
        tfContraseña.setPosition(40, 275);
        tfContraseña.setSize(200, 20);
        tfContraseña.setPasswordCharacter('*');
        tfContraseña.setPasswordMode(true);

        tbEntrar = new TextButton("Entrar", getSkin());
        tbEntrar.setPosition(170, 230);
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
