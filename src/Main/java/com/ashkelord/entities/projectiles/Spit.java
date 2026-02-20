package com.ashkelord.entities.projectiles;

import java.awt.Graphics;
import java.awt.Rectangle;

import com.ashkelord.entities.creatures.Creature;
import com.ashkelord.gfx.Assets;
import com.ashkelord.worlds.World;
import com.ashkelord.tiles.Tile;

public class Spit extends Creature {

    private float floatX, floatY; // Precise coordinates
    private float velX, velY;
    private long creationTime;
    private static final long LIFETIME = 1000; // 1 second

    private Creature owner; // Who fired this?

    public Spit(World world, float x, float y, int dir) {
        super(world, x, y, 24, 24); // Larger visual size
        
        floatX = x;
        floatY = y;
        
        float speed = 6.0f;
        
        // 0=down, 1=up, 2=left, 3=right
        if (dir == 0) { velX = 0; velY = speed; }
        else if (dir == 1) { velX = 0; velY = -speed; }
        else if (dir == 2) { velX = -speed; velY = 0; }
        else if (dir == 3) { velX = speed; velY = 0; }
        
        // Hit box
        bounds.x = 6;
        bounds.y = 6;
        bounds.width = 12;
        bounds.height = 12;
        
        creationTime = System.currentTimeMillis();
    }
    
    public void setOwner(Creature owner) {
        this.owner = owner;
    }

    @Override
    public boolean checkEntityCollisions(float xOffset, float yOffset) {
        for (com.ashkelord.entities.Entity e : world.getEntityManager().getEntities()) {
            if (e.equals(this) || e.equals(owner))
                continue;
            if (e.getCollisionBounds(0f, 0f).intersects(getCollisionBounds(xOffset, yOffset))) {
                if(e instanceof com.ashkelord.entities.creatures.Creature){
                     ((com.ashkelord.entities.creatures.Creature)e).hurt(5); // 5 Damage
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public void tick() {
        if (System.currentTimeMillis() - creationTime > LIFETIME) {
            active = false;
            return;
        }

        // Move
        move();
    }
    
    @Override
    public void move() {
        // Custom simple movement with collision check
        // If collision, destroy
        if (!checkEntityCollisions(velX, 0f)) {
            moveX();
        } else {
            active = false;
        }
        
        if (!checkEntityCollisions(0f, velY)) {
            moveY();
        } else {
            active = false;
        }
    }
    
    @Override
    public void moveX() {
        if (velX > 0) { // Right
            int tx = (int) (x + velX + bounds.x + bounds.width) / Tile.TILEWIDTH;
            if (!collisionWithTile(tx, (int) (y + bounds.y) / Tile.TILEHEIGHT) &&
                    !collisionWithTile(tx, (int) (y + bounds.y + bounds.height) / Tile.TILEHEIGHT)) {
                x += velX;
            } else {
                active = false; // Hit wall
            }
        } else if (velX < 0) { // Left
            int tx = (int) (x + velX + bounds.x) / Tile.TILEWIDTH;
            if (!collisionWithTile(tx, (int) (y + bounds.y) / Tile.TILEHEIGHT) &&
                    !collisionWithTile(tx, (int) (y + bounds.y + bounds.height) / Tile.TILEHEIGHT)) {
                x += velX;
            } else {
                active = false; // Hit wall
            }
        }
    }

    @Override
    public void moveY() {
        if (velY < 0) { // Up
            int ty = (int) (y + velY + bounds.y) / Tile.TILEHEIGHT;
            if (!collisionWithTile((int) (x + bounds.x) / Tile.TILEWIDTH, ty) &&
                    !collisionWithTile((int) (x + bounds.x + bounds.width) / Tile.TILEWIDTH, ty)) {
                y += velY;
            } else {
                active = false; // Hit wall
            }
        } else if (velY > 0) { // Down
            int ty = (int) (y + velY + bounds.y + bounds.height) / Tile.TILEHEIGHT;
            if (!collisionWithTile((int) (x + bounds.x) / Tile.TILEWIDTH, ty) &&
                    !collisionWithTile((int) (x + bounds.x + bounds.width) / Tile.TILEWIDTH, ty)) {
                y += velY;
            } else {
                active = false; // Hit wall
            }
        }
    }

    @Override
    public void render(Graphics g) {
        if (Assets.spit_projectile != null)
             g.drawImage(Assets.spit_projectile, (int) (x - world.getGame().getGameCamera().getxOffset()), 
                     (int) (y - world.getGame().getGameCamera().getyOffset()), width, height, null);
        else 
             g.fillRect((int) (x - world.getGame().getGameCamera().getxOffset()), 
                     (int) (y - world.getGame().getGameCamera().getyOffset()), width, height);
    }
}
