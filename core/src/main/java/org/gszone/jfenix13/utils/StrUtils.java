package org.gszone.jfenix13.utils;

import com.badlogic.gdx.utils.Array;

/**
 * Funciones de utilidad para el manejo de Strings.
 */
public class StrUtils {

    /**
     * Obtiene un array de líneas, creando una línea nueva cuando encuentre el caractér '\n'
     */
    public static Array<String> getLineas(String sb) {

        Array<String> arr = new Array<>();
        int firstIndex = 0;
        int secondIndex = 0;
        StringBuilder s;
        while (secondIndex != -1) {
            secondIndex = sb.indexOf("\n", firstIndex);
            if (secondIndex != -1) {
                s = new StringBuilder();
                s.append(sb.substring(firstIndex, secondIndex));
                arr.add(s.toString());
                firstIndex = secondIndex + 1;
            }
        }
        s = new StringBuilder();
        s.append(sb.substring(firstIndex));
        arr.add(s.toString());

        return arr;
    }

    /**
     * Formatea el texto agregando sáltos de línea, evitando que las palabras queden cortadas
     *
     * @param text: texto a formatear
     * @param maxChars cantidad máxima de caracteres por línea
     * @return texto formateado
     */
    public static String getFormattedText(String text, int maxChars) {
        StringBuilder sb = new StringBuilder(text.length() + 10);

        int pos = 0;
        while (text.length() - pos > maxChars) {
            String prov = text.substring(pos, pos + maxChars);
            int lastSpace = prov.lastIndexOf(" ");
            if (lastSpace != -1) {
                prov = prov.substring(0, lastSpace);
                pos++;
            }
            sb.append(prov.trim() + '\n');
            pos += prov.length();
        }
        sb.append(text.substring(pos).trim());

        return sb.toString();
    }

}