package com.lumibottle.gameobjects.enemies;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.lumibottle.gameobjects.Bullets.Bullet;
import com.lumibottle.gameobjects.FX;
import com.lumibottle.gameobjects.GameEvent;
import com.lumibottle.helper.FXHelper;
import com.lumibottle.screen.GameScreen;

/**
 * Created by MG on 2016-09-01.
 */
public class Boomerang extends GameEvent {


    private Vector2 acceleration;
    private boolean turn;

    final private float speed = 210;

    public Boomerang() {
        super(16, 16, new Polygon(new float[]{0,0,16,0,16,16,0,16}), 0);
        acceleration = new Vector2(65,0);
        turn = false;
    }

    @Override
    public void update(float delta) {
        if (isVISIBLE()) {
            addTheta(delta*100.0f);

            if (turn) {
                if (isOutOfScreen(false, true, true, true))
                    silentDead();
            } else {
                if (isOutOfScreen(true, false, false, false))
                    turn = true;
            }

            getVelocity().add(acceleration.cpy().scl(delta));
            getPosition().add(getVelocity().cpy().scl(delta));
            getHitbox().setPosition(getX(), getY());
        }

    }

    public void reset(float x) {
        float rand = MathUtils.random(GameScreen.gameHeight- getHeight());
        FXHelper.getInstance().newFX(240-16,rand+getHeight()/2-8, FX.WARN);
        super.reset(x, rand, 0, 0, 0);
        turn = false;
        if (getY()-getHeight()/2.0f < GameScreen.gameHeight/2.0f){
            setVelocity(-speed,15);
        } else {
            setVelocity(-speed,-15);
        }
    }

    @Override
    public void bottleHitsEnemy(Bullet b) {
        //do nothing. bad object orientation?( inverted)
    }
}
