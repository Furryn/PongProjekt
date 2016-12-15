package com.grutschus.pong.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.grutschus.pong.Pong;
import com.grutschus.pong.REFERENCE;

import static java.lang.Math.abs;

/**
 * Created by Till on 30.09.2016
 * Class Description:
 * Class describing the Ball.
 */
public class ActorBall extends Actor {

    private final ShapeRenderer ball;
    private final Rectangle boundingRectangle;
    private final Sprite texture;
    private Vector2 velocity, position, tempVector2;
    private float size;


    /**
     * Constructor for a ActorBall-Object with default values
     */
    public ActorBall() {
        this.ball = new ShapeRenderer();
        this.position = REFERENCE.BALL_DEF_POS.cpy();
        this.size = REFERENCE.BALL_DEF_SIZE;
        this.boundingRectangle = new Rectangle(this.position.x, this.position.y, this.size, this.size);
        this.velocity = REFERENCE.BALL_DEF_VELOCITY.cpy();
        this.tempVector2 = new Vector2();
        this.texture = new Sprite(new Texture(REFERENCE.TEXTURES.BALL));

        // Texture resizing
        this.texture.setSize(this.size, this.size);

        this.setBounds(position.x, position.y, size, size);

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        texture.setPosition(position.x, position.y);
        texture.draw(batch);
       /* batch.end();
        ball.begin(ShapeRenderer.ShapeType.Filled);
        ball.setColor(Color.WHITE);
        ball.rect(this.position.x, this.position.y, size, size);
        ball.setProjectionMatrix(Pong.getStageGame().getCamera().combined);
        ball.end();
        batch.begin();*/
    }

    @Override
    public void act(float delta) {
        this.position.x = this.position.x + velocity.x * delta;
        this.position.y = this.position.y + velocity.y * delta;
        boundingRectangle.setPosition(this.position.x, this.position.y);
    }

    /**
     * Resets the Game.
     */
    public void reset() {
        this.position.x = REFERENCE.BALL_DEF_POS.x;
        this.position.y = REFERENCE.BALL_DEF_POS.y;
        size = REFERENCE.BALL_DEF_SIZE;
        velocity = REFERENCE.BALL_DEF_VELOCITY.cpy();
    }

    public void setPosition(Vector2 position) {
        this.position = position.cpy();
    }

    public void reverseYVelocity() {
        this.velocity.y = -this.velocity.y;
    }

    public void reverseXVelocity() {
        this.velocity.x = -this.velocity.x;
    }

    public Rectangle getBoundingRectangle() {
        return boundingRectangle;
    }

    public void speedUpBall() {
        this.velocity.setLength(this.velocity.len() + 10);
    }

    @Override
    public float getY() {
        return this.position.y;
    }

    @Override
    public float getX() {
        return this.position.x;
    }

    @Override
    public float getHeight() {
        return size;
    }

    public void applyFriction(float amount) {
        tempVector2.set(velocity.cpy());
        float len = tempVector2.len2();
        tempVector2.y = tempVector2.y + amount;
        tempVector2.setLength2(len);
        if (!(abs(tempVector2.angle(Vector2.Y)) > 160) && !(abs(tempVector2.angle(Vector2.Y)) < 20))
            velocity.set(tempVector2.cpy());
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void applyCurvature(ActorPlayer player, float curvature) {
        float distance = ((player.getPosition().y + player.getHeight() / 2) - (this.getY() + this.getHeight() / 2));
        distance = distance / (player.getHeight() / 2); // Relative to height

        tempVector2.set(velocity.cpy());
        float len = tempVector2.len2();
        tempVector2.y = tempVector2.y + distance * curvature;
        tempVector2.setLength2(len);

        if (!(abs(tempVector2.angle(Vector2.Y)) > 160) && !(abs(tempVector2.angle(Vector2.Y)) < 20))
            velocity.set(tempVector2.cpy());
    }

}