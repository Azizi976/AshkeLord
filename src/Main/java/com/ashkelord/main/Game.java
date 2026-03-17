package com.ashkelord.main;

import com.ashkelord.states.State;
import com.ashkelord.states.StateManager;
import com.ashkelord.states.PrologueState;
import com.ashkelord.states.MenuState;
import com.ashkelord.input.KeyManager;
import com.ashkelord.gfx.Assets;
import com.ashkelord.gfx.GameCamera;
import com.ashkelord.gfx.Renderer;
import com.ashkelord.gfx.AWTRenderer;
import com.ashkelord.ui.UIManager;

public class Game implements Runnable {

    private Display display;
    public int width, height;
    public String title;

    private boolean running = false;
    private Thread thread;

    // State Management
    private StateManager stateManager;

    // Input, Camera, UI & Rendering
    private KeyManager keyManager;
    private GameCamera gameCamera;
    private UIManager uiManager;
    private Renderer renderer;

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

    // Audio — register game sounds
    com.ashkelord.audio.AudioManager.getInstance().registerSound("bgm_main", "/sounds/Phrygian_Pixel_Palms.wav");

        // Camera
        gameCamera = new GameCamera(this, 0, 0);

        // UI Manager
        uiManager = new UIManager(width, height);

        // Renderer
        renderer = new AWTRenderer(display.getCanvas());

        // State Manager (stack-based)
        stateManager = new StateManager();

        // Start with the prologue intro
        stateManager.swap(new PrologueState(this));
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
        stateManager.tick();
        if (uiManager != null)
            uiManager.tick();
    }

    private void render() {
        if (!renderer.begin()) return;
        renderer.clear(width, height);

        stateManager.render(renderer);

        renderer.end();
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
        State current = stateManager.peek();
        if (current instanceof com.ashkelord.states.GameState) {
            return (com.ashkelord.states.GameState) current;
        }
        return null;
    }

    public StateManager getStateManager() {
        return stateManager;
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