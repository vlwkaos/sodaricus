package com.lumibottle.gameobjects;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by MG-POW on 2016-03-10.
 */
public class Squirrel {

	private enum SquirrelState {
		IDLE, ASCENDING, DEAD
	}

	private SquirrelState currentState;


	private Vector2 position;
	private Vector2 velocity;
	private Vector2 acceleration;

	private int width, height;

	private float rotation;

	public Squirrel(float x, float y, int width, int height) {
		this.width = width;
		this.height = height;
		position = new Vector2(x, y);
		velocity = new Vector2(0, 0);
		acceleration = new Vector2(0, -460);
	}


	public void update(float delta){
		//add velo to position

		//add acc to velocity

		//rotation

		//shoot

	}

	public void onClick(){
		velocity.y=40;
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

	public boolean isAscending(){
		return currentState==SquirrelState.ASCENDING;
	}
	public boolean isIdle(){
		return currentState==SquirrelState.IDLE;
	}
}

