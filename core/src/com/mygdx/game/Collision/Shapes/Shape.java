package com.mygdx.game.Collision.Shapes;


import com.badlogic.gdx.math.Vector2;

public interface Shape {

    /**
     * @return AaBb form for shape
     */
    AaBb getAaBb();

    Vector2 intersection(float x1,float y1,float x2,float y2);

    boolean contains(float x,float y);

}
