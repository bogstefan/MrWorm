package com.stefan.mrworm.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.stefan.mrworm.Main;
import com.stefan.mrworm.screens.LevelScreen;

import static com.stefan.mrworm.screens.LevelScreen.LEVEL_HEIGHT;
import static com.stefan.mrworm.screens.LevelScreen.LEVEL_WIDTH;
import static com.stefan.mrworm.screens.LevelScreen.METERS_TO_PIXEL;

public class Worm extends Actor {

    private static final float RADIUS = 0.1F * METERS_TO_PIXEL;
    private static final float MOVEMENT_SPEED = 0.1F * METERS_TO_PIXEL;
    public Array<Circle> bodyParts;
    public boolean moveLeft;
    public boolean moveRight;
    private Main main;
    private LevelScreen levelScreen;
    private float angle = 10F;
    private Vector2 direction;
    private long lastTimeMoved;

    public Worm(Main main, LevelScreen levelScreen) {
        this.main = main;
        this.levelScreen = levelScreen;
        bodyParts = new Array<Circle>();
        direction = new Vector2(0, MOVEMENT_SPEED);
        bodyParts.add(new Circle(LEVEL_WIDTH / 2, LEVEL_HEIGHT / 2, RADIUS));
        addSnakeSize(5);
    }

    @Override
    public void act(float delta) {
        setAngle();
        move();
        checkIfEatenApple();
        checkIfEatenBigApple();
        handleWallCollision();
        handleSelfCollision();
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.end();
        main.renderer.setColor(Color.CYAN);
        main.renderer.begin(ShapeRenderer.ShapeType.Filled);
        for (Circle bodyPart : bodyParts) {
            main.renderer.circle(bodyPart.x, bodyPart.y, bodyPart.radius);
        }
        main.renderer.end();
        batch.begin();
        super.draw(batch, parentAlpha);
    }

    private void addPart() {
        Circle tail = bodyParts.get(bodyParts.size - 1);
        bodyParts.insert(bodyParts.size - 1, new Circle(tail.x, tail.y, RADIUS));
    }

    private void move() {
        if (System.currentTimeMillis() - lastTimeMoved < 1) {
            return;
        }
        lastTimeMoved = System.currentTimeMillis();
        Circle head = bodyParts.get(0);
        Circle tail = bodyParts.removeIndex(bodyParts.size - 1);
        tail.setPosition(head.x + direction.x, head.y + direction.y);
        bodyParts.insert(0, tail);
    }

    private void handleSelfCollision() {
        Circle head = bodyParts.get(0);
        for (Circle bodyPart : bodyParts) {
            if (head != bodyPart && bodyPart.overlaps(head)
                    && bodyParts.indexOf(bodyPart, true) > 360 / angle) {
                levelScreen.gameOver = true;
            }
        }
    }

    private void handleWallCollision() {
        Circle head = bodyParts.get(0);
        if (head.x > LEVEL_WIDTH || head.x < 0 || head.y > LEVEL_HEIGHT || head.y < 0) {
            levelScreen.gameOver = true;
        }
    }

    private void setAngle() {
        if (moveRight) {
            direction.rotate(-angle);
        } else if (moveLeft) {
            direction.rotate(angle);
        }
    }

    private void checkIfEatenApple() {
        if (levelScreen.apple == null) {
            return;
        }
        Circle head = bodyParts.get(0);
        if (head.overlaps(levelScreen.apple.circle)) {
            levelScreen.score += 5;
            levelScreen.hud.update();
            levelScreen.apple.remove();
            levelScreen.apple = null;
            levelScreen.lastTimeAppleEaten = System.currentTimeMillis();
            addPart();
            levelScreen.hasApple = false;
        }
    }

    private void checkIfEatenBigApple() {
        if (levelScreen.bigApple == null) {
            return;
        }
        Circle head = bodyParts.get(0);
        if (head.overlaps(levelScreen.bigApple.circle)) {
            levelScreen.score += 20;
            levelScreen.hud.update();
            levelScreen.bigApple.remove();
            levelScreen.bigApple = null;
            levelScreen.lastTimeBigAppleEaten = System.currentTimeMillis();
            addPart();
            addPart();
            levelScreen.hasBigApple = false;
        }
    }

    private void addSnakeSize(int size) {
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                addPart();
            }
        }, 0.01F, 0.01F, size);
    }

}
