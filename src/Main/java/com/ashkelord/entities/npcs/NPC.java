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

    @Override
    public void interact() {
        // Simple Interaction for now - later we can hook this to QuestManager
        System.out.println("Interacting with " + name);
        // We will need to access GameState to get QuestManager, but for now let's just use Game access if possible
        // or just print. The actual quest hook will be added in GameState setup or we'll pass QuestManager here.
        // For the specific assignment, Miri needs to trigger the quest.
        if (name.equals("Miri Kapara")) {
             // Hardcoded for the prototype to start the quest logic
             // Ideally we find the QuestManager from the game instance
             // But Game class doesn't have quest manager getter yet.
             // Accessing via State.getState() might be cleaner if we cast to GameState
             // For now, let's just show a dialogue to prove interaction works 
             // game.getUIManager().getDialogBox().show("Hello from " + name + "!");
        }
    }


    public String getName() {
        return name;
    }
}
