package com.lumibottle.gameobjects.enemies;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.lumibottle.gameobjects.Bullets.Bullet;
import com.lumibottle.gameobjects.GameEvent;
import com.lumibottle.gameobjects.Squirrel;
import com.lumibottle.screen.GameScreen;


/**
 *  This enemy object will do no harm but make bullet by player go awry
 */
public class Blackhole extends GameEvent {

    final private float acc = 5f;

    private Polygon center;


    public Blackhole() {
        super(64, 64, null , 0);
        setHitbox(new float[]{getWidth()/ 3f, 0, getWidth() * 2 / 3f, 0,
                getWidth(), getHeight() / 3f, getWidth(), getHeight() * 2 / 3f,
                getWidth() * 2 / 3f, getHeight(), getWidth() / 3f, getHeight(),
                0, getHeight() * 2 / 3f, 0, getHeight() / 3f});

    }

    @Override
    public void update(float delta) {
        if (isVISIBLE()) {
            addTheta(delta * 200f);


            getPosition().add(getVelocity().cpy().scl(delta));
            getHitbox().setPosition(getX(), getY());
            if (isOutOfScreen(true, false, true, true))
                silentDead();
        }

    }

    public void reset(float x) {
        super.reset(x, (GameScreen.gameHeight / 20f) * MathUtils.random(5, 13), -15, 0, 0);
    }


    @Override
    public void bottleHitsEnemy(Bullet b) {
        float theta = MathUtils.atan2(getY()+getHeight()/2 - (b.getY()+b.getHeight()/2), getX()+getWidth()/2 - (b.getX()+b.getWidth()/2));
        float dx = acc * MathUtils.cos(theta);
        float dy = acc * MathUtils.sin(theta);
        b.getVelocity().add(acc * dx, acc * dy);
    }
    @Override
    public void enemyHitsSquirrel(Squirrel squirrel){

    }
}
