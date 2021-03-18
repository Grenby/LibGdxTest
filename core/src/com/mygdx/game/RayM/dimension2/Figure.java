package com.mygdx.game.RayM.dimension2;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public interface Figure{

    float distance(Vector2 point);
    void render(ShapeRenderer renderer);

}
