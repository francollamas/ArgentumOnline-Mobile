package org.gszone.jfenix13.objects;

/**
 * Representa a una Fuente
 *
 * name: nombre de la fuente
 * tex: número de textura asignado
 * offset: espacio extra entre cada letra.
 * characters: conjunto de caracteres con sus propiedades
 */
public class Font {
    private String name;
    private int tex;
    private int offset;
    private Char[] characters;

    public Font() {
        characters = new Char[256];
        for (int i = 0; i < characters.length; i++) {
            characters[i] = new Char();
        }
    }

    public String getName() {
        return name;
    }

    public int getTex() {
        return tex;
    }

    public int getOffset() {
        return offset;
    }

    public Char[] getChars() {
        return characters;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTex(int tex) {
        this.tex = tex;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
}