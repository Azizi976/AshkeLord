package com.ashkelord.states;

import com.ashkelord.entities.creatures.Car;
import com.ashkelord.entities.creatures.Player;
import com.ashkelord.entities.creatures.SocialNPC;
import com.ashkelord.entities.npcs.NPC;
import com.ashkelord.gfx.Assets;
import com.ashkelord.main.Game;
import com.ashkelord.worlds.World;
import java.awt.Graphics;

import com.ashkelord.ui.HUD;

public class GameState extends State {

    private World world;
    private Player player;
    private HUD hud;

    public GameState(Game game) {
        super(game);

        world = new World(game, "/maps/world1.txt");
        hud = new HUD();

        // Player
        player = new Player(game, world,
                world.getSpawnX() * 64, world.getSpawnY() * 64);
        world.getEntityManager().addEntity(player);

        // Miri the Shawarma Lady (Static NPC)
        NPC shawarmaGuy = new NPC(game, world, 15 * 64, 16 * 64,
                "Miri Kapara", Assets.player_walk[0][0]);
        world.getEntityManager().addEntity(shawarmaGuy);

        // Cars on roads (spawning at road coordinates)
        // Horizontal road is at Y=10 and Y=20 roughly? No, let's check map.
        // Map has roads at x=10, y=10 etc.
        // Let's spawn a horizontal white car
        Car car1 = new Car(world, 2 * 64, 11 * 64, 0); // White
        world.getEntityManager().addEntity(car1);

        Car car2 = new Car(world, 20 * 64, 12 * 64, 1); // Red
        world.getEntityManager().addEntity(car2);

        // Vertical cars
        Car car3 = new Car(world, 11 * 64, 2 * 64, 0); // White
        world.getEntityManager().addEntity(car3);

        Car car4 = new Car(world, 12 * 64, 25 * 64, 1); // Red
        world.getEntityManager().addEntity(car4);

        // Social NPCs (Savtas and Kids) wandering the neighborhood
        world.getEntityManager().addEntity(new SocialNPC(world, 14 * 64, 14 * 64, 0)); // Savta
        world.getEntityManager().addEntity(new SocialNPC(world, 18 * 64, 15 * 64, 1)); // Kid
        world.getEntityManager().addEntity(new SocialNPC(world, 8 * 64, 18 * 64, 0)); // Savta
        world.getEntityManager().addEntity(new SocialNPC(world, 22 * 64, 8 * 64, 1)); // Kid
        world.getEntityManager().addEntity(new SocialNPC(world, 25 * 64, 22 * 64, 0)); // Savta

        game.getUIManager().getDialogBox().show("Welcome to Ashkelon! Watch out for traffic!");
    }

    @Override
    public void tick() {
        world.tick();
    }

    @Override
    public void render(Graphics g) {
        world.render(g);
        if (game.getUIManager() != null) {
            game.getUIManager().render(g);
        }
        if (hud != null && player != null) {
            hud.render(g, player);
        }
    }

    public World getWorld() {
        return world;
    }
}