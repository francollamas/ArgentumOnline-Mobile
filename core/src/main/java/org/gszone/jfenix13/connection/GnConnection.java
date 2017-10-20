package org.gszone.jfenix13.connection;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.kotcrab.vis.ui.util.dialog.Dialogs;
import org.gszone.jfenix13.Main;

import java.io.*;

/**
 * Clase que se encarga de la conexión con el servidor y el tráfico de paquetes de información (en Desktop, Android, iOS)
 *
 * socket: conexión con el servidor
 * thread: segundo hilo de ejecución para escuchar respuestas constantemente.
 * disposed: booleano que indica si va a destruir el socket (para evitar excepciones en el nuevo thread)
 */
public class GnConnection implements Connection {
    public static final String IP = "sv1.arghostum.com";
    public static final int PORT = 7677;

    private Thread thread;
    private Socket socket;
    private boolean disposed;

    private ClientPackages clPack;
    private ServerPackages svPack;

    public GnConnection() {
        clPack = new ClientPackages();
        svPack = new ServerPackages();
    }

    @Override
    public ClientPackages getClPack() { return clPack; }

    @Override
    public ServerPackages getSvPack() { return svPack; }

    /**
     * Devuelve el thread que se encarga de enviar y recibir paquetes
     */
    private Thread getThread() {
        // Escuchamos en un nuevo thread para que no afecte al juego
        Thread th = new Thread(new Runnable() {

            @Override
            public void run() {
                while (!disposed) {
                    try {
                        if (socket == null) return;

                        // Si el socket está desconectado o se está por destruir no sigue.
                        if (disposed || !socket.isConnected()) return;

                        // Mandamos lo que haya para mandar...
                        write();

                        // Si hay datos entrantes...
                        if (socket.getInputStream().available() > 0) {
                            byte[] bytes = new byte[socket.getInputStream().available()];
                            socket.getInputStream().read(bytes);
                            svPack.getCola().addLast(bytes);
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }

                socket.dispose();
                disposed = false;
            }
        });

        return th;
    }

    @Override
    public boolean connect() {
        try {
            // Si el socket y el thread estaban abiertos, los destruye
            dispose();

            // Conectarse al servidor
            SocketHints hints = new SocketHints();
            socket = Gdx.net.newClientSocket(Net.Protocol.TCP, IP, PORT, hints);
            if (socket.isConnected()) {
                thread = getThread();
                thread.start();
            }
        }
        catch (GdxRuntimeException ex) {
            Dialogs.showOKDialog(Main.getInstance().getCurrentView().getStage(), "Error", "No se pudo establecer la conexión con el servidor.");
            socket = null;
            return false;
        }
        return true;
    }

    @Override
    public void write() {
        if (socket == null || !socket.isConnected()) return;

        byte[] bytes = getClPack().removeAll();

        if (bytes.length > 0)
            try {
                socket.getOutputStream().write(bytes);
            }
            catch (IOException ex){
                ex.printStackTrace();
            }
    }

    @Override
    public void dispose() {
        if (thread != null && thread.isAlive()) {
            disposed = true;
            while (disposed)
                if (!thread.isAlive()) break;
        }
        else
            if (socket != null) socket.dispose();
    }

}
