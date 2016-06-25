package com.lumibottle.helper;

import com.lumibottle.gameobjects.FX;

/**
 * Created by MG-POW on 2016-03-18.
 */
public class FXHelper {
	private static FXHelper ourInstance = new FXHelper();

	public static FXHelper getInstance() {
		return ourInstance;
	}


	private FX[] myFXs;

	private FXHelper() {
		myFXs = new FX[20];// max number of FXs to be drawn on screen at the moment
		for (int i=0;i<myFXs.length;i++)
			myFXs[i] = new FX();
	}

	public void newFX(float x, float y, short animNo){
		for (FX f: myFXs){
			if (f.isREADY()) {
				f.reset(x, y, animNo);
				break;
			}
		}

	}
	public FX[] getMyFXs() {
		return myFXs;
	}
}
