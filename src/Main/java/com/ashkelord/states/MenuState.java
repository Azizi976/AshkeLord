package com.ashkelord.states;

import com.ashkelord.main.Game;
import java.awt.Graphics;
import java.awt.Color;

public class MenuState extends State {

    public MenuState(Game game) {
        super(game);
    }

    @Override
    public void tick() {
        // לוגיקה של תפריט
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 800, 600);
        g.setColor(Color.WHITE);
        g.drawString("MENU - Press Enter", 100, 100);
    }
}