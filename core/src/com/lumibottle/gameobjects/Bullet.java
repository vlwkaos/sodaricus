package com.lumibottle.gameobjects;


import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;


/**
 * Created by MG-POW on 2016-03-12.
 */
public class Bullet extends GameEvent {

	private float theta;
	//load in gameworld first, the move

	public Bullet() {
		super(12, 12,new Polygon(new float[]{4,4,8,4,8,8,4,8}));
		getHitbox().setOrigin(getWidth()/2f,getHeight()/2f);
	}

	public void update(float delta) {
		getHitbox().setPosition(getX(), getY());
		getHitbox().setRotation(getTheta());

		if (isVISIBLE()) {
			getPosition().add(getVelocity().cpy().scl(delta));


			if (isOutOfScreen(false))
				ready();
		}
	}

	public void reset(float x, float y, float speed, float theta) {
		if (theta > 0)
			this.theta=5*MathUtils.log2(theta);
		else if (theta <0)
			this.theta=(-5)*MathUtils.log2((-1)*theta);
		else
			this.theta=theta;

		super.reset(x,y,speed * MathUtils.cos(MathUtils.degreesToRadians*this.theta), speed * MathUtils.sin(MathUtils.degreesToRadians*this.theta), this.theta);
	}

}
