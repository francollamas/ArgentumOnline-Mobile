package org.gszone.jfenix13.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.TimeUtils;
import org.gszone.jfenix13.Main;
import org.gszone.jfenix13.general.GnLoader;
import org.gszone.jfenix13.general.Loader;
import org.gszone.jfenix13.general.WebLoader;

import static org.gszone.jfenix13.general.FileNames.getViewDir;
import static com.badlogic.gdx.Application.ApplicationType.*;

/**
 * Pantalla de carga
 */
public class CargaView extends View {
    public static final String ID = "carga";

    private Loader loader;
    private long tiempoInicio;
    private boolean solicitaSalir;

    @Override
    public String getViewId() { return ID; }

    @Override
    public FileHandle getTemplateFile() {
        return Gdx.files.internal(getViewDir(ID));
    }

    @Override
    public void show() {
        setBackground();
        super.show();

        if (Gdx.app.getType() == WebGL)
            loader = new WebLoader();
        else
            loader = new GnLoader();

        tiempoInicio = TimeUtils.millis();

        // Eventos generales de mouse y teclado
        getStage().addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                solicitaSalir = true;
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                if (keycode == Input.Keys.ESCAPE)
                    solicitaSalir = true;
                return super.keyUp(event, keycode);
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
            Main.getInstance().setView(MenuView.class);
        }
    }
}
