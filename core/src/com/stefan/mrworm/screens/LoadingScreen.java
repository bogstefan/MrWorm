package com.stefan.mrworm.screens;

import com.badlogic.gdx.Screen;
import com.stefan.mrworm.Main;

public class LoadingScreen implements Screen {

    private Main main;

    public LoadingScreen(Main main) {
        this.main = main;
        addAssets();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if (main.manager.update()) {
            main.setScreen(new LevelScreen(main));
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    private void addAssets() {

    }

}
