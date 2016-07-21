package com.lumibottle.gameobjects.Bullets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Polygon;
import com.lumibottle.screen.GameScreen;

/**
 * Created by MG-POW on 2016-07-17.
 */

public class PipeEnemyBullet extends EnemyBullet{


	private float targetYpos; // 중간지점
	private boolean up;

    public PipeEnemyBullet() {
        super(25, 120, new Polygon(new float[]{0,0,0,120,25,120,25,0}));
	    up=false;
	    targetYpos = 0;
    }

    @Override
    public void specificUpdate(float delta) {
	    if (up){
			if (getY()<(targetYpos+20)){ // 20 is squirrel height, make space

				setY(targetYpos+20);
				setVelocity(-50,0);
			} else{
				//have to appear on screen
				setVelocity(0,-50);
			}
	    } else {
		    if (getY()>(targetYpos-getHeight()-20)){ // 20 is squirrel height, make space
				Gdx.app.log(getClass().getSimpleName(),"밑에꺼딱");
			    setY(targetYpos-getHeight()-20);
			    setVelocity(-50,0);
		    } else{
			    //have to appear on screen
			    setVelocity(0,50);
		    }
	    }
    }

	public void reset(float targetYpos, boolean up){
		this.targetYpos = targetYpos+getHeight()/2;
		this.up = up;
		if (this.up){
			super.reset(240-getWidth()*2, GameScreen.gameHeight, 0, 0, 0);
		} else {
			super.reset(240-getWidth()*2, 0-getHeight(), 0, 0, 0);
		}
	}

	public boolean isUp(){
		return up;
	}

}
