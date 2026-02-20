package com.ashkelord.main;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import com.ashkelord.states.State;
import com.ashkelord.states.PrologueState;
import com.ashkelord.states.MenuState; // יצרתי אותו למטה
import com.ashkelord.input.KeyManager;
import com.ashkelord.gfx.Assets;
import com.ashkelord.gfx.GameCamera; // וודא שיש לך את הקובץ הזה
import com.ashkelord.ui.UIManager; // וודא שיש לך את הקובץ הזה

public class Game implements Runnable {

    private Display display;
    public int width, height;
    public String title;

    private boolean running = false;
    private Thread thread;

    // States
    private State gameState;
    private State menuState;

    // Input, Camera & UI
    private KeyManager keyManager;
    private GameCamera gameCamera;
    private UIManager uiManager; // הוספתי

    public Game(String title, int width, int height) {
        this.width = width;
        this.height = height;
        this.title = title;
        keyManager = new KeyManager();
    }

    private void init() {
        display = new Display(title, width, height);
        display.getFrame().addKeyListener(keyManager);
        display.getCanvas().addKeyListener(keyManager);
        display.getCanvas().requestFocus();
        Assets.init();

        // מצלמה
        // Camera
        gameCamera = new GameCamera(this, 0, 0);

        // UI Manager
        uiManager = new UIManager(width, height);

        // Start with the prologue intro
        State.setState(new PrologueState(this));
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
        if (uiManager != null)
            uiManager.tick();
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
    }

    public UIManager getUIManager() {
        return uiManager;
    } 

    public com.ashkelord.states.GameState getGameState() {
        if (com.ashkelord.states.State.getState() instanceof com.ashkelord.states.GameState) {
            return (com.ashkelord.states.GameState) com.ashkelord.states.State.getState();
        }
        return null;
    }

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
    
    public com.ashkelord.quests.QuestManager getQuestManager() {
        if (getGameState() != null) {
            return getGameState().getQuestManager();
        }
        return null; // Or handle appropriately
    }
}