package com.mygdx.game.RayM.dimension2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;

public class Input implements InputProcessor {

    private RayMScreen1 screen;

    Input(RayMScreen1 screen){
        this.screen=screen;
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode){
            case com.badlogic.gdx.Input.Keys.ESCAPE:{
                Gdx.app.exit();
                break;
            }
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        screen.move(screenX,screenY);
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        screen.setPoint(screenX,screenY);
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
