package com.lumibottle.gameobjects;

import com.badlogic.gdx.math.MathUtils;

/**
 * Created by MG-POW on 2016-03-13.
 */
public class Star extends Scrollable {


	private boolean isBigStar;

	public Star() {
		super(0,0,9,9,0,0);
		reset();

	}

	@Override
	public void update(float delta) {
		super.update(delta);

		if (isOOScreen()){
			reset();
		}
	}


	private void reset(){
		super.reset(240+MathUtils.random(0,15)*20 + MathUtils.random(-5,5));
		setVelocity(MathUtils.random(-100,-70),0);
		if (MathUtils.random(5)==0)
			isBigStar=true;
		else
			isBigStar=false;

	}

	public boolean isBigStar(){
		return isBigStar;
	}

}
