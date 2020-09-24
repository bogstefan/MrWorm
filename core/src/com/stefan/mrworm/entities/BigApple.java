package com.stefan.mrworm.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Timer;
import com.stefan.mrworm.Main;
import com.stefan.mrworm.screens.LevelScreen;

import static com.stefan.mrworm.screens.LevelScreen.METERS_TO_PIXEL;

public class BigApple extends Actor {

    private static final float RADIUS = 0.3F * METERS_TO_PIXEL;
    Circle circle;
    private Main main;
    private LevelScreen levelScreen;
    private static final float TIME = 5;

    public BigApple(Main main, LevelScreen levelScreen, float x, float y) {
        this.main = main;
        this.levelScreen = levelScreen;
        circle = new Circle(x, y, RADIUS);
        decay();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.end();
        main.renderer.setColor(Color.GREEN);
        main.renderer.begin(ShapeRenderer.ShapeType.Filled);
        main.renderer.circle(circle.x, circle.y, circle.radius);
        main.renderer.end();
        batch.begin();
        super.draw(batch, parentAlpha);
    }

    private void decay() {
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                remove();
                levelScreen.bigApple = null;
                levelScreen.lastTimeBigAppleEaten = System.currentTimeMillis();
                levelScreen.hasBigApple = false;
            }
        }, TIME);
    }
}
