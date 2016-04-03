package com.lumibottle.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.AssetLoader;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import com.lumibottle.helper.AssetHelper;
import com.lumibottle.helper.FXHelper;

/**
 * Created by MG-POW on 2016-03-10.
 */
public class Squirrel {

	private enum SquirrelState {
		IDLE, SHOOTING, DEAD
	}

	private SquirrelState currentState;

	private float runTime;
	private float animRunTime;

	//Vector Information
	private Vector2 position;
	private Vector2 velocity;
	private Vector2 acceleration;
	private float rotation;

	//Size
	private int width, height;
	private float ceiling;


	private Bullet[] bullets;// hold for optimum performance
	private ParticleEffect sodaburst;


	private Polygon hitbox;

	public Squirrel(float x, float y, int width, int height) {
		this.width = width;
		this.height = height;
		position = new Vector2(x, y);
		velocity = new Vector2(0, 0);
		rotation = 0;
		acceleration = new Vector2(0, -460);
		currentState=SquirrelState.IDLE;

		ceiling = (Gdx.graphics.getHeight()/(Gdx.graphics.getWidth()/240))-getHeight();// temporary
		//TODO: is this device independent?

		hitbox = new Polygon(new float[] {4,0,width-4,0,width-4,height-8,4,height-8});
		hitbox.setOrigin(width/2f,height/2f);

		/*
		init bullet
		 */
		bullets = new Bullet[20];
		for (int i=0;i<bullets.length;i++)
			bullets[i] = new Bullet();

		runTime=0;

		sodaburst = AssetHelper.sodaburstPool.obtain();
	}



	public void updateDead(float delta){
		for (Bullet b: bullets)
			b.update(delta);

	}

	public void update(float delta){
		// constant delta
		if (delta > .15f)
			delta = .15f;
		runTime+=delta;
		animRunTime+=delta;
		/*
				Bullet Update
		 */
		for (Bullet b: bullets)
			b.update(delta);
		/*
		SHOOTING MECHANIC
		 */
		if (runTime >0.5f && isIdle()){
			runTime-=0.5f;
			animRunTime = 0;
			currentState=SquirrelState.SHOOTING;
			for (Bullet b: bullets){
				if (b.isREADY()){
					b.reset(position.x+getWidth()/2f,position.y+getHeight()/2f,200,rotation);
					break;
				}
			}
			//shoot bullet
		} else if (runTime >0.2f && isShooting()){
			runTime-=0.2f;
			currentState = SquirrelState.IDLE;
		}
		/*
		Position constraints
		TODO: just put static enemy that detects collision and kill this thing
		 */
		if (velocity.y < -150) {
			velocity.y = -150;
		} // maximum falling speed
		if (position.y > ceiling) {
			position.y = ceiling;
			velocity.y = 0;
		}

		//temporary bottom
		if (position.y<0){
			position.y = 0;
			rotation=0;
		}
		/*
		Physics
		 */
		velocity.add(acceleration.cpy().scl(delta));//add acc to velocity
		position.add(velocity.cpy().scl(delta));//add velo to position

		//         _|_ angle
		//rotation
			if (velocity.y >= 0) {
				rotation += 600 * delta;
				if (rotation > 45)
					rotation = 45;
			}
			if (velocity.y < -50) {
				rotation -= 280 * delta;
				if (rotation < -90)
					rotation = -90;
			}

		/*
			collision
		 */
		hitbox.setPosition(position.x,position.y);
		hitbox.setRotation(rotation);

	}

	public void onClick(){

		if (isDead()){
			currentState = SquirrelState.IDLE;
			position.set(55,55);
		}
			else
		velocity.y=160;
	}

	public void kill(){
		//TODO white flash

		currentState = SquirrelState.DEAD;
		FXHelper.getInstance().newFX(getX()-108/2f,getY()-108/2f,(short)4);
		position.set(-255f,-255f);
	}


	/*
	GETTER and SETTER
	 */
	public float getX(){
		return position.x;
	}

	public float getY(){
		return position.y;
	}

	public float getWidth(){
		return width;
	}

	public float getHeight(){
		return height;
	}

	public float getRotation(){
		return rotation;
	}

	public float getAnimRunTime() {
		return animRunTime;
	}

	public Bullet[] getBullets(){
		return bullets;
	}

	public Polygon getHitbox() {
		return hitbox;
	}

	public Vector2 getVelocity() {
		return velocity;
	}

	public ParticleEffect getSodaburst() {
		return sodaburst;
	}

	public boolean isDead(){
		return currentState==SquirrelState.DEAD;
	}

	public boolean isShooting(){
		return currentState==SquirrelState.SHOOTING;
	}

	public boolean isIdle(){
		return currentState==SquirrelState.IDLE;
	}

	}

