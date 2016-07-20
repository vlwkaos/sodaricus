package com.lumibottle.gameobjects.enemies;


import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.lumibottle.gameobjects.GameEvent;
import com.lumibottle.helper.AssetHelper;

/**
 *  RoadRoller is the most basic type of an enemy; it just moves with a constant speed
 */
public class RoadRoller extends GameEvent {


	private ParticleEffect popcornParticle;


	public RoadRoller() {
		super(32, 14, new Polygon(new float[]{0, 0, 32, 0, 32, 14, 0, 14}),3);
	}

	@Override
	public void update(float delta) {
		if (isVISIBLE()) {
			getHitbox().setPosition(getX(), getY());
			getPosition().add(getVelocity().cpy().scl(delta));
			if (isOutOfScreen(true))
				hit();
		}

	}

	public ParticleEffect getParticle() {
		return popcornParticle;
	}

	public void reset(float x) {
		super.reset(x, MathUtils.random(GameEvent.gameHeight)-getHeight(), -50, 0, 0);
		popcornParticle = AssetHelper.popcornPool.obtain();

	}


	@Override
	public void dead(){
		super.dead();
		AssetHelper.popcornPool.free((ParticleEffectPool.PooledEffect) popcornParticle);
	}
}


