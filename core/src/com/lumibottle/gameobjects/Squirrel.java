package com.lumibottle.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.lumibottle.helper.AssetHelper;
import com.lumibottle.helper.FXHelper;
import com.lumibottle.screen.GameScreen;

/**
 * Created by MG-POW on 2016-03-10.
 */
public class Squirrel {

	private enum SquirrelState {
		IDLE, SHOOTING, DEAD,SPAWNING
	}

	private SquirrelState currentState;
	private boolean isInvincible;


	private float runTime;
	private float animRunTime;
	private float invincTime;

	//Vector Information
	private Vector2 position;
	private Vector2 velocity;
	private Vector2 acceleration;
	private float rotation;

	//Size
	private int width, height;
	private float ceiling;

	final private float shootFreq = 0.05f;

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
		currentState=SquirrelState.DEAD;
		isInvincible = true;// 나중에 바꿔 무적시간.


		ceiling = GameScreen.gameHeight-getHeight();// temporary
		//TODO: is this device independent?
		Gdx.app.log("Squirrel", "ceiling="+ceiling);


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
		// constant delta
		if (delta > .15f)
			delta = .15f;

		for (Bullet b: bullets)
			b.update(delta);

	}

	public void update(float delta){
		// constant delta
		if (delta > .15f)
			delta = .15f;


		/*
				Bullet Update
		 */
		for (Bullet b: bullets)
			b.update(delta);


		if (!isSPAWNING()) {

			runTime += delta; // used for timing

			animRunTime += delta; // timing of animation

		/*
		SHOOTING MECHANIC
		 */
			if (runTime > shootFreq && isIDLE()) {// shooting speed adjust
				runTime -= shootFreq;
				animRunTime = 0;
				currentState = SquirrelState.SHOOTING;
				for (Bullet b : bullets) {
					if (b.isDEAD()) {
						b.reset(position.x + getWidth() / 2f, position.y + getHeight() / 2f, 200, rotation); // speed
						break;
					}
				}
				//shoot bullet
			} else if (runTime > 0.2f && isSHOOTING()) {
				runTime -= 0.2f;
				currentState = SquirrelState.IDLE;
			}
		/*
		Position constraints
		TODO: just put static enemy that detects collision and dead this thing
		 */
			if (velocity.y < -150) {
				velocity.y = -150;
			} // maximum falling speed
			if (position.y > ceiling) {
				position.y = ceiling;
				velocity.y = 0;

				Gdx.app.log("Squirrel", "hit head");
			}


			//temporary bottom
			if (position.y < 0) {
				position.y = 0;
				rotation = 0;
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

		}
		else { // spawning animation
			//simulate appearing from left
			if (position.x > 50) {
				position.x = 50;
				currentState = SquirrelState.IDLE;
			}
				else
				position.x += 1;
		}

		/*
			invincible
		 */
		if (isInvincible){
			if (invincTime >3.0f)
				isInvincible=false;
			else
				invincTime+=delta;
		}

		/*
			collision
		 */
			hitbox.setPosition(position.x, position.y);
			hitbox.setRotation(rotation);

	}

	public void onClick(){

		//change how respawn is handled later
		if (isDEAD()){
			currentState = SquirrelState.SPAWNING;
			position.set(-1*width, GameScreen.midPointY);
			isInvincible = true;
			invincTime=0;
		}
			else if (isSPAWNING()){
			//?
		} else
		velocity.y=160; // jump
	}

	public void dead(){
		//TODO white flash

		currentState = SquirrelState.DEAD;
		FXHelper.getInstance().newFX(getX()-108/2f,getY()-108/2f,(short)4);
		AssetHelper.sodaburstPool.free((ParticleEffectPool.PooledEffect) sodaburst);
		position.set(-255f,-255f);
		rotation=0;

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
	public float getRunTime() {
		return runTime;
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

	public boolean isDEAD(){
		return currentState==SquirrelState.DEAD;
	}

	public boolean isSHOOTING(){
		return currentState==SquirrelState.SHOOTING;
	}

	public boolean isIDLE(){
		return currentState==SquirrelState.IDLE;
	}

	public boolean isSPAWNING(){return currentState==SquirrelState.SPAWNING;}

	public boolean IsInvincible(){
		return isInvincible;
	}

	}

