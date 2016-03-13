package com.lumibottle.gameobjects;

import com.badlogic.gdx.math.Vector2;

/**
 * This Scrollable class is applied to GameObjects that scrolls leftward
 */
public abstract class Scrollable {

	private Vector2 position;
	private Vector2 velocity;

	private int width,height;

	public Scrollable(float x,float y, int width, int height, int velocity){
		position = new Vector2(x,y);
		this.width=width;
		this.height=height;

	}

	public boolean isOOScreen(){
		return (position.x + width < 0);
	} // out of screen on the left side

	public void update(){};


}
