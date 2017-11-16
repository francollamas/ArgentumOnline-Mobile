package org.gszone.jfenix13.general;

/**
 * Clase que permite obtener la dirección de cada fichero del juego.
 */
public class FileNames {
    public static final String DIR_VIEWS = "views";
    public static final String DIR_GUI = "gui";
    public static final String DIR_TEXTURAS = "textures";
    public static final String DIR_INITS = "inits";
    public static final String DIR_MAPS = "maps";
    public static final String DIR_MUSICS = "musics";
    public static final String DIR_SOUNDS = "sounds";

    public static String getDtConfigDir() {
        // Dirección relativa de las configuraciones para Escritorio
        return "/.jfenix13/jfenix13.properties";
    }

    public static String getCursorDir() {
        return DIR_GUI + "/cursor.png";
    }

    public static String getSkinDir() {
        return "skin/skin.json";
    }

    public static String getViewDir(String name) {
        return DIR_VIEWS + "/" + name + ".lml";
    }

    public static String getMapDir(int num) {
        return DIR_MAPS + "/Mapa" + num + ".mcl";
    }

    public static String getGrhsIndDir() {
        return DIR_INITS + "/grhs.ind";
    }

    public static String getBodiesIndDir() {
        return DIR_INITS + "/bodies.ind";
    }

    public static String getHeadsIndDir() {
        return DIR_INITS + "/heads.ind";
    }

    public static String getHelmetsIndDir() {
        return DIR_INITS + "/helmets.ind";
    }

    public static String getFxsIndDir() {
        return DIR_INITS + "/fxs.ind";
    }

    public static String getWeaponsIndDir() {
        return DIR_INITS + "/weapons.ind";
    }

    public static String getShieldsIndDir() {
        return DIR_INITS + "/shields.ind";
    }

    public static String getDtGuiDir(String name) {
        return DIR_GUI + "/desktop/" + name + ".png";
    }

    public static String getMbGuiDir(String name) {
        return DIR_GUI + "/mobile/" + name + ".png";
    }

    public static String getMusicDir(int num) {
        return getMusicDir("" + num);
    }

    public static String getMusicDir(String fileName) {
        return DIR_MUSICS + "/" + fileName + ".ogg";
    }

    public static String getSoundDir(int num) {
        return getSoundDir("" + num);
    }

    public static String getSoundDir(String fileName) {
        return DIR_SOUNDS + "/" + fileName + ".ogg";
    }


}
