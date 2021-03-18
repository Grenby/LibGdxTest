package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.RayM3v2.Shaders.MeshTutorial;
import com.mygdx.game.RayM3v2.Shaders.ScreenSh;
import com.mygdx.game.Start;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title="name";
		config.fullscreen=true;
		//config.vSyncEnabled = true;
		config.height=1080;
		config.width=1920;
		new LwjglApplication(new Start(), config);
	}
}
