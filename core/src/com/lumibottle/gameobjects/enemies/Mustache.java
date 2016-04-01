package com.lumibottle.gameobjects.enemies;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.lumibottle.gameobjects.GameEvent;
import com.lumibottle.helper.AssetHelper;

/**
 *  Mustache moves in a jelly-fish-like fashion, it is slightly unpredictable.
 */
public class Mustache extends GameEvent {

	private boolean doneMoving;

	private ParticleEffect rainbowParticle;

	final private int speed=120;
	private float runTime;

	public Mustache() {
		super(33, 16,new Polygon(new float[]{3, 0, 30, 0, 30, 16, 3, 16}));

	}


	//must separate moving, stopping
	@Override
	public void update(float delta) {
		getHitbox().setPosition(getX(), getY());
		runTime +=delta;

		if (isVISIBLE()) {
			if (!doneMoving && runTime >= .4f) {
				runTime -= .4f;
				doneMoving=true;
				nextTheta();
			}

			if (doneMoving && runTime >= 1.2f) { // resting
				runTime-=1.2f;
				doneMoving = false;
			}

			if (!doneMoving) {
				setVelocity(MathUtils.sin(runTime * MathUtils.PI / 0.4f) * MathUtils.cosDeg(getTheta())*speed,
						MathUtils.sin(runTime * MathUtils.PI / 0.4f) * MathUtils.sinDeg(getTheta())*speed);// changing
			}

			getPosition().add(getVelocity().cpy().scl(delta));

			if (isOutOfScreen(true))
				ready();
		}

	}

	public void reset(float x) {
		super.reset(x, MathUtils.random(GameEvent.gameHeight)-getHeight(), -50, 0, 0);
		rainbowParticle = AssetHelper.rainbowPool.obtain();
		runTime=MathUtils.random(0,0.5f);
		doneMoving=false;
		nextTheta();
	}

	public ParticleEffect getParticle() {
		return rainbowParticle;
	}

	@Override
	public void ready(){
		super.ready();
		AssetHelper.rainbowPool.free((ParticleEffectPool.PooledEffect)rainbowParticle);

	}

	public boolean isDoneMoving(){
		return doneMoving;
	} // to notify sprite batch

	private void nextTheta(){
		if (getY() < getHeight())
			setTheta(MathUtils.random(100,120));
		else if ( getY() > GameEvent.gameHeight-getHeight())
			setTheta(MathUtils.random(240,260));
		else
			setTheta(MathUtils.random(110,250));// set direction for the next move

	}

	public float getRunTime() {
		return runTime;
	}

	//checks in progress handler, then call squirrel's dead, reset method
}
