package com.ashkelord.minigames;

import com.ashkelord.gfx.Renderer;
import com.ashkelord.main.Game;
import com.ashkelord.input.KeyManager;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

/**
 * Skeleton Sheshbesh (Backgammon) mini-game.
 * Press Space to roll dice, Arrow keys to pick/place pieces.
 * Press Escape to forfeit and return to game.
 */
public class SheshbeshGame extends MiniGame {

    private int[] dice = new int[2];
    private boolean rolled = false;
    private int turnCount = 0;
    private static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 28);
    private static final Font INFO_FONT = new Font("Arial", Font.PLAIN, 18);

    public SheshbeshGame(Game game) {
        super(game);
        onStart();
    }

    @Override
    protected void onStart() {
        turnCount = 0;
        rolled = false;
    }

    @Override
    public void tick() {
        KeyManager km = game.getKeyManager();

        // Escape to forfeit
        if (km.isEscape()) {
            finish(false);
            return;
        }

        // Space to roll dice
        if (km.isSpaceJustPressed()) {
            dice[0] = (int) (Math.random() * 6) + 1;
            dice[1] = (int) (Math.random() * 6) + 1;
            rolled = true;
            turnCount++;

            // Auto-finish after 10 turns (placeholder win condition)
            if (turnCount >= 10) {
                finish(true);
            }
        }
    }

    @Override
    public void render(Renderer r) {
        Graphics g = r.getRawGraphics();

        // Background
        g.setColor(new Color(101, 67, 33)); // Wood brown
        g.fillRect(0, 0, game.width, game.height);

        // Board outline
        g.setColor(new Color(139, 90, 43));
        g.fillRect(100, 80, 600, 400);
        g.setColor(Color.BLACK);
        g.drawRect(100, 80, 600, 400);

        // Title
        g.setColor(Color.WHITE);
        g.setFont(TITLE_FONT);
        g.drawString("SHESHBESH", 310, 60);

        // Info
        g.setFont(INFO_FONT);
        g.drawString("Turn: " + turnCount + " / 10", 120, 120);
        g.drawString("Press SPACE to roll dice", 120, 150);
        g.drawString("Press ESC to forfeit", 120, 180);

        // Dice
        if (rolled) {
            g.setColor(Color.WHITE);
            g.fillRect(350, 250, 50, 50);
            g.fillRect(420, 250, 50, 50);
            g.setColor(Color.BLACK);
            g.drawRect(350, 250, 50, 50);
            g.drawRect(420, 250, 50, 50);
            g.setFont(TITLE_FONT);
            g.drawString(String.valueOf(dice[0]), 365, 285);
            g.drawString(String.valueOf(dice[1]), 435, 285);
        }
    }

    @Override
    protected void onFinish() {
        if (won) {
            System.out.println("Sheshbesh Won! +10 StreetCreds");
            // TODO: Grant rewards via Player
        } else {
            System.out.println("Sheshbesh forfeited.");
        }
    }
}
