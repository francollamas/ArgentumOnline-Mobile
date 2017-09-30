package org.gszone.jfenix13.general;

/**
 * Interfaz para el control de la carga de recursos del juego
 * (uso la interfaz para evitar problemas con threads, que no son soportados en GWT)
 */
public interface Loader {

    /**
     * Metodo que carga los recursos del juego
     */
    void load();

    /**
     * Indica si los recursos se estan cargando
     */
    boolean isLoading();

    /**
     * Indica si los recursos se cargaron
     */
    boolean isLoaded();

}
