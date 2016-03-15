package com.lumibottle.gameobjects;

import com.badlogic.gdx.math.GeometryUtils;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by MG-POW on 2016-03-12.
 */
public class Bullet extends GameEvent {

	private Polygon hitbox;
	private float theta;
	//load in gameworld first, the move

	public Bullet() {
		super(16, 16);
		hitbox = new Polygon(new float[]{0,0,getWidth(),0,getWidth(),getHeight(),0,getHeight()});
		hitbox.setOrigin(getX()/2f,getY()/2f);
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

	public void kill(){
		//FX when collide
	}


	public Polygon getHitbox() {
		return hitbox;
	}
}
