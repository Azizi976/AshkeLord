package com.ashkelord.ui;

import java.awt.Graphics;

/**
 * Manages all UI components (dialog boxes, HUD, menus).
 * Renders as an overlay on top of the game world.
 */
public class UIManager {

    private DialogBox dialogBox;

    public UIManager(int screenWidth, int screenHeight) {
        dialogBox = new DialogBox(screenWidth, screenHeight);
    }

    public void tick() {
        dialogBox.tick();
    }

    public void render(Graphics g) {
        dialogBox.render(g);
    }

    public DialogBox getDialogBox() {
        return dialogBox;
    }
}
