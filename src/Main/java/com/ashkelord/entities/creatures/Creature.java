package com.ashkelord.entities.creatures;

import com.ashkelord.entities.Entity;
import com.ashkelord.tiles.Tile; // וודא שיש לך את הקבצים האלו
import com.ashkelord.worlds.World; // וודא שיש לך את הקבצים האלו

public abstract class Creature extends Entity {

    public static final int DEFAULT_HEALTH = 100;
    public static final float DEFAULT_SPEED = 3.0f;
    public static final int DEFAULT_CREATURE_WIDTH = 64,
            DEFAULT_CREATURE_HEIGHT = 64;

    protected int health;
    protected float speed;
    protected float xMove, yMove;
    protected World world; // חובה כדי לבדוק קירות!

    // הבנאי חייב לקבל World עכשיו
    public Creature(World world, float x, float y, int width, int height) {
        super(x, y, width, height);
        this.world = world;
        health = DEFAULT_HEALTH;
        speed = DEFAULT_SPEED;
        xMove = 0;
        yMove = 0;
    }

    // פונקציית התנועה החכמה (במקום סתם להוסיף X ו-Y)
    public void move() {
        if (!checkEntityCollisions(xMove, 0f))
            moveX();
        if (!checkEntityCollisions(0f, yMove))
            moveY();
    }

    // תנועה בציר X עם בדיקת התנגשות
    public void moveX() {
        if (xMove > 0) { // זז ימינה
            int tx = (int) (x + xMove + bounds.x + bounds.width) / Tile.TILEWIDTH;
            if (!collisionWithTile(tx, (int) (y + bounds.y) / Tile.TILEHEIGHT) &&
                    !collisionWithTile(tx, (int) (y + bounds.y + bounds.height) / Tile.TILEHEIGHT)) {
                x += xMove;
            } else {
                x = tx * Tile.TILEWIDTH - bounds.x - bounds.width - 1; // נצמד לקיר
            }
        } else if (xMove < 0) { // זז שמאלה
            int tx = (int) (x + xMove + bounds.x) / Tile.TILEWIDTH;
            if (!collisionWithTile(tx, (int) (y + bounds.y) / Tile.TILEHEIGHT) &&
                    !collisionWithTile(tx, (int) (y + bounds.y + bounds.height) / Tile.TILEHEIGHT)) {
                x += xMove;
            } else {
                x = tx * Tile.TILEWIDTH + Tile.TILEWIDTH - bounds.x; // נצמד לקיר
            }
        }
    }

    // תנועה בציר Y עם בדיקת התנגשות
    public void moveY() {
        if (yMove < 0) { // זז למעלה
            int ty = (int) (y + yMove + bounds.y) / Tile.TILEHEIGHT;
            if (!collisionWithTile((int) (x + bounds.x) / Tile.TILEWIDTH, ty) &&
                    !collisionWithTile((int) (x + bounds.x + bounds.width) / Tile.TILEWIDTH, ty)) {
                y += yMove;
            } else {
                y = ty * Tile.TILEHEIGHT + Tile.TILEHEIGHT - bounds.y; // נצמד לתקרה
            }
        } else if (yMove > 0) { // זז למטה
            int ty = (int) (y + yMove + bounds.y + bounds.height) / Tile.TILEHEIGHT;
            if (!collisionWithTile((int) (x + bounds.x) / Tile.TILEWIDTH, ty) &&
                    !collisionWithTile((int) (x + bounds.x + bounds.width) / Tile.TILEWIDTH, ty)) {
                y += yMove;
            } else {
                y = ty * Tile.TILEHEIGHT - bounds.y - bounds.height - 1; // נצמד לרצפה
            }
        }
    }

    protected boolean collisionWithTile(int x, int y) {
        return world.getTile(x, y).isSolid();
    }

    // פונקציית עזר לעתיד
    public boolean checkEntityCollisions(float xOffset, float yOffset) {
        return false;
    }

    // Getters & Setters
    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }
}