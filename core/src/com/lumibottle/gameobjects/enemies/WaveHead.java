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
 * Created by MG on 2016-09-01.
 */
public class WaveHead extends GameEvent {



    private ParticleEffect waveheadParticle;

    final private float speed = 40;
    private float runTime;

    public WaveHead() {
        super(20, 20, new Polygon(new float[]{0,0,20,0,20,20,0,20}), 3);
    }

    @Override
    public void update(float delta) {
        if (isVISIBLE()) {
            runTime +=delta;
            setVelocity(getVelocity().x, ((GameScreen.gameHeight - getHeight()) / 2.0f) * (MathUtils.cos(runTime)));
            getHitbox().setPosition(getX(), getY());
            getPosition().add(getVelocity().cpy().scl(delta));

            if (isOutOfScreen(true, false, false, false))
                dead();

        }

    }

    public void reset(float x) {
        super.reset(x, MathUtils.random(GameScreen.gameHeight) - getHeight(), -speed, 0, 0);
        waveheadParticle = AssetHelper.waveheadPool.obtain();
        runTime =0.0f;
    }

    @Override
    public void dead() {
        super.dead();
        AssetHelper.waveheadPool.free((ParticleEffectPool.PooledEffect) waveheadParticle);
        FXHelper.getInstance().newFX(getPrevX(), getPrevY(), Math.max(getWidth(), getHeight()), (short) 5);// puff
    }


    public ParticleEffect getParticle() {
        return waveheadParticle;
    }

}
