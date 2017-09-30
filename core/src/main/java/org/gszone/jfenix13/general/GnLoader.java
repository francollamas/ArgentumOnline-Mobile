package org.gszone.jfenix13.general;

import org.gszone.jfenix13.Main;

/**
 * Clase para el control de la carga de recursos del juego (en Desktop, Android e iOS)
 *
 * thread: hilo de ejecucion aparte para cargar los recursos.
 * cargado: indica si se cargaron los recursos
 */
public class GnLoader implements Loader {
    private Thread thread;
    private boolean cargado;

    public GnLoader() {
        // Defino las acciones del thread de carga.
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Main.getInstance().getAssets().loadRemaining();
                cargado = true;
            }
        });
    }

    @Override
    public void load() {
        // Activo el thread
        thread.start();
    }

    @Override
    public boolean isLoading() {
        return thread.isAlive();
    }

    @Override
    public boolean isLoaded() {
        return cargado;
    }
}
