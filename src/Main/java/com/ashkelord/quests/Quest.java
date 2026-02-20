package com.ashkelord.quests;

import com.ashkelord.main.Game;
import java.awt.Graphics;

public abstract class Quest {

    protected Game game;
    protected String name;
    protected boolean completed = false;
    protected boolean rewardCollected = false;
    protected int state = 0; // 0=Not Started, 1=InProgress, 2=Completed, 3=ReturnToNPC
    
    public static final int STATE_NOT_STARTED = 0;
    public static final int STATE_IN_PROGRESS = 1;
    public static final int STATE_COMPLETED = 2;
    public static final int STATE_RETURN_TO_NPC = 3;

    public Quest(Game game, String name) {
        this.game = game;
        this.name = name;
    }

    public abstract void tick();

    public abstract void render(Graphics g);

    public void start() {
        state = 1;
        onStart();
    }

    public void complete() {
        state = 2;
        completed = true;
        onComplete();
    }

    // Hooks
    protected abstract void onStart();
    protected abstract void onComplete();

    // Getters
    public boolean isCompleted() {
        return completed;
    }

    public String getName() {
        return name;
    }
    
    public int getState() {
        return state;
    }
    
    public boolean isRewardCollected() {
        return rewardCollected;
    }

    public void setState(int state) {
        this.state = state;
    }
}
