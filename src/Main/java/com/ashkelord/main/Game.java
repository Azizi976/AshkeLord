package Main.java.com.ashkelord.main;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;

// Main Game Class - The Heart of the Engine
public class Game implements Runnable {

    private Display display;
    public int width, height;
    public String title;

    private boolean running = false;
    private Thread thread;

    // States
    private State gameState;
    private State menuState;

    // Input
    private KeyManager keyManager;

    public Game(String title, int width, int height) {
        this.width = width;
        this.height = height;
        this.title = title;
        keyManager = new KeyManager();
    }

    private void init() {
        display = new Display(title, width, height);
        display.getFrame().addKeyListener(keyManager);

        // טעינת הנכסים (Singleton) - סאחי אבל יעיל
        Assets.init();

        // אתחול מצבי משחק
        gameState = new GameState(this);
        menuState = new MenuState(this);
        State.setState(gameState);
    }

    // The Game Loop (Fixed Time Step)
    @Override
    public void run() {
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
            display.getCanvas().createBufferStrategy(3); // Triple Buffering
            return;
        }
        Graphics g = bs.getDrawGraphics();

        // Clear Screen
        g.clearRect(0, 0, width, height);

        // Draw State
        if (State.getState() != null)
            State.getState().render(g);

        bs.show();
        g.dispose();
    }
}