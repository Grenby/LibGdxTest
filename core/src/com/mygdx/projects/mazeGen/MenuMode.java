package com.mygdx.projects.mazeGen;

import com.badlogic.gdx.*;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.mygdx.projects.Resource;

public class MenuMode extends InputAdapter implements Screen {

    public static int WIDTH = Gdx.graphics.getWidth();
    public static int HEIGHT = Gdx.graphics.getHeight();

    private final Stage stage = new Stage();

    HorizontalGroup horizontalGroup = new HorizontalGroup();
    WidgetGroup widgetGroup = new WidgetGroup();

    DefaultGraph<Integer> graph;

    public MenuMode(){
    }


    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.ESCAPE){
            Gdx.app.exit();
        }
        return super.keyDown(keycode);
    }

    @Override
    public void show() {
        Skin skin = Resource.getUISkin();

        Gdx.input.setInputProcessor(new InputMultiplexer(stage,this));

        TextButton button = new TextButton("Hello", skin,"toggle");
        //button.setBounds(100,100,100,50);
        Label l = new Label("0",skin);
        Slider slider = new Slider(0,100,1,false, skin){
            @Override
            public boolean setValue(float value) {
                boolean set = super.setValue(value);
                l.setText(Integer.toString((int)getValue()));
                return set;
            }
        };

        // slider.setBounds(100,200,100,50);
        stage.addActor(button);
        //area.getPrefHeight();
        horizontalGroup.addActor(new Label("Size: ",skin));
        horizontalGroup.addActor(slider);
        horizontalGroup.addActor(l);
        //Window window = new Window();

        //widgetGroup.addActor(new TextArea("Hello",skin,textFieldStyle));
        //horizontalGroup.rowCenter();
        horizontalGroup.rowAlign(Align.left);
        horizontalGroup.rowCenter();

        horizontalGroup.setPosition(100,200);
        //horizontalGroup.setBounds(100,200,100,50);
        //stage.setDebugAll(true);
        stage.addActor(horizontalGroup);
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
    }

}
