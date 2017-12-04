package org.gszone.jfenix13.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.TimeUtils;
import org.gszone.jfenix13.Main;
import org.gszone.jfenix13.general.GnLoader;
import org.gszone.jfenix13.general.Loader;
import org.gszone.jfenix13.general.WebLoader;
import org.gszone.jfenix13.views.MenuView;

import static com.badlogic.gdx.Application.ApplicationType.Desktop;
import static com.badlogic.gdx.Application.ApplicationType.WebGL;

public class CargaManager extends ViewManager {

    private Loader loader;
    private long tiempoInicio;
    private boolean solicitaSalir;

    public CargaManager() {
        // Crea el Loader
        if (Gdx.app.getType() == WebGL)
            loader = new WebLoader();
        else
            loader = new GnLoader();

        tiempoInicio = TimeUtils.millis();
    }

    public void salir() {
        solicitaSalir = true;
    }

    public void update() {
        float value;
        value = Main.getInstance().getAssets().loadNextAsset();

        if (value == 1 && !loader.isLoading() && !loader.isLoaded())
            loader.load();

        if (loader.isLoaded() && (TimeUtils.millis() - tiempoInicio > 6000 || solicitaSalir)) {
            setScreen(new MenuView());
        }
    }
}
