package com.lumibottle.gameobjects.enemies.bosses;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.lumibottle.gameobjects.Bullets.Bullet;
import com.lumibottle.gameobjects.Bullets.EnemyBullet;
import com.lumibottle.gameobjects.GameEvent;
import com.lumibottle.gameobjects.Squirrel;
import com.lumibottle.helper.AssetHelper;

/**
 * Created by MG on 2016-09-11.
 */
public class FirePropulsion extends GameEvent {


    private ParticleEffect firep;
    private float speed = 150.0f;
    private float timeout;

    public FirePropulsion() {
        super(20, 20, new Polygon(new float[] {0,0,20,0,20,20,0,20}), 0);
        timeout = 0.0f;
    }

    @Override
    public void update(float delta) {
        if (isVISIBLE()) {

            timeout += delta;
            if (timeout > 10.0f)
                dead();

            getPosition().add(getVelocity().cpy().scl(delta));
            getHitbox().setPosition(getX(), getY());
        }
    }

    public void reset(float x, float y, float dir) {
        super.reset(x, y, speed * MathUtils.cos(dir), speed * MathUtils.sin(dir),0);
        firep = AssetHelper.firePool.obtain();
        timeout = 0.0f;
    }

    @Override
    public void dead(){
        super.dead();
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
            if (bomberBoss.getInvtime()>3.0f && Intersector.overlapConvexPolygons(bomberBoss.getHitbox(), getHitbox())) {
                bomberBoss.hit();
            }
        }
    }

}
