package com.lumibottle.gameobjects.Bullets;


import com.badlogic.gdx.math.Polygon;
import com.lumibottle.gameobjects.FX;
import com.lumibottle.helper.FXHelper;
import com.lumibottle.screen.GameScreen;


/**
 * Created by MG-POW on 2016-07-17.
 */

public class PipeEnemyBullet extends EnemyBullet{


	private float targetYpos; // 중간지점
	private boolean up;

	private float margin;

    public PipeEnemyBullet() {
        super(25, 120, new Polygon(new float[]{0,0,0,120,25,120,25,0}));
	    up=false;
	    targetYpos = 0;
	    margin = 25;
    }

    @Override
    public void specificUpdate(float delta) {
	    if (up){
			if (getY()<(targetYpos+margin)){ // 20 is squirrel height, make space
				setY(targetYpos+margin);
				setVelocity(-60,0);
			} else{
				//have to appear on screen
				setVelocity(0,-60);
			}
	    } else {
		    if (getY()>(targetYpos-getHeight()-margin)){ // 20 is squirrel height, make space

			    setY(targetYpos-getHeight()-margin);
			    setVelocity(-60,0);
		    } else{
			    //have to appear on screen
			    setVelocity(0,60);
		    }
	    }
    }

	public void reset(float targetYpos, boolean up){
		this.targetYpos = targetYpos+16;//pipeboss origin
		if (this.targetYpos > GameScreen.gameHeight-margin*4)
			this.targetYpos = GameScreen.gameHeight-margin*4;
		if (this.targetYpos < margin*2)
			this.targetYpos = margin*2;


		this.up = up;
		if (this.up){
			super.reset(240-getWidth()*3, this.targetYpos+GameScreen.gameHeight, 0, 0, 0);
		} else {
			super.reset(240-getWidth()*3, this.targetYpos-GameScreen.gameHeight-getHeight(), 0, 0, 0);
		}
	}

	@Override
	public void bottleHitsEnemy(Bullet b){
		FXHelper.getInstance().newFX(b.getX(), b.getY(), FX.SODA_EXPLOSION);
		b.hit();
	}

	public boolean isUp(){
		return up;
	}

}
