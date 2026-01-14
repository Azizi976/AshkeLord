package Main.java.com.ashkelord.states;

import Main.java.com.ashkelord.main.Game;
import java.awt.Graphics;

public abstract class State {

    // --- State Manager (Static) ---
    private static State currentState = null;

    public static void setState(State state) {
        currentState = state;
    }

    public static State getState() {
        return currentState;
    }

    // --- Abstract Methods ---
    protected Game game;

    public State(Game game) {
        this.game = game;
    }

    public abstract void tick();

    public abstract void render(Graphics g);
}
