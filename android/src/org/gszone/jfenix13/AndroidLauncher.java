package org.gszone.jfenix13;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import org.gszone.jfenix13.general.Main;

/**
 * Clase que inicia el juego en android
 */
public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useWakelock = true;
		config.hideStatusBar = true;
		config.useImmersiveMode = true;
		initialize(new Main(), config);
	}
}
