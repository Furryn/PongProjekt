package com.grutschus.pong;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Till on 01.10.2016
 * Class Description:
 * This class contains reference to several values.
 */
public final class REFERENCE {

    // Screen-Data
    public static final float GAME_WORLD_HEIGHT = 900;
    public static final float GAME_WORLD_WIDTH = 1600;
    public static final float ASPECT_RATIO = (float) Gdx.graphics.getWidth() / (float) Gdx.graphics.getHeight();

    // Actor-Data
    public static final float PLAYER1_SIZE_X = GAME_WORLD_WIDTH / 50;
    public static final float PLAYER1_SIZE_Y = PLAYER1_SIZE_X * 10;

    public static final float PLAYER2_SIZE_X = GAME_WORLD_WIDTH / 50;
    public static final float PLAYER2_SIZE_Y = PLAYER2_SIZE_X * 10;

    public static final float PLAYER1_DEF_VELOCITY = GAME_WORLD_HEIGHT / 2;
    public static final float PLAYER2_DEF_VELOCITY = GAME_WORLD_HEIGHT / 2;

    public static final Vector2 PLAYER1_DEF_POS = new Vector2(10, GAME_WORLD_HEIGHT / 2);
    public static final Vector2 PLAYER2_DEF_POS = new Vector2(GAME_WORLD_WIDTH - (10 + PLAYER2_SIZE_X), GAME_WORLD_HEIGHT / 2);

    public static final Vector2 BALL_DEF_POS = new Vector2(GAME_WORLD_WIDTH / 2, GAME_WORLD_HEIGHT / 2);
    public static final Vector2 BALL_DEF_VELOCITY = new Vector2(-500, 50);
    public static final float BALL_DEF_SIZE = GAME_WORLD_WIDTH / 25;

    public final class TEXTURES {
        public static final String BALL = "bacteria.png";
        public static final String PLAYER = "syringe.png";
        public static final String BACKGROUND = "background.png";
    }
}
