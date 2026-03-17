package com.ashkelord.gfx;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

/**
 * AWT/Canvas-backed implementation of the Renderer interface.
 * Uses triple-buffered BufferStrategy for smooth rendering.
 */
public class AWTRenderer implements Renderer {

    private final Canvas canvas;
    private BufferStrategy bs;
    private Graphics g;

    public AWTRenderer(Canvas canvas) {
        this.canvas = canvas;
    }

    @Override
    public boolean begin() {
        bs = canvas.getBufferStrategy();
        if (bs == null) {
            canvas.createBufferStrategy(3);
            return false; // Retry next frame
        }
        g = bs.getDrawGraphics();
        return true;
    }

    @Override
    public void end() {
        if (g != null) {
            g.dispose();
            g = null;
        }
        if (bs != null) {
            bs.show();
        }
    }

    @Override
    public void clear(int width, int height) {
        if (g != null) {
            g.clearRect(0, 0, width, height);
        }
    }

    @Override
    public void drawImage(BufferedImage img, int x, int y, int w, int h) {
        if (g != null) {
            g.drawImage(img, x, y, w, h, null);
        }
    }

    @Override
    public void drawImage(BufferedImage img, int x, int y) {
        if (g != null) {
            g.drawImage(img, x, y, null);
        }
    }

    @Override
    public void drawRect(int x, int y, int w, int h, Color c) {
        if (g != null) {
            g.setColor(c);
            g.drawRect(x, y, w, h);
        }
    }

    @Override
    public void fillRect(int x, int y, int w, int h, Color c) {
        if (g != null) {
            g.setColor(c);
            g.fillRect(x, y, w, h);
        }
    }

    @Override
    public void drawString(String text, int x, int y, Font font, Color c) {
        if (g != null) {
            g.setFont(font);
            g.setColor(c);
            g.drawString(text, x, y);
        }
    }

    @Override
    public void fillOval(int x, int y, int w, int h, Color c) {
        if (g != null) {
            g.setColor(c);
            g.fillOval(x, y, w, h);
        }
    }

    @Override
    public Graphics getRawGraphics() {
        return g;
    }
}
