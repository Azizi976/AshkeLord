package com.ashkelord.minigames;

import com.ashkelord.gfx.Renderer;
import com.ashkelord.main.Game;
import com.ashkelord.states.State;
import java.awt.Graphics;

/**
 * Base class for all mini-games.
 * Mini-games are pushed onto the StateManager stack on top of GameState.
 * When finished, they pop themselves, returning control to GameState.
 */
public abstract class MiniGame extends State {

    protected boolean finished = false;
    protected boolean won = false;

    public MiniGame(Game game) {
        super(game);
    }

    /** Called once when the mini-game is first entered. */
    protected abstract void onStart();

    /** Called when the mini-game ends (cleanup, grant rewards, etc). */
    protected abstract void onFinish();

    /**
     * End the mini-game and return to the previous state.
     * @param won true if the player completed/won the game
     */
    protected void finish(boolean won) {
        this.won = won;
        this.finished = true;
        onFinish();
        game.getStateManager().pop();
    }

    public boolean isFinished() { return finished; }
    public boolean isWon() { return won; }
}
