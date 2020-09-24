package com.stefan.mrworm.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.stefan.mrworm.Main;
import com.stefan.mrworm.screens.LevelScreen;

import java.util.Locale;

import static com.stefan.mrworm.screens.LevelScreen.METERS_TO_PIXEL;

public class Hud {

    public static final float HEIGHT = 1F * METERS_TO_PIXEL;
    private LevelScreen levelScreen;
    private Label score;

    public Hud(Main main, LevelScreen levelScreen) {
        this.levelScreen = levelScreen;
        score = new Label(String.format(Locale.US, "Score: %d", levelScreen.score),
                new Label.LabelStyle(main.font, Color.WHITE));

        Label credits = new Label("Mr Worm by Stefan", new Label.LabelStyle(main.font, Color.WHITE));
        Table table = new Table();
        table.setFillParent(true);
        table.top();
        table.add(score).padTop(10).expandX();
        table.add(credits).padTop(10).expandX();
        levelScreen.stage.addActor(table);
    }

    public void update() {
        score.setText(String.format(Locale.US, "Score: %d", levelScreen.score));
    }

}
