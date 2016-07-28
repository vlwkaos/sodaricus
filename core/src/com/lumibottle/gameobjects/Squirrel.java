package com.lumibottle.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.lumibottle.gameobjects.Bullets.Bullet;
import com.lumibottle.helper.AssetHelper;
import com.lumibottle.helper.FXHelper;
import com.lumibottle.screen.GameScreen;

/**
 * Created by MG-POW on 2016-03-10.
 */
public class Squirrel {

	private enum SquirrelState {
		IDLE, SHOOTING,SPAWNING
	}

	private SquirrelState currentState;
	private boolean isInvincible;
    private short life;

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

	final private float shootFreq = 0.1f;
	private Bullet[] bullets;// hold for optimum performance
	private ParticleEffect sodaburst;


	private Polygon hitbox;

	public Squirrel( int width, int height) {
		this.width = width;
		this.height = height;
		position = new Vector2(-1.5f*width, GameScreen.midPointY);
		velocity = new Vector2(0, 0);
		rotation = 0;
		acceleration = new Vector2(0, -460);
		currentState=SquirrelState.SPAWNING;
        invincTime=0;
		isInvincible = true;// 나중에 바꿔 무적시간.
        life = 2;

		ceiling = GameScreen.gameHeight-getHeight()/2.0f;// temporary
		Gdx.app.log("Squirrel", "ceiling="+ceiling);


		hitbox = new Polygon(new float[] {4,0,width-4,0,width-4,height-8,4,height-8});
		hitbox.setOrigin(width/2f,height/2f);

		/*
		init bullet
		 */
		bullets = new Bullet[15];
		for (int i=0;i<bullets.length;i++)
            bullets[i] = new Bullet();

		runTime=0;
		sodaburst = AssetHelper.sodaburstPool.obtain();
	}



	public void update(float delta){
		// constant delta
		if (delta > .15f)
			delta = .15f;


		/*
				Bullet Update
		 */
		for (com.lumibottle.gameobjects.Bullets.Bullet b: bullets)
			b.update(delta);


        if (life >=0)
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
				for (com.lumibottle.gameobjects.Bullets.Bullet b : bullets) {
					if (b.isDEAD()) {
						b.reset(position.x + getWidth() / 2f, position.y + getHeight() / 2f, 250, rotation); // speed
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

            velocity.add(acceleration.cpy().scl(delta));//add acc to velocity
			//temporary bottom
			if (position.y < -getHeight()) {
     			dead();
			}



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
                velocity.x = 0;

				currentState = SquirrelState.IDLE;
			}
				else {
                rotation=0;
                velocity.y=0;
                velocity.x = 50;
            }
		}

		/*
			invincible
		 */
		if (isInvincible){
			if (invincTime >5.0f)
				isInvincible=false;
			else
				invincTime+=delta;
		}

		/*
			collision
		 */

        position.add(velocity.cpy().scl(delta));//add velo to position
        hitbox.setPosition(position.x, position.y);
        hitbox.setRotation(rotation);

	}

	public void onClick(){

		//change how respawn is handled later
        if (!isSPAWNING())
		velocity.y=160; // jump


        //if (life <0 하고 일정 시간이 지나면(UI가 나오면))
        // 재시작?

	}

	public void dead(){
		//TODO white flash maybe not?

		FXHelper.getInstance().newFX(getX()-108/2f,getY()-108/2f,FX.QUANTUM_EXPLOSION);
		AssetHelper.sodaburstPool.free((ParticleEffectPool.PooledEffect) sodaburst);
		rotation=0;
        currentState = SquirrelState.SPAWNING;
        position.set(-5.0f*width, GameScreen.midPointY);
        isInvincible = true;
        invincTime=0;
        life--;
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

    public short getLife(){
        return life;
    }
	}

