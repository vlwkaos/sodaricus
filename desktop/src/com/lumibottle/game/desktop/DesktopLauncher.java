package com.lumibottle.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.lumibottle.game.GameMain;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "LumiBottle";
		config.height = 360;
		config.width = 640;
		config.forceExit = false;
		new LwjglApplication(new GameMain(), config);
	}
}
