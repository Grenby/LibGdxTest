package com.mygdx.game.searchWay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;


public class Input implements InputProcessor {

    private StartScreen screen;
    private int button;
    private boolean[] keyPress=new boolean[4];


    Input(StartScreen startScreen){
        this.screen=startScreen;
    }


    @Override
    public boolean keyDown(int keycode) {
        switch (keycode){
            case com.badlogic.gdx.Input.Keys.W: {
                keyPress[0]=true;
                screen.move(0,1);
            }break;
            case com.badlogic.gdx.Input.Keys.A: {
                keyPress[3]=true;
                screen.move(-1,0);
            }break;
            case com.badlogic.gdx.Input.Keys.S: {
                keyPress[2]=true;
                screen.move(0,-1);
            }break;
            case com.badlogic.gdx.Input.Keys.D: {
                keyPress[1]=true;
                screen.move(1,0);
            }break;
            case com.badlogic.gdx.Input.Keys.T:screen.search();break;
            case com.badlogic.gdx.Input.Keys.ESCAPE:Gdx.app.exit();break;
            case com.badlogic.gdx.Input.Keys.N:screen.rebuild();break;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode){
            case com.badlogic.gdx.Input.Keys.W:keyPress[0]=false;break;
            case com.badlogic.gdx.Input.Keys.A:keyPress[3]=false;break;
            case com.badlogic.gdx.Input.Keys.S:keyPress[2]=false;break;
            case com.badlogic.gdx.Input.Keys.D:keyPress[1]=false;break;
        }
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        this.button=button;
        screenY= Gdx.graphics.getHeight()-screenY;
        if (button==0)screen.addCell(screenX,screenY);
        else screen.removeCell(screenX,screenY);
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        this.button=0;
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        screenY= Gdx.graphics.getHeight()-screenY;
        if (button==0)screen.addCell(screenX,screenY);
        else screen.removeCell(screenX,screenY);
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {

        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        screen.zoom(amount);
        return false;
    }

    public void act(float delta){
        if (keyPress[0])screen.move(0,1);
        if (keyPress[2])screen.move(0,-1);
        if (keyPress[1])screen.move(1,0);
        if (keyPress[3])screen.move(-1,0);
    }


}
