package com.lumibottle.gameobjects;

import com.badlogic.gdx.math.GeometryUtils;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by MG-POW on 2016-03-12.
 */
public class Bullet {

	private enum BulletState{
		VISIBLE, READY
	}

	private BulletState currentState;

	private Vector2 position;
	private Vector2 velocity;

	private float theta;
	private int width, height;//fixed?

	private boolean fired;// check when to draw


	//load in gameworld first, the move

	public Bullet() {
		position = new Vector2(-50, 0);
		velocity = new Vector2(0, 0);
		theta = 0;
		width = 16;
		height = 16;
		currentState = BulletState.READY;
	}

	public void update(float delta) {
		if (isVISIBLE())
			position.add(velocity.cpy().scl(delta));



		if ((position.x + width) > 240 || position.y>256) {// out of screen only need to be known in this class
			position.set(-50,0);
			currentState = BulletState.READY;
		}
	}



	public void shot(float x, float y, float speed, float theta) {
		position.set(x,y);
		this.theta=theta;
		velocity.set(speed * MathUtils.cos(MathUtils.degreesToRadians*theta), speed * MathUtils.degreesToRadians*theta);
		currentState=BulletState.VISIBLE;
	}



	/*

	 */
	public boolean isREADY(){
		return currentState==BulletState.READY;
	}
	public boolean isVISIBLE(){
		return currentState==BulletState.VISIBLE;
	}


	//it will use coordinate from main actor, so ..
	public float getX() {
		return position.x;
	}

	public float getY() {
		return position.y;
	}

	public boolean isFired() {
		return fired;
	}

	public float getRotation() {
		return theta;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}
