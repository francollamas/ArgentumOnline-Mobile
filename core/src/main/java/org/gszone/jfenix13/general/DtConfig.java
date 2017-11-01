package org.gszone.jfenix13.general;

import java.io.*;
import java.util.Properties;

import static org.gszone.jfenix13.general.FileNames.getDtConfigDir;

/**
 * Configuraciones específicas para Escritorio
 * (Esta clase se usa ÚNICA y EXCLUSIVAMENTE para escritorio)
 *
 * width, height: ancho y alto de la pantalla en donde se ejecuta
 * decorated: indica si hay que mostrar los bordes y barra de título de la pantalla.
 * resizable: indica si se puede redimensionar la pantalla
 * fullscreen: determina si se tiene que ejecutar en pantalla completa
 * vSync: indica la sincronización vertical
 *
 */
public class DtConfig {
    public static int width;
    public static int height;
    public static boolean decorated;
    public static boolean resizable;
    public static boolean fullscreeen;
    public static boolean vSync;

    /**
     * Carga la configuración desde el archivo
     */
    public static void loadConfig() {
        File configFile = new File(getDtConfigDir());
        configFile.getParentFile().mkdir();

        try {
            configFile.createNewFile();
            FileReader reader = new FileReader(configFile);
            Properties props = new Properties();
            props.load(reader);

            width = Integer.parseInt(props.getProperty("width", "1024"));
            height = Integer.parseInt(props.getProperty("height", "768"));
            decorated = Boolean.parseBoolean(props.getProperty("decorated", "false"));
            resizable = Boolean.parseBoolean(props.getProperty("resizable", "false"));
            fullscreeen = Boolean.parseBoolean(props.getProperty("fullscreeen", "false"));
            vSync = Boolean.parseBoolean(props.getProperty("vSync", "true"));

            reader.close();
        } catch (FileNotFoundException ex) {
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Guarda la configuración en el archivo
     */
    public static void saveConfig() {
        File configFile = new File(getDtConfigDir());
        configFile.getParentFile().mkdir();

        try {
            FileWriter writer = new FileWriter(configFile);
            Properties props = new Properties();
            props.setProperty("width", "" + width);
            props.setProperty("height", "" + height);
            props.setProperty("decorated", "" + decorated);
            props.setProperty("resizable", "" + resizable);
            props.setProperty("fullscreeen", "" + fullscreeen);
            props.setProperty("vSync", "" + vSync);

            props.store(writer, "");
            writer.flush();
            writer.close();

        } catch (FileNotFoundException ex) {
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
