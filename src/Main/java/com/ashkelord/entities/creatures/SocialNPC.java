package com.ashkelord.entities.creatures;

import com.ashkelord.gfx.Animation;
import com.ashkelord.gfx.Assets;
import com.ashkelord.worlds.World;
import java.awt.Graphics;
import java.util.Random;

public class SocialNPC extends Creature {

    private int type; // 0=Savta, 1=Kid
    private Animation animDown;
    private Random random;
    private int direction = 4; // 0-3 moves, 4 idle

    public SocialNPC(World world, float x, float y, int type) {
        super(world, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);
        this.type = type;
        this.random = new Random();
        this.speed = 1.0f;
        if (type == 1)
            this.speed = 1.5f;

        bounds.x = 20;
        bounds.y = 40;
        bounds.width = 24;
        bounds.height = 24;

        if (type == 0)
            animDown = new Animation(300, Assets.savta_walk[0]);
        else
            animDown = new Animation(200, Assets.kid_walk[0]);
    }

    @Override
    public void tick() {
        animDown.tick();

        // 2% chance to change direction/state per tick
        if (random.nextInt(50) == 0) {
            direction = random.nextInt(6); // 0-3 move, 4-5 idle
        }

        xMove = 0;
        yMove = 0;

        if (direction == 0)
            yMove = speed; // Down
        if (direction == 1)
            yMove = -speed; // Up
        if (direction == 2)
            xMove = -speed; // Left
        if (direction == 3)
            xMove = speed; // Right

        move();
    }

    @Override
    public void render(Graphics g) {
        // Since we only have 'Down' sprites for now, we use them for all directions
        // But we can pause animation if idle
        if (xMove == 0 && yMove == 0) {
            animDown.reset(); // Stand still
        }

        g.drawImage(animDown.getCurrentFrame(),
                (int) (x - world.getGame().getGameCamera().getxOffset()),
                (int) (y - world.getGame().getGameCamera().getyOffset()),
                width, height, null);
    }

    @Override
    protected boolean collisionWithTile(int x, int y) {
        // Pedestrians shouldn't walk on roads (ID 3 or 16)
        if (x < 0 || x >= world.getWidth() || y < 0 || y >= world.getHeight())
            return true;

        int id = world.getTile(x, y).getId();
        if (id == 3 || id == 16 || id == 17)
            return true; // Avoid roads

        return super.collisionWithTile(x, y);
    }
}
