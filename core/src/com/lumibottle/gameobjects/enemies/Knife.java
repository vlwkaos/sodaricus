package com.lumibottle.gameobjects.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.lumibottle.gameobjects.Bullets.Bullet;
import com.lumibottle.gameobjects.GameEvent;
import com.lumibottle.gameobjects.Squirrel;
import com.lumibottle.helper.AssetHelper;
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
        super(32, 8, new Polygon(new float[]{0,0,32,0,32,8,0,8}), 0);
        mySquirrel = msq;
        targetAngle = 0;
        targetAquired=false;
    }

    @Override
    public void update(float delta) {
        if (isVISIBLE()) {

            if (!targetAquired) {

                addTheta(delta * 720.0f);

                if (getX() < 240 - getWidth() * 1.5f) {
                    setVelocity(0, 0);
                    if (getTheta() > 360*4) {
                        setTheta(0);
                        targetAquired = true;
                        targetAngle = MathUtils.atan2(mySquirrel.getY()-getY(),getX()-mySquirrel.getX());
                        setVelocity(-speed * MathUtils.cos(targetAngle), speed * MathUtils.sin(targetAngle));
                    }

                } else {
                    //entering
                    setVelocity(-speed, 0);
                }
            }

            if (isOutOfScreen(true, false, true, true))
                dead();

            getHitbox().setPosition(getX(), getY());
            getPosition().add(getVelocity().cpy().scl(delta));
        }

    }

    public void reset(float x) {
        super.reset(x, MathUtils.random(GameScreen.gameHeight) - getHeight(), 0, 0, 0);
        targetAquired=false;
    }

    @Override
    public void dead() {
        super.dead();
        FXHelper.getInstance().newFX(getPrevX(), getPrevY(), Math.max(getWidth(), getHeight()), (short) 5);// puff
    }

    @Override
    public void bottleHitsEnemy(Bullet b) {
        //do nothing. bad object orientation?( inverted)
    }
}
