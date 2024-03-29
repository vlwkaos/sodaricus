package com.lumibottle.gameobjects.enemies.bosses;


import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.lumibottle.gameobjects.GameEvent;
import com.lumibottle.helper.FXHelper;
import com.lumibottle.screen.GameScreen;


/**
 * Created by MG-POW on 2016-07-10.
 */

public class PangBoss extends GameEvent {


    final public int max_gen = 4;
    private int generation;
    private float speed;
    private float aestheticTheta;

    private boolean fullyVisible;
    private boolean breeding;

    public PangBoss() {
        super(64, 64, new Polygon(new float[]{0, 0, 0, 64, 64, 64, 64, 0}), 1);
        generation = 1;
        speed = 70;
        fullyVisible = false;
        aestheticTheta = 0.0f;
        breeding = false;
    }

    @Override
    public void update(float delta) {
        if (isVISIBLE()) {
            addTheta(delta);

            //generation 1
            if (!fullyVisible) {
                if (getX() < 240 - getWidth()) {
                    setX(240 - getWidth());
                    float deg = randDeg();
                    aestheticTheta = deg;
                    setTheta(deg);
                    setVelocity(speed * MathUtils.cosDeg(deg), speed * MathUtils.sinDeg(deg));
                    fullyVisible = true;
                } else {
                    setVelocity(-50, 0);
                }
                getPosition().add(getVelocity().cpy().scl(delta));
                getHitbox().setPosition(getX(), getY());
            } else {
                aestheticTheta += delta * 10.0f;
                getPosition().add(getVelocity().cpy().scl(delta));
                getHitbox().setPosition(getX(), getY());

                //change velocity first,
                if (getX() > 240 - getWidth()) {
                    setX(240 - getWidth());
                    setVelocity(-getdX(), getdY());
                    setTheta(180 - getTheta());
                }
                if (getX() < 0) {
                    setX(0);
                    setVelocity(-getdX(), getdY());
                    setTheta(180 - getTheta());
                }
                if (getY() > GameScreen.gameHeight - getHeight()) {
                    setY(GameScreen.gameHeight - getHeight());
                    setVelocity(getdX(), -getdY());
                    setTheta(-getTheta());
                }
                if (getY() < 0) {
                    setY(0);
                    setVelocity(getdX(), -getdY());
                    setTheta(-getTheta());
                }


            }


        }
    }

    @Override
    public void hit() {
        if (getX() < 240 - getWidth())
            super.hit();
//        addTheta(MathUtils.random(-15, 15));
//        setVelocity(speed * MathUtils.cosDeg(getTheta()), speed * MathUtils.sinDeg(getTheta()));
// 어짜피 한방이니까
    }

    @Override
    public void dead() {
        super.dead();
        if (generation < max_gen)
            breeding = true;
        else
            breeding = false;

    }

    public void reset(float x, float y, int n) {
        breeding = false;
        float deg = randDeg();
        aestheticTheta = deg;
        super.reset(x, y, speed * MathUtils.cosDeg(deg), speed * MathUtils.sinDeg(deg), deg);

        generation = n;
        setWidth(64 / n);
        setHeight(64 / n);
        setHitbox(new float[]{getWidth()/ 3f, 0, getWidth() * 2 / 3f, 0,
                getWidth(), getHeight() / 3f, getWidth(), getHeight() * 2 / 3f,
                getWidth() * 2 / 3f, getHeight(), getWidth() / 3f, getHeight(),
                0, getHeight() * 2 / 3f, 0, getHeight() / 3f});

        fullyVisible = generation != 1;
        //0,0에서
    }

    private float randDeg() {
        float deg = MathUtils.random(135, 225);
        int dir = MathUtils.random(1);
        if (dir == 1) {
            return deg;
        } else {
            return deg - 180;
        }

    }

    public boolean isBreeding() {
        return breeding;
    }

    public void doneBreeding() {
        breeding = false;
    }

    public float getAestheticTheta() {
        return aestheticTheta;
    }

    public int getGeneration() {
        return generation;
    }

}
