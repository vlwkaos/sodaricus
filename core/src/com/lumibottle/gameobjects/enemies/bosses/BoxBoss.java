package com.lumibottle.gameobjects.enemies.bosses;

import com.badlogic.gdx.math.Polygon;
import com.lumibottle.gameobjects.Bullets.Bullet;
import com.lumibottle.gameobjects.GameEvent;
import com.lumibottle.gameobjects.Squirrel;
import com.lumibottle.helper.FXHelper;
import com.lumibottle.screen.GameScreen;

/**
 * Created by MG-POW on 2016-07-10.
 */


public class BoxBoss extends GameEvent {

    private enum BoxState {
        IDLE, PREPARE, SHOOT, VULNERABLE
    }

    private BoxState currentState;

    private float runTime;
    private float hitAnimRunTime;
    private float offsetHeight;

    private boolean gotHit;

    private Squirrel mySquirrel;


    public BoxBoss(Squirrel s) {
        super((int) (GameScreen.gameHeight / 5), (int) (GameScreen.gameHeight / 5), new Polygon(new float[]{0, 0, (GameScreen.gameHeight / 5), 0, (GameScreen.gameHeight / 5), (GameScreen.gameHeight / 5), 0, (GameScreen.gameHeight / 5)}), 50);
        //dynamic size according to scren height
        runTime = 0;
        hitAnimRunTime = 0;
        mySquirrel = s;
        gotHit = false;
        currentState = BoxState.IDLE;

        offsetHeight = getHeight() - s.getHeight();
        offsetHeight /= 2.0f;

    }


    /*
        Follows squirrel, after n seconds, it turns red and shoots a block

     */
    @Override
    public void update(float delta) {
        if (isVISIBLE()) {
            if (getX() < 240 - getWidth()) {// fully appeared
                //count
                runTime += delta;
                if (currentState == BoxState.IDLE) {
                    if ((getY() + offsetHeight) - mySquirrel.getY() > 1) { // box is upper
                        setVelocity(0, -50);
                    } else if ((getY() + offsetHeight) - mySquirrel.getY() < -1) { // box is under
                        setVelocity(0, 50);
                    } else
                        setVelocity(0, 0);

                    //stay inside screen
                    if (getY() < 0)
                        setY(0);

                    if (getY() + getHeight() > GameScreen.gameHeight)
                        setY(GameScreen.gameHeight - getHeight());

                    if (runTime > 4.0f) {
                        currentState = BoxState.PREPARE;
                        runTime = 0;
                    }
                }
                //adjust/go to block position
                if (currentState == BoxState.PREPARE) {
                    if (closestBlock() * getHeight() - getY() > 1) {
                        setVelocity(0, (30 - runTime * 10));
                    } else if (closestBlock() * getHeight() - getY() < -1) {
                        setVelocity(0, -(30 - runTime * 10));
                    } else {
                        setVelocity(0, 0);
                        setY(closestBlock() * getHeight());
                        currentState = BoxState.SHOOT;
                        runTime = 0;
                    }
                }

                if (currentState == BoxState.VULNERABLE) {
                    setVelocity(0, 0);
                    if (runTime > 5.0f) {
                        runTime = 0;
                        currentState = BoxState.IDLE;
                    }
                }

                if (gotHit) {

                    if (hitAnimRunTime > 1 / 10f) {
                        gotHit = false;
                        hitAnimRunTime = 0;
                    } else
                        hitAnimRunTime += delta;
                }


            } else { // yet to be on screen
                setVelocity(-50, 0);
            }


            getPosition().add(getVelocity().cpy().scl(delta));
            getHitbox().setPosition(getX(), getY());

        }
    }

    public void reset() {
        super.reset(240, GameScreen.gameHeight / 2.0f - getHeight() / 2.0f, 0, 0, 0);
        currentState = BoxState.IDLE;
        runTime = 0;
        gotHit = false;
        hitAnimRunTime = 0;
    }

    @Override
    public void dead() {
        super.dead();
        FXHelper.getInstance().newFX(getPrevX(), getPrevY(), Math.max(getWidth(), getHeight()), (short) 5);
    }

    private int closestBlock() {
        int closest = 5;
        float tempMin = getHeight();
        for (int i = 0; i < 5; i++) {
            if (Math.abs(getY() - i * getHeight()) < tempMin) {
                closest = i;
                tempMin = Math.abs(getY() - i * getHeight());

            }
        }

        return closest;
    }


    @Override
    public void bottleHitsEnemy(Bullet b) {
        if (isVULNERABLE()) {
            super.bottleHitsEnemy(b);
            if (!gotHit) {
                gotHit = true;
                hitAnimRunTime = 0;
            }
        }
    }

    /*
            public
     */
    public boolean isSHOOT() {
        return currentState == BoxState.SHOOT;
    }

    public boolean isIDLE() {
        return currentState == BoxState.IDLE;
    }

    public boolean isPREPARE() {
        return currentState == BoxState.PREPARE;
    }

    public boolean isVULNERABLE() {
        return currentState == BoxState.VULNERABLE;
    }

    public void doneShooting() {
        currentState = BoxState.IDLE;
    }

    public float getRunTime() {
        return runTime;
    }

    public boolean gotHit() {
        return gotHit;
    }

    public void setVul() {
        currentState = BoxState.VULNERABLE;
        runTime = 0;
    }
    /*
        don't call collide function for this one
     */
}
