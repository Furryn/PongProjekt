package com.grutschus.pong.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
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


    public Rectangle getBoundingRectangle() {
        return boundingRectangle;
    }

    public void speedUpBall() {
        this.velocity.setLength(this.velocity.len() + 50);
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

    public Vector2 getVelocity() {
        return velocity;
    }

    public void applyPhysics(ActorPlayer player) {
        float distance = (player.getY() + player.getHeight() / 2) - getY();
        distance = abs(distance / (player.getHeight() / 2));

        float angle = velocity.angle();
        tempVector2.set(velocity.cpy());
        tempVector2.x = -tempVector2.x;
        float angle2 = tempVector2.angle();

        float deltaAngle = angle2 - angle;

        if (distance < 0.75) {
            velocity.rotate(deltaAngle);
        } else {
            velocity.rotate(180);
        }
    }
}