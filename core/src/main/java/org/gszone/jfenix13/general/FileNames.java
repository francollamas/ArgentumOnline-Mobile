package org.gszone.jfenix13.general;

/**
 * Clase que permite obtener la dirección de cada fichero del juego.
 */
public class FileNames {
    public static final String DIR_FONTS = "fonts";
    public static final String DIR_GUI = "gui";
    public static final String DIR_TEXTURAS = "textures";
    public static final String DIR_INITS = "inits";
    public static final String DIR_ICON = "icon";
    public static final String DIR_MAPS = "maps";
    public static final String DIR_MUSICS = "musics";
    public static final String DIR_SOUNDS = "sounds";

    public static String getIconDir() {
        return DIR_ICON + "/ic_launcher.png";
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

    public static String getFontsIndicesDir() {
        return DIR_FONTS + "/fonts.ind";
    }

    public static String getAtlasFontTexDir() {
        return DIR_FONTS + "/fuentes.atlas";
    }

    public static String getAtlasDtGuiDir() {
        return DIR_GUI + "/dt_gui.atlas";
    }

    public static String getAtlasMbGuiDir() {
        return DIR_GUI + "/mb_gui.atlas";
    }

    public static String getAtlasGralGuiDir() {
        return DIR_GUI + "/gral_gui.atlas";
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
