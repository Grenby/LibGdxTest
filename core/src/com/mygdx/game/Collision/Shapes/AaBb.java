package com.mygdx.game.Collision.Shapes;

import com.badlogic.gdx.math.Vector2;

public class AaBb implements Shape {



    @Override
    public AaBb getAaBb() {
        return this;
    }

    @Override
    public Vector2 intersection(float x1, float y1, float x2, float y2) {
        return null;
    }

    @Override
    public boolean contains(float x, float y) {
        return false;
    }

}
