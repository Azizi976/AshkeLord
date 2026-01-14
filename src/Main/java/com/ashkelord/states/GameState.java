package Main.java.com.ashkelord.states;

package com.ashkelord.states;

import com.ashkelord.entities.creatures.Player;
import com.ashkelord.main.Game;
import java.awt.Graphics;

public class GameState extends State {

    private Player player;

    public GameState(Game game) {
        super(game);
        // יצירת השחקן בנקודה 100,100
        player = new Player(game, 100, 100);
    }

    @Override
    public void tick() {
        player.tick();
    }

    @Override
    public void render(Graphics g) {
        player.render(g);
    }
}
