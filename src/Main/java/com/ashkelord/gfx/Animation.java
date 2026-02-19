package com.ashkelord.gfx;

import java.awt.image.BufferedImage;

/**
 * Simple frame-cycling animation.
 * Give it an array of frames and a speed, and it cycles through them.
 */
public class Animation {

    private int speed; // ms per frame
    private int index;
    private long lastTime, timer;
    private BufferedImage[] frames;

    public Animation(int speed, BufferedImage[] frames) {
        this.speed = speed;
        this.frames = frames;
        index = 0;
        timer = 0;
        lastTime = System.currentTimeMillis();
    }

    public void tick() {
        timer += System.currentTimeMillis() - lastTime;
        lastTime = System.currentTimeMillis();

        if (timer > speed) {
            index++;
            timer = 0;
            if (index >= frames.length)
                index = 0;
        }
    }

    public BufferedImage getCurrentFrame() {
        return frames[index];
    }

    /** Reset to the first (standing) frame. */
    public void reset() {
        index = 0;
        timer = 0;
    }
}
