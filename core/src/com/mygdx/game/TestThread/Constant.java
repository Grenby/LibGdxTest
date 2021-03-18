package com.mygdx.game.TestThread;

import com.badlogic.gdx.Gdx;

abstract class Constant {

    private static float l=100;
    private static int windowW=Gdx.graphics.getWidth();
    private static int windowH=Gdx.graphics.getHeight();
    private static float cameraW=l;
    private static float cameraH=cameraW*windowH/windowW;

    public static float getL() {
        return l;
    }

    public static int getWindowW() {
        return windowW;
    }

    public static int getWindowH() {
        return windowH;
    }

    public static float getCameraW() {
        return cameraW;
    }

    public static float getCameraH() {
        return cameraH;
    }

    public static float pixelToMeter(int x){
        return x*cameraW/windowW;
    }

}
