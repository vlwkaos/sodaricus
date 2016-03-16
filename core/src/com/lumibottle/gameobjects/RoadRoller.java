package com.lumibottle.gameobjects;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;

/**
 * Created by MG-POW on 2016-03-13.
 */
public class RoadRoller extends GameEvent {

	private Polygon hitbox;

	public RoadRoller() {
		super(20, 12);
		hitbox = new Polygon(new float[] {0,0,20,0,20,12,0,12});
	}

	@Override
	public void update(float delta) {
		hitbox.setPosition(getX(), getY());

		if (isVISIBLE())
			getPosition().add(getVelocity().cpy().scl(delta));


//		collide(mySquirrel); this is progress
		if (isOutOfScreen())
			ready();

	}

	public void reset(float x){
		super.reset(x, MathUtils.random(GameEvent.gameHeight), -50,0,0);
	}


//checks in progress handler, then call squirrel's dead, reset method
	public void collide(Squirrel squirrel){
		for (Bullet b:squirrel.getBullets()){
			if (Intersector.overlapConvexPolygons(b.getHitbox(),hitbox)){
				b.setEffectReady(true);
				setEffectReady(true);
				Gdx.app.log("bullet hits","roadroller");
			}
		}

		if (squirrel.getX()+squirrel.getWidth()> getX()){
			if (Intersector.overlapConvexPolygons(squirrel.getHitbox(),hitbox)) {
				squirrel.kill();
				setEffectReady(true);

			}
		}

	}





}
