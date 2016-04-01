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

	private ParticleEffect nitroParticle;

	public RoadRoller() {
		super(20, 12, new Polygon(new float[]{0, 0, 20, 0, 20, 12, 0, 12}));
	}

	@Override
	public void update(float delta) {
		getHitbox().setPosition(getX(), getY());

		if (isVISIBLE()) {
			getPosition().add(getVelocity().cpy().scl(delta));
			if (isOutOfScreen(true))
				ready();
		}

	}

	public ParticleEffect getParticle() {
		return nitroParticle;
	}

	public void reset(float x) {
		super.reset(x, MathUtils.random(GameEvent.gameHeight)-getHeight(), -50, 0, 0);
		nitroParticle = AssetHelper.nitroPool.obtain();

	}


	@Override
	public void ready(){
		super.ready();
		AssetHelper.nitroPool.free((ParticleEffectPool.PooledEffect) nitroParticle);

	}
}


