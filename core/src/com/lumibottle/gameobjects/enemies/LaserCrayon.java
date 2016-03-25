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
 * Created by MG-UP on 2016-03-24.
 */
public class LaserCrayon extends GameEvent {

	private enum LaserCrayonState{
		INIT,READYTOSHOOT, SHOT
	}

	private LaserCrayonState currentState;
    private float runTime;
	private float delay;
    public LaserCrayon() {
        super(29, 5, new Polygon(new float[]{0, 0, 29, 0, 29, 5, 0, 5}));

    }

    @Override
    public void update(float delta) {
        getHitbox().setPosition(getX(), getY());
	    runTime+=delta;
        if (isVISIBLE()) {
            getPosition().add(getVelocity().cpy().scl(delta));

	        // move right and
            if (getX()<240-getWidth()*2 && currentState == LaserCrayonState.INIT){
                getVelocity().add(2*runTime,0);
            }

	        //stop
	        if (getdX()>0 && currentState == LaserCrayonState.INIT){
		        currentState = LaserCrayonState.READYTOSHOOT;
		        setVelocity(0,0);
		        FXHelper.getInstance().newFX(0-(240-getX()),getY()-12f,(short)1);
		        delay=0;
	        }

	        //its ready to shoot after 1 second
			if (currentState == LaserCrayonState.READYTOSHOOT || currentState == LaserCrayonState.SHOT){
				delay+=delta;
			}


	        if (currentState == LaserCrayonState.READYTOSHOOT && delay > 1f){
			//change hit box
		        setHitbox(new float[]{
				        -240,17.5f,
				        0,17.5f,
				        0, 5,
				        29, 5,
				        29, 0,
				        0, 0,
				        0,-13.5f,
				        -240,-13.5f});
		        //put fx
		        FXHelper.getInstance().newFX(0-(240-getX()),getY()-12f,(short)2);
		        currentState = LaserCrayonState.SHOT;
		        delay=0;
	        }

	        if (currentState == LaserCrayonState.SHOT && delay > 0.6f){
		        //change hit box
				setHitbox(new float[]{0, 0, 29, 0, 29, 5, 0, 5});
		        // go back to your home
		        setVelocity(delay*30,0);
	        }

			if (currentState == LaserCrayonState.SHOT  && getX()>240)
                ready();
        }

    }

//    public ParticleEffect getParticle() {
//        return nitroParticle;
//    }

    public void reset(float x) {
        super.reset(x, MathUtils.random(GameEvent.gameHeight), -50, 0, 0);
    runTime = 0;
	    delay=0;
	    currentState = LaserCrayonState.INIT;
    }


    @Override
    public void ready(){
        super.ready();
      //  AssetLoader.nitroPool.free((ParticleEffectPool.PooledEffect) nitroParticle);

    }

	@Override
	public void collide(Squirrel squirrel) {
		if (isVISIBLE()) {
				if (Intersector.overlapConvexPolygons(squirrel.getHitbox(), getHitbox())) {
					Gdx.app.log("squirrel is hit by: ", this.getClass().toString());
				}

		}
	}

}