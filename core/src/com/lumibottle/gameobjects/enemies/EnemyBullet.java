package com.lumibottle.gameobjects.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.lumibottle.gameobjects.GameEvent;
import com.lumibottle.gameobjects.Squirrel;

/**
 * Created by MG-POW on 2016-04-01.
 */
public class EnemyBullet extends GameEvent {


    /*
        0 = cowboyhat
        1 = Block
     */
	private int type;
	private float runTime;

    private float minXpos;

	public EnemyBullet() {
		super(32, 16, new Polygon(new float[]{  4,4,
												28,4,
												28,12,
												4,12}),0);
        type=0;
        runTime=0;
        minXpos=0;
	}

	@Override
	public void update(float delta) {
		if (isVISIBLE()) {
			runTime+=delta;
			getHitbox().setPosition(getX(), getY());
			getHitbox().setRotation(getTheta());
			if (type == 1){
                if (getX()<minXpos) {
                    setX(minXpos);

                }
                else
                    getPosition().add(getVelocity().cpy().scl(delta));
            } else
            getPosition().add(getVelocity().cpy().scl(delta));

			if (isOutOfScreen(true))
				dead();
		}
	}
/*
	when reset, calculate theta from squirrel and mob
    theta for local rotation of the texture
 */

	public void reset(float x, float y, float dx, float dy, float theta, int type) {
		super.reset(x, y, dx, dy, theta);
		runTime=0;
		this.type =type;
	}

	@Override
	public void collide(Squirrel squirrel) {
		if (isVISIBLE()) {
			if (Intersector.overlapConvexPolygons(squirrel.getHitbox(), getHitbox())) {
				Gdx.app.log("squirrel is hit by: ", this.getClass().toString());
				squirrel.dead();
			}

		}
	}

    public void setMinXpos(float x){
        minXpos = x;
    }
	public float getRunTime() {
		return runTime;
	}

	public int getType() {
		return type;
	}

}
