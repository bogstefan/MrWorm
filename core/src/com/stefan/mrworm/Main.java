package com.stefan.mrworm;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.stefan.mrworm.screens.LoadingScreen;

public class Main extends Game {

    public ShapeRenderer renderer;
    public AssetManager manager;
    public BitmapFont font;

    @Override
    public void create() {
        renderer = new ShapeRenderer();
        manager = new AssetManager();
        font = new BitmapFont(Gdx.files.internal("font/font1.fnt"));
        setScreen(new LoadingScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        renderer.dispose();
        manager.dispose();
        font.dispose();
    }

}
