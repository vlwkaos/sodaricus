package com.lumibottle.gameobjects.enemies.bosses;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.lumibottle.gameobjects.GameEvent;
import com.lumibottle.helper.AssetHelper;
import com.lumibottle.helper.FXHelper;
import com.lumibottle.screen.GameScreen;

/**
 *  Roam around in restricted region, shit bomb,
 *  bomb explodes after n seconds, it can hit both the player and this boss.
 */
public class BomberBoss extends GameEvent {

    private enum BomberState{
        IDLE,MOV
    }

    private ParticleEffect smokeParticle;
    private BomberState currentState;
    private float runTime;
    private float shootDelay;
    private boolean shoot;
    private float cirmov;

    final private float speed = 100.0f;

    public BomberBoss() {
        super(20, 20, new Polygon(new float[]{}), 3);
        runTime = 0.0f;
        cirmov = 0.0f;
        shootDelay = 0.0f;
        shoot = false;
        currentState = BomberState.IDLE;

    }

    @Override
    public void update(float delta) {
        if (isVISIBLE()) {
            if (getX() < 240 - getWidth()) {
                if (currentState == BomberState.IDLE){
                    if (runTime > 3.0f)
                        currentState = BomberState.MOV;
                    else
                        runTime += delta;
                }

                if (currentState == BomberState.MOV){
                    float direction = MathUtils.random(360);
                    runTime = 0.0f;
                    setVelocity(speed * MathUtils.cosDeg(direction), speed * MathUtils.sinDeg(direction));
                    currentState = BomberState.IDLE;
                }

                if (!shoot) // reset bullet then set it to true in ProgressHandler
                    if (shootDelay < 5.0f){
                        shootDelay += delta;
                    } else {
                        shootDelay = 0.0f;
                        shoot = true;
                    }
                // for circular movement
                if (cirmov < 360.0f)
                    cirmov += delta * 10.0f;
                else
                    cirmov = 0;

                // circular movment relative to angle

            } else {
                setVelocity(-50, 0);
            }

            getPosition().add(getVelocity().cpy().scl(delta));
            getHitbox().setPosition(getX(), getY());
        }
    }

    public void reset() {
        super.reset(240, GameScreen.gameHeight / 2.0f - getHeight() / 2.0f, 0, 0, 0);
        runTime = 0.0f;
        cirmov = 0.0f;
        shootDelay = 0.0f;
        shoot = false;
        currentState = BomberState.IDLE;
        smokeParticle = AssetHelper.smokePool.obtain();
    }

    @Override
    public void dead() {
        super.dead();
        AssetHelper.smokePool.free((ParticleEffectPool.PooledEffect) smokeParticle);
        FXHelper.getInstance().newFX(getPrevX(), getPrevY(), Math.max(getWidth(), getHeight()), (short) 5);
    }

    public void setShootDone(){
        shoot = false;
    }
    public ParticleEffect getParticle() {
        return smokeParticle;
    }
}
