package com.mygdx.game.Test2D;

public class Wall {

    private static StringBuilder builder = new StringBuilder(12*3+20);

    float [] vertices;

    Wall(float [] vertices){
        this.vertices=vertices;
    }
    Wall(){
        this.vertices=new float [12];
    }



    @Override
    public String toString() {
        StringBuilder builder = Wall.builder;
        builder.append(" walls.add(new Wall(new float[]{");
        for (int i=0;i<vertices.length;i++){
            builder.append(vertices[i]);
            if (i<vertices.length-1)builder.append("f,");
        }
        builder.append("f}))");
        String s = builder.toString();
        builder.setLength(0);
        return s;
    }
}
