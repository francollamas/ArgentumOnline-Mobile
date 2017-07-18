package org.gszone.jfenix13.general;

public class FileNames {
    public static final String DIR_SKINS = "skins";
    public static final String DIR_FONTS = "fonts";
    public static final String DIR_GUI = "gui";
    public static final String DIR_TEXTURAS = "textures";
    public static final String DIR_INITS = "inits";
    public static final String DIR_ICON = "icon";
    public static final String DIR_MAPS = "maps";

    public static String getIconDir() {
        return DIR_ICON + "/ic_launcher.png";
    }

    public static String getMapDir(int num) {
        return DIR_MAPS + "/Mapa" + num + ".mcl";
    }

    public static String getTexIndicesDir() {
        return DIR_INITS + "/graficos.ind";
    }

    public static String getAtlasNormTexDir() {
        return DIR_TEXTURAS + "/normal.atlas";
    }

    public static String getAtlasBigTexDir() {
        return DIR_TEXTURAS + "/big.atlas";
    }

    public static String getFontsIndicesDir() {
        return DIR_FONTS + "/fonts.ind";
    }

    public static String getAtlasFontTexDir() {
        return DIR_FONTS + "/fuentes.atlas";
    }

    public static String getAtlasGuiDir() {
        return DIR_GUI + "/gui.atlas";
    }

    public static final String getSkinFlat() {
        return DIR_SKINS + "/flat/skin.json";
    }
}
