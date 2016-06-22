package com.lumibottle.gameobjects.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.lumibottle.gameobjects.GameEvent;
import com.lumibottle.gameobjects.Squirrel;

/**
 * Created by MG-POW on 2016-04-01.
 */
public class EnemyBullet extends GameEvent {


	private int type;
	private float runTime;

	public EnemyBullet() {
		super(32, 16, new Polygon(new float[]{  4,4,
												28,4,
												28,12,
												4,12}),0);

	}

	@Override
	public void update(float delta) {
		if (isVISIBLE()) {
			runTime+=delta;
			getHitbox().setPosition(getX(), getY());
			getHitbox().setRotation(getTheta());
			getPosition().add(getVelocity().cpy().scl(delta));

			if (isOutOfScreen(true))
				dead();
		}
	}
/*
	when reset, calculate theta from squirrel and mob

 */

	public void reset(float x, float y, float dx, float dy, float theta, int type) {
		super.reset(x, y, dx, dy, theta);
		runTime=0;
		this.type =type;
	}

	@Override
	public void collide(Squirrel squirrel) {
		if (isVISIBLE()) {
			if (Intersector.overlapConvexPolygons(squirrel.getHitbox(), getHitbox())) {
				Gdx.app.log("squirrel is hit by: ", this.getClass().toString());
				squirrel.dead();
			}

		}
	}

	public float getRunTime() {
		return runTime;
	}

	public int getType() {
		return type;
	}

}
