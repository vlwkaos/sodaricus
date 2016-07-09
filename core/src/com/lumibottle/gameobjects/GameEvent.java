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
		DEAD, VISIBLE
	}//DEAD state notifies that it went out of screen and is now hit to be reset

	public static float gameHeight = Gdx.graphics.getHeight() / (Gdx.graphics.getWidth() / 240);
	//runtime for enemy
	private Vector2 position;
	private Vector2 velocity;
	private EventState currentState;

	// Heatlh
	private int maxhp;
	private int hitpoint;

	private int width, height; // mostly for rendering...?
	private float theta;

	private Polygon hitbox;

	public GameEvent(int width, int height, Polygon hitbox, int hp){
		position = new Vector2(-255,-255);
		velocity = new Vector2(0,0);
		this.width = width;
		this.height = height;
		this.theta = 0;
		currentState = EventState.DEAD;
		this.hitbox = hitbox;
		this.maxhp = hp;
		this.hitpoint = hp;
	}


	/*
	BASIC PROTOTYPE : only update position when it's on visible state.
					  set to hit state when Out of screen.
					  set to hit state when hit by bullet.
					  visible state starts when reset.
	 */
	public abstract void update(float delta);


	//If using particle, You should obtain particle here by overriding this method.
	public void reset(float x, float y, float dx, float dy, float theta){
		this.position.set(x,y);
		this.velocity.set(dx,dy);
		this.theta=theta;
		this.hitpoint = this.maxhp;
		this.currentState = EventState.VISIBLE;
	}// re deploy, set to visible

	public boolean isOutOfScreen(boolean toLeft){
		//was visible but is out of screen, so move it else where hit to be re deployed
		if (toLeft)
			return (position.x+width*2<0 || position.y>gameHeight + height || position.y < 0-height );
		else
			return (  position.y>gameHeight + height || position.y < 0-height || position.x>240 );
	}


	public void hit(){
		if (hitpoint == 0){
			dead();
		} else {
			hitpoint--;
		}
	}

	// if using particle, free particle here
	public void dead(){
		position.set(-255, -255);
		currentState = EventState.DEAD; // hit to deploy

	}
	

	/*
	collision
	 */
	public void collide(Squirrel squirrel) {
		if (isVISIBLE()) {
			for (Bullet b : squirrel.getBullets()) {
				if (b.getX() + b.getWidth() > getX())
					if (Intersector.overlapConvexPolygons(b.getHitbox(), hitbox) && b.isVISIBLE()) {
						//When bullet hits Event
						FXHelper.getInstance().newFX(b.getX(), b.getY(), (short) 0);
						hit();
						b.hit();//dead fx goes in hit?
						break;
					}
			}

			//When squirrel is hit by event
			if (squirrel.getX() + squirrel.getWidth() > getX() && !squirrel.IsInvincible()) {
				if (Intersector.overlapConvexPolygons(squirrel.getHitbox(), hitbox)) {
					Gdx.app.log("squirrel is hit by: ", this.getClass().toString());
					squirrel.dead();
				}
			}
		}
	}



	/*
		GETTER & SETTER
	 */
	public boolean isDEAD(){
		return currentState==EventState.DEAD;
	}
	public boolean isVISIBLE(){
		return currentState==EventState.VISIBLE;
	}

	public void setHitbox(float[] hitbox) {
		this.hitbox.setVertices(hitbox);
	}
	//it will use coordinate from main actor, so ..
	public void setVelocity(float x, float y) {
		this.velocity.set(x,y);
	}

	public void setTheta(float theta) {
		this.theta = theta;
	}

	public void addTheta(float theta) {this.theta += theta;}

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

	public int getHeight() {return height;}

	public Vector2 getPosition() {
		return position;
	}

	public Vector2 getVelocity() {
		return velocity;
	}

	public Polygon getHitbox() {
		return hitbox;
	}

	public float getdX(){
		return velocity.x;
	}



}
