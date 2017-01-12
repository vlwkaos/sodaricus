package com.lumibottle.gameobjects.Bullets;


import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.lumibottle.gameobjects.FX;
import com.lumibottle.gameobjects.GameEvent;
import com.lumibottle.gameobjects.Squirrel;
import com.lumibottle.helper.AssetHelper;
import com.lumibottle.helper.FXHelper;
import com.lumibottle.screen.GameScreen;


/**
 * Created by MG-POW on 2016-03-12.
 */
public class Bullet extends GameEvent {

    private ParticleEffect particle;
    private float theta;
    //load in gameworld first, the move
    private Vector2 acceleration;

    private float runTime; // kill timer

    private boolean thunderMode;
    private boolean fireThunder;

    Squirrel mySquirrel;


    public Bullet(Squirrel s) {
        super(12, 12, new Polygon(new float[]{2, 6, 2, 12, 6, 12, 6, 6}), 1);
        getHitbox().setOrigin(getWidth() / 2f, getHeight() / 2f);
        acceleration = new Vector2(0, -460);
        mySquirrel = s;
    }

    public void update(float delta) {
        if (isVISIBLE()) {
            runTime+= delta;


            if (!thunderMode)
            getVelocity().add(acceleration.cpy().scl(delta));//add acc to velocity
            else{
                if (!fireThunder) {
                    float targetAngle = MathUtils.atan2(mySquirrel.getY()-getY(),getX()-mySquirrel.getX());
                    setVelocity(-250 * MathUtils.cos(targetAngle), 250 * MathUtils.sin(targetAngle));
                    if (mySquirrel.getY()+mySquirrel.getHeight()/2 > getY()){
                        setVelocity(250,MathUtils.random(-30,30));
                        fireThunder = true;
                    }
                }
            }


            getPosition().add(getVelocity().cpy().scl(delta));
            getHitbox().setPosition(getX(), getY());
            getHitbox().setRotation(getTheta());
            this.theta -= delta * 1000;//1-000

            if (isOutOfScreen(true, true, true, true) || runTime > 2.0f) {
                silentDead();
            }
        }
    }

    public void reset(float x, float y, float dx, float dy, float theta, boolean thunderMode) {
//		if (theta > 0)
//			this.theta=5*MathUtils.log2(theta);
//		else if (theta <0)
//			this.theta=(-5)*MathUtils.log2((-1)*theta);
//		else
        this.thunderMode = thunderMode;
        this.fireThunder = false;
        this.theta = theta;
        this.runTime = 0.0f;
        if (!this.thunderMode) {
            setMaxhp(1);
            super.reset(x, y, dx * MathUtils.cos(MathUtils.degreesToRadians * this.theta), dy * MathUtils.sin(MathUtils.degreesToRadians * this.theta), this.theta);
        } else {
            setMaxhp(2);
            particle = AssetHelper.thunderPool.obtain();
            super.reset(x, y, dx, dy, this.theta);
        }
    }

    @Override
    public void hit(){
        if (!thunderMode)
        FXHelper.getInstance().newFX(getX(), getY(), FX.SODA_EXPLOSION);
       super.hit();
    }

    @Override
    public float getTheta() {
        return theta;
    }

    public boolean getThunderMode(){return thunderMode;}

    @Override
    public void dead(){
        silentDead();
    }

    @Override
    public void silentDead(){
        super.silentDead();
        if (this.thunderMode) {
            AssetHelper.thunderPool.free((ParticleEffectPool.PooledEffect) particle);
        }

    }

    public ParticleEffect getParticle() {
        return particle;
    }


}
