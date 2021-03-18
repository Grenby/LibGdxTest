package com.mygdx.game.RayM.dimension2;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class Circle implements Figure {
    private Vector2 position;
    private float r;

    Circle(Vector2 position,float r){
        this.r=r;
        this.position=position;
    }

    @Override
    public float distance(Vector2 point) {
        float s=Vector2.dst(point.x,point.y,position.x,position.y);
        return s-r;
    }

    @Override
    public void render(ShapeRenderer renderer) {
        renderer.circle(position.x,position.y,r);
    }
}
