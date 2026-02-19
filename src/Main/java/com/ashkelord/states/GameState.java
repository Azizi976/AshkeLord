package com.ashkelord.states;

import com.ashkelord.entities.creatures.Player;
import com.ashkelord.main.Game;
import com.ashkelord.worlds.World; // ייבוא
import java.awt.Graphics;

public class GameState extends State {

    private Player player;
    private World world;

    public GameState(Game game) {
        super(game);
        // יצירת עולם (טוען מפה או יוצר מפת דמה)
        world = new World(game, "res/worlds/world1.txt");

        // העברת העולם לשחקן כדי שידע איפה הקירות
        player = new Player(game, world, 100, 100);
    }

    @Override
    public void tick() {
        world.tick();
        player.tick();
    }

    @Override
    public void render(Graphics g) {
        world.render(g);
        player.render(g);
    }
}