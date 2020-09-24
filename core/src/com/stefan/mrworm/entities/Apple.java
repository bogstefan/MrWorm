package com.stefan.mrworm.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.stefan.mrworm.Main;

import static com.stefan.mrworm.screens.LevelScreen.METERS_TO_PIXEL;

public class Apple extends Actor {

    private static final float RADIUS = 0.1F * METERS_TO_PIXEL;
    Circle circle;
    private Main main;

    public Apple(Main main, float x, float y) {
        this.main = main;
        circle = new Circle(x, y, RADIUS);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.end();
        main.renderer.setColor(Color.RED);
        main.renderer.begin(ShapeRenderer.ShapeType.Filled);
        main.renderer.circle(circle.x, circle.y, circle.radius);
        main.renderer.end();
        batch.begin();
        super.draw(batch, parentAlpha);
    }
}
