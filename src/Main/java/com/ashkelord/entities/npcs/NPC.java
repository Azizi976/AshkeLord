package com.ashkelord.entities.npcs;

import com.ashkelord.entities.creatures.Creature;
import com.ashkelord.gfx.Assets;
import com.ashkelord.main.Game;
import com.ashkelord.worlds.World;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * A basic NPC that extends Creature.
 * Currently stationary — can be extended for patrol routes, dialogue, etc.
 */
public class NPC extends Creature {

    private Game game;
    private BufferedImage texture;
    private String name;

    public NPC(Game game, World world, float x, float y, String name, BufferedImage texture) {
        super(world, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);
        this.game = game;
        this.name = name;
        this.texture = texture;

        // Hitbox similar to player
        bounds.x = 16;
        bounds.y = 32;
        bounds.width = 32;
        bounds.height = 32;
    }

    @Override
    public void tick() {
        // Stationary NPC — future: AI patrol, idle animations, dialogue triggers
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(texture,
                (int) (x - game.getGameCamera().getxOffset()),
                (int) (y - game.getGameCamera().getyOffset()),
                width, height, null);
    }

    public String getName() {
        return name;
    }
}
