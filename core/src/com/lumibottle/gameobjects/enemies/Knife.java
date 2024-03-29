package com.lumibottle.gameobjects.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.lumibottle.gameobjects.Bullets.Bullet;
import com.lumibottle.gameobjects.FX;
import com.lumibottle.gameobjects.GameEvent;
import com.lumibottle.gameobjects.Squirrel;
import com.lumibottle.helper.FXHelper;
import com.lumibottle.screen.GameScreen;

/**
 * Created by MG on 2016-09-01.
 */
public class Knife extends GameEvent {

    private Squirrel mySquirrel;

    private boolean targetAquired;

    private float targetAngle;
    final private float speed = 120;

    public Knife(Squirrel msq) {
        super(24, 6, new Polygon(new float[]{0,0,24,0,24,6,0,6}), 0);
        getHitbox().setOrigin(12,3);
        mySquirrel = msq;
        targetAngle = 0;
        targetAquired=false;
    }

    @Override
    public void update(float delta) {
        if (isVISIBLE()) {

            if (!targetAquired) {

                addTheta(delta * 3 * 360.0f);

                if (getX() < 240 - getWidth() * 1.5f) {
                    setVelocity(0, 0);
                    if (getTheta() > 360*3) {

                        targetAquired = true;
                        targetAngle = MathUtils.atan2(mySquirrel.getY()-getY(),getX()-mySquirrel.getX());
                        setVelocity(-speed * MathUtils.cos(targetAngle), speed * MathUtils.sin(targetAngle));
                        setTheta(-targetAngle*MathUtils.radDeg);
                        getHitbox().setRotation(getTheta());

                    }

                } else {
                    //entering
                    setVelocity(-speed, 0);
                }
            }

            if (isOutOfScreen(true, false, true, true))
                silentDead();



            getPosition().add(getVelocity().cpy().scl(delta));
            getHitbox().setPosition(getX(), getY());
        }

    }

    public void reset(float x) {
        float rand = MathUtils.random(GameScreen.gameHeight- getHeight());
        FXHelper.getInstance().newFX(240-16,rand+getHeight()/2-8, FX.WARN);
        super.reset(x, rand, 0, 0, 0);
        targetAquired=false;
    }

    @Override
    public void bottleHitsEnemy(Bullet b) {
        //do nothing. bad object orientation?( inverted)
    }
}
