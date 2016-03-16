package com.lumibottle.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;
import com.lumibottle.helper.AssetLoader;

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


	public FX(){
		runTime=0;
		currentState = FXState.READY;
		position = new Vector2(-255,-255);
	}


	public void update(float delta){
		runTime+=delta;

		if (myAnimation!=null && myAnimation.isAnimationFinished(runTime) /*runTime>duration && isTOBEDRAWN()*/) {
			Gdx.app.log("fx done","go away");
			currentState = FXState.READY;
			position.set(-255,-255);
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

	public void reset(float x, float y, short animationNumber){
		runTime=0;
		currentState = FXState.TOBEDRAWN;

		position.set(x,y);

		switch (animationNumber){
			case 0:
				myAnimation = AssetLoader.explosionAnim1;
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
}
