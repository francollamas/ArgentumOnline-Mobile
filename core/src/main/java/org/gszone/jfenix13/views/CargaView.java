package org.gszone.jfenix13.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.TimeUtils;
import org.gszone.jfenix13.Main;
import org.gszone.jfenix13.general.GnLoader;
import org.gszone.jfenix13.general.Loader;
import org.gszone.jfenix13.general.WebLoader;

import static com.badlogic.gdx.Application.ApplicationType.*;

import static org.gszone.jfenix13.utils.Actors.*;

/**
 * Pantalla de carga
 */
public class CargaView extends View {

    private Loader loader;
    private long tiempoInicio;
    private boolean solicitaSalir;

    @Override
    public void show() {
        super.show();

        newFirstTable(getBackground("carga"), false);


        // Crea el Loader
        if (Gdx.app.getType() == WebGL)
            loader = new WebLoader();
        else
            loader = new GnLoader();

        tiempoInicio = TimeUtils.millis();

        // Eventos generales de mouse y teclado
        stage.addListener(new InputListener() {
            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                if (keycode == Input.Keys.ESCAPE)
                    solicitaSalir = true;
                return super.keyUp(event, keycode);
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                solicitaSalir = true;
                return super.touchDown(event, x, y, pointer, button);
            }
        });

        Main.getInstance().getAssets().getAudio().playMusic(6);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        float value;
        value = Main.getInstance().getAssets().loadNextAsset();

        if (value == 1 && !loader.isLoading() && !loader.isLoaded())
            loader.load();

        if (loader.isLoaded() && (TimeUtils.millis() - tiempoInicio > 6000 || solicitaSalir)) {
            setScreen(new MenuView());
        }
    }
}
