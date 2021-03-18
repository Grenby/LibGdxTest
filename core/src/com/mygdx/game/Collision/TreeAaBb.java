package com.mygdx.game.Collision;

import java.util.ArrayList;

class TreeAaBb {

    private static class Node{
        boolean isLeaf=false;
        Shape shape;
        AaBb aaBb;
        Node left;
        Node right;
    }

    private Node root;
    private int numNode=0;
    private ArrayList<Shape> intersection;

    TreeAaBb(){

    }

    void add(Shape shape){
        Node n=new Node();
        n.aaBb=shape.getAaBb();
        n.isLeaf=true;
        n.shape=shape;
        if (numNode==0)root=n;
        else{
            numNode++;
            Node k=root;
            while(!k.isLeaf){
                float deltaAreaLeft=AaBb.area(k.left.aaBb,n.aaBb)-k.left.aaBb.area();
                float deltaAreaRight=AaBb.area(k.right.aaBb,n.aaBb)-k.right.aaBb.area();
                k.aaBb.set(k.aaBb,n.aaBb);
                if (deltaAreaLeft<deltaAreaRight)k=k.left;
                else k = k.right;
            }
            Node leaf=new Node();
            leaf.aaBb=k.aaBb;
            leaf.shape=k.shape;
            leaf.isLeaf=true;
            k.left=leaf;
            k.right=n;
            k.isLeaf=false;
            k.shape=null;
            k.aaBb=new AaBb(leaf.aaBb,n.aaBb);
        }
        numNode++;
    }

    private void intersection(float x1,float y1,float x2,float y2,Node n){
        //q++;
        if (n.left!=null&&n.left.aaBb.intersectionSection(x1,y1,x2,y2)){
            if(n.left.isLeaf)intersection.add(n.left.shape);
            else intersection(x1,y1,x2,y2,n.left);
        }
        if (n.right!=null&&n.right.aaBb.intersectionSection(x1,y1,x2,y2)){
            if(n.right.isLeaf)intersection.add(n.right.shape);
            else intersection(x1,y1,x2,y2,n.right);
        }
    }

    ArrayList<Shape> getIntersection(float x1,float y1,float x2,float y2){
        if (numNode==0)return null;
        ArrayList<Shape> list=new ArrayList<>();
        if (numNode==1)list.add(root.shape);
        else if (root.aaBb.intersectionSection(x1,y1,x2,y2)){
            intersection=list;
            intersection(x1, y1, x2, y2, root);
        }
        return list;
    }
}
