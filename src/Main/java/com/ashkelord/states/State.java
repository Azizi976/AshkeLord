package com.ashkelord.states;

import com.ashkelord.main.Game;
import com.ashkelord.gfx.Renderer;

public abstract class State {

    protected Game game;

    public State(Game game) {
        this.game = game;
    }

    public abstract void tick();

    public abstract void render(Renderer r);
}
