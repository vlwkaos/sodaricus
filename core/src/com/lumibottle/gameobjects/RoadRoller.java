package com.lumibottle.gameobjects;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.lumibottle.helper.AssetLoader;
import com.lumibottle.helper.FXHelper;

/**
 * Created by MG-POW on 2016-03-13.
 */
public class RoadRoller extends GameEvent {

	private Polygon hitbox;
	private ParticleEffect nitroParticle;


	public RoadRoller() {
		super(20, 12);
		hitbox = new Polygon(new float[]{0, 0, 20, 0, 20, 12, 0, 12});
	}

	@Override
	public void update(float delta) {
		hitbox.setPosition(getX(), getY());

		if (isVISIBLE()) {
			getPosition().add(getVelocity().cpy().scl(delta));
//		collide(mySquirrel); this is progress
			if (isOutOfScreen(true))
				ready();
		}

	}

	public ParticleEffect getParticle() {
		return nitroParticle;
	}

	public void reset(float x) {
		super.reset(x, MathUtils.random(GameEvent.gameHeight), -50, 0, 0);
		nitroParticle = AssetLoader.nitroPool.obtain();

	}


	@Override
	public void ready(){
		super.ready();
		AssetLoader.nitroPool.free((ParticleEffectPool.PooledEffect)nitroParticle);

	}

	//checks in progress handler, then call squirrel's dead, reset method
	public void collide(Squirrel squirrel) {
		if (isVISIBLE()) {
			for (Bullet b : squirrel.getBullets()) {
				if (b.getX() + b.getWidth() > getX())
					if (Intersector.overlapConvexPolygons(b.getHitbox(), hitbox) && b.isVISIBLE()) {
						FXHelper.getInstance().newFX(b.getX(), b.getY(), (short) 0);
						ready();
						b.ready();
						break;
					}
			}

			if (squirrel.getX() + squirrel.getWidth() > getX()) {
				if (Intersector.overlapConvexPolygons(squirrel.getHitbox(), hitbox)) {

				}
			}
		}
	}

}


