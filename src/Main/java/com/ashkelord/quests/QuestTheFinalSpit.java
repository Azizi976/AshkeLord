package com.ashkelord.quests;

import com.ashkelord.main.Game;
import com.ashkelord.entities.npcs.NPC;
import com.ashkelord.entities.creatures.Player;
import com.ashkelord.entities.creatures.LiranBoss;
import com.ashkelord.entities.Entity;
import com.ashkelord.worlds.World;
import com.ashkelord.gfx.Assets;
import java.awt.Graphics;
import java.awt.Color;

public class QuestTheFinalSpit extends Quest {

    private boolean inBossFight = false;
    private NPC liranNPC;
    private LiranBoss boss;
    private boolean fightTriggered = false;
    private boolean victoryTriggered = false;

    public QuestTheFinalSpit(Game game) {
        super(game, "The Spitting Duel");
    }

    @Override
    protected void onStart() {
        // Spawn Liran NPC in World 1
        World world = game.getGameState().getWorld();
        // Position him near the cafe/center
        liranNPC = new NPC(game, world, 12 * 64, 15 * 64, "Liran", Assets.liran_boss) {
            @Override
            public void interact() {
                startBossFight();
            }
        };
        world.getEntityManager().addEntity(liranNPC);
        
        game.getUIManager().getDialogBox().show("A menacing figure appears near the Shawarma stand...");
    }

    private void startBossFight() {
        if (fightTriggered) return;
        fightTriggered = true;
        
        game.getUIManager().getDialogBox().show(new String[] {
            "Liran: So... you think you're the Ashkelord?",
            "Nadav: Who are you?",
            "Liran: I am Liran. The keeper of the Golden Amba.",
            "Liran: Defeat me in a spitting duel, and the city is yours.",
            "Nadav: Spitting? Oh, you're on."
        });
        
        // Delay or listen for dialog close? 
        // For simplicity, we'll check in tick() when dialog is closed to teleport
    }

    @Override
    public void tick() {
        // Epilogue transition: runs even after quest is completed
        // Must be checked BEFORE the state guard below
        if (victoryTriggered && !game.getUIManager().getDialogBox().isActive()) {
            game.getStateManager().swap(new com.ashkelord.states.EpilogueState(game));
            return;
        }
        
        if (state != STATE_IN_PROGRESS) return;

        if (fightTriggered && !inBossFight && !game.getUIManager().getDialogBox().isActive()) {
            // Teleport to Boss Arena
            inBossFight = true;
            game.getGameState().loadWorld("/maps/world_boss.txt");
            
            // Get player directly from GameState (persists across world loads)
            World world = game.getGameState().getWorld();
            Player player = game.getGameState().getPlayer();
            
            if (player != null) {
                player.setX(10 * 64);
                player.setY(15 * 64);
            }
            
            boss = new LiranBoss(game, world, 10 * 64, 5 * 64, player);
            world.getEntityManager().addEntity(boss);
            if (player != null) {
                game.getGameCamera().centerOnEntity(player);
            }
            
            game.getUIManager().getDialogBox().show("FIGHT!");
        }
        
        // Win Condition
        if (inBossFight && boss != null) {
            if (!boss.isActive() && !victoryTriggered) {
                complete();
            }
        }
    }

    @Override
    public void render(Graphics g) {
        if (inBossFight && boss != null && boss.isActive()) {
            // Draw Boss Health Bar
            g.setColor(Color.RED);
            g.fillRect(200, 20, 400, 20);
            g.setColor(Color.GREEN);
            int width = (int) ((boss.getHealth() / 100.0f) * 400);
            g.fillRect(200, 20, width, 20);
            g.setColor(Color.WHITE);
            g.drawRect(200, 20, 400, 20);
            g.drawString("Liran Boss", 210, 35);
        }
    }

    @Override
    protected void onComplete() {
        System.out.println("Boss Defeated!");
        victoryTriggered = true;
        game.getUIManager().getDialogBox().show(new String[] {
            "Liran: *Cough* ...Not bad... for a periphery kid.",
            "Liran: The Golden Amba... is yours.",
            "CONGRATULATIONS! YOU HAVE BECOME THE ASHKELORD!"
        });
    }
}
