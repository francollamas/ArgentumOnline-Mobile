package org.gszone.jfenix13.utils;

/**
 * Excepción que se lanza al querer leer y no hay datos disponibles en un buffer
 */
public class NotEnoughDataException extends Exception {
    public NotEnoughDataException() {
        super("No hay suficientes datos en el buffer para leer");
    }
}
