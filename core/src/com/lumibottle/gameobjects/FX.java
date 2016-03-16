package com.lumibottle.gameobjects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by MG-POW on 2016-03-15.
 */
public class FX {
//in event, ready means ready to be reset()

	//in FX ready means ready to be set?
	public enum FXState {
		READY, TOBEDRAWN
	}

	private Vector2 position;
	private String animationName;

	private float runTime;
	private float duration;




	/*


	 */
	public Animation getAnimation(){
		return null;
	}


	public float getX(){
		return position.x;
	}

	public float getY(){
		return position.y;
	}


}
