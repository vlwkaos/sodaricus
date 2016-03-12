package com.lumibottle.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;

/**
 * Created by MG-POW on 2016-03-10.
 */
public class Squirrel {

	private enum SquirrelState {
		IDLE, SHOOTING, DEAD
	}

	private SquirrelState currentState;
	private float runTime;

	private Vector2 position;
	private Vector2 velocity;
	private Vector2 acceleration;

	private int width, height;
	private int ceiling;


	private float rotation;

	public Squirrel(float x, float y, int width, int height) {
		this.width = width;
		this.height = height;
		position = new Vector2(x, y);
		velocity = new Vector2(0, 0);
		rotation = 0;
		acceleration = new Vector2(0, -460);
		currentState=SquirrelState.IDLE;
		ceiling = (Gdx.graphics.getHeight()/(Gdx.graphics.getWidth()/240))-65;// temporary

		runTime=0;
	}


	public void update(float delta){
		if (isShooting()/*and not dead*/) {
			Timer.schedule(new Timer.Task() {
				@Override
				public void run() {
					currentState = SquirrelState.IDLE;
				}
			},0.3f);
		}


		//runtime to determine the frequency of shooting
		if (delta > .15f) {
			delta = .15f;
		}
		runTime+=delta;

		if (runTime >1){
			runTime-=1;
			currentState=SquirrelState.SHOOTING;
		}

		//
		if (velocity.y < -200) {
			velocity.y = -200;
		} // maximum falling speed

		// CEILING CHECK
		if (position.y > ceiling) {
			Gdx.app.log("heat ceiling!","yes");
			position.y = ceiling;
			velocity.y = 0;
		}
		//add acc to velocity
	//	velocity.add(acceleration.cpy().scl(delta));
		//add velo to position
	//	position.add(velocity.cpy().scl(delta));



		//rotation

		//shoot

	}

	public void onClick(){
		velocity.y=140;
	}





	/*
	GETTER and SETTER
	 */
	public float getX(){
		return position.x;
	}

	public float getY(){
		return position.y;
	}

	public float getWidth(){
		return width;
	}

	public float getHeight(){
		return height;
	}

	public float getRotation(){
		return rotation;
	}

	public boolean isDead(){
		return currentState==SquirrelState.DEAD;
	}

	public boolean isShooting(){
		return currentState==SquirrelState.SHOOTING;
	}

	public boolean isIdle(){
		return currentState==SquirrelState.IDLE;
	}
}

