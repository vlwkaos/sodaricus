package com.lumibottle.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

/**
 * This Scrollable class is applied to GameObjects that scrolls leftward
 */
public abstract class Scrollable {

	private Vector2 position;
	private Vector2 velocity;

	private int width,height;

	public Scrollable(float x,float y, int width, int height, float x_speed, float y_speed){
		position = new Vector2(x,y);
		velocity= new Vector2(x_speed,y_speed);
		this.width=width;
		this.height=height;

	}


	public void update(float delta) {
		position.add(velocity.cpy().scl(delta));
	}

	public void reset(float x){
		position.set(x, MathUtils.random(Gdx.graphics.getHeight() / (Gdx.graphics.getWidth() / 240)));
	}

	public boolean isOOScreen(){
		return (position.x + width < 0);
	} // out of screen on the left side


	/*
		GETTER & SETTER
	 */
	public float getX(){
		return position.x;
	}

	public float getY(){
		return position.y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void setVelocity(float x, float y) {
		this.velocity.set(x,y);
	}
}
