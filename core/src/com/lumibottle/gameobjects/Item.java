package com.lumibottle.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.lumibottle.gameobjects.Bullets.Bullet;
import com.lumibottle.helper.AssetHelper;
import com.lumibottle.helper.FXHelper;
import com.lumibottle.helper.SoundManager;
import com.lumibottle.screen.GameScreen;

/**
 * Created by MG on 2017-01-04.
 */

public class Item extends GameEvent {

    private int type;

    public Item() {super(16, 18, new Polygon(new float[]{0, 0, 16, 0, 16, 18, 0, 18}), 0);
    }

    @Override
    public void update(float delta) {
        if (isVISIBLE()) {

            getPosition().add(getVelocity().cpy().scl(delta));
            getHitbox().setPosition(getX(), getY());
            if (isOutOfScreen(true, false, true, true))
                silentDead();
        }
    }

    @Override
    public void enemyHitsSquirrel(Squirrel squirrel) {
        switch (type){
            case 0:
                squirrel.incrementLife();
                break;
        }

        SoundManager.getInstance().play(SoundManager.ONEUP);
        silentDead();
    }

    @Override
    public void bottleHitsEnemy(Bullet b) {
    }

    public void reset(float x, int type) {
        super.reset(x, MathUtils.random(GameScreen.gameHeight- getHeight()) , -70, 0, 0);
        this.type = type;
    }

    public int getType(){return this.type;}
}
