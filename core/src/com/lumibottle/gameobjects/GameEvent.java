package com.lumibottle.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.lumibottle.gameobjects.Bullets.Bullet;
import com.lumibottle.helper.FXHelper;
import com.lumibottle.helper.ScoreHelper;
import com.lumibottle.helper.SoundManager;
import com.lumibottle.screen.GameScreen;

/**
 * OOP
 * Basically when an object is out of the screen, it is moved else where untouchable and stops moving
 * until reset() is called which will redeploy our objects on the screen
 */
public abstract class GameEvent {

    private enum EventState {
        DEAD, VISIBLE
    }//DEAD state notifies that it went out of screen and is now hit to be reset

    //runtime for enemy
    private Vector2 prev_pos;
    private Vector2 position;
    private Vector2 velocity;
    private EventState currentState;

    // Heatlh
    private int maxhp;
    private int hitpoint;

    private int width, height; // mostly for rendering...?
    private float theta;

    private Polygon hitbox;

    public GameEvent(int width, int height, Polygon hitbox, int hp) {
        prev_pos = new Vector2(-255, -255);
        position = new Vector2(-255, -255);
        velocity = new Vector2(0, 0);
        this.width = width;
        this.height = height;
        this.theta = 0;
        currentState = EventState.DEAD;
        this.hitbox = hitbox;
        if (this.hitbox != null)
            this.hitbox.setPosition(-255,-255);
        this.maxhp = hp;
        this.hitpoint = hp;

    }


    /*
    BASIC PROTOTYPE : only update position when it's on visible state.
                      set to hit state when Out of screen.
                      set to hit state when hit by bullet.
                      visible state starts when reset.

                      set hitbox position at the last part
     */
    public abstract void update(float delta);


    //If using particle, You should obtain particle here by overriding this method.
    public void reset(float x, float y, float dx, float dy, float theta) {
        this.position.set(x, y);
        this.velocity.set(dx, dy);
        this.theta = theta;
        this.hitpoint = this.maxhp;
        this.currentState = EventState.VISIBLE;
    }// re deploy, set to visible

    public boolean isOutOfScreen(boolean left, boolean right, boolean top, boolean bottom) {
        //was visible but is out of screen, so move it else where hit to be re deployed
        if (left)
            return (position.x + width < 0);
        if (right)
            return position.x > 240;
        if (top)
            return position.y > (GameScreen.gameHeight);
        if (bottom)
            return (position.y + height) < 0;
        return false;
    }


    public void hit() {
        if (hitpoint == 1) {
            dead();
        } else if (hitpoint > 1){
            ScoreHelper.getInstance().incrementScore(10);
            hitpoint--;
        }
    }

    // if using particle, free particle here
    public void dead() {
        prev_pos.set(getPosition());
        FXHelper.getInstance().newFX(getPrevX(), getPrevY(), Math.max(getWidth(), getHeight()), FX.POOF);// puff
        hitbox.setPosition(300,300);
        position.set(-255, -255);
        currentState = EventState.DEAD; // hit to deploy

    }

    public void silentDead(){
        prev_pos.set(getPosition());
        hitbox.setPosition(300,300);
        position.set(-255, -255);
        currentState = EventState.DEAD; // hit to deploy
    }



    /*
    collision
     */
    protected void collide(Squirrel squirrel) {
        if (isVISIBLE()) {
            for (Bullet b : squirrel.getBullets()) {
                    if (b.isVISIBLE() && Intersector.overlapConvexPolygons(b.getHitbox(), hitbox)) {
                        //When bullet hits Event
                        bottleHitsEnemy(b);
                        Gdx.app.log("GameEvent","bottle hits"+this.getClass().getSimpleName());
                        break;
                    }
            }
            //When squirrel is hit by event

            if (!squirrel.IsInvincible()) {
                if (Intersector.overlapConvexPolygons(squirrel.getHitbox(), hitbox)) {
                    enemyHitsSquirrel(squirrel);
                }
            }
        }
    }

    public void enemyHitsSquirrel(Squirrel squirrel) {
        Gdx.app.log("squirrel is hit by: ", this.getClass().getSimpleName());
        squirrel.dead();
    }

    public void bottleHitsEnemy(Bullet b) {
        FXHelper.getInstance().newFX(b.getX(), b.getY(), FX.SODA_EXPLOSION);
        hit();
        b.hit();
    }


    /*
        GETTER & SETTER
     */
    public boolean isDEAD() {
        return currentState == EventState.DEAD;
    }

    public boolean isVISIBLE() {
        return currentState == EventState.VISIBLE;
    }

    public void setHitbox(float[] hitbox) {
        if (this.hitbox != null)
            this.hitbox.setVertices(hitbox);
        else
            this.hitbox = new Polygon(hitbox);
    }

    //it will use coordinate from main actor, so ..
    public void setVelocity(float x, float y) {
        this.velocity.set(x, y);
    }

    public void setTheta(float theta) {
        this.theta = theta;
    }

    public void setX(float x) {
        this.position.x = x;
    }

    public void setY(float y) {
        this.position.y = y;
    }

    public void setWidth(int w) {
        this.width = w;
    }

    public void setHeight(int h) {
        this.height = h;
    }

    public void setMaxhp(int hp) {
        this.maxhp = hp;
    }

    public void addTheta(float theta) {
        this.theta += theta;
    }

    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public float getTheta() {
        return theta;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Vector2 getPosition() {
        return position;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public Polygon getHitbox() {
        return hitbox;
    }

    public float getdX() {
        return velocity.x;
    }

    public float getdY() {
        return velocity.y;
    }

    public float getPrevX() {
        return prev_pos.x;
    } // adding this can improve some of other codes.. particle?

    public float getPrevY() {
        return prev_pos.y;
    }

}
