package org.gszone.jfenix13.connection;

import com.badlogic.gdx.Gdx;
import com.github.czyzby.websocket.WebSocket;
import com.github.czyzby.websocket.WebSocketAdapter;
import com.github.czyzby.websocket.WebSockets;
import com.github.czyzby.websocket.net.ExtendedNet;

/**
 * Clase que se encarga de la conexión con el servidor y el tráfico de paquetes de información (en Web)
 *
 * socket: conexión con el servidor
 */
public class WebConnection implements Connection {
    public static final String IP = "localhost";
    public static final int PORT = 7667;

    private ClientPackages clPack;
    private ServerPackages svPack;

    private WebSocket socket;


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
        socket = ExtendedNet.getNet().newWebSocket(IP, PORT);
        socket.addListener(new WebSocketAdapter() {


            @Override
            public boolean onMessage(WebSocket webSocket, byte[] packet) {
                svPack.getCola().addLast(packet);
                return true;
            }

            @Override
            public boolean onError(WebSocket webSocket, Throwable error) {
                Gdx.app.debug("Crasheo: ", error.getMessage());
                return true;
            }
        });

        socket.connect();

        return true;
    }

    @Override
    public void write() {
        if (socket == null || !socket.isOpen()) return;

        byte[] bytes = getClPack().removeAll();
        if (bytes.length > 0)
            socket.send(bytes);
    }

    @Override
    public void dispose() {
        WebSockets.closeGracefully(socket);
    }
}
