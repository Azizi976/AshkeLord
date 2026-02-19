package com.ashkelord.ui;

import com.ashkelord.entities.creatures.Player;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class HUD {

    private Font font;

    public HUD() {
        font = new Font("SansSerif", Font.BOLD, 20);
    }

    public void render(Graphics g, Player player) {
        g.setFont(font);

        // Street Creds (White/Gold)
        drawTextWithOutline(g, "StreetCreds: " + player.getStreetCreds(), 20, 30, new Color(0xFF, 0xD7, 0x00),
                Color.BLACK);

        // Nervim (Red)
        drawTextWithOutline(g, "Nervim: " + player.getNervim(), 20, 60, new Color(0xFF, 0x40, 0x40), Color.BLACK);
    }

    private void drawTextWithOutline(Graphics g, String text, int x, int y, Color c, Color outline) {
        g.setColor(outline);
        g.drawString(text, x + 1, y + 1);
        g.drawString(text, x - 1, y - 1);
        g.drawString(text, x + 1, y - 1);
        g.drawString(text, x - 1, y + 1);
        g.setColor(c);
        g.drawString(text, x, y);
    }
}
