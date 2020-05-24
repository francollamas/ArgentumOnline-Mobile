package org.gszone.jfenix13.utils;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.kotcrab.vis.ui.FocusManager;
import com.kotcrab.vis.ui.Focusable;
import com.kotcrab.vis.ui.widget.VisDialog;
import org.gszone.jfenix13.Main;
import org.gszone.jfenix13.views.screens.View;

/**
 * Funcionamiento parecido al de la clase Actors.
 * Sirve para crear cuadros de diálogo
 */
public class Dialogs {
    public static VisDialog showOKDialog (String title, String text) {
        final Stage s = ((View)Main.getInstance().getScreen()).getStage();

        //Guardo el Actor que tiene el foco
        final Focusable a = FocusManager.getFocusedWidget();

        final VisDialog dialog = new VisDialog(title);
        dialog.closeOnEscape();
        dialog.text(text);
        dialog.button(bu("ok")).padBottom(3);

        // Obtengo el ultimo elemento del dialog, que es el boton creado arriba, y le agrego un listener.
        dialog.getChildren().peek().addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                // Internamente, al cliquear el botón, el dialog es cerrado, por lo que solo queda volver el foco a donde estaba
                Actors.setFocus(a);
            }
        });
        dialog.pack();
        dialog.centerWindow();

        // Si doy ENTER
        dialog.addListener(new InputListener() {
            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                if (keycode == Input.Keys.ENTER) {
                    // Cierro la ventana y vuelvo el foco a donde estaba
                    dialog.fadeOut();
                    Actors.setFocus(a);
                    return true;
                }
                return super.keyUp(event, keycode);
            }
        });

        s.addActor(dialog.fadeIn());
        return dialog;
    }

    private static String bu(String key) {
        return Main.getInstance().getBundle().get(key);
    }
}
