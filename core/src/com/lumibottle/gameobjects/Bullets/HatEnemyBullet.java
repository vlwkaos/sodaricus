package com.lumibottle.gameobjects.Bullets;

import com.badlogic.gdx.math.Polygon;

/**
 * Created by MG-POW on 2016-07-16.
 */

public class HatEnemyBullet extends EnemyBullet {
    public HatEnemyBullet() {
        super(32, 16, new Polygon(new float[]{4, 4,
                28, 4,
                28, 12,
                4, 12}));
    }
}
