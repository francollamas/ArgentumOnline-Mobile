package org.gszone.jfenix13.connection;

import com.sksamuel.gwt.websockets.Base64Utils;
import com.sksamuel.gwt.websockets.BinaryWebsocketListener;
import com.sksamuel.gwt.websockets.WebsocketListener;
import com.sksamuel.gwt.websockets.WebsocketListenerExt;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by llama on 19/9/2017.
 */
public class Websocket {
    private static int counter = 1;

    private static native boolean _isWebsocket() /*-{
        return ("WebSocket" in window);
    }-*/;

    public static boolean isSupported() {
        return _isWebsocket();
    }

    private final Set<WebsocketListener> listeners = new HashSet<WebsocketListener>();

    private final String varName;
    private final String url;

    public Websocket(String url) {
        this.url = url;
        this.varName = "gwtws-" + counter++;
    }

    public native void _close(String s) /*-{
        $wnd[s].close();
    }-*/;

    private native void _open(org.gszone.jfenix13.connection.Websocket ws, String s, String url) /*-{
        $wnd[s] = new WebSocket(url, ['base64']);
        $wnd[s].onopen = function() { ws.@org.gszone.jfenix13.connection.Websocket::onOpen()(); };
        $wnd[s].onclose = function() { ws.@org.gszone.jfenix13.connection.Websocket::onClose()(); };
        $wnd[s].onerror = function() { ws.@org.gszone.jfenix13.connection.Websocket::onError()(); };
        $wnd[s].onmessage = function(msg) { ws.@org.gszone.jfenix13.connection.Websocket::onMessage(Ljava/lang/String;)(msg.data); }
    }-*/;

    public native void _send(String s, String msg) /*-{
        $wnd[s].send(msg);
    }-*/;

    private native int _state(String s) /*-{
        return $wnd[s].readyState;
    }-*/;

    public void addListener(WebsocketListener listener) {
        listeners.add(listener);
    }

    public void close() {
        _close(varName);
    }

    public int getState() {
        return _state(varName);
    }

    protected void onClose() {
        for (WebsocketListener listener : listeners)
            listener.onClose();
    }

    protected void onError() {
        for (WebsocketListener listener : listeners) {
            if (listener instanceof WebsocketListenerExt) {
                ((WebsocketListenerExt)listener).onError();
            }
        }
    }

    protected void onMessage(String msg) {
        for (WebsocketListener listener : listeners) {
            listener.onMessage(msg);
            if (listener instanceof BinaryWebsocketListener) {
                byte[] bytes = Base64Utils.fromBase64(msg);
                ((BinaryWebsocketListener) listener).onMessage(bytes);
            }
        }
    }

    protected void onOpen() {
        for (WebsocketListener listener : listeners)
            listener.onOpen();
    }

    public void open() {
        _open(this, varName, url);
    }

    public void send(String msg) {
        _send(varName, msg);
    }

    public void send(byte[] bytes) {
        String base64 = Base64Utils.toBase64(bytes);
        send(base64);
    }
}
