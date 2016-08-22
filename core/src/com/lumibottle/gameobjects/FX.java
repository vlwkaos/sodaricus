package com.lumibottle.gameobjects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;
import com.lumibottle.helper.AssetHelper;

/**
 * Created by MG-POW on 2016-03-15.
 */
public class FX {
//in event, hit means hit to be reset()

    //in FX hit means hit to be set?
    public enum FXState {
        READY, TOBEDRAWN
    }

    public static final short SODA_EXPLOSION = 0;
    public static final short LASER_LINE = 1;
    public static final short LASER_SHOT = 2;
    public static final short BOMB_EXPLOSION = 3;
    public static final short QUANTUM_EXPLOSION = 4;


    private FXState currentState;

    private Vector2 position;

    private float size;
    private float runTime;

    private Animation myAnimation;

    private short animNo;

    public FX() {
        runTime = 0;
        currentState = FXState.READY;
        position = new Vector2(-255, -255);
        size = 0;
    }


    public void update(float delta) {
        if (isTOBEDRAWN()) {
            runTime += delta;
            if (myAnimation != null && myAnimation.isAnimationFinished(runTime)) {
                position.set(-255, -255);
                currentState = FXState.READY;
            }
        }
    }

	/*

	 */

    public Animation getAnimation() {
        return myAnimation;
    }

    public boolean isREADY() {
        return currentState == FXState.READY;
    }

    public boolean isTOBEDRAWN() {
        return currentState == FXState.TOBEDRAWN;
    }

    /*
        0 explosion for soda
        1 laser line
        2 laser animation
        3 explosion for tanklorry
        4 explosion for squirrel

     */
    public void reset(float x, float y, short animationNumber) {
        this.size = 0;
        runTime = 0;
        position.set(x, y);
        animNo = animationNumber;// needed for discrening drawing batch (e.g anim4 need rotation draw function)
        currentState = FXState.TOBEDRAWN;
        switch (animationNumber) {
            case 0:
                myAnimation = AssetHelper.explosionAnim1;
                break;
            case 1:
                myAnimation = AssetHelper.redlaserinit;
                break;
            case 2:
                myAnimation = AssetHelper.redlaserAnim;
                break;
            case 3:
                myAnimation = AssetHelper.bombexplosionAnim;
                break;
            case 4:
                myAnimation = AssetHelper.deadAnim;
                break;
            default:
                //5,6,7 same but render, different size
        }

    }

    public void reset(float x, float y, float size, short animationNumber) {
        this.size = size;
        runTime = 0;
        position.set(x, y);
        animNo = animationNumber;// needed for discrening drawing batch (e.g anim4 need rotation draw function)
        currentState = FXState.TOBEDRAWN;
        switch (animationNumber) {
            case 0:
                myAnimation = AssetHelper.explosionAnim1;
                break;
            case 1:
                myAnimation = AssetHelper.redlaserinit;
                break;
            case 2:
                myAnimation = AssetHelper.redlaserAnim;
                break;
            case 3:
                myAnimation = AssetHelper.bombexplosionAnim;
                break;
            case 4:
                myAnimation = AssetHelper.deadAnim;
                break;
            case 5:
                myAnimation = AssetHelper.cloudexplosionAnim;
            default:
                //5,6,7 same but render, different size
        }

    }


    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public float getRunTime() {
        return runTime;
    }

    public short getAnimNo() {
        return animNo;
    }

    public float getSize() {
        return size;
    }
}
