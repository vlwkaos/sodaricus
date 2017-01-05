package com.lumibottle.gameobjects.enemies.bosses;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.lumibottle.gameobjects.GameEvent;
import com.lumibottle.helper.AssetHelper;
import com.lumibottle.helper.FXHelper;
import com.lumibottle.helper.SoundManager;
import com.lumibottle.screen.GameScreen;

/**
 *  Roam around in restricted region, shit bomb,
 *  bomb explodes after n seconds, it can hit both the player and this boss.
 */
public class BomberBoss extends GameEvent {

    private enum BomberState{
        IDLE,MOV
    }


    private Vector2 curVel;

    private ParticleEffect smokeParticle;
    private BomberState currentState;
    private float runTime;
    private float invTime;
    private float shootDelay;
    private boolean prepare;
    private boolean shoot;
    private float cirmov;

    final private float speed = 70.0f;

    public BomberBoss() {
        super(32, 32, new Polygon(new float[]{6,0,26,0,26,32,6,32}), 10);
        curVel = new Vector2(0,0);
        runTime = 0.0f;
        invTime = 5.0f;
        cirmov = 0.0f;
        shootDelay = 0.0f;
        shoot = false;
        prepare = false;
        currentState = BomberState.IDLE;

    }

    /*
        dont let it go out
     */
    @Override
    public void update(float delta) {
        if (isVISIBLE()) {
            if (getX() < 240 - getWidth()) {
                if (invTime < 5.0f){
                    invTime += delta;
                }


                if (currentState == BomberState.IDLE){
                    if (runTime > 0.2f)
                        currentState = BomberState.MOV;
                    else
                        runTime += delta;
                }

                if (currentState == BomberState.MOV){
                    float direction = MathUtils.random(360);
                    runTime = 0.0f;
                    setVelocity(speed * MathUtils.cosDeg(direction), speed * MathUtils.sinDeg(direction));
                    curVel = getVelocity();
                    currentState = BomberState.IDLE;
                }

                //prepare
                shootDelay += delta;
                if (!shoot)
                if (!prepare){
                    if (shootDelay >= 4/60f){
                        shootDelay = 0.0f;
                        prepare = true;

                    }
                } else {
                    if (shootDelay >= 2.2f) {
                        shootDelay = 0.0f;
                        shoot = true;
                    }
                }


                // for circular movement
                if (cirmov < 360.0f)
                    cirmov += delta * 360.0f;
                else
                    cirmov = 0;

                float radius = 5.0f;
                // circular movment relative to angle
                setVelocity(curVel.x-radius*MathUtils.sinDeg(cirmov),curVel.y+radius*MathUtils.cosDeg(cirmov));


                //bound constraint
                if (getY()>GameScreen.gameHeight-getHeight()) {
                    setY(GameScreen.gameHeight - getHeight());
                    setVelocity(getVelocity().x-radius*MathUtils.sinDeg(cirmov),-speed+radius*MathUtils.cosDeg(cirmov));
                }

                if (getY()<0) {
                    setY(0);
                    setVelocity(getVelocity().x-radius*MathUtils.sinDeg(cirmov),speed+radius*MathUtils.cosDeg(cirmov));
                }

                if (getX()>240-getWidth()) {
                    setX(240 - getWidth());
                    setVelocity(-speed-radius*MathUtils.sinDeg(cirmov),getVelocity().y+radius*MathUtils.cosDeg(cirmov));
                }

                if (getX()<80.0f) {
                    setX(80.0f);
                    setVelocity(speed-radius*MathUtils.sinDeg(cirmov),getVelocity().y+radius*MathUtils.cosDeg(cirmov));
                }

            } else {
                setVelocity(-50, 0);
                runTime = 0.0f;
            }

            getPosition().add(getVelocity().cpy().scl(delta));
            getHitbox().setPosition(getX(), getY());
        }
    }

    public void reset() {
        super.reset(240, GameScreen.gameHeight / 2.0f - getHeight() / 2.0f, 0, 0, 0);
        invTime = 5.0f;
        runTime = 0.0f;
        cirmov = 0.0f;
        shootDelay = 0.0f;
        shoot = false;
        prepare = false;
        currentState = BomberState.IDLE;
        smokeParticle = AssetHelper.smokePool.obtain();
    }

    @Override
    public void hit(){
        super.hit();
        SoundManager.getInstance().play(SoundManager.HURT);
        invTime = 0.0f;
    }

    @Override
    public void dead() {
        super.dead();
        AssetHelper.smokePool.free((ParticleEffectPool.PooledEffect) smokeParticle);
    }

    public void setShootDone(){
        prepare = false;
        shoot = false;
        shootDelay = 0.0f;
    }

    public float getInvtime(){
        return invTime;
    }
    public ParticleEffect getParticle() {
        return smokeParticle;
    }
    public boolean getPrepared(){return prepare;}
    public boolean getShoot(){return shoot;}
    public float getShootDelay(){return shootDelay;}
}
