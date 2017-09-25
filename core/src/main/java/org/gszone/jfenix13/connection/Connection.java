package org.gszone.jfenix13.connection;

/**
 * Interfaz que define los métodos que tiene que tener una conexión
 */
public interface Connection {

    ClientPackages getClPack();

    ServerPackages getSvPack();

    /**
     * Se conecta con el servidor
     */
    void connect();

    /**
     * Busca en la cola y envía datos al servidor
     */
    void write();

    /**
     * Cierra la conexión
     */
    void dispose();

}
