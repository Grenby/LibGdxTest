package com.mygdx.projects.mazeGen;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Collection;

public class MazeDrawer {

    private Rectangle visibleRegion;
    private Rectangle tmp;
    private Maze maze;

    public void draw(ShapeRenderer renderer){
        final Iterable<Vector2> nodes = maze.getNodes();
        final float []oX = maze.getOX();
        final float []oY = maze.getOY();

        for (Vector2 v:nodes) {
            for (int i = 0; i < oX.length; i++) {
                if (!maze.hasExit(v, i)) {
                    float x = v.x+Math.min(oX[i],oX[i+1]);
                    float y = v.y+Math.min(oY[i],oY[i+1]);
                    float w = Math.abs(oX[i] - oX[i+1]);
                    float h = Math.abs(oY[i] - oY[i+1]);
                    if (visibleRegion.overlaps(tmp.set(x,y,w,h)))
                        renderer.line(v.x + oX[i], v.y + oY[i], v.x + oX[i + 1], v.y + oY[i + 1]);
                }
            }
        }
    }

}
