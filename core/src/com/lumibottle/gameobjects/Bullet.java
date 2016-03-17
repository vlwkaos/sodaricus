package com.lumibottle.gameobjects;


import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;


/**
 * Created by MG-POW on 2016-03-12.
 */
public class Bullet extends GameEvent {

	private Polygon hitbox;
	private float theta;
	//load in gameworld first, the move

	public Bullet() {
		super(12, 12);
		hitbox = new Polygon(new float[]{4,4,8,4,8,8,4,8});
		hitbox.setOrigin(getWidth()/2f,getHeight()/2f);
	}

	public void update(float delta) {
		hitbox.setPosition(getX(), getY());
		hitbox.setRotation(getTheta());

		if (isVISIBLE())
			getPosition().add(getVelocity().cpy().scl(delta));


		if (isOutOfScreen())
			ready();
	}

	public void reset(float x, float y, float speed, float theta) {
		this.theta=theta+MathUtils.random(-20,20);
		super.reset(x,y,speed * MathUtils.cos(MathUtils.degreesToRadians*this.theta), speed * MathUtils.sin(MathUtils.degreesToRadians*this.theta), this.theta);
	}


	public Polygon getHitbox() {
		return hitbox;
	}
}
