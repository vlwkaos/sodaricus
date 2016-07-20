package com.lumibottle.gameobjects.enemies.bosses;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.math.Polygon;
import com.lumibottle.gameobjects.GameEvent;

/**
 * Created by MG-POW on 2016-07-10.
 */

public class PangBoss extends GameEvent {

    public PangBoss(int width, int height, Polygon hitbox, int hp) {
        super(width, height, hitbox, hp);
    }

    @Override
    public void update(float delta) {
        if (isVISIBLE()) {
            if (getX() < 240 - getWidth()) {// fully appeared
            } else { // yet to be on screen
                setVelocity(-50,0);
            }
        }
    }
}
