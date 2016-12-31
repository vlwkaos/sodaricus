package com.lumibottle.gameobjects.enemies;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.lumibottle.gameobjects.Bullets.Bullet;
import com.lumibottle.gameobjects.GameEvent;
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
        acceleration = new Vector2(75,0);
        turn = false;
    }

    @Override
    public void update(float delta) {
        if (isVISIBLE()) {
            addTheta(delta*100.0f);

            if (turn) {
                if (isOutOfScreen(false, true, true, true))
                    dead();
            } else {
                if (isOutOfScreen(true, false, false, false))
                    turn = true;
            }
            getHitbox().setPosition(getX(), getY());
            getVelocity().add(acceleration.cpy().scl(delta));
            getPosition().add(getVelocity().cpy().scl(delta));
        }

    }

    public void reset(float x) {
        super.reset(x, MathUtils.random(GameScreen.gameHeight) - getHeight(), 0, 0, 0);
        turn = false;
        if (getY()-getHeight()/2.0f < GameScreen.gameHeight/2.0f){
            setVelocity(-speed,15);
        } else {
            setVelocity(-speed,-15);
        }
    }

    @Override
    public void dead() {
        super.dead();
    }

    @Override
    public void bottleHitsEnemy(Bullet b) {
        //do nothing. bad object orientation?( inverted)
    }
}
