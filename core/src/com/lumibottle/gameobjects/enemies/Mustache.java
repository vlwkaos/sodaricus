package com.lumibottle.gameobjects.enemies;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.lumibottle.gameobjects.GameEvent;
import com.lumibottle.helper.AssetHelper;
import com.lumibottle.helper.FXHelper;
import com.lumibottle.screen.GameScreen;

/**
 * Mustache moves in a jelly-fish-like fashion, it is slightly unpredictable.
 */
public class Mustache extends GameEvent {

    private boolean doneMoving;

    private ParticleEffect rainbowParticle;

    final private int speed = 120;
    private float runTime;

    public Mustache() {
        super(33, 16, new Polygon(new float[]{3, 3, 30, 3, 23, 16, 12, 16}), 5);

    }


    //must separate moving, stopping
    @Override
    public void update(float delta) {
        if (isVISIBLE()) {
            getHitbox().setPosition(getX(), getY());
            runTime += delta;


            if (!doneMoving && runTime >= .4f) {
                runTime -= .4f;
                doneMoving = true;
                nextTheta();
            }

            if (doneMoving && runTime >= 1.2f) { // resting
                runTime -= 1.2f;
                doneMoving = false;
            }

            if (!doneMoving) {
                setVelocity(MathUtils.sin(runTime * MathUtils.PI / 0.4f) * MathUtils.cosDeg(getTheta()) * speed,
                        MathUtils.sin(runTime * MathUtils.PI / 0.4f) * MathUtils.sinDeg(getTheta()) * speed);// changing
            }

            getPosition().add(getVelocity().cpy().scl(delta));

            if (isOutOfScreen(true, false, true, true))
                dead();
        }

    }

    public void reset(float x) {
        super.reset(x, MathUtils.random(GameScreen.gameHeight) - getHeight(), -50, 0, 0);
        rainbowParticle = AssetHelper.rainbowPool.obtain();
        runTime = MathUtils.random(0, 0.5f);
        doneMoving = false;
        nextTheta();
    }

    public ParticleEffect getParticle() {
        return rainbowParticle;
    }

    @Override
    public void dead() {
        super.dead();
        AssetHelper.rainbowPool.free((ParticleEffectPool.PooledEffect) rainbowParticle);
        FXHelper.getInstance().newFX(getPrevX(), getPrevY(), Math.max(getWidth(), getHeight()), (short) 5);
    }

    public boolean isDoneMoving() {
        return doneMoving;
    } // to notify sprite batch

    private void nextTheta() {
        if (getY() < getHeight())
            setTheta(MathUtils.random(100, 120));
        else if (getY() > GameScreen.gameHeight - getHeight())
            setTheta(MathUtils.random(240, 260));
        else
            setTheta(MathUtils.random(110, 250));// set direction for the next move

    }

    public float getRunTime() {
        return runTime;
    }


    //checks in progress handler, then call squirrel's dead, reset method
}
