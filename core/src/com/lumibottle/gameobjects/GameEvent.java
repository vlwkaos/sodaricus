package com.lumibottle.gameobjects;

import com.badlogic.gdx.math.Vector2;

/**
 * 		OOP
 */
public abstract class GameEvent {

	private enum EventState{
		READY, VISIBLE
	}

	//runtime for enemy
	private Vector2 position;
	private Vector2 velocity;
	private EventState currentState;

	public GameEvent(float x, float y, int width, int height, float dx, float dy, float theta){

	}

	public abstract void update(float delta);
	public abstract void reset();



}
