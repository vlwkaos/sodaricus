package com.lumibottle.gameobjects.enemies.bosses;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.lumibottle.gameobjects.GameEvent;

/**
 *  Roam around in restricted region, shit bomb,
 *  bomb explodes after n seconds, it can hit both the player and this boss.
 */
public class BomberBoss extends GameEvent {

    private enum BomberState{
        IDLE,MOV,SHT
    }

    private BomberState currentState;
    private float runTime;


    public BomberBoss() {
        super(20, 20, new Polygon(new float[]{}), 3);
        runTime = 0;
        currentState = BomberState.IDLE;
    }

    @Override
    public void update(float delta) {
        if (isVISIBLE()) {
            if (getX() < 240 - getWidth()) {
                int dir4 = MathUtils.random(4);
                Gdx.app.log("Bomber","which dir?"+dir4);



            } else {
                setVelocity(-50, 0);
            }


            getPosition().add(getVelocity().cpy().scl(delta));
            getHitbox().setPosition(getX(), getY());
        }
    }
}
