package com.ashkelord.entities;

import java.awt.Graphics;
import java.awt.Rectangle; // הוספתי את זה - חשוב מאוד!

public abstract class Entity {

    protected float x, y;
    protected int width, height;
    protected Rectangle bounds; // ה-Hitbox (קופסת ההתנגשות)
    protected boolean active = true;

    public Entity(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        // ברירת מחדל: כל התמונה היא גוף
        bounds = new Rectangle(0, 0, width, height);
    }

    public abstract void tick();

    public abstract void render(Graphics g);
    
    public void interact() {
        // Default: do nothing
    }

    // Getters & Setters
    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Rectangle getCollisionBounds(float xOffset, float yOffset) {
        return new Rectangle((int) (x + bounds.x + xOffset), (int) (y + bounds.y + yOffset), bounds.width,
                bounds.height);
    }
    
    public boolean isActive() {
        return active;
    }
    
    public void setActive(boolean active) {
        this.active = active;
    }
}