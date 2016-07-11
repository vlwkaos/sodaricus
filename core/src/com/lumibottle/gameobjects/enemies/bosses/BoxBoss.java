package com.lumibottle.gameobjects.enemies.bosses;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.lumibottle.gameobjects.GameEvent;
import com.lumibottle.gameobjects.Squirrel;
import com.lumibottle.helper.AssetHelper;
import com.lumibottle.screen.GameScreen;

/**
 * Created by MG-POW on 2016-07-10.
 */


public class BoxBoss extends GameEvent {

    private float runTime;
    private float offsetHeight;

    private Squirrel mySquirrel;

    public BoxBoss(Squirrel s) {
        super((int) (GameScreen.gameHeight/5), (int) (GameScreen.gameHeight/5), new Polygon(new float[]{0,0,(GameScreen.gameHeight/5),0, (GameScreen.gameHeight/5),(GameScreen.gameHeight/5),0,(GameScreen.gameHeight/5)}), 0);
        //dynamic size according to scren height
        runTime=0;
        mySquirrel = s;

        offsetHeight = getHeight()-s.getHeight();
        offsetHeight/=2.0f;

    }


    /*
        Follows squirrel, after n seconds, it turns red and shoots a block

     */
    @Override
    public void update(float delta) {
        if (isVISIBLE()) {
            runTime += delta;

            if (getX()<240-getWidth()){
                if ((getY()+offsetHeight)-mySquirrel.getY()>1){ // box is upper
                    setVelocity(0,-50);
                } else if ((getY()+offsetHeight)-mySquirrel.getY()<-1){ // box is under
                    setVelocity(0,50);
                } else
                setVelocity(0,0);
            } else {
                setVelocity(-50,0);
            }

            if (getY()<0)
                setY(0);

            if (getY()+getHeight()>GameScreen.gameHeight)
                setY(GameScreen.gameHeight-getHeight());

            getPosition().add(getVelocity().cpy().scl(delta));
            getHitbox().setPosition(getX(), getY());

        }
    }

    public void reset() {
        super.reset(240, GameScreen.gameHeight/2.0f - getHeight()/2.0f, 0, 0, 0);
    }

    /*
        don't call collide function for this one
     */
}
