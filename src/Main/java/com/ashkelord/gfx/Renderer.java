package com.ashkelord.gfx;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * Abstraction layer for all rendering operations.
 * Decouples game logic from java.awt.Graphics, enabling future migration
 * to hardware-accelerated backends (e.g., LibGDX, LWJGL).
 */
public interface Renderer {

    /**
     * Acquire the drawing context. Must be called before any draw operations.
     * @return true if the context was successfully acquired, false if it needs retry.
     */
    boolean begin();

    /**
     * Release the drawing context and flush to screen.
     */
    void end();

    /**
     * Clear the screen with the default background color.
     */
    void clear(int width, int height);

    /**
     * Draw a BufferedImage at the specified position and size.
     */
    void drawImage(BufferedImage img, int x, int y, int w, int h);

    /**
     * Draw a BufferedImage at the specified position (original size).
     */
    void drawImage(BufferedImage img, int x, int y);

    /**
     * Draw an outlined rectangle.
     */
    void drawRect(int x, int y, int w, int h, Color c);

    /**
     * Draw a filled rectangle.
     */
    void fillRect(int x, int y, int w, int h, Color c);

    /**
     * Draw a string at the specified position.
     */
    void drawString(String text, int x, int y, Font font, Color c);

    /**
     * Draw a filled oval.
     */
    void fillOval(int x, int y, int w, int h, Color c);

    /**
     * Get the underlying raw Graphics context.
     * Escape hatch for complex rendering (HUD, DialogBox, etc.)
     * that hasn't been migrated yet. Will return null for non-AWT backends.
     */
    Graphics getRawGraphics();
}
