package com.lumibottle.gameobjects.Bullets;


import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.lumibottle.gameobjects.GameEvent;


/**
 * Created by MG-POW on 2016-03-12.
 */
public class Bullet extends GameEvent {

    private float theta;
    //load in gameworld first, the move
    private Vector2 acceleration;

    private float runTime; // kill timer


    public Bullet() {
        super(12, 12, new Polygon(new float[]{2, 6, 2, 12, 6, 12, 6, 6}), 1);
        getHitbox().setOrigin(getWidth() / 2f, getHeight() / 2f);
        acceleration = new Vector2(0, -460);
    }

    public void update(float delta) {
        if (isVISIBLE()) {
            runTime+= delta;
//			setX(100);
//			setY(100);

            getVelocity().add(acceleration.cpy().scl(delta));//add acc to velocity
            getPosition().add(getVelocity().cpy().scl(delta));
            getHitbox().setPosition(getX(), getY());
            getHitbox().setRotation(getTheta());

            this.theta -= delta * 1000;//1-000


            if (isOutOfScreen(true, true, false, true) || runTime > 2.0f) {
                silentDead();
            }
        }
    }

    public void reset(float x, float y, float speed, float theta) {
//		if (theta > 0)
//			this.theta=5*MathUtils.log2(theta);
//		else if (theta <0)
//			this.theta=(-5)*MathUtils.log2((-1)*theta);
//		else
        this.theta = theta;
        this.runTime = 0.0f;
        super.reset(x, y, speed * MathUtils.cos(MathUtils.degreesToRadians * this.theta), speed * MathUtils.sin(MathUtils.degreesToRadians * this.theta), this.theta);
    }

    @Override
    public float getTheta() {
        return theta;
    }

    @Override
    public void dead(){
        silentDead();
    }
}
