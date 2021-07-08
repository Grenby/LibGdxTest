package com.mygdx.projects.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.projects.Start;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		//config.backgroundFPS
		config.title="name";
		config.fullscreen=true;
		config.vSyncEnabled = true;
		config.height = 1920 ;
		config.width = 1080;
		new LwjglApplication(new Start(), config);
	}
}
