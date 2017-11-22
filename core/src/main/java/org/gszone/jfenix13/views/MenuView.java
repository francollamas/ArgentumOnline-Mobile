package org.gszone.jfenix13.views;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.kotcrab.vis.ui.widget.*;
import org.gszone.jfenix13.Main;
import org.gszone.jfenix13.utils.Dialogs;

import static org.gszone.jfenix13.utils.Actors.*;

public class MenuView extends View {

    private VisTextField tfNombre;
    private VisTextField tfContraseña;
    private VisTextButton tbEntrar;

    @Override
    public void show() {
        super.show();

        VisWindow w = newWindow(bu("mn.title"), null, false, false);
            Table t1 = newTable(w).padBottom(4).getActor();
                newLabel(t1, bu("mn.manage"), "col-title", "smallgradient").align(Align.left).row();
                newTextButton(t1, bu("mn.createchar")).fill().row();
                newTextButton(t1, bu("mn.recoverchar")).fill().row();
                newTextButton(t1, bu("mn.deletechar")).fill().row();
                newTextButton(t1, bu("mn.config")).fill().row();

            w.row();
            Table t2 = newTable(w).padTop(4).getActor();
                newLabel(t2, bu("mn.login"), "col-title", "smallgradient").align(Align.left).row();
                tfNombre = newTextField(t2, "", bu("mn.name-ms"), "bold").getActor(); t2.row();
                tfContraseña = newTextField(t2, "", bu("mn.pass-ms"), "bold", true).getActor(); t2.row();
                tbEntrar = newTextButton(t2, bu("mn.enter")).getActor();
        fitWindow(w);

        // Eventos generales del teclado
        stage.addListener(new InputListener() {
            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                if (keycode == Input.Keys.ESCAPE)
                    Main.getInstance().salir();
                return super.keyUp(event, keycode);
            }
        });

        // Eventos de los TextField
        InputListener il = new InputListener() {
            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                if (keycode == Input.Keys.ENTER)
                    conectar();
                return super.keyUp(event, keycode);
            }
        };
        tfNombre.addListener(il);
        tfContraseña.addListener(il);

        tbEntrar.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                conectar();
            }
        });

        // Hago foco en el nombre del loggin
        setTfFocus(tfNombre);

        Main.getInstance().getAssets().getAudio().playMusic(6);
    }

    private void conectar() {
        if (tfNombre.getText().length() == 0)
            Dialogs.showOKDialog("Error", "El nombre no puede estar vacío.");
        else if (tfContraseña.getText().length() == 0)
            Dialogs.showOKDialog("Error", "La contraseña no puede estar vacía");
        else {
            if (getConnection().connect())
                getClPack().writeLoginExistingChar(tfNombre.getText(), tfContraseña.getText());
        }
    }

    private void crearPj() {
        if (getConnection().connect()) {
            //setScreen(CrearPjView.class);
            getClPack().writeThrowDices();
        }
    }
}
