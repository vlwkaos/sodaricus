package com.lumibottle.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.lumibottle.gameobjects.Bullets.Bullet;
import com.lumibottle.helper.AssetHelper;
import com.lumibottle.helper.FXHelper;
import com.lumibottle.helper.SoundManager;
import com.lumibottle.screen.GameScreen;

/**
 * Created by MG-POW on 2016-03-10.
 */
public class Squirrel {

    private enum SquirrelState {
        IDLE, SHOOTING, SPAWNING
    }

    private SquirrelState currentState;
    private boolean isInvincible;
    private boolean isTransparent;
    private boolean isRamseyThunder;
    private short life;

    private float runTime;
    private float animRunTime;
    private float invincTime;
    private float thunderTime;

    //Vector Information
    private Vector2 position;
    private Vector2 velocity;
    private Vector2 acceleration;
    private float rotation;

    //Size
    private int width, height;
    private float ceiling;

    private float shootFreq = 0.1f;
    private Bullet[] bullets;// hold for optimum performance
    private ParticleEffect sodaburst;



    private Polygon hitbox;

    public Squirrel(int width, int height) {
        this.width = width;
        this.height = height;
        position = new Vector2(-1.5f * width, GameScreen.midPointY);
        velocity = new Vector2(0, 0);
        rotation = 0;
        acceleration = new Vector2(0, -460);
        currentState = SquirrelState.SPAWNING;
        invincTime = 0;
        isInvincible = true;// 나중에 바꿔 무적시간.
        isTransparent = false;
        isRamseyThunder = false;
        thunderTime = 0.0f;
        life = -1;//life

        ceiling = GameScreen.gameHeight - getHeight() / 2.0f;// temporary


        hitbox = new Polygon(new float[]{5, 0, width - 5, 0, width - 5, height - 10, 5, height - 10});
        hitbox.setOrigin(width / 2f, height / 2f);

		/*
        init bullet
		 */
        bullets = new Bullet[20];
        for (int i = 0; i < bullets.length; i++)
            bullets[i] = new Bullet(this);

        runTime = 0;
        sodaburst = AssetHelper.sodaburstPool.obtain();
    }


    public void update(float delta) {
        // constant delta

		/*
				Bullet Update
		 */
        for (Bullet b : bullets)
            b.update(delta);


        if (life >= 0)
            if (!isSPAWNING()) {
                runTime += delta; // used for timing
                animRunTime += delta; // timing of animation

		/*
		SHOOTING MECHANIC
		 */
                if (isRamseyThunder){
                    thunderTime+=delta;
                    shootFreq = 0.02f;
                    if (thunderTime > 5.5f){
                        thunderTime = 0.0f;
                        isRamseyThunder = false;
                        shootFreq = 0.1f;
                    }
                }

                if (isRamseyThunder){
                    if (thunderTime > 0.5f){
                        shoot();
                    }
                } else {
                    shoot();
                }


		/*
		Position constraints
		 */
                if (velocity.y < -150) {
                    velocity.y = -150;
                } // maximum falling speed
                if (position.y > ceiling) {
                    position.y = ceiling;
                    velocity.y = 0;

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

            } else { // spawning animation
                //simulate appearing from left
                if (position.x > 50) {
                    position.x = 50;
                    velocity.x = 0;

                    currentState = SquirrelState.IDLE;
                } else {
                    rotation = 0;
                    velocity.y = 0;
                    velocity.x = 50;
                }
            }

		/*
			invincible
		 */
        if (isInvincible) {
            if (invincTime > 5.0f) {
                isInvincible = false;
                isTransparent = false;
            } else {
                invincTime += delta; // strobe effect
                if ((int) (invincTime * 100) % 6 == 0) {
                    isTransparent = !isTransparent;
                }
            }
        }

		/*
			collision
		 */

        position.add(velocity.cpy().scl(delta));//add velo to position
        hitbox.setPosition(position.x, position.y);
        hitbox.setRotation(rotation);

    }

    private void shoot(){
        if (runTime > shootFreq && isIDLE()) {// shooting speed adjust
            runTime = 0;
            animRunTime = 0;
            currentState = SquirrelState.SHOOTING;
            for (com.lumibottle.gameobjects.Bullets.Bullet b : bullets) {
                if (b.isDEAD()) {
                    if (!isRamseyThunder) {
                        SoundManager.getInstance().play(SoundManager.THRO);
                        b.reset(position.x + getWidth() / 2f, position.y + getHeight() / 2f, 250, 250, rotation, false); // speed
                    }else
                        b.reset(position.x + getWidth() / 2f + MathUtils.random(-30,30), GameScreen.gameHeight,0,-250,rotation,true);

                    break;
                }
            }
            //shoot bullet
        } else if (runTime > shootFreq*2 && isSHOOTING()) {
            runTime = 0;
            currentState = SquirrelState.IDLE;
        }
    }

    public void onClick() {

        //change how respawn is handled later
        if (!isSPAWNING())
            velocity.y = 160; // jump


        //if (life <0 하고 일정 시간이 지나면(UI가 나오면))
        // 재시작?

    }

    public void dead() {



        SoundManager.getInstance().stop(SoundManager.POW);

        FXHelper.getInstance().newFX(getX() - 108 / 2f, getY() - 108 / 2f, FX.QUANTUM_EXPLOSION);
        AssetHelper.sodaburstPool.free((ParticleEffectPool.PooledEffect) sodaburst);
        rotation = 0;
        currentState = SquirrelState.SPAWNING;
        position.set(-5.0f * width, GameScreen.midPointY);
        isInvincible = true;
        isTransparent = true;
        isRamseyThunder = false;
        thunderTime = 0.0f;

        invincTime = 0;
        shootFreq = 0.1f;
        life--;
    }

    public void realDead(){
        SoundManager.getInstance().stop(SoundManager.POW);
        AssetHelper.sodaburstPool.free((ParticleEffectPool.PooledEffect) sodaburst);
        rotation = 0;
        currentState = SquirrelState.SPAWNING;
        position.set(-5.0f * width, GameScreen.midPointY);
        isInvincible = true;
        isTransparent = true;
        isRamseyThunder = false;
        thunderTime = 0.0f;
        invincTime = 0;
        shootFreq = 0.1f;
        life= -1;
    }

	/*
	GETTER and SETTER
	 */

    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getRotation() {
        return rotation;
    }

    public float getAnimRunTime() {
        return animRunTime;
    }

    public float getRunTime() {
        return runTime;
    }


    public Bullet[] getBullets() {
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


    public boolean isSHOOTING() {
        return currentState == SquirrelState.SHOOTING;
    }

    public boolean isIDLE() {
        return currentState == SquirrelState.IDLE;
    }

    public boolean isSPAWNING() {
        return currentState == SquirrelState.SPAWNING;
    }

    public boolean IsInvincible() {
        return isInvincible;
    }

    public boolean IsTransparent() {
        return isTransparent;
    }

    void resetLife() {life = 2;}

    void incrementLife(){life++;}

    void setRamseyThunder(boolean sw){
        isRamseyThunder = sw;
        thunderTime = 0.0f;
    }

    public boolean isRamseyThunder(){return isRamseyThunder;}

    public short getLife() {
            return life;}


}

