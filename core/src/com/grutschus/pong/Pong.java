package com.grutschus.pong;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.grutschus.pong.actors.ActorBall;
import com.grutschus.pong.actors.ActorPlayer;

public class Pong extends ApplicationAdapter {

    private static Stage stageGame;
    private static GameManager gameManager;

    @Override
    public void create() {
        initstageGame();
        
        stageGame.getViewport().setWorldSize((int) REFERENCE.GAME_WORLD_WIDTH, (int) REFERENCE.GAME_WORLD_HEIGHT);
        stageGame.getViewport().apply();
        stageGame.getCamera().position.set(REFERENCE.GAME_WORLD_WIDTH / 2, REFERENCE.GAME_WORLD_HEIGHT / 2, 0);

        Gdx.input.setInputProcessor(stageGame);

    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0.15f, 0.15f, 0.15f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stageGame.act(Gdx.graphics.getDeltaTime());
        stageGame.draw();
    }

    @Override
    public void dispose() {
        stageGame.dispose();
    }

    private void initstageGame() {
        stageGame = new Stage(new FitViewport(REFERENCE.GAME_WORLD_HEIGHT * REFERENCE.ASPECT_RATIO, REFERENCE.GAME_WORLD_HEIGHT));
        gameManager = new GameManager();

        gameManager.addPlayer(REFERENCE.PLAYER1_DEF_POS,
                REFERENCE.PLAYER1_SIZE_X,
                REFERENCE.PLAYER1_SIZE_Y,
                REFERENCE.PLAYER1_DEF_VELOCITY,
                true,
                "player",
                Input.Keys.W,
                Input.Keys.S);

        gameManager.addPlayer(REFERENCE.PLAYER2_DEF_POS,
                REFERENCE.PLAYER2_SIZE_X,
                REFERENCE.PLAYER2_SIZE_Y,
                REFERENCE.PLAYER2_DEF_VELOCITY,
                true,
                "computer",
                Input.Keys.UP,
                Input.Keys.DOWN);

        gameManager.addBall("ball");

        stageGame.addActor(gameManager);
        stageGame.addActor(gameManager.getUiElements());
        stageGame.setKeyboardFocus(gameManager);
    }

    public static Stage getStageGame() {
        return stageGame;
    }
}
