package com.grutschus.pong;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.grutschus.pong.actors.ActorBall;
import com.grutschus.pong.actors.ActorPlayer;

/**
 * Created by Till on 05.10.2016
 * Class Description:
 * Manages interaction of actors.
 */
public class GameManager extends Actor {

    private Group players, balls, uiElements;
    private InputListener inputListener;
    private boolean[] isPressed = new boolean[256];
    private Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
    private Label counter = new Label("0:0", skin);
    private float friction = 20;
    private float curvatureConstant = 10;
    private Sprite background;

    public GameManager() {
        players = new Group();
        balls = new Group();
        uiElements = new Group();
        addUiElement(new Table(skin), "rootTable");
        addUiElement(counter, "counter");

        background = new Sprite(new Texture(REFERENCE.TEXTURES.BACKGROUND));
        background.setSize(REFERENCE.GAME_WORLD_WIDTH, REFERENCE.GAME_WORLD_HEIGHT);
        background.setPosition(0, 0);

        initInputListener();
        addListener(inputListener);

        initRootTable();
    }

    private void initRootTable() {
        getRootTable().setSize(REFERENCE.GAME_WORLD_WIDTH, REFERENCE.GAME_WORLD_HEIGHT);
        getRootTable().setVisible(true);
        getRootTable().top();
        getRootTable().add(counter);
        getRootTable().row();
        counter.setFontScale(2);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        background.draw(batch);
        players.draw(batch, parentAlpha);
        balls.draw(batch, parentAlpha);
    }

    @Override
    public void act(float delta) {
        handleCollision();
        moveHumanPlayers(delta);
        moveComputerPlayers(delta);
        players.act(delta);
        balls.act(delta);
    }

    private void moveComputerPlayers(float delta) {
        ActorBall closestBall = null;
        for (Actor player : players.getChildren()) {
            if (!((ActorPlayer) player).isControlled()) {
                for (Actor ball : balls.getChildren()) {
                    if (closestBall == null)
                        closestBall = (ActorBall) ball;
                    if (Math.abs(ball.getX() - player.getX()) < Math.abs(closestBall.getX() - player.getX()))
                        closestBall = (ActorBall) ball;
                }
                if (closestBall.getY() > player.getY() + player.getHeight() / 2 && player.getY() < REFERENCE.GAME_WORLD_HEIGHT - player.getHeight())
                    ((ActorPlayer) player).moveUp(delta);
                if (closestBall.getY() < player.getY() + player.getHeight() / 2 && player.getY() > 0)
                    ((ActorPlayer) player).moveDown(delta);
            }
        }
    }


    /**
     * Processes key inputs for human players
     *
     * @param delta Time since last frame
     */
    private void moveHumanPlayers(float delta) {
        for (Actor player : players.getChildren()) {
            if (((ActorPlayer) player).isControlled()) {
                if (isPressed[Input.Keys.W] && player.getY() < REFERENCE.GAME_WORLD_HEIGHT - player.getHeight())
                    ((ActorPlayer) player).moveUp(delta);
                if (isPressed[Input.Keys.S] && player.getY() > 0)
                    ((ActorPlayer) player).moveDown(delta);
            }
        }
    }

    /**
     * Resets the game
     */
    private void reset(ActorBall ball) {
        ActorPlayer closestPlayer = null;
        for (Actor player : players.getChildren()) {
            if (closestPlayer == null)
                closestPlayer = (ActorPlayer) player;
            if (Math.abs(ball.getX() - player.getX()) < Math.abs(closestPlayer.getX() - player.getX()))
                closestPlayer = (ActorPlayer) player;
        }
        closestPlayer.incrementScore();

        counter.setText(((ActorPlayer) players.findActor("computer")).getScore()
                + ":" +
                ((ActorPlayer) players.findActor("player")).getScore());
        ball.reset();
    }


    /**
     * Initiates the InputListener for Keyboard-Control
     */
    private void initInputListener() {
        inputListener = new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                isPressed[keycode] = true;
                handleKeyDown(event, keycode);
                return true;
            }


            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                isPressed[keycode] = false;
                return true;
            }
        };
    }

    /**
     * Handles the KeyDown-Event
     *
     * @param event   The fired event
     * @param keycode The keycode of the pessed key
     */
    private void handleKeyDown(Event event, int keycode) {
    }

    /**
     * Handles all collisions of actors
     */
    private void handleCollision() {
        // Handle all balls:
        for (Actor ball : balls.getChildren()) {
            // Handle top and bottom of the screen
            if (ball.getY() <= 0 || ball.getY() + ball.getHeight() >= REFERENCE.GAME_WORLD_HEIGHT) {
                ((ActorBall) ball).reverseYVelocity();
            }

            // Handle player-collision
            for (Actor player : players.getChildren()) {
                if (((ActorBall) ball).getBoundingRectangle().overlaps(((ActorPlayer) player).getBoundingRectangle())) // Some ball hit some paddle
                {
                    ((ActorBall) ball).speedUpBall();
                    ((ActorBall) ball).reverseXVelocity();
                    ((ActorBall) ball).applyFriction(-((ActorPlayer) player).getDeltaPositionY() * friction);
                    ((ActorBall) ball).applyCurvature(((ActorPlayer) player), curvatureConstant);
                }
            }

            // Handle left and right of the screen
            if (ball.getX() <= 0 || ball.getX() >= REFERENCE.GAME_WORLD_WIDTH) {
                reset((ActorBall) ball);
            }
        }
    }

    /**
     * Adds a player to the game.
     *
     * @param actorPlayer The ActorPlayer to add
     * @param name        The ID of the actor
     */
    public void addPlayer(ActorPlayer actorPlayer, String name) {
        actorPlayer.setName(name);
        players.addActor(actorPlayer);
    }

    /**
     * Adds a player to the game with provided values.
     *
     * @param pos        Default position of the new player
     * @param sizeX      Size in x-direction of the new player
     * @param sizeY      Size in y-direction of the new player
     * @param velocity   Default velocity of the new player
     * @param controlled Player or AI control
     * @param name       The ID of the new player
     */
    public void addPlayer(Vector2 pos, float sizeX, float sizeY, float velocity, boolean controlled, String name) {
        addPlayer(new ActorPlayer(pos, sizeX, sizeY, velocity, controlled), name);
    }

    /**
     * Add a ball to the game
     *
     * @param actorBall The ActorBall to add
     * @param name      The ID of the actor
     */
    public void addBall(ActorBall actorBall, String name) {
        actorBall.setName(name);
        balls.addActor(actorBall);
    }

    /**
     * Adds a default ball to the game
     *
     * @param name The ID of the actor
     */
    public void addBall(String name) {
        addBall(new ActorBall(), name);
    }

    public void addUiElement(Actor actor, String name) {
        actor.setName(name);
        uiElements.addActor(actor);
    }

    /**
     * Gets the player-Group
     *
     * @return Group
     */
    public Group getPlayers() {
        return players;
    }

    /**
     * Gets the ball-Group
     *
     * @return Group
     */
    public Group getBalls() {
        return balls;
    }

    /**
     * Gets the first actor in the player-Group that is controlled by a human
     *
     * @return Actor
     */
    public Actor getKeyboardFocusActor() {
        for (Actor a : players.getChildren()) {
            if (a instanceof ActorPlayer) {
                if (((ActorPlayer) a).isControlled())
                    return a;
            }
        }
        return null;
    }

    public Table getRootTable() {
        return uiElements.findActor("rootTable");
    }

    public Label getCounter() {
        return uiElements.findActor("counter");
    }

    public Skin getSkin() {
        return skin;
    }

    public Group getUiElements() {
        return uiElements;
    }
}
