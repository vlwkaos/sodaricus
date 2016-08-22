package com.lumibottle.gameobjects.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.lumibottle.gameobjects.Bullets.Bullet;
import com.lumibottle.gameobjects.FX;
import com.lumibottle.gameobjects.GameEvent;
import com.lumibottle.gameobjects.Squirrel;
import com.lumibottle.helper.AssetHelper;
import com.lumibottle.helper.FXHelper;
import com.lumibottle.screen.GameScreen;

/**
 * So you thought RoadRoller was too easy?
 */
public class Bomb extends GameEvent {


    private ParticleEffect nitroParticle;

    private boolean isExploding;
    private float explodingCounter;

    private float bomb_x = 40;
    private float bomb_y = 40;

    private float[] normalhitbox;
    private float[] xplhitbox;


    public Bomb() {
        super(20, 12, new Polygon(new float[]{0, 0, 20, 0, 20, 12, 0, 12}), 3);

        normalhitbox = new float[]{0, 0, 20, 0, 20, 12, 0, 12};
        xplhitbox = new float[]{0 - (bomb_x - getWidth()) / 2f, 0 - (bomb_y - getHeight()) / 2f,
                getWidth() + (bomb_x - getWidth()) / 2, 0 - (bomb_y - getHeight()) / 2f,
                getWidth() + (bomb_x - getWidth()) / 2f, getHeight() + (bomb_y - getHeight()) / 2f,
                0 - (bomb_x - getWidth()) / 2f, getHeight() + (bomb_y - getHeight()) / 2f};

    }

    @Override
    public void update(float delta) {
        if (isVISIBLE()) {
            getHitbox().setPosition(getX(), getY());

            if (isExploding)
                explodingCounter += delta;
            else
                getPosition().add(getVelocity().cpy().scl(delta));

            if (isExploding && explodingCounter > 7 / 60f)
                super.dead();


            if (isOutOfScreen(true, false, true, true))
                super.dead();
        }

    }

    public ParticleEffect getParticle() {
        return nitroParticle;
    }

    public void reset(float x) {
        super.reset(x, MathUtils.random(GameScreen.gameHeight) - getHeight(), -40, 0, 0);
        setHitbox(normalhitbox); // reset hitbox
        isExploding = false;
        nitroParticle = AssetHelper.nitroPool.obtain();

    }


    @Override
    public void dead() {
        explode();
    }


    public void explode() {
        FXHelper.getInstance().newFX(getX() - (bomb_x - getWidth()) / 2f, getY() - (bomb_y - getHeight()) / 2f, FX.BOMB_EXPLOSION);
        isExploding = true;
        AssetHelper.nitroPool.free((ParticleEffectPool.PooledEffect) nitroParticle);
        //폭발 카운트를 시작한다.
        explodingCounter = 0;
        setHitbox(xplhitbox);
    }


    public void bottleHitsEnemy(Bullet b) {
        if (!isExploding)// 터지는 도중에는 맞으면 안되죠
            super.bottleHitsEnemy(b);
    }

    public boolean isExploding() {
        return isExploding;
    }


}
