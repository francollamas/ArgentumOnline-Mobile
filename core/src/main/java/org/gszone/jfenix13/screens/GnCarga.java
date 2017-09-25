package org.gszone.jfenix13.screens;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import org.gszone.jfenix13.Main;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.Input.Keys;

/**
 * Pantalla de carga (para todos los dispositivos)
 *
 * tiempoInicio: guarda el momento en que se abrió la pantalla
 * thread: hilo separado para la carga de recursos propios del juego
 * cargado: indica si se terminaron de cargar todos los recursos
 * solicitaSalir: si el usuario forzó la salida de la pantalla de carga.
 */
public class GnCarga extends Screen {

    public GnCarga() { super(Scr.CARGA, "carga"); }

    private long tiempoInicio;
    //private Thread thread;
    private boolean cargado;
    private boolean solicitaSalir;

    @Override
    public void show() {
        super.show();
        tiempoInicio = TimeUtils.millis();

        // Defino el hilo
        /*thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Main.getInstance().getAssets().loadRemaining();
                cargado = true;
            }
        });*/

        // Agrego los eventos
        stage.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                solicitaSalir = true;
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                if (keycode == Keys.ESCAPE)
                    solicitaSalir = true;
                return super.keyUp(event, keycode);
            }
        });

        Main.getInstance().getAssets().getAudio().playMusic(6);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        getBatch().begin();
        if (background != null) background.draw(getBatch(), 1);
        getBatch().end();

        stage.draw();

        float value;
        value = Main.getInstance().getAssets().loadNextAsset();

        // Si se cargaron los assets de libGDX, inicio el thread para cargar los demás assets del juego
        /*if (value == 1 && !cargado)
            if (!thread.isAlive())
                thread.start();*/

        if (value == 1 && (TimeUtils.millis() - tiempoInicio > 20000 || solicitaSalir)) {
            Main.getInstance().getAssets().loadRemaining();
            Main.getInstance().setScreen(new GnMenu());
        }
    }

}
