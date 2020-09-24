package com.stefan.mrworm.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.stefan.mrworm.Main;
import com.stefan.mrworm.entities.Apple;
import com.stefan.mrworm.entities.BigApple;
import com.stefan.mrworm.entities.Worm;
import com.stefan.mrworm.hud.Hud;

import java.util.Random;

public class LevelScreen implements Screen, InputProcessor {

    public static final float METERS_TO_PIXEL = 80F;

    public static final float LEVEL_WIDTH = 10F * METERS_TO_PIXEL;
    public static final float LEVEL_HEIGHT = 10F * METERS_TO_PIXEL;
    private static final float BORDER_SIZE = 0.1F * METERS_TO_PIXEL;
    private static final long APPLE_SPAWN_TIME = 0;
    private static final long BIG_APPLE_SPAWN_TIME = 25 * 1000;

    public boolean gameOver;
    public boolean hasApple;
    public boolean hasBigApple;
    public int score;
    public Stage stage;
    public Apple apple;
    public BigApple bigApple;
    public long lastTimeAppleEaten;
    public long lastTimeBigAppleEaten;
    public Hud hud;
    private Main main;
    private Worm worm;
    private Random random;

    LevelScreen(Main main) {
        random = new Random();
        this.main = main;
        stage = new Stage(new FitViewport(LEVEL_WIDTH, LEVEL_HEIGHT + Hud.HEIGHT));
        main.renderer.setProjectionMatrix(stage.getCamera().combined);
        apple = new Apple(main, LEVEL_WIDTH / 3, LEVEL_HEIGHT / 2);
        worm = new Worm(main, this);
        hud = new Hud(main, this);
        stage.addActor(worm);
        Gdx.input.setInputProcessor(this);
        lastTimeBigAppleEaten = System.currentTimeMillis();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        drawBorder();
        handleFood();
        handleGameOver();

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
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
        Gdx.input.setInputProcessor(null);
        stage.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.LEFT) {
            worm.moveLeft = true;
        }
        if (keycode == Input.Keys.RIGHT) {
            worm.moveRight = true;
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.LEFT) {
            worm.moveLeft = false;
        }
        if (keycode == Input.Keys.RIGHT) {
            worm.moveRight = false;
        }
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (screenX < Gdx.graphics.getWidth() / 2) {
            worm.moveLeft = true;
        }
        if (screenX > Gdx.graphics.getWidth() / 2) {
            worm.moveRight = true;
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {

        if (screenX < Gdx.graphics.getWidth() / 2) {
            worm.moveLeft = false;
        }
        if (screenX > Gdx.graphics.getWidth() / 2) {
            worm.moveRight = false;
        }
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    private void handleFood() {
        if (!hasApple && System.currentTimeMillis() - lastTimeAppleEaten > APPLE_SPAWN_TIME) {
            hasApple = true;
            spawnApple();
        }
        if (!hasBigApple && System.currentTimeMillis() - lastTimeBigAppleEaten > BIG_APPLE_SPAWN_TIME) {
            hasBigApple = true;
            spawnBigApple();
        }
    }

    private void spawnBigApple() {
        float x = 0;
        float y = 0;
        int minX = (int) (1 * METERS_TO_PIXEL);
        int maxX = (int) (LEVEL_WIDTH - 1 * METERS_TO_PIXEL);

        int minY = (int) (1 * METERS_TO_PIXEL);
        int maxY = (int) (LEVEL_HEIGHT - 1 * METERS_TO_PIXEL);

        boolean foundX = false;
        outer:
        while (!foundX) {
            x = random.nextInt(maxX - minX + 1) + minX;
            for (Circle bodyPart : worm.bodyParts) {
                if (bodyPart.x == x) {
                    continue outer;
                }
            }
            foundX = true;
        }

        boolean foundY = false;
        outer:
        while (!foundY) {
            y = random.nextInt(maxY - minY + 1) + minY;
            for (Circle bodyPart : worm.bodyParts) {
                if (bodyPart.y == y) {
                    continue outer;
                }
            }
            foundY = true;
        }
        bigApple = new BigApple(main, this, x, y);
        stage.addActor(bigApple);
    }

    private void spawnApple() {
        float x = 0;
        float y = 0;
        int minX = (int) (1 * METERS_TO_PIXEL);
        int maxX = (int) (LEVEL_WIDTH - 1 * METERS_TO_PIXEL);

        int minY = (int) (1 * METERS_TO_PIXEL);
        int maxY = (int) (LEVEL_HEIGHT - 1 * METERS_TO_PIXEL);

        boolean foundX = false;
        outer:
        while (!foundX) {
            x = random.nextInt(maxX - minX + 1) + minX;
            for (Circle bodyPart : worm.bodyParts) {
                if (bodyPart.x == x) {
                    continue outer;
                }
            }
            foundX = true;
        }

        boolean foundY = false;
        outer:
        while (!foundY) {
            y = random.nextInt(maxY - minY + 1) + minY;
            for (Circle bodyPart : worm.bodyParts) {
                if (bodyPart.y == y) {
                    continue outer;
                }
            }
            foundY = true;
        }
        apple = new Apple(main, x, y);
        stage.addActor(apple);
    }

    private void handleGameOver() {
        if (gameOver) {
            dispose();
            main.setScreen(new LevelScreen(main));
        }
    }

    private void drawBorder() {
        main.renderer.setColor(Color.BLUE);
        main.renderer.begin(ShapeRenderer.ShapeType.Filled);
        main.renderer.rect(0, 0, LEVEL_WIDTH, BORDER_SIZE);
        main.renderer.rect(0, 0, BORDER_SIZE, LEVEL_HEIGHT);
        main.renderer.rect(0, LEVEL_HEIGHT - BORDER_SIZE, LEVEL_WIDTH, BORDER_SIZE);
        main.renderer.rect(LEVEL_WIDTH - BORDER_SIZE, 0, BORDER_SIZE, LEVEL_HEIGHT);
        main.renderer.end();
    }

}
