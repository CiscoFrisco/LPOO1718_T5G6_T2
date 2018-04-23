package com.ciscominas.airhockeymania.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.ciscominas.airhockeymania.AirHockeyMania;
import com.ciscominas.airhockeymania.utils.Constants;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = Constants.APP_WIDTH;
		config.height = Constants.APP_HEIGHT;
		config.resizable = false;
		new LwjglApplication(new AirHockeyMania(), config);
	}
}
