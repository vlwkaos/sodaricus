package com.lumibottle.gameobjects.enemies;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.lumibottle.gameobjects.GameEvent;
import com.lumibottle.helper.AssetHelper;
import com.lumibottle.screen.GameScreen;

/**
 *  Cowboy is a shooting enemy that never goes away unless killed
 */
public class Cowboy extends GameEvent{


	private enum CowboyState {
		IDLE, PREPARE, SHOOT
	}

	private ParticleEffect nitroParticle;

	private float runTime;
	private CowboyState currentState;



	public Cowboy() {
		super(32, 48, new Polygon(new float[]{6,0,26,0,26,32,6,32}),3);
	}

	@Override
	public void update(float delta) {
		if (isVISIBLE()) {
			getHitbox().setPosition(getX(), getY());

			if (getX()>240-getWidth()*2f)
			getPosition().add(getVelocity().cpy().scl(delta));

			//switch here, then when bullet is fired in ProgressHandler, it will put isSHOOTING to false
			runTime+=delta;
			switch (currentState){
				case IDLE :if (runTime >= 2.0f) {
						runTime -= 2.0f;
						currentState = CowboyState.PREPARE; // animation starts
					} break;
				case PREPARE: if (runTime >= 4/30f){ // giv esome time for animation to complete
						runTime -= 4/30f;
						currentState = CowboyState.SHOOT;
				} break;
			}


			if (isOutOfScreen(true,false,true,true))
				dead();
		}

	}
	public void reset(float x) {
		super.reset(x, MathUtils.random(GameScreen.gameHeight)-getHeight()*2, -50, 0, 0);
		runTime=0;
		nitroParticle = AssetHelper.nitro2Pool.obtain();
		currentState = CowboyState.IDLE;
	}



	@Override
	public void dead(){
		super.dead();
		AssetHelper.nitro2Pool.free((ParticleEffectPool.PooledEffect) nitroParticle);

	}

	public boolean isShooting(){
		return currentState == CowboyState.SHOOT;
	}
	public boolean isIdle(){return currentState ==CowboyState.IDLE;}
	public boolean isPreparing(){return currentState == CowboyState.PREPARE;}

	public void doneShooting(){
		currentState = CowboyState.IDLE;
	}

	public ParticleEffect getParticle() {
		return nitroParticle;
	}

	public float getRunTime() { // for animation, and timing
		return runTime;
	}

	//TODO: enemy bullet should appear in ProgressHandler and be recycled for an optimum performance
}
