package com.lumibottle.gameobjects.enemies.bosses;


import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.lumibottle.gameobjects.GameEvent;
import com.lumibottle.screen.GameScreen;


/**
 * Created by MG-POW on 2016-07-10.
 */

public class PangBoss extends GameEvent {

    private int generation;
    private float speed;
    private  Vector2 prev_pos;

    private boolean fullyVisible;

    public PangBoss() {
        super(64, 64, new Polygon(new float[]{0,0,0,64,64,64,64,0}), 4);
        generation=1;
        speed=70;
        fullyVisible = false;
    }

    @Override
    public void update(float delta) {
        if (isVISIBLE()) {
            addTheta(delta);

            if (!fullyVisible){
                if  (getX()<240-getWidth()){
                    setX(240-getWidth());
                    float deg = MathUtils.random(0,360);
                    setVelocity(speed*MathUtils.cosDeg(deg),speed*MathUtils.sinDeg(deg));
                    fullyVisible=true;
                } else {
                    setVelocity(-50,0);
                }
                getPosition().add(getVelocity().cpy().scl(delta));
                getHitbox().setPosition(getX(), getY());
            } else {
                prev_pos = getPosition();
                getPosition().add(getVelocity().cpy().scl(delta));
                getHitbox().setPosition(getX(), getY());
                //change velocity first,
                if (getX()>240-getWidth()){
                    setX(240-getWidth());
                    setVelocity(-getdX(), getdY());
                }
                if (getX()<0){
                    setX(0);
                    setVelocity(-getdX(), getdY());
                }
                if (getY()> GameScreen.gameHeight-getHeight()) {
                    setY(GameScreen.gameHeight-getHeight());
                    setVelocity(getdX(), -getdY());
                }
                if (getY()< 0) {
                    setY(0);
                    setVelocity(getdX(), -getdY());
                }


            }


        }
    }


    public void reset(float x,float y,int n){
        float deg = MathUtils.random(0,360);
        super.reset(x,y,speed*MathUtils.cosDeg(deg),speed*MathUtils.sinDeg(deg),deg);

        generation = n;
        setWidth(64/n);
        setHeight(64/n);
        setHitbox(new float[]{0,0,0,getHeight()/n,getWidth()/n,getHeight()/n,getWidth()/n,0});

        if (generation == 1)
            fullyVisible = false;
        else
            fullyVisible = true;

        //0,0에서

    }


}
