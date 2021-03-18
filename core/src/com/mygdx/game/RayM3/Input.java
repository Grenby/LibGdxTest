package com.mygdx.game.RayM3;

import com.badlogic.gdx.InputProcessor;

public class Input implements InputProcessor {

    RayM3Screen screen;

    Input(RayM3Screen screen){
        this.screen=screen;
    }

    @Override
    public boolean keyDown(int keycode) {
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
        screen.choose(screenX,screenY,button);
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
        screen.moved(screenX,screenY);
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
