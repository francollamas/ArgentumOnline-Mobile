package org.gszone.jfenix13.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.github.czyzby.lml.annotation.LmlActor;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextField;

import static org.gszone.jfenix13.general.FileNames.getViewDir;

public class PrincipalView extends View {
    public static final String ID = "principal";

    @LmlActor("fps") private VisLabel lbFps;
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
        //fondo.setBackground(getBackground()); // TODO: descomentar y agregar un fondo!

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

        //getStage().setDebugAll(true);

        // Agrego todos los elementos a la pantalla
        ctConsola.setActor(getGD().getConsola());
        ctWorld.setActor(getGD().getWorld());
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        lbFps.setText("FPS: " + Gdx.graphics.getFramesPerSecond());
    }

}