package com.mygdx.projects.Utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class FPSui extends Label {

    private final FPSCounter counter = new FPSCounter();
    private float fps = counter.getFps();

    public FPSui(Skin skin) {
        super(Float.toString(0), skin);
    }

    public FPSui(Skin skin, String styleName) {
        super(Float.toString(0), skin, styleName);
    }

    public FPSui(Skin skin, String fontName, Color color) {
        super(Float.toString(0), skin, fontName, color);
    }

    public FPSui(Skin skin, String fontName, String colorName) {
        super(Float.toString(0), skin, fontName, colorName);
    }

    public FPSui(LabelStyle style) {
        super(Float.toString(0), style);
    }

    public float getFps() {
        return counter.getFps();
    }

    public int getMaxStep() {
        return counter.getMaxStep();
    }

    public void setMaxStep(int maxStep) {
        counter.setMaxStep(maxStep);
    }

    @Override
    public void act(float delta) {
        counter.update(delta);
        float newFPS = counter.getFps();
        if (newFPS!=fps){
            fps = newFPS;
            setText("fps: " + fps);
        }
        super.act(delta);
    }
}
