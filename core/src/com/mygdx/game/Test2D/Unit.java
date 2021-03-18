package com.mygdx.game.Test2D;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class Unit {

    Vector2 pos= new Vector2();



    void draw(ShapeRenderer renderer){
        renderer.circle(pos.x,pos.y,10);
    }

}
