package org.gszone.jfenix13.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.github.czyzby.lml.annotation.LmlAction;
import com.github.czyzby.lml.annotation.LmlActor;
import com.kotcrab.vis.ui.widget.VisTextField;
import org.gszone.jfenix13.Main;
import org.gszone.jfenix13.utils.Dialogs;

import static org.gszone.jfenix13.general.FileNames.getViewDir;

public class MenuView extends View {
    public static final String ID = "menu";

    @LmlActor("nombre") private VisTextField tfNombre;
    @LmlActor("contraseña") private VisTextField tfContraseña;

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

        // Hago foco en el nombre del loggin
        setTfFocus(tfNombre);

        Main.getInstance().getAssets().getAudio().playMusic(6);
    }

    @LmlAction("conectar")
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

    @LmlAction("cpj")
    private void cpj() {
        if (getConnection().connect()) {
            setView(CrearPjView.class);
            getClPack().writeThrowDices();
        }
    }


}
