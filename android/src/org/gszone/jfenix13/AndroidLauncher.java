package org.gszone.jfenix13;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

/** Launches the Android application. */
public class AndroidLauncher extends AndroidApplication {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration configuration = new AndroidApplicationConfiguration();
        configuration.useWakelock = true;
        configuration.hideStatusBar = true;
        configuration.useImmersiveMode = true;
        initialize(new Main(), configuration);
    }
}