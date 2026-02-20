package com.ashkelord.entities;

import com.ashkelord.gfx.Assets;
import com.ashkelord.main.Game;
import com.ashkelord.quests.Quest;
import com.ashkelord.quests.QuestManager;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class QuestItem extends Entity {

    private Game game;
    private BufferedImage texture;
    private String questName;
    private QuestManager questManager;

    public QuestItem(Game game, QuestManager qm, float x, float y, int width, int height, BufferedImage texture, String questName) {
        super(x, y, width, height);
        this.game = game;
        this.questManager = qm;
        this.texture = texture;
        this.questName = questName;
        
        // Approx center hit box
        bounds.x = width / 4;
        bounds.y = height / 4;
        bounds.width = width / 2;
        bounds.height = height / 2;
    }

    @Override
    public void tick() {
        // Floating animation or glow could go here
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(texture,
                (int) (x - game.getGameCamera().getxOffset()),
                (int) (y - game.getGameCamera().getyOffset()),
                width, height, null);
    }

    @Override
    public void interact() {
        Quest q = questManager.getQuest(questName);
        if (q != null && q.getState() == Quest.STATE_IN_PROGRESS) { // If quest is in progress
            q.setState(Quest.STATE_RETURN_TO_NPC);
            // Move item away
            x = -1000;
            y = -1000;
            
            // Switch back to main world
            game.getGameState().loadWorld("/maps/world1.txt");
            
            game.getUIManager().getDialogBox().show("You have the Amba. Return to Miri!");
        } else {
            game.getUIManager().getDialogBox().show("It's locked or you don't need this yet.");
        }
    }
}
