package com.ashkelord.quests;

import com.ashkelord.main.Game;
import com.ashkelord.entities.npcs.NPC;
import com.ashkelord.entities.creatures.Player;
import com.ashkelord.entities.Entity;
import com.ashkelord.worlds.World;
import com.ashkelord.gfx.Assets;
import java.awt.Graphics;

/**
 * Quest 3: "Escape from Periphery"
 * Escort Yotam the Tel-Avivian back to safety.
 * Flow: Find Yotam -> Start escort -> Survive 2 mob encounters -> Complete
 * Reward: Oat Milk Cortado (speed buff item)
 */
public class QuestEscapeFromPeriphery extends Quest {

    private boolean mazeActive = false;
    private NPC yotamFollower;
    private NPC ars1, ars2;
    private boolean mob1Triggered = false;
    private boolean mob2Triggered = false;

    public QuestEscapeFromPeriphery(Game game) {
        super(game, "Escape from Periphery");
    }

    @Override
    public void tick() {
        if (state != STATE_IN_PROGRESS) return;
        
        // Triggers and logic inside the maze
        if (mazeActive) {
            World world = game.getGameState().getWorld();
            Player player = null;
            
            // Find player
            for (Entity e : world.getEntityManager().getEntities()) {
                if (e instanceof Player) {
                    player = (Player) e;
                    break;
                }
            }
            
            if (player == null) return;
            
            // Mob 1 Trigger (Proximity to Ars 1 at 9,5)
            // Row 5 is y=320. Check x range 7-11.
            if (!mob1Triggered && player.getX() > 7 * 64 && player.getX() < 11 * 64 && player.getY() > 4 * 64 && player.getY() < 6 * 64) {
                 mob1Triggered = true;
                 game.getUIManager().getDialogBox().show(new String[] {
                    "Tracksuit Ars: Eyyy! You in the wrong neighborhood.",
                    "Yotam: Is this... a gated community?",
                    "Nadav: Just keep walking, Yotam.",
                    "Tracksuit Ars: Next time you pay entrance fee. 5 shekels."
                });
            }
            
            // Remove Ars 1 after dialog closes
            if (mob1Triggered && ars1 != null && !game.getUIManager().getDialogBox().isActive()) {
                ars1.setActive(false);
                game.getGameState().getWorld().getEntityManager().removeEntity(ars1);
                ars1 = null;
            }
            
            // Mob 2 Trigger (Proximity to Ars 2 at 9,13)
            // Row 13 is y=832. Check x range 7-11.
            if (!mob2Triggered && mob1Triggered && player.getX() > 7 * 64 && player.getX() < 11 * 64 && player.getY() > 12 * 64 && player.getY() < 14 * 64) {
                 mob2Triggered = true;
                 game.getUIManager().getDialogBox().show(new String[] {
                    "Another Ars: Nice bag. My sister wants it.",
                    "Yotam: No! It's a limited edition!",
                    "Nadav: He said no. Leave us alone.",
                    "Another Ars: Tch. Tel-Avivim... stingy."
                });
            }
            
            // Remove Ars 2 after dialog closes
            if (mob2Triggered && ars2 != null && !game.getUIManager().getDialogBox().isActive()) {
                ars2.setActive(false);
                game.getGameState().getWorld().getEntityManager().removeEntity(ars2);
                ars2 = null;
            }

            // Win condition: Reach (18, 18)
            if (player.getX() > 17.5 * 64 && player.getY() > 17.5 * 64) {
                 complete();
            }
        }
    }

    @Override
    public void render(Graphics g) {
        // No special rendering
    }

    @Override
    protected void onStart() {
        mazeActive = true;
        
        // 1. Switch to Maze World
        game.getUIManager().getDialogBox().show(new String[] {
            "Yotam: Oh no! We're trapped in a maze of Keter chairs!",
            "Yotam: This must be the Periphery Labyrinth...",
            "Yotam: Lead the way, Nadav! I'll follow you!"
        });
        
        game.getGameState().loadWorld("/maps/world_maze.txt");
        
        // 2. Spawn Yotam in Maze next to player (Player is at 2,1)
        World world = game.getGameState().getWorld();
        
        // Spawn Yotam at 1,1 (Behind player)
        yotamFollower = new NPC(game, world, 1 * 64, 1 * 64, "Yotam", Assets.yotam_telaviv) {
            @Override
            public void tick() {
                // Custom Follower AI with Collision
                // Find player
                Player player = null;
                for (Entity e : QuestEscapeFromPeriphery.this.game.getGameState().getWorld().getEntityManager().getEntities()) {
                    if (e instanceof Player) {
                        player = (Player) e;
                        break;
                    }
                }
                
                if (player != null) {
                    float dist = (float) Math.sqrt(Math.pow(player.getX() - getX(), 2) + Math.pow(player.getY() - getY(), 2));
                    
                    xMove = 0;
                    yMove = 0;
                    
                    if (dist > 50) { // Keep distance (tighter follow)
                         float speed = 4.5f; // Even Faster to catch up
                         float dirX = player.getX() - getX();
                         float dirY = player.getY() - getY();
                         float len = (float) Math.sqrt(dirX*dirX + dirY*dirY);
                         if (len > 0) {
                             xMove = (dirX / len) * speed;
                             yMove = (dirY / len) * speed;
                         }
                    }
                    
                    // Teleport if too far stuck (reduced threshold)
                    if (dist > 200) {
                        setX(player.getX());
                        setY(player.getY());
                    }
                }
                
                // IMPORTANT: Call move() explicitly because NPC.tick() is empty!
                move();
            }
        
            @Override
            public void interact() {
                QuestEscapeFromPeriphery.this.game.getUIManager().getDialogBox().show("Yotam: Why do they keep staring at my shoes?");
            }

            // Make Yotam Ethereal
            @Override
            public java.awt.Rectangle getCollisionBounds(float xOffset, float yOffset) {
                return new java.awt.Rectangle(0, 0, 0, 0);
            }
        };
        world.getEntityManager().addEntity(yotamFollower);
        
        // 3. Spawn Ars Enemies (Obstacles)
        // Ars 1 at (9, 5) - blocking the snake path
        ars1 = new NPC(game, world, 9 * 64, 5 * 64, "Tracksuit Ars", Assets.ars_walk[0][0]) {
             @Override
             public void interact() {
                 game.getUIManager().getDialogBox().show("Tracksuit Ars: You lucky I'm on break.");
             }
        };
        world.getEntityManager().addEntity(ars1);
        
        // Ars 2 at (9, 13) - blocking the snake path
        ars2 = new NPC(game, world, 9 * 64, 13 * 64, "Another Ars", Assets.ars_walk[0][0]) { // Reusing ars sprite
             @Override
             public void interact() {
                 game.getUIManager().getDialogBox().show("Another Ars: You pass. For now.");
             }
        };
        world.getEntityManager().addEntity(ars2);
    }

    @Override
    protected void onComplete() {
        System.out.println("Quest Completed: " + name);
        mazeActive = false;
        
        // Return to Main World
        game.getGameState().loadWorld("/maps/world1.txt");
        
        game.getUIManager().getDialogBox().show(new String[] {
            "Yotam: We made it! Thank you so much!",
            "Yotam: Here, take this. It's an Oat Milk Cortado.",
            "Yotam: Specialty blend. Gives you ENERGY.",
            "Nadav: ...Thanks, I guess.",
            "+1 Oat Milk Cortado (use for speed boost!)"
        });
        
        // Reward
        Player player = null;
        for (Entity e : game.getGameState().getWorld().getEntityManager().getEntities()) {
            if (e instanceof Player) {
                player = (Player) e;
                break;
            }
        }
        if (player != null) {
            player.getInventory().addItem(com.ashkelord.items.Item.oatMilkCortadoItem.createNew(1));
            player.addStreetCreds(10);
            player.setX(4 * 64); // Spawn NEXT to Yotam (who is at 3,20) to avoid collision stuck
            player.setY(20 * 64);
        }
        
        // Start Quest 4: The Final Spit
        com.ashkelord.quests.Quest nextQ = game.getQuestManager().getQuest("The Spitting Duel");
        if (nextQ != null) {
            nextQ.start();
        }
    }

    public boolean isMazeActive() { return mazeActive; }
}
