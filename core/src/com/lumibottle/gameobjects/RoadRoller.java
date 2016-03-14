package com.lumibottle.gameobjects;


import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;

/**
 * Created by MG-POW on 2016-03-13.
 */
public class RoadRoller extends Scrollable {

	private Polygon hitbox;

	public RoadRoller(float x, float y, float x_speed) {
		super(x, y, 20, 12	, x_speed, 0);
		hitbox = new Polygon(new float[] {0,0,20,0,20,12,0,12});
		reset(x);
	}

	//Overload
	public void update(float delta, Squirrel mySquirrel) {
		super.update(delta);
		hitbox.setPosition(getX(), getY());

		collide(mySquirrel);
	}

	@Override
	public void reset(float x) {
		super.reset(x);
	}
//checks in progress handler, then call squirrel's dead, reset method

	public void collide(Squirrel squirrel){
		for (Bullet b:squirrel.getBullets()){
			if (Intersector.overlapConvexPolygons(b.getHitbox(),hitbox)){
				kill();
				
			}
		}

		if (squirrel.getX()+squirrel.getWidth()> getX()){
			if (Intersector.overlapConvexPolygons(squirrel.getHitbox(),hitbox))
				squirrel.kill();
		}

	}

	private void kill(){

	}



}
