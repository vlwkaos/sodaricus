package com.lumibottle.gameobjects;

import com.badlogic.gdx.math.Rectangle;

import org.w3c.dom.css.Rect;

/**
 * Created by MG-POW on 2016-03-13.
 */
public class RoadRoller extends Scrollable {

	private Rectangle hitbox;


	public RoadRoller(float x, float y, int width, int height, float x_speed, float y_speed) {
		super(x, y, width, height, x_speed, y_speed);
		hitbox = new Rectangle();
	}

	@Override
	public void update(float delta) {
		super.update(delta);
		hitbox.set(getX(),getY(),getWidth(),getHeight());

	}

	@Override
	public void reset(float x) {
		super.reset(x);
	}
//checks in progress handler, then call squirrel's dead, reset method

	public boolean hitSquirrel(Squirrel squirrel){
		if (squirrel.getX()+squirrel.getWidth()> getX())
			return false;

		return false;
	}


}
