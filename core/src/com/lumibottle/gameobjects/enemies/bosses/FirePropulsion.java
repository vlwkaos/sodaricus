package com.lumibottle.gameobjects.enemies.bosses;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.lumibottle.gameobjects.Bullets.Bullet;
import com.lumibottle.gameobjects.GameEvent;
import com.lumibottle.gameobjects.Squirrel;
import com.lumibottle.helper.AssetHelper;

/**
 * Created by MG on 2016-09-11.
 */
public class FirePropulsion extends GameEvent {


    private ParticleEffect firep;
    private float speed = 180.0f;
    private float timeout;

    //TODO adjust hitbox
    public FirePropulsion() {
        super(20, 20, new Polygon(new float[] {2,2,18,2,18,18,2,18}), 0);
        getHitbox().setOrigin(10,10);
        timeout = 0.0f;
    }

    @Override
    public void update(float delta) {
        if (isVISIBLE()) {

            timeout += delta;
            if (timeout > 10.0f)
                silentDead();

            getPosition().add(getVelocity().cpy().scl(delta));
            getHitbox().setPosition(getX(), getY());
        }
    }

    public void reset(float x, float y, float dir) {
        super.reset(x, y, speed * MathUtils.cosDeg(dir), speed * MathUtils.sinDeg(dir),0);
        getHitbox().setRotation(dir);
        firep = AssetHelper.firePool.obtain();
        timeout = 0.0f;
    }

    @Override
    public void silentDead(){
        super.silentDead();
        AssetHelper.firePool.free((ParticleEffectPool.PooledEffect) firep);
    }

    @Override
    public void bottleHitsEnemy(Bullet b) {
        //do nothing. bad object orientation?( inverted)
    }

    public ParticleEffect getParticle() {
        return firep;
    }

    public void collide(Squirrel mySquirrel, BomberBoss bomberBoss) {
        super.collide(mySquirrel);

        //fire hits bomberboss
        if (isVISIBLE()) {
            if (bomberBoss.getInvtime()>2.0f && Intersector.overlapConvexPolygons(bomberBoss.getHitbox(), getHitbox())) {
                bomberBoss.hit();
            }
        }
    }

}
