package com.ciscominas.airhockeymania.desktop;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.ciscominas.airhockeymania.AirHockeyMania;
import com.ciscominas.airhockeymania.database.DesktopDatabase;
import com.ciscominas.airhockeymania.utils.Constants;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 400;
		config.height = 600;
		config.resizable = false;
		new LwjglApplication(new AirHockeyMania(new DesktopDatabase()), config);
	}
}
