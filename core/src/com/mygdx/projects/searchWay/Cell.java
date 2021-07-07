package com.mygdx.projects.searchWay;

import com.badlogic.gdx.graphics.Color;

public class Cell {

    public static int averagePrice=10;

    private Color color=Color.BLACK;
    private int x;
    private int y;
    private boolean free=true;
    private int price=averagePrice;


    Cell(int x, int y){
        this.x=x;
        this.y=y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isFree() {
        return free;
    }

    public void setFree(boolean free) {
        this.free = free;
    }

    public Color getColor() {
        return color;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public int hashCode() {
        return getHash(x,y);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Cell)return x==((Cell) obj).getX()&&y==((Cell) obj).getY();
        else return false;
    }

    @Override
    public String toString() {
        return "x="+x+" y="+y;
    }

    public static int getHash(int x, int y){
        int sum=x+y;
        return sum*(sum+1)+x;
    }

}
