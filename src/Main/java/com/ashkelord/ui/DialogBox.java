package com.ashkelord.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

/**
 * Pokemon-style dialogue box rendered at the bottom of the screen.
 * White rectangle with thick black border, text drawn inside.
 */
public class DialogBox {

    // Box dimensions & position
    private static final int BOX_MARGIN = 32;
    private static final int BOX_HEIGHT = 120;
    private static final int BORDER_THICKNESS = 4;
    private static final int TEXT_PADDING = 20;
    private static final Font DIALOG_FONT = new Font("Monospaced", Font.BOLD, 18);

    private int screenWidth, screenHeight;
    private String currentText;
    private boolean active;

    public DialogBox(int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.currentText = "";
        this.active = false;
    }

    public void tick() {
        // Future: text typewriter animation, input handling to advance/close
    }

    public void render(Graphics g) {
        if (!active)
            return;

        int boxX = BOX_MARGIN;
        int boxY = screenHeight - BOX_HEIGHT - BOX_MARGIN;
        int boxWidth = screenWidth - (BOX_MARGIN * 2);

        // Draw thick black border
        g.setColor(Color.BLACK);
        g.fillRoundRect(boxX, boxY, boxWidth, BOX_HEIGHT, 12, 12);

        // Draw white inner fill
        g.setColor(Color.WHITE);
        g.fillRoundRect(
                boxX + BORDER_THICKNESS,
                boxY + BORDER_THICKNESS,
                boxWidth - (BORDER_THICKNESS * 2),
                BOX_HEIGHT - (BORDER_THICKNESS * 2),
                8, 8);

        // Draw text
        g.setColor(Color.BLACK);
        g.setFont(DIALOG_FONT);
        drawWrappedText(g, currentText,
                boxX + TEXT_PADDING,
                boxY + TEXT_PADDING + 18,
                boxWidth - (TEXT_PADDING * 2));
    }

    /**
     * Draws text with basic word-wrapping.
     */
    private void drawWrappedText(Graphics g, String text, int x, int y, int maxWidth) {
        FontMetrics fm = g.getFontMetrics();
        String[] words = text.split(" ");
        StringBuilder line = new StringBuilder();
        int lineY = y;

        for (String word : words) {
            String testLine = line.length() == 0 ? word : line + " " + word;
            if (fm.stringWidth(testLine) > maxWidth) {
                g.drawString(line.toString(), x, lineY);
                line = new StringBuilder(word);
                lineY += fm.getHeight() + 4;
            } else {
                line = new StringBuilder(testLine);
            }
        }
        // Draw the last line
        if (line.length() > 0) {
            g.drawString(line.toString(), x, lineY);
        }
    }

    // --- Controls ---

    public void show(String text) {
        this.currentText = text;
        this.active = true;
    }

    public void hide() {
        this.active = false;
        this.currentText = "";
    }

    public boolean isActive() {
        return active;
    }

    public String getCurrentText() {
        return currentText;
    }
}
