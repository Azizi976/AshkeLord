package com.ashkelord.quests;

import com.ashkelord.main.Game;
import java.awt.Graphics;

public class QuestTheSacredShawarma extends Quest {

    private boolean inPort = false;

    public QuestTheSacredShawarma(Game game) {
        super(game, "The Sacred Shawarma");
    }

    @Override
    public void tick() {
        if (state == Quest.STATE_IN_PROGRESS && !inPort && !game.getUIManager().getDialogBox().isActive()) {
            // Dialog finished, switch to Port
            game.getGameState().loadWorld("/maps/world_port.txt");
            inPort = true;
            
            // Spawn Amba in Port
            com.ashkelord.entities.QuestItem amba = new com.ashkelord.entities.QuestItem(game, game.getGameState().getQuestManager(), 
                 10 * 64, 10 * 64, 64, 64, com.ashkelord.gfx.Assets.golden_amba, "The Sacred Shawarma");
            game.getGameState().setQuestItem(amba);
            
            game.getUIManager().getDialogBox().show("Amba located at the Port! Find it!");
        }
        
        // Completion logic moved to NPC interaction in GameState or specific method here
        // We just watch for state change to 2 (Completed) to show final message if needed, 
        // but user wants "Thank You" dialog from NPC.
    }

    @Override
    public void render(Graphics g) {
        // Render quest specific elements if needed
    }

    @Override
    protected void onStart() {
        // Trigger dialog
        game.getUIManager().getDialogBox().show(new String[] {
            "Miri: Nadav, kapara! Why aren't you eating?",
            "Miri: You look like a skeleton.",
            "Nadav: My bike... it's gone.",
            "Nadav: And Shirel left with Liran.",
            "Miri: Tsk tsk. That Shark is trouble.",
            "Miri: Listen, I need the Golden Amba from the port.",
            "Miri: Go get it, I'll make you a heavy laffa.",
            "Nadav: Okay... for the laffa."
        });
    }

    @Override
    protected void onComplete() {
        System.out.println("Quest Completed: " + name);
        // Reward: Miri's Heavy Laffa
        com.ashkelord.entities.creatures.Player player = null;
        for (com.ashkelord.entities.Entity e : game.getGameState().getWorld().getEntityManager().getEntities()) {
            if (e instanceof com.ashkelord.entities.creatures.Player) {
                player = (com.ashkelord.entities.creatures.Player) e;
                break;
            }
        }
        if (player != null) {
            player.getInventory().addItem(com.ashkelord.items.Item.laffaItem.createNew(1));
            player.addStreetCreds(10);
        }
    }
}
