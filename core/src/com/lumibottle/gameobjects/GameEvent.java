package com.lumibottle.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

/**
 * 		OOP
 * 		Basically when an object is out of the screen, it is moved else where untouchable and stops moving
 * 		until reset() is called which will redeploy our objects on the screen
 *
 */
public abstract class GameEvent {

	private enum EventState{
		READY, VISIBLE
	}

	public static float gameHeight = Gdx.graphics.getHeight() / (Gdx.graphics.getWidth() / 240);
	//runtime for enemy
	private Vector2 position;
	private Vector2 velocity;
	private EventState currentState;

	private int width, height;
	private float theta;


	public GameEvent(int width, int height){
		position = new Vector2(-255,-255);
		velocity = new Vector2(0,0);
		this.width = width;
		this.height = height;
		this.theta = 0;
		currentState = EventState.READY;
	}


	/*
	only update position when its visible.
	 */
	public abstract void update(float delta);

	public void reset(float x, float y, float dx, float dy, float theta){
		position.set(x,y);
		velocity.set(dx,dy);
		this.theta=theta;
		currentState = EventState.VISIBLE;
	}// re deploy, set to visible

	public boolean isOutOfScreen(){
		//was visible but is out of screen, so move it else where ready to be re deployed
		return (isVISIBLE() && (position.x+width<0 || position.y>gameHeight || (position.y+height)<0 ));
	}

	public void ready(){
		position.set(-255, -255);
		currentState = EventState.READY;
	}


	public abstract void kill();

	/*
		GETTER & SETTER
	 */
	public boolean isREADY(){
		return currentState==EventState.READY;
	}
	public boolean isVISIBLE(){
		return currentState==EventState.VISIBLE;
	}


	//it will use coordinate from main actor, so ..
	public float getX() {
		return position.x;
	}

	public float getY() {
		return position.y;
	}

	public float getTheta() {
		return theta;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Vector2 getPosition() {
		return position;
	}

	public Vector2 getVelocity() {
		return velocity;
	}

	public void setVelocity(float dx, float dy){
		velocity.set(dx,dy);
	}

}
