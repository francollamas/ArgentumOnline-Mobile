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

    /**
     * Obtiene un array con las palabras de un texto.
     */
    public static Array<String> getPalabras(String texto) {
        // Crea un array de palabras separadas por " "
        String[] s = texto.trim().split(" ");

        // Crea otro a partir de este, sin contar palabras vacías (debidas a varios espacios consecutivos)
        Array<String> arr = new Array<>();
        for (String i : s)
            if (!i.trim().equals("")) arr.add(i);
        return arr;
    }

    /**
     * Determina si una cadena usa caracteres no válidos
     *
     * @return el caractér inválido, o null en caso de ser correcto.
     */
    public static Character getInvalidChar(String text, char... exclude) {
        outerloop:
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);

            // Si es alguno de los caractéres permitidos....
            for (int j = 0; j < exclude.length; j++) {
                // Si es uno de ellos, el caracter es correcto y se sigue con el siguiente
                if (c == exclude[j]) continue outerloop;
            }

            if (!legalCharacter(c)) {
                return c;
            }
        }
        return null;
    }

    /**
     * Chequea si un caracter está permitido (se usa para nombre del pj y contraseña)
     */
    private static boolean legalCharacter(char c) {
        if ((c < 32 && c != 8) || c > 126 || c == 34 || c == 42 || c == 44 || c == 47 ||
                c == 58 || c == 60 || c == 62 || c == 63 || c == 92 || c == 124) return false;
        return true;
    }


}
