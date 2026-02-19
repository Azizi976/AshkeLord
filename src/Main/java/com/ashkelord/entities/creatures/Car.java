package com.ashkelord.entities.creatures;

import com.ashkelord.gfx.Assets;
import com.ashkelord.tiles.Tile;
import com.ashkelord.worlds.World;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Car extends Creature {

    private int colorType; // 0=White, 1=Red
    private int direction; // 0=Down, 1=Up, 2=Left, 3=Right
    private Random random;

    public Car(World world, float x, float y, int colorType) {
        super(world, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);
        this.colorType = colorType;
        this.random = new Random();
        this.speed = 4.0f; // Faster than player
        this.direction = random.nextInt(4);

        // Smaller hitbox for car to fit on road
        bounds.x = 8;
        bounds.y = 8;
        bounds.width = 48;
        bounds.height = 48;
    }

    @Override
    public void tick() {
        move();

        // If stuck or random chance at intersection, change direction
        if (xMove == 0 && yMove == 0) {
            changeDirection();
        } else if (random.nextInt(100) == 0) { // 1% chance to turn at intersections
            changeDirection();
        }
    }

    private void changeDirection() {
        // Try to pick a valid direction (one that isn't blocked)
        int attempts = 0;
        int newDir;
        do {
            newDir = random.nextInt(4);
            attempts++;
        } while (isBlocked(newDir) && attempts < 10);
        direction = newDir;
    }

    private boolean isBlocked(int dir) {
        // Simple check just ahead
        int tx = (int) x / Tile.TILEWIDTH;
        int ty = (int) y / Tile.TILEHEIGHT;
        if (dir == 0)
            ty++;
        if (dir == 1)
            ty--;
        if (dir == 2)
            tx--;
        if (dir == 3)
            tx++;
        return collisionWithTile(tx, ty);
    }

    @Override
    public void move() {
        xMove = 0;
        yMove = 0;

        if (direction == 0)
            yMove = speed;
        if (direction == 1)
            yMove = -speed;
        if (direction == 2)
            xMove = -speed;
        if (direction == 3)
            xMove = speed;

        // Check if map bounds reached to wrap or bounce?
        // For now just bounce/turn via collision logic
        super.move();
    }

    @Override
    public void render(Graphics g) {
        BufferedImage texture = Assets.cars[colorType][direction];
        g.drawImage(texture, (int) (x - world.getGame().getGameCamera().getxOffset()),
                (int) (y - world.getGame().getGameCamera().getyOffset()), width, height, null);
    }

    @Override
    protected boolean collisionWithTile(int x, int y) {
        // Cars only drive on Road (ID 3)
        if (x < 0 || x >= world.getWidth() || y < 0 || y >= world.getHeight())
            return true;
        return world.getTile(x, y).getId() != 3;
    }
}
