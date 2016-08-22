package com.lumibottle.gameobjects;

import com.badlogic.gdx.math.MathUtils;
import com.lumibottle.screen.GameScreen;

/**
 * Created by MG-POW on 2016-03-13.
 */
public class Star extends GameEvent {

    private boolean isBigStar;

    public Star() {
        super(9, 9, null, 0);
        reset();

    }

    @Override
    public void update(float delta) {
        if (isVISIBLE()) {
            getPosition().add(getVelocity().cpy().scl(delta));

            if (isOutOfScreen(true, false, true, true)) {
                reset();
            }
        }
    }


    private void reset() {
        super.reset(240 + MathUtils.random(10) * 25 + MathUtils.random(-5, 5), MathUtils.random(GameScreen.gameHeight), MathUtils.random(-100, -70), 0, 0);
        isBigStar = MathUtils.random(5) == 0;

    }

    public boolean isBigStar() {
        return isBigStar;
    }

}
