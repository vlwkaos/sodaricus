package com.lumibottle.gameobjects.Bullets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.lumibottle.screen.GameScreen;

/**
 * Created by MG-POW on 2016-07-16.
 */

public class BlockEnemyBullet extends EnemyBullet {

    private int[] myBlockspace;
    private int minPosX;

    private Vector2 acceleration;

    public BlockEnemyBullet(int[] blockspace) {
        super((int) GameScreen.gameHeight / 5, (int) GameScreen.gameHeight / 5, new Polygon(new float[]{0, 0, GameScreen.gameHeight / 5, 0, GameScreen.gameHeight / 5, GameScreen.gameHeight / 5, 0, GameScreen.gameHeight / 5}));
        myBlockspace = blockspace;
        minPosX = 0;
        acceleration = new Vector2(-50, 0);
    }


    @Override
    public void specificUpdate(float delta) {
        getVelocity().add(acceleration.cpy().scl(delta));//add acc to velocity

        if (getX() < minPosX) {
            myBlockspace[(int) getY() / getHeight()] += getWidth();
            setVelocity(0, 0);
            acceleration.set(0, 0);
            setX(minPosX);
            Gdx.app.log("BB ", "block " + (int) getY() / getHeight() + ": " + myBlockspace[(int) getY() / getHeight()]);
        }

    }

    @Override
    public void reset(float x, float y, float speed, float theta) {
        super.reset(x, y, -speed * MathUtils.cos(theta), -speed * MathUtils.sin(theta), theta);
        minPosX = myBlockspace[(int) getY() / getHeight()];

    }


}
