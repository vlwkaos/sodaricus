package com.lumibottle.gameobjects.enemies;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.lumibottle.gameobjects.GameEvent;
import com.lumibottle.helper.AssetHelper;

/**
 *  CowboySausage is a shooting enemy that never goes away unless killed
 */
public class CowboySausage extends GameEvent{


	private enum CowboySausageState{
		IDLE, PREPARE, SHOOT
	}

	private float runTime;
	private CowboySausageState currentState;


	public CowboySausage() {
		super(32, 32, new Polygon(new float[]{0,0,32,0,32,32,0,32}));
	}

	@Override
	public void update(float delta) {
		if (isVISIBLE()) {
			getHitbox().setPosition(getX(), getY());

			if (getX()>240-getWidth()*2f)
			getPosition().add(getVelocity().cpy().scl(delta));

			//switch here, then when bullet is fired in ProgressHandler, it will put isShooting to false
			runTime+=delta;
			switch (currentState){
				case IDLE :if (runTime >= 2.0f) {
						runTime -= 2.0f;
						currentState = CowboySausageState.PREPARE; // animation starts
					//TODO FX?
					} break;
				case PREPARE: if (runTime >= 0.3f){ // giv esome time for animation to complete
						runTime -= 0.3f;
						currentState = CowboySausageState.SHOOT;
				} break;
			}


			if (isOutOfScreen(true))
				ready();
		}

	}
	public void reset(float x) {
		super.reset(x, MathUtils.random(GameEvent.gameHeight)-getHeight(), -50, 0, 0);
		runTime=0;
		currentState = CowboySausageState.IDLE;
	}

	public boolean isShooting(){
		return currentState == CowboySausageState.SHOOT;
	}

	public void doneShooting(){
		currentState = CowboySausageState.IDLE;
	}

	public float getRunTime() { // for animation, and timing
		return runTime;
	}

	//TODO: enemy bullet should appear in ProgressHandler and be recycled for an optimum performance
}
