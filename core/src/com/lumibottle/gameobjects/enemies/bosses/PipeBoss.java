package com.lumibottle.gameobjects.enemies.bosses;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.lumibottle.gameobjects.Bullets.Bullet;
import com.lumibottle.gameobjects.GameEvent;

import com.lumibottle.helper.FXHelper;
import com.lumibottle.screen.GameScreen;

/**
 * Created by MG-POW on 2016-07-10.
 */

public class PipeBoss extends GameEvent {

    private float runTime;
    private float shootRunTime;
    private int shootCount;

    private enum PipeState {
        IDLE, PREPARE, SHOOT
    }

    private PipeState currentState;
    private boolean gotHit;
    private float hitAnimRunTime;

    public PipeBoss() {
        super(32, 32, new Polygon(new float[]{4, 9, 4, 25, 25, 25, 25, 9}), 50); // render height is different
        gotHit = false;
        hitAnimRunTime = 0;
        shootCount = 0;
        shootRunTime = 0;
        currentState = PipeState.IDLE;
    }

    @Override
    public void update(float delta) {
        if (isVISIBLE()) {
            if (getX() < 240 - getWidth()) {// fully appeared
                runTime += delta;

                setVelocity(0, ((GameScreen.gameHeight - getHeight()) / 2.0f) * (MathUtils.cos(runTime)));
                if (currentState == PipeState.IDLE) {
                    if (shootRunTime > 5.0f) {
                        shootRunTime = 0;
                        currentState = PipeState.PREPARE;
                    } else
                        shootRunTime += delta;
                }

                if (currentState == PipeState.PREPARE) {
                    if (shootRunTime > 3.0f) {
                        if (shootCount == 3) {
                            shootRunTime = 0;
                            shootCount = 0;
                            currentState = PipeState.IDLE;
                        } else {
                            shootCount++;
                            shootRunTime = 0;
                            currentState = PipeState.SHOOT;
                        }
                    } else
                        shootRunTime += delta;
                }


            } else { // yet to be on screen
                setVelocity(-50, 0);
            }


            if (gotHit) {
                if (hitAnimRunTime > 5 / 60f) {
                    gotHit = false;
                    hitAnimRunTime = 0;
                } else
                    hitAnimRunTime += delta;
            }

            getPosition().add(getVelocity().cpy().scl(delta));
            getHitbox().setPosition(getX(), getY());
        }
    }

    public void reset() {
        super.reset(240, GameScreen.gameHeight / 2.0f - getHeight() / 2, 0, 0, 0);

    }

    @Override
    public void dead() {
        super.dead();
        FXHelper.getInstance().newFX(getPrevX(), getPrevY(), Math.max(getWidth(), getHeight()), (short) 5);
    }

    public void bottleHitsEnemy(Bullet b) {
        super.bottleHitsEnemy(b);
        //force shield 지지직하는 sprite만들 FX
        if (!gotHit) {
            gotHit = true;
            hitAnimRunTime = 0;
        }
    }


    //TODO maybe integrate gotHit() in event as default
    public boolean gotHit() {
        return gotHit;
    }

    public float getHitAnimRunTime() {
        return hitAnimRunTime;
    }

    public float getShootRunTime() {
        return shootRunTime;
    }

    public boolean isSHOOT() {
        return currentState == PipeState.SHOOT;
    }

    public boolean isIDLE() {
        return currentState == PipeState.IDLE;
    }

    public void doneShooting() {
        currentState = PipeState.PREPARE;
    }
}
