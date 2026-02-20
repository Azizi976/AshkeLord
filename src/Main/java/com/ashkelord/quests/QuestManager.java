package com.ashkelord.quests;

import com.ashkelord.main.Game;
import java.awt.Graphics;
import java.util.ArrayList;

public class QuestManager {

    private Game game;
    private ArrayList<Quest> quests;

    public QuestManager(Game game) {
        this.game = game;
        quests = new ArrayList<>();
    }

    public void tick() {
        for (Quest q : quests) {
            if (q.getState() == 1) { // In Progress
                q.tick();
            }
        }
    }

    public void render(Graphics g) {
        for (Quest q : quests) {
            if (q.getState() == 1) {
                q.render(g);
            }
        }
    }

    public void addQuest(Quest q) {
        quests.add(q);
    }

    public Quest getQuest(String name) {
        for (Quest q : quests) {
            if (q.getName().equals(name))
                return q;
        }
        return null;
    }
}
