package com.lumibottle.gameobjects;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.lumibottle.helper.AssetLoader;

/**
 * Created by MG-UP on 2016-03-24.
 */
public class LaserCrayon extends GameEvent {

    private float runTime;
    public LaserCrayon() {
        super(20, 12, new Polygon(new float[]{0, 0, 20, 0, 20, 12, 0, 12}));

    }

    @Override
    public void update(float delta) {
        runTime+=0;
        getHitbox().setPosition(getX(), getY());

        if (isVISIBLE()) {
            getPosition().add(getVelocity().cpy().scl(delta));

            if (getX()>240-getWidth()){
                setVelocity(runTime,0);
            }


            if (isOutOfScreen(true))
                ready();
        }

    }

//    public ParticleEffect getParticle() {
//        return nitroParticle;
//    }

    public void reset(float x) {
        super.reset(x, MathUtils.random(GameEvent.gameHeight), -50, 0, 0);

    }


    @Override
    public void ready(){
        super.ready();
      //  AssetLoader.nitroPool.free((ParticleEffectPool.PooledEffect) nitroParticle);

    }
}
