package com.lumibottle.gameobjects.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.lumibottle.gameobjects.Bullet;
import com.lumibottle.gameobjects.GameEvent;
import com.lumibottle.gameobjects.Squirrel;
import com.lumibottle.helper.AssetLoader;
import com.lumibottle.helper.FXHelper;

/**
 * Created by MG-POW on 2016-03-27.
 */
public class Bomb extends GameEvent{

	private ParticleEffect popcornParticle;
	private boolean isExploding;
	 private float explodingCounter;

	private float bomb_x=40;
	private float bomb_y=40;

	public Bomb() {
		super(32, 14, new Polygon(new float[]{0, 0, 32, 0, 32, 14, 0, 14}));

	}

	@Override
	public void update(float delta) {
		getHitbox().setPosition(getX(), getY());

		if (isVISIBLE()) {


			if (isExploding)
				explodingCounter+=delta;
			else
				getPosition().add(getVelocity().cpy().scl(delta));

			if (isExploding && explodingCounter > 3/15f)
				ready();

			if (isOutOfScreen(true))
				ready();
		}

	}

	public ParticleEffect getParticle() {
		return popcornParticle;
	}

	public void reset(float x) {
//		super.reset(x, getHeight(), -70, 0, 0);
		super.reset(x, MathUtils.random(GameEvent.gameHeight)-getHeight(), -70, 0, 0);
		isExploding=false;
		popcornParticle = AssetLoader.popcornPool.obtain();

	}


	@Override
	public void ready(){
		super.ready();
		AssetLoader.nitroPool.free((ParticleEffectPool.PooledEffect) popcornParticle);
		setHitbox(new float[]{0, 0, 32, 0, 32, 14, 0, 14});
	}



	public void explode(){
		explodingCounter=0;
		isExploding=true;
		setHitbox(new float[]{0-(bomb_x-getWidth()/2f), 0-(bomb_y-getHeight()/2f),
							32+(bomb_x-getWidth()/2f), 0-(bomb_y-getHeight()/2f),
							32+(bomb_x-getWidth()/2f), 14+(bomb_y-getHeight()/2f),
							0-(bomb_x-getWidth()/2f), 14+(bomb_y-getHeight()/2f)});//TODO
	}



	public void collide(Squirrel squirrel) {
		if (isVISIBLE()) {
			// explosion occurs only once
			if (!isExploding)
			for (Bullet b : squirrel.getBullets()) {
				if (b.getX() + b.getWidth() > getX())
					if (Intersector.overlapConvexPolygons(b.getHitbox(), getHitbox()) && b.isVISIBLE()) {
						FXHelper.getInstance().newFX(b.getX(), b.getY(), (short) 0);
						//bomb fx
						FXHelper.getInstance().newFX(getX()-(bomb_x-getWidth())/2f,getY()-(bomb_y-getHeight())/2f,(short) 3);
						explode();

						b.ready();
						break;
					}
			}

			//explosion will enlarge the hitbox
			if (squirrel.getX() + squirrel.getWidth() > getX()) {
				if (Intersector.overlapConvexPolygons(squirrel.getHitbox(), getHitbox())) {
					Gdx.app.log("squirrel is hit by: ", this.getClass().toString());
				}
			}
		}
	}

	public boolean isExploding() {
		return isExploding;
	}


}
