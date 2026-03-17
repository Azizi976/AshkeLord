package com.ashkelord.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyManager implements KeyListener {

    private boolean[] keys;
    public boolean up, down, left, right;
    public boolean enter, space, shift, qKey, escape;

    public boolean enterJustPressed;
    public boolean spaceJustPressed;
    public boolean shiftJustPressed;
    public boolean qJustPressed;
    private boolean[] justPressed;
    private boolean[] cantPress;

    public KeyManager() {
        keys = new boolean[256];
        justPressed = new boolean[256];
        cantPress = new boolean[256];
    }

    public void tick() {
        for (int i = 0; i < keys.length; i++) {
            if (cantPress[i] && !keys[i]) {
                cantPress[i] = false;
            } else if (justPressed[i]) {
                cantPress[i] = true;
                justPressed[i] = false;
            }
            if (!cantPress[i] && keys[i]) {
                justPressed[i] = true;
            }
        }

        up = keys[KeyEvent.VK_W] || keys[KeyEvent.VK_UP];
        down = keys[KeyEvent.VK_S] || keys[KeyEvent.VK_DOWN];
        left = keys[KeyEvent.VK_A] || keys[KeyEvent.VK_LEFT];
        right = keys[KeyEvent.VK_D] || keys[KeyEvent.VK_RIGHT];
        enter = keys[KeyEvent.VK_ENTER];
        space = keys[KeyEvent.VK_SPACE];
        shift = keys[KeyEvent.VK_SHIFT];
        qKey = keys[KeyEvent.VK_Q];
        
        enterJustPressed = keyJustPressed(KeyEvent.VK_ENTER);
        spaceJustPressed = keyJustPressed(KeyEvent.VK_SPACE);
        shiftJustPressed = keyJustPressed(KeyEvent.VK_SHIFT);
        qJustPressed = keyJustPressed(KeyEvent.VK_Q);
        escape = keys[KeyEvent.VK_ESCAPE];
    }
    
    public boolean isEscape() { return escape; }
    public boolean isSpaceJustPressed() { return spaceJustPressed; }
    
    public boolean keyJustPressed(int keyCode){
        if(keyCode < 0 || keyCode >= keys.length)
            return false;
        return justPressed[keyCode];
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() < 0 || e.getKeyCode() >= keys.length)
            return;
        keys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() < 0 || e.getKeyCode() >= keys.length)
            return;
        keys[e.getKeyCode()] = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}
