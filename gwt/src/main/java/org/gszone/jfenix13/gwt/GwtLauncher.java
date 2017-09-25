package org.gszone.jfenix13.gwt;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import org.gszone.jfenix13.Main;

/** Launches the GWT application. */
public class GwtLauncher extends GwtApplication {
    @Override
    public GwtApplicationConfiguration getConfig() {
        GwtApplicationConfiguration configuration = new GwtApplicationConfiguration(800, 600);
        return configuration;
    }

    @Override
    public ApplicationListener createApplicationListener() {
        return new Main();
    }
}