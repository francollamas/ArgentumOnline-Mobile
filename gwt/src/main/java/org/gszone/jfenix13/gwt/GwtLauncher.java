package org.gszone.jfenix13.gwt;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.github.czyzby.websocket.GwtWebSockets;
import org.gszone.jfenix13.Main;

/** Launches the GWT application. */
public class GwtLauncher extends GwtApplication {
    @Override
    public GwtApplicationConfiguration getConfig() {
        GwtApplicationConfiguration configuration = new GwtApplicationConfiguration(1024, 768);
        return configuration;
    }

    @Override
    public ApplicationListener createApplicationListener() {
        GwtWebSockets.initiate();
        return new Main();
    }
}