package com.example.tondeuse_spring_batch.entities;

public class Pelouse {
    private int maxX;
    private int maxY;

    public Pelouse(int maxX, int maxY) {
        this.maxX = maxX;
        this.maxY = maxY;
    }

    public int getMaxX() {
        return maxX;
    }

    public int getMaxY() {
        return maxY;
    }
}
