package com.lumibottle.gameobjects.Bullets;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.lumibottle.gameobjects.GameEvent;

/**
 *  Bullets are objects fired, or reset by other Objects
 */
public class EnemyBullet extends GameEvent {


    private float runTime; // 애니메이션이 각자 돌기 위해 필요하다.

    public EnemyBullet(int width, int height, Polygon hitbox) {
        super(width, height, hitbox, 0);
        runTime = 0;
    }

    @Override
    public void update(float delta) {
        if (isVISIBLE()) {
            runTime += delta;
            getHitbox().setPosition(getX(), getY());
            getHitbox().setRotation(getTheta());
            getPosition().add(getVelocity().cpy().scl(delta));
            specificUpdate(delta);
            if (isOutOfScreen(true, false, false, false))
                silentDead();
        }
    }
/*
    when reset, calculate theta from squirrel and mob
    theta for local rotation of the texture
 */

    public void reset(float x, float y, float speed, float theta) {
        super.reset(x, y, -speed * MathUtils.cos(theta), -speed * MathUtils.sin(theta), theta);
        runTime = 0;
    }

    @Override
    public void bottleHitsEnemy(Bullet b) {
        //do nothing. bad object orientation?( inverted)
    }


    public float getRunTime() {
        return runTime;
    }

    public void specificUpdate(float delta) {

    }



}
