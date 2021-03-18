package com.mygdx.game.RayM3;

import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

public class Camera3 {

    float angle=67;
    float width,heigth;
    boolean readyUpdate=false;
    Vector3 direction;
    Vector3 pos;

    Camera3(float width,float height){
        this.width=width;
        this.heigth=height;
    }

    Camera3(float width,float height,float angle){
        this.width=width;
        this.heigth=height;
        this.angle=angle;
    }


    void update(){
        readyUpdate=true;
    }



}
