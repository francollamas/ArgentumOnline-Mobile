package org.gszone.jfenix13.views;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.kotcrab.vis.ui.widget.*;
import org.gszone.jfenix13.managers.MenuManager;

import static org.gszone.jfenix13.utils.Actors.*;

public class MenuView extends View {

    public MenuView() {
        super(new MenuManager());
    }
    public MenuManager getGestor() { return (MenuManager)gestor; }

    private VisTextField tfNombre;
    private VisTextField tfContraseña;
    private VisTextButton tbCrearPj;
    private VisTextButton tbConfig;
    private VisTextButton tbEntrar;

    @Override
    public void show() {
        super.show();

        // Definición de los elementos de la pantalla
        VisWindow w = newWindow(bu("mn.title"), null, false, false);
            Table t1 = newTable(w).padBottom(4).getActor(); w.row();
                newLabel(t1, bu("mn.manage"), "col-title", "smallgradient").left().row();
                tbCrearPj = newTextButton(t1, bu("mn.createchar")).fill().getActor(); t1.row();
                newTextButton(t1, bu("mn.recoverchar")).fill().row();
                newTextButton(t1, bu("mn.deletechar")).fill().row();
                tbConfig = newTextButton(t1, bu("mn.config")).fill().getActor(); t1.row();

            Table t2 = newTable(w).padTop(4).getActor();
                newLabel(t2, bu("mn.login"), "col-title", "smallgradient").left().row();
                tfNombre = newTextField(t2, "", bu("mn.name-ms"), "bold").getActor(); t2.row();
                tfNombre.setMaxLength(30);
                tfContraseña = newTextField(t2, "", bu("mn.pass-ms"), "bold", true).getActor(); t2.row();
                tbEntrar = newTextButton(t2, bu("mn.enter")).getActor();
        fitWindow(w);

        // Eventos generales de la pantalla
        stage.addListener(new InputListener() {
            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                // Salir del juego al presionar Escape
                if (keycode == Input.Keys.ESCAPE || keycode == Input.Keys.BACK)
                    getGestor().exitGame();
                return super.keyUp(event, keycode);
            }
        });

        // Click del botón CrearPj
        tbCrearPj.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                getGestor().crearPj();
            }
        });

        // Click del boton Configurar.
        tbConfig.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                setScreen(new ConfigView());
            }
        });

        // Eventos de los TextField
        // como ambos tienen que tener el mismo evento, lo defino una vez y lo asigno a ambos.
        InputListener il = new InputListener() {
            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                if (keycode == Input.Keys.ENTER)
                    getGestor().conectar(tfNombre.getText(), tfContraseña.getText());
                return super.keyUp(event, keycode);
            }
        };
        tfNombre.addListener(il);
        tfContraseña.addListener(il);

        // Click del botón Entrar
        tbEntrar.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                getGestor().conectar(tfNombre.getText(), tfContraseña.getText());
            }
        });

        // Hago foco en el campo de Nombre.
        setFocus(tfNombre);

        getGestor().playMusic(6);
    }
}
