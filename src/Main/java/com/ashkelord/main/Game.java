package com.ashkelord.main;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import com.ashkelord.states.State;
import com.ashkelord.states.GameState;
import com.ashkelord.states.MenuState; // יצרתי אותו למטה
import com.ashkelord.input.KeyManager;
import com.ashkelord.gfx.Assets;
import com.ashkelord.gfx.GameCamera; // וודא שיש לך את הקובץ הזה

public class Game implements Runnable {

    private Display display;
    public int width, height;
    public String title;

    private boolean running = false;
    private Thread thread;

    // States
    private State gameState;
    private State menuState;

    // Input & Camera
    private KeyManager keyManager;
    private GameCamera gameCamera; // הוספתי

    public Game(String title, int width, int height) {
        this.width = width;
        this.height = height;
        this.title = title;
        keyManager = new KeyManager();
    }

    private void init() {
        display = new Display(title, width, height);
        display.getFrame().addKeyListener(keyManager);
        Assets.init();

        // מצלמה
        gameCamera = new GameCamera(this, 0, 0);

        gameState = new GameState(this);
        menuState = new MenuState(this);
        State.setState(gameState);
    }

    @Override
    public void run() {
        // ... (אותו לוגיקה של FPS כמו קודם)
        init();
        int fps = 60;
        double timePerTick = 1000000000 / fps;
        double delta = 0;
        long now;
        long lastTime = System.nanoTime();

        while (running) {
            now = System.nanoTime();
            delta += (now - lastTime) / timePerTick;
            lastTime = now;

            if (delta >= 1) {
                tick();
                render();
                delta--;
            }
        }
        stop();
    }

    private void tick() {
        keyManager.tick();
        if (State.getState() != null)
            State.getState().tick();
    }

    private void render() {
        BufferStrategy bs = display.getCanvas().getBufferStrategy();
        if (bs == null) {
            display.getCanvas().createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        g.clearRect(0, 0, width, height);

        if (State.getState() != null) {
            // מעדכן את המצלמה לפני הציור (אם נרצה אפקטים)
            State.getState().render(g);
        }

        bs.show();
        g.dispose();
    }

    // Getters
    public KeyManager getKeyManager() {
        return keyManager;
    }

    public GameCamera getGameCamera() {
        return gameCamera;
    } // קריטי

    public synchronized void start() {
        if (running)
            return; // אם כבר רץ, לא לעשות כלום

        running = true;
        thread = new Thread(this); // "this" זה המחלקה Game שמממשת Runnable
        thread.start(); // זה קורא לפונקציה run() באופן אוטומטי
    }

    public synchronized void stop() {
        if (!running)
            return;

        running = false;
        try {
            thread.join(); // מחכה שהט'רד יסיים בצורה נקייה
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}