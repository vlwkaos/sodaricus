package com.lumibottle.gameobjects.enemies.bosses;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.lumibottle.gameobjects.Bullets.Bullet;
import com.lumibottle.gameobjects.Bullets.EnemyBullet;
import com.lumibottle.gameobjects.FX;
import com.lumibottle.gameobjects.GameEvent;
import com.lumibottle.helper.FXHelper;
import com.lumibottle.helper.SoundManager;
import com.lumibottle.screen.GameScreen;

/**
 * Created by MG on 2016-09-11.
 */
public class TimeBomb extends GameEvent {

    private enum TimeBombState{
        IDLE,TICK,EXPLODE
    }

    private TimeBombState currentState;
    private float timer;
    float dir;
    float speed;


    public TimeBomb() {
        super(20, 20, new Polygon(new float[] {4,0,16,0,16,20,4,20}),0);
        timer = 0.0f;
        dir = 0.0f;
        speed = 120.0f;
        currentState = TimeBombState.IDLE;
    }

    @Override
    public void update(float delta) {
        if (isVISIBLE()) {

            timer += delta;
            switch (currentState){
                case IDLE: if (timer > 2.0f){
                    timer = 0.0f;
                    setVelocity(0,0);
                    currentState = TimeBombState.TICK;
                    setTheta(getTheta()%360);
                } else {
                    speed -= 50.0f*delta;
                    setVelocity(speed*MathUtils.cosDeg(dir), speed*MathUtils.sinDeg(dir));
                    addTheta(delta*720.0f);
                }
                    break;
                case TICK: if (timer > 2.0f){
                    // 초후 폭발
                    currentState = TimeBombState.EXPLODE;
                } else {

                }
                    break;



            }


            //밖으로 못나가
            if (getX() > 240 - getWidth()) {
                setX(240 - getWidth());
                setVelocity(-getdX(), getdY());
                dir = 180-dir;
            }
            if (getX() < 80) {
                setX(80);
                setVelocity(-getdX(), getdY());
                dir = 180-dir;
            }
            if (getY() > GameScreen.gameHeight - getHeight()) {
                setY(GameScreen.gameHeight - getHeight());
                setVelocity(getdX(), -getdY());
                dir = -dir;
            }
            if (getY() < 0) {
                setY(0);
                setVelocity(getdX(), -getdY());
                dir = -dir;
            }

            getPosition().add(getVelocity().cpy().scl(delta));
            getHitbox().setPosition(getX(), getY());
        }
    }


//update 안에서 돌리다가 소환 후 progress handler에서 dead를 콜해야되는 반대인 상황입니다.

    public void reset(float x, float y) {
        dir = MathUtils.random(360);
        super.reset(x+6, y+16, speed*MathUtils.cosDeg(dir), speed*MathUtils.sinDeg(dir), 0);
        timer = 0.0f;
        speed = 120.0f;
        currentState = TimeBombState.IDLE;
    }


    @Override
    public void hit(){
        currentState = TimeBombState.EXPLODE;
        //체력 상관없다
    }

    @Override
    public void dead() {
        super.dead();
        currentState = TimeBombState.IDLE;
        FXHelper.getInstance().newFX(getPrevX(), getPrevY(), Math.max(getWidth(), getHeight()), (short) 5);
        SoundManager.getInstance().play(SoundManager.FIRE);
    }

    @Override
    public void bottleHitsEnemy(Bullet b) {
        FXHelper.getInstance().newFX(b.getX(), b.getY(), FX.SODA_EXPLOSION);

        if (isTICKstate())
            hit();

        b.hit();

    }


    public boolean isTICKstate(){
        return currentState == TimeBombState.TICK; // change draw to animation
    }

    public boolean isEXPLODEstate(){
        return currentState == TimeBombState.EXPLODE;
    }
}
