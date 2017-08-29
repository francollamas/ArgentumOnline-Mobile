package org.gszone.jfenix13.connection;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.Queue;
import com.kotcrab.vis.ui.util.dialog.Dialogs;
import org.gszone.jfenix13.general.Main;
import org.gszone.jfenix13.screens.Screen;

import java.io.*;

/**
 * Clase que se encarga de la conexión con el servidor y el tráfico de paquetes de información
 *
 * socket: conexión con el servidor
 * thread: segundo hilo de ejecución para escuchar respuestas constantemente.
 * disposed: booleano que indica si va a destruir el socket (para evitar excepciones en el nuevo thread)
 */
public class Connection {
    //public static final String IP = "francollamas.ddns.net";
    public static final String IP = "127.0.0.1";
    public static final int PORT = 7666;

    private Thread thread;
    private Socket socket;
    private boolean disposed;

    private ClientPackages clPack;
    private ServerPackages svPack;

    public Connection() {
        clPack = new ClientPackages();
        svPack = new ServerPackages();
    }

    public ClientPackages getClPack() { return clPack; }

    /**
     * Se conecta con el servidor
     *
     * @return: booleano que indica si se estableció o no la conexión.
     */
    public boolean connect() {
        try {
            // Si el socket y el thread estaban abiertos, los destruye
            dispose();
            disposed = false;

            // Conectarse al servidor
            SocketHints hints = new SocketHints();
            hints.connectTimeout = 5000;
            socket = Gdx.net.newClientSocket(Net.Protocol.TCP, IP, PORT, hints);
        }
        catch (GdxRuntimeException ex) {
            Dialogs.showOKDialog(((Screen)Main.getInstance().getScreen()).getStage(), "Error", "No se pudo establecer la conexión con el servidor.");
            socket = null;
            return false;
        }

        if (socket.isConnected()) sendAndListen();
        return true;
    }


    /**
     * Recibe y envía datos al servidor
     */
    public void sendAndListen() {
        // Escuchamos en un nuevo thread para que no afecte al juego
        thread = new Thread(new Runnable() {

            @Override
            public void run() {
                while (true) {
                    try {
                        if (socket == null) return;

                        // Si el socket está desconectado o se está por destruir no sigue.
                        if (disposed || !socket.isConnected()) return;

                        // Mandamos lo que haya para mandar...
                        Queue<Array<Byte>> cola = clPack.getCola();
                        int size = cola.size;
                        for (int i = 0; i < size; i++)
                            write(cola.removeFirst());

                        // Si hay datos entrantes...
                        if (socket.getInputStream().available() > 0) {
                            BufferedInputStream buffer = new BufferedInputStream(socket.getInputStream());

                            // Junto todos los bytes del buffer en un array
                            Array<Byte> bytes = new Array();
                            while (buffer.available() > 0) {
                                byte[] leido = new byte[1];
                                buffer.read(leido);
                                bytes.add(leido[0]);
                            }
                            svPack.handleReceived(bytes);

                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        thread.start();
    }


    /**
     * Envía datos al servidor
     */
    public void write(Array<Byte> bytes) {
        try {
            if (bytes.size == 0) return;
            if (socket == null) return;
            if (!socket.isConnected()) return;
            byte[] b = new byte[bytes.size];
            for (int i = 0; i < b.length; i++) {
                b[i] = bytes.get(i);
            }
            socket.getOutputStream().write(b);

            /* Obliga a enviar los datos que están en el OutputStream
             * TODO: sacarlo si da lag. Lo dejo porque sino podría llegar a tardar en enviar un paquete sumamente necesario.
             */
            socket.getOutputStream().flush();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    /**
     * Cierra la conexión
     */
    public void dispose() {
        disposed = true;
        if (thread != null) thread.interrupt();
        if (socket != null) socket.dispose();
    }

}
