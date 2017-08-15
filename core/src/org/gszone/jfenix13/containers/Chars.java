package org.gszone.jfenix13.containers;

import org.gszone.jfenix13.objects.Char;

/**
 * Contiene a los usuarios que el cliente va mostrando
 */
public class Chars {
    private Char[] chars;
    private int numChars;
    private int lastChar;

    public Chars() {
        chars = new Char[10000];
    }

    public Char getChar(int index) {
        // Si quiero obtener un char que no existe, lo creo (ya que se va a estar usando).
        if (chars[index] == null)
            chars[index] = new Char();
        return chars[index];
    }

    public int getNumChars() {
        return numChars;
    }

    public void setNumChars(int numChars) {
        this.numChars = numChars;
    }

    public int getLastChar() {
        return lastChar;
    }

    public void setLastChar(int lastChar) {
        this.lastChar = lastChar;
    }
}
