package com.mygdx.projects.z1;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Entity extends Actor {

    Circle me = new Circle(1,1,30);
    Vector2 pos = new Vector2(1,1);
    Vector2 view = new Vector2(0,1);

    public void update(float delta){


        me.x = pos.x;
        me.y = pos.y;
    }

    @Override
    public void drawDebug(ShapeRenderer renderer){
        renderer.circle(me.x,me.y,me.radius);
        renderer.line(pos.x,pos.y,pos.x+me.radius*view.x,pos.y+me.radius*view.y);
    }

}
