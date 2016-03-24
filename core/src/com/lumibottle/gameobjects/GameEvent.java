package com.lumibottle.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.lumibottle.helper.FXHelper;

/**
 * 		OOP
 * 		Basically when an object is out of the screen, it is moved else where untouchable and stops moving
 * 		until reset() is called which will redeploy our objects on the screen
 *
 */
public abstract class GameEvent {


	private enum EventState{
		READY, VISIBLE
	}//READY state notifies that it went out of screen and is now ready to be reset




	public static float gameHeight = Gdx.graphics.getHeight() / (Gdx.graphics.getWidth() / 240);
	//runtime for enemy
	private Vector2 position;
	private Vector2 velocity;
	private EventState currentState;

	private int width, height;
	private float theta;

	private Polygon hitbox;

	public GameEvent(int width, int height, Polygon hitbox){
		position = new Vector2(-255,-255);
		velocity = new Vector2(0,0);
		this.width = width;
		this.height = height;
		this.theta = 0;
		currentState = EventState.READY;
		this.hitbox = hitbox;
	}


	/*
	BASIC PROTOTYPE : only update position when it's on visible state.
					  set to ready state when OOS
	 */
	public abstract void update(float delta);


	//If using particle, You should obtain particle here by overriding this method.
	public void reset(float x, float y, float dx, float dy, float theta){
		position.set(x,y);
		velocity.set(dx,dy);
		this.theta=theta;
		currentState = EventState.VISIBLE;
	}// re deploy, set to visible

	public boolean isOutOfScreen(boolean toLeft){
		//was visible but is out of screen, so move it else where ready to be re deployed
		if (toLeft)
			return (position.x+width*2<0 || position.y>gameHeight + height || position.y < 0-height );
		else
			return (  position.y>gameHeight + height || position.y < 0-height || position.x>240 );
	}

	// if using particle, free particle here
	public void ready(){
		position.set(-255, -255);
		currentState = EventState.READY;
	}


	/*
	collision
	 */
	public void collide(Squirrel squirrel) {
		if (isVISIBLE()) {
			for (Bullet b : squirrel.getBullets()) {
				if (b.getX() + b.getWidth() > getX())
					if (Intersector.overlapConvexPolygons(b.getHitbox(), hitbox) && b.isVISIBLE()) {
						FXHelper.getInstance().newFX(b.getX(), b.getY(), (short) 0);
						ready();
						b.ready();
						break;
					}
			}

			if (squirrel.getX() + squirrel.getWidth() > getX()) {
				if (Intersector.overlapConvexPolygons(squirrel.getHitbox(), hitbox)) {

				}
			}
		}
	}



	/*
		GETTER & SETTER
	 */
	public boolean isREADY(){
		return currentState==EventState.READY;
	}
	public boolean isVISIBLE(){
		return currentState==EventState.VISIBLE;
	}

	public void setHitbox(Polygon hitbox) {
		this.hitbox = hitbox;
	}
	//it will use coordinate from main actor, so ..
	public void setVelocity(float x, float y) {
		this.velocity.set(x,y);
	}

	public void setTheta(float theta) {
		this.theta = theta;
	}

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

	public Polygon getHitbox() {
		return hitbox;
	}

}
