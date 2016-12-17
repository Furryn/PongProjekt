package com.grutschus.pong.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.grutschus.pong.Pong;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 960;
		config.height = 540;
		config.resizable = true;
		config.addIcon("bacteria_128x128.png", Files.FileType.Internal);
		config.addIcon("bacteria_32x32.png", Files.FileType.Internal);
		config.addIcon("bacteria_16x16.png", Files.FileType.Internal);
		config.title = "Bakterienschleuder";
		new LwjglApplication(new Pong(), config);
	}
}
