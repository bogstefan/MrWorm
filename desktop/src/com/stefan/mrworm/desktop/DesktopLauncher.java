package com.stefan.mrworm.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.stefan.mrworm.Main;

public class DesktopLauncher {

    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.resizable = true;
        config.title = "Mr. Worm";
        config.width = 700;
        config.height = 800;
        new LwjglApplication(new Main(), config);
    }

}
