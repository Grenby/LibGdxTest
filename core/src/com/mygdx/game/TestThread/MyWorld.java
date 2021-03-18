package com.mygdx.game.TestThread;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

class MyWorld {

    private static MyWorld myWorld;
    public World world;

    MyWorld(float delta){
        world=new World(new Vector2(0,-10),true);
        createStaticBody(0,0,10,10);
        createDynamicBody(9.01f,20);
//        ThreadClass.add(() -> {
//            while (true){
//                world.step(delta,4,4);
//                try {
//                    Thread.sleep((long)(delta*1000));
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
    }

    static MyWorld getMyWorld(){
        if (myWorld==null)myWorld=new MyWorld(1);
        return myWorld;
    }

    World getWorld() {
        return world;
    }

    private Body createStaticBody(float x,float y,float w,float h){
        BodyDef bodyDef=new BodyDef();
        bodyDef.type=BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x,y);

        PolygonShape polygonShape =new PolygonShape();
        polygonShape.setAsBox(w,h);

        FixtureDef fixtureDef=new FixtureDef();
        fixtureDef.shape=polygonShape;

        Body body = getWorld().createBody(bodyDef);
        body.createFixture(fixtureDef);
        polygonShape.dispose();
        return body;
    }

    private Body createKinematicBody(float x,float y){
        BodyDef bodyDef=new BodyDef();
        bodyDef.type=BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(x,y);

        CircleShape circleShape=new CircleShape();
        circleShape.setRadius(1);

        FixtureDef fixtureDef=new FixtureDef();
        fixtureDef.shape=circleShape;

        Body body= getWorld().createBody(bodyDef);
        body.createFixture(fixtureDef);
        circleShape.dispose();
        return body;
    }

    private Body createDynamicBody(float x,float y){
        BodyDef bodyDef=new BodyDef();
        bodyDef.type=BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x,y);

        CircleShape circleShape=new CircleShape();
        circleShape.setRadius(1);

        FixtureDef fixtureDef=new FixtureDef();
        fixtureDef.shape=circleShape;

        fixtureDef.density=10;
        fixtureDef.restitution=0.5f;
        fixtureDef.friction=0f;

        Body body= getWorld().createBody(bodyDef);
        body.createFixture(fixtureDef);
        circleShape.dispose();
        return body;
    }


    void step(float delta){
        world.step(delta,4,4);
    }
}
