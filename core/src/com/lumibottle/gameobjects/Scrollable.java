package com.lumibottle.gameobjects;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by MG-POW on 2016-03-10.
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

	public boolean isLeftofScreen(){
		return (position.x + width < 0);
	}

	public void update(){};


}
