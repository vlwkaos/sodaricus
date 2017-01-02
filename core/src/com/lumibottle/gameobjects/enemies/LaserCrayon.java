package com.lumibottle.gameobjects.enemies;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.lumibottle.gameobjects.Bullets.Bullet;
import com.lumibottle.gameobjects.GameEvent;
import com.lumibottle.helper.AssetHelper;
import com.lumibottle.helper.FXHelper;
import com.lumibottle.screen.GameScreen;

/**
 * LaserCrayon is a deadly weapon that cannot be eliminated. It is used to confine Player's movement
 */
public class LaserCrayon extends GameEvent {

    private enum LaserCrayonState {
        INIT, READYTOSHOOT, SHOT
    }

    private LaserCrayonState currentState;
    private float runTime;
    private float delay;

    private ParticleEffect energyParticle;


    public LaserCrayon() {
        super(29, 5, new Polygon(new float[]{0, 0, 29, 0, 29, 5, 0, 5}), 0);

    }

    @Override
    public void update(float delta) {

        if (isVISIBLE()) {
            getHitbox().setPosition(getX(), getY());
            runTime += delta;

            getPosition().add(getVelocity().cpy().scl(delta));

            // move right and
            if (getX() < 240 - getWidth() * 2 && currentState == LaserCrayonState.INIT) {
                getVelocity().add(4 * runTime, 0);
            }

            //stop
            if (getdX() > 0 && currentState == LaserCrayonState.INIT) {
                currentState = LaserCrayonState.READYTOSHOOT;
                setVelocity(0, 0);
                FXHelper.getInstance().newFX(0 - (240 - getX()), getY() - 12f, (short) 1);
                delay = 0;
            }

            //its hit to shoot after 1 second
            if (currentState == LaserCrayonState.READYTOSHOOT || currentState == LaserCrayonState.SHOT) {
                delay += delta;
            }


            if (currentState == LaserCrayonState.READYTOSHOOT && delay > 1f) {
                //change hit box
                setHitbox(new float[]{
                        -240, 14.5f,
                        0, 14.5f,
                        0, 5,
                        29, 5,
                        29, 0,
                        0, 0,
                        0, -10.5f,
                        -240, -10.5f});
                //put fx
                FXHelper.getInstance().newFX(0 - (240 - getX()), getY() - 12f, (short) 2);
                currentState = LaserCrayonState.SHOT;
                delay = 0;
            }

            if (currentState == LaserCrayonState.SHOT && delay > 3 / 15f) {
                //change hit box
                setHitbox(new float[]{0, 0, 29, 0, 29, 5, 0, 5});
                // go back to your home
                setVelocity(delay * 50, 0);
            }

            if (currentState == LaserCrayonState.SHOT && getX() > 240)
                dead();
        }

    }


    public void reset(float x) {
        super.reset(x, MathUtils.random(GameScreen.gameHeight- getHeight()), -50, 0, 0);
        energyParticle = AssetHelper.energyPool.obtain();
        runTime = 0;
        delay = 0;
        currentState = LaserCrayonState.INIT;
    }


    @Override
    public void dead() {
        super.dead();
        AssetHelper.energyPool.free((ParticleEffectPool.PooledEffect) energyParticle);

    }

    @Override
    public void bottleHitsEnemy(Bullet b) {
        //do nothing. bad object orientation?( inverted)
    }


    //getter setter
    public ParticleEffect getParticle() {
        return energyParticle;
    }

    public boolean isREADYTOSHOOT() {
        return currentState == LaserCrayonState.READYTOSHOOT;
    }

    public boolean isSHOT() {
        return currentState == LaserCrayonState.SHOT;
    }
}