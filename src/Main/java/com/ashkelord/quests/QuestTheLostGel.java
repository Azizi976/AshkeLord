package com.ashkelord.quests;

import com.ashkelord.main.Game;
import java.awt.Graphics;

/**
 * Quest 2: "The Lost Gel"
 * Tzion the Barber lost his Strong Hold Wax to Avi the Gym Boss.
 * Flow: Talk Tzion -> Find Avi -> Intimidate him (need 10+ creds) -> Return wax to Tzion
 * Reward: +10 Charisma, intel about The Shark
 */
public class QuestTheLostGel extends Quest {

    // Custom states for this quest's flow
    public static final int FIND_AVI = 1;
    public static final int RETURN_TO_TZION = 3;
    
    private boolean aviDefeated = false;

    public QuestTheLostGel(Game game) {
        super(game, "The Lost Gel");
    }

    @Override
    public void tick() {
        // Quest logic handled via NPC interactions in GameState
    }

    @Override
    public void render(Graphics g) {
        // No special rendering needed
    }

    @Override
    protected void onStart() {
        game.getUIManager().getDialogBox().show(new String[] {
            "Tzion: Achi! You look like you haven't combed in weeks!",
            "Tzion: Listen, I had the BEST wax. Strong Hold. Imported from Turkey.",
            "Tzion: But that gorilla Avi from the gym took it!",
            "Tzion: Says it's 'protein for his muscles'. Meshugena!",
            "Tzion: Get it back for me and I'll make you look SHARP.",
            "Tzion: Sharp enough to steal Shirel back, eh?",
            "Nadav: ...I'm in."
        });
    }

    @Override
    protected void onComplete() {
        System.out.println("Quest Completed: " + name);
        // Reward: +10 Charisma, Strong Hold Wax, intel
        com.ashkelord.entities.creatures.Player player = null;
        for (com.ashkelord.entities.Entity e : game.getGameState().getWorld().getEntityManager().getEntities()) {
            if (e instanceof com.ashkelord.entities.creatures.Player) {
                player = (com.ashkelord.entities.creatures.Player) e;
                break;
            }
        }
        if (player != null) {
            player.addCharisma(10);
            player.addStreetCreds(15);
            player.getInventory().addItem(com.ashkelord.items.Item.strongHoldWaxItem.createNew(1));
            // Tzion uses the wax for the haircut — remove from inventory
            player.getInventory().removeItem(com.ashkelord.items.Item.strongHoldWaxItem.getId());
        }
    }

    public boolean isAviDefeated() { return aviDefeated; }
    public void setAviDefeated(boolean b) { aviDefeated = b; }
}
