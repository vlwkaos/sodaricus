package com.lumibottle.gameobjects.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.lumibottle.gameobjects.Bullet;
import com.lumibottle.gameobjects.GameEvent;
import com.lumibottle.gameobjects.Squirrel;
import com.lumibottle.helper.FXHelper;

/**
 * Created by MG-POW on 2016-04-01.
 */
public class Blackhole extends GameEvent {


	public Blackhole(int width, int height, Polygon hitbox) {
		super(width, height, hitbox);
	}

	@Override
	public void update(float delta) {
		if (isVISIBLE()) {
			getHitbox().setPosition(getX(), getY());
			getPosition().add(getVelocity().cpy().scl(delta));
			if (isOutOfScreen(true))
				ready();
		}

	}

	//TODO
	public void collide(Squirrel squirrel) {
		if (isVISIBLE()) {
			for (Bullet b : squirrel.getBullets()) {
				if (b.getX() + b.getWidth() > getX())
					if (Intersector.overlapConvexPolygons(b.getHitbox(), getHitbox()) && b.isVISIBLE()) {
						FXHelper.getInstance().newFX(b.getX(), b.getY(), (short) 0); // draw bullet into the blackhole
						ready();
						b.ready();//dead fx goes in ready?
						break;
					}
			}

			if (squirrel.getX() + squirrel.getWidth() > getX()) {
				if (Intersector.overlapConvexPolygons(squirrel.getHitbox(), getHitbox())) {
					Gdx.app.log("squirrel is hit by: ", this.getClass().toString());
					squirrel.kill();
				}
			}

		}
	}


}
