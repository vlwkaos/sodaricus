package com.lumibottle.gameobjects.enemies;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.lumibottle.gameobjects.Bullets.Bullet;
import com.lumibottle.gameobjects.GameEvent;
import com.lumibottle.gameobjects.Squirrel;


/**
 * Created by MG-POW on 2016-04-01.
 */
public class Blackhole extends GameEvent {

	private float acc= 5f;

	public Blackhole() {
		super(32, 32, new Polygon(new float[]{-6,-24,22,-24,22,-6,40,22,22,40,-6,40,-24,22,-24,-6}),0);

	}

	@Override
	public void update(float delta) {
		if (isVISIBLE()) {
			addTheta(delta*10f);
			getHitbox().setPosition(getX(), getY());
			getPosition().add(getVelocity().cpy().scl(delta));
			if (isOutOfScreen(true))
				dead();
		}

	}

	public void reset(float x) {
		super.reset(x, (GameEvent.gameHeight/20f)*MathUtils.random(5,13), -15, 0, 0);
	}



    @Override
    public void bottleHitsEnemy(Bullet b){
        float theta= MathUtils.atan2(getY()-b.getY(),getX()-b.getX());
        float dx = acc* MathUtils.cos(theta);
        float dy = acc* MathUtils.sin(theta);
        b.getVelocity().add(acc*dx,acc*dy);
    }
}
