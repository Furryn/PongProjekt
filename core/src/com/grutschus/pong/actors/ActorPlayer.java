package com.grutschus.pong.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.grutschus.pong.Pong;
import com.grutschus.pong.REFERENCE;

/**
 * Created by Till on 30.09.2016
 * Class Description:
 * Class describing the Players
 */
public class ActorPlayer extends Actor {

    private ShapeRenderer bat;
    private Rectangle boundingRectangle;
    private Sprite texture;
    private Vector2 position;
    private Vector2[] positionHistory;
    private float sizeX;
    private float sizeY;
    private float velocity;
    private int score;
    private int upKeyCode, downKeyCode;
    private boolean controlled;


    /**
     * Constructor for a ActorPlayer-Object with given values
     *
     * @param pos        Default position of the new player
     * @param sizeX      Size in x-direction of the new player
     * @param sizeY      Size in y-direction of the new player
     * @param velocity   Default velocity of the new player
     * @param controlled Player or AI control
     */
    public ActorPlayer(Vector2 pos, float sizeX, float sizeY, float velocity, boolean controlled, int upKeyCode, int downKeyCode) {
        this.bat = new ShapeRenderer();
        this.positionHistory = new Vector2[2];
        this.positionHistory[0] = pos.cpy();
        this.position = pos;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.velocity = velocity;
        this.controlled = controlled;
        this.boundingRectangle = new Rectangle(this.position.x, this.position.y, this.sizeX, this.sizeY);
        this.texture = new Sprite(new Texture(REFERENCE.TEXTURES.PLAYER));
        this.upKeyCode = upKeyCode;
        this.downKeyCode = downKeyCode;

        // Resize texture
        this.texture.setSize(this.sizeX, this.sizeY);

        setBounds(this.position.x, this.position.y, sizeX, sizeY);
        setVisible(true);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        texture.setPosition(position.x, position.y);
        texture.draw(batch);
        /*batch.end();
        bat.begin(ShapeRenderer.ShapeType.Filled);
        bat.setColor(Color.WHITE);
        bat.rect(this.position.x, this.position.y, sizeX, sizeY);
        bat.setProjectionMatrix(Pong.getStageGame().getCamera().combined);
        bat.end();
        batch.begin();*/
    }

    @Override
    public void act(float delta) {
        boundingRectangle.setPosition(this.position.x, this.position.y);
        positionHistory[1] = positionHistory[0].cpy();
        positionHistory[0] = position.cpy();
    }

    /**
     * Returns whether this is controlled by a human
     *
     * @return boolean
     */
    public boolean isControlled() {
        return controlled;
    }

    /**
     * Gets the Bounding Rectangle of the player. Used for hit detection.
     *
     * @return Rectangle
     */
    public Rectangle getBoundingRectangle() {
        return boundingRectangle;
    }

    /**
     * Gets the current score of the player
     *
     * @return int
     */
    public int getScore() {
        return score;
    }

    /**
     * Sets the score of the player
     *
     * @param score New score
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Increments the score of the player by 1
     */
    public void incrementScore() {
        this.score++;
    }

    public void moveUp(float delta) {
        this.position.y = this.position.y + velocity * delta;
    }

    public void moveDown(float delta) {
        this.position.y = this.position.y - velocity * delta;
    }

    @Override
    public float getX() {
        return position.x;
    }

    @Override
    public float getY() {
        return position.y;
    }

    public Vector2 getPosition()
    {
        return position;
    }

    @Override
    public float getHeight() {
        return sizeY;
    }

    public Vector2[] getPositionHistory() {
        return positionHistory;
    }

    public float getDeltaPositionY() {
        return positionHistory[1].y - positionHistory[0].y;
    }

    public void setUpKeyCode(int i)
    {
        this.upKeyCode = i;
    }


    public void setDownKeyCode(int i)
    {
        this.downKeyCode = i;
    }

    public int getUpKeyCode()
    {
        return this.upKeyCode;
    }

    public int getDownKeyCode()
    {
        return this.downKeyCode;
    }
}
