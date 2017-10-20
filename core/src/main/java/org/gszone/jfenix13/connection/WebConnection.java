package org.gszone.jfenix13.connection;

import com.sksamuel.gwt.websockets.*;

/**
 * Clase que se encarga de la conexión con el servidor y el tráfico de paquetes de información (en Web)
 *
 * socket: conexión con el servidor
 */
public class WebConnection implements Connection {
    public static final String IP = "192.168.1.15";
    public static final int PORT = 7678;

    private ClientPackages clPack;
    private ServerPackages svPack;

    private org.gszone.jfenix13.connection.Websocket socket;

    public WebConnection() {
        clPack = new ClientPackages();
        svPack = new ServerPackages();
    }

    @Override
    public ClientPackages getClPack() { return clPack; }

    @Override
    public ServerPackages getSvPack() { return svPack; }

    @Override
    public boolean connect() {

        if (socket == null || socket.getState() != 1) {
            socket = new Websocket("ws://" + IP + ":" + PORT);
            socket.addListener(new BinaryWebsocketListener() {
                @Override
                public void onMessage(byte[] bytes) {
                    svPack.getCola().addLast(bytes);
                }

                @Override
                public void onClose() {
                }

                @Override
                public void onMessage(String msg) {

                }

                @Override
                public void onOpen() {
                }
            });

            socket.open();
            // TODO: controlar si no se pudo conectar, y retornar falso.
        }
        return true;
    }

    @Override
    public void write() {
        if (socket == null || socket.getState() != 1) return;

        byte[] bytes = getClPack().removeAll();
        if (bytes.length > 0)
            socket.send(bytes);
    }

    @Override
    public void dispose() {
        if (socket != null)
            socket.close();
    }
}
