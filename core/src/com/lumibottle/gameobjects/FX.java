package com.lumibottle.gameobjects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;
import com.lumibottle.helper.AssetHelper;

/**
 * Created by MG-POW on 2016-03-15.
 */
public class FX {
//in event, ready means ready to be reset()

	//in FX ready means ready to be set?
	public enum FXState {
		READY, TOBEDRAWN
	}
	//F

	private FXState currentState;

	private Vector2 position;

	private float runTime;

	private Animation myAnimation;

	private short animNo;

	public FX(){
		runTime=0;
		currentState = FXState.READY;
		position = new Vector2(-255,-255);
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

	public Animation getAnimation(){
		return myAnimation;
	}

	public boolean isREADY(){
		return currentState == FXState.READY;
	}
	public boolean isTOBEDRAWN(){
		return currentState == FXState.TOBEDRAWN;
	}

	/*
		0 explosion for soda
		1 laser line
		2 laser animation
		3 explosion for tanklorry
		4 explosion for squirrel
		5 snowman
	 */
	public void reset(float x, float y, short animationNumber){
		runTime=0;
		position.set(x,y);
		animNo=animationNumber;
		currentState = FXState.TOBEDRAWN;
		switch (animationNumber){
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
				myAnimation = AssetHelper.explosionAnim2;
				break;
			case 4:
				myAnimation = AssetHelper.deadAnim;
				break;
			default:
		}

	}


	public float getX(){
		return position.x;
	}
	public float getY(){
		return position.y;
	}

	public float getRunTime() {
		return runTime;
	}

	public short getAnimNo() {
		return animNo;
	}
}
