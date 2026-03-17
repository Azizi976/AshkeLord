package com.ashkelord.states;

import com.ashkelord.entities.creatures.Car;
import com.ashkelord.entities.creatures.Player;
import com.ashkelord.entities.creatures.SocialNPC;
import com.ashkelord.entities.npcs.NPC;
import com.ashkelord.gfx.Assets;
import com.ashkelord.gfx.Renderer;
import com.ashkelord.main.Game;
import com.ashkelord.worlds.World;
import com.ashkelord.worlds.Portal;
import com.ashkelord.worlds.PortalManager;
import com.ashkelord.tiles.Tile;
import java.awt.Graphics;

import com.ashkelord.ui.HUD;
import com.ashkelord.quests.QuestManager;

public class GameState extends State {

    private World world;
    private Player player;
    private HUD hud;
    private QuestManager questManager;
    private PortalManager portalManager;

    public GameState(Game game) {
        super(game);

        world = new World(game, "/maps/world1.txt");
        hud = new HUD();
        portalManager = new PortalManager();
        
        // Quests
        questManager = new QuestManager(game);
        questManager.addQuest(new com.ashkelord.quests.QuestTheSacredShawarma(game));
        questManager.addQuest(new com.ashkelord.quests.QuestTheLostGel(game));
        questManager.addQuest(new com.ashkelord.quests.QuestEscapeFromPeriphery(game));
        questManager.addQuest(new com.ashkelord.quests.QuestTheFinalSpit(game));

        // Player
        player = new Player(game, world,
                world.getSpawnX() * 64, world.getSpawnY() * 64);
        world.getEntityManager().addEntity(player);

        // Miri and Stand references
        initMiri();
        
        // Quest Item: Golden Amba
        // Placed at "the port" (some far coordinates)
        com.ashkelord.entities.QuestItem amba = new com.ashkelord.entities.QuestItem(game, questManager, 
             24 * 64, 24 * 64, 64, 64, Assets.golden_amba, "The Sacred Shawarma");
        world.getEntityManager().addEntity(amba);

        // Cars on roads (spawning at road coordinates)
        Car car1 = new Car(world, 2 * 64, 11 * 64, 0); // White
        world.getEntityManager().addEntity(car1);

        Car car2 = new Car(world, 20 * 64, 12 * 64, 1); // Red
        world.getEntityManager().addEntity(car2);

        // Vertical cars
        Car car3 = new Car(world, 11 * 64, 2 * 64, 0); // White
        world.getEntityManager().addEntity(car3);

        Car car4 = new Car(world, 12 * 64, 25 * 64, 1); // Red
        world.getEntityManager().addEntity(car4);

        // Social NPCs (Savtas and Kids) wandering the neighborhood
        world.getEntityManager().addEntity(new SocialNPC(world, 14 * 64, 14 * 64, 0)); // Savta
        world.getEntityManager().addEntity(new SocialNPC(world, 18 * 64, 15 * 64, 1)); // Kid

        // New Ars NPC
        NPC trackArs = new NPC(game, world, 12 * 64, 7 * 64, "Tracksuit Ars", Assets.ars_walk[0][0]) {
            @Override
            public void interact() {
                game.getUIManager().getDialogBox().show("Ars: What you lookin' at, kapara?");
            }
        };
        world.getEntityManager().addEntity(trackArs);

        // ===== EASTER EGGS =====
        
        // 1. Nadav's Stolen Bike Frame — right near spawn, hard to miss
        NPC bikeFrame = new NPC(game, world, 4 * 64, 16 * 64, "Rusty Bike Frame", Assets.rusty_bike) {
            @Override
            public void interact() {
                game.getUIManager().getDialogBox().show(new String[] {
                    "Your old electric bike... the battery is gone.",
                    "Liran took everything.",
                    "The frame is rusted. The tires are flat.",
                    "But you still remember the sunset rides with Shirel."
                });
            }
        };
        world.getEntityManager().addEntity(bikeFrame);

        // 2. Shirel's Selfie Wall — opposite side of spawn
        NPC shirelWall = new NPC(game, world, 17 * 64, 16 * 64, "Graffiti Wall", Assets.graffiti_wall) {
            private boolean found = false;
            @Override
            public void interact() {
                if (!found) {
                    game.getUIManager().getDialogBox().show(new String[] {
                        "Someone spray-painted 'Shirel + Liran' inside a heart.",
                        "Your blood boils.",
                        "Underneath it says: 'The Prince is FINISHED'",
                        "You clench your fist. Not yet.",
                        "+2 Street Creds"
                    });
                    // Find player and add creds
                    for (com.ashkelord.entities.Entity e : world.getEntityManager().getEntities()) {
                        if (e instanceof Player) {
                            ((Player) e).addStreetCreds(2);
                            break;
                        }
                    }
                    found = true;
                } else {
                    game.getUIManager().getDialogBox().show("The graffiti stares back at you. Motivation.");
                }
            }
        };
        world.getEntityManager().addEntity(shirelWall);

        // 3. Abu Rafi — wise old man sitting on bench near spawn
        NPC abuRafi = new NPC(game, world, 8 * 64, 19 * 64, "Abu Rafi", Assets.abu_rafi) {
            private int talkCount = 0;
            @Override
            public void interact() {
                switch (talkCount % 4) {
                    case 0:
                        game.getUIManager().getDialogBox().show(new String[] {
                            "Abu Rafi: Ah, Nadav. Sit, sit.",
                            "Abu Rafi: You know what your father used to say?",
                            "Abu Rafi: 'The strongest chain is honor, not gold.'"
                        });
                        break;
                    case 1:
                        game.getUIManager().getDialogBox().show(new String[] {
                            "Abu Rafi: I see you looking for trouble.",
                            "Abu Rafi: Liran thinks he owns the port.",
                            "Abu Rafi: But the port remembers who was there first."
                        });
                        break;
                    case 2:
                        game.getUIManager().getDialogBox().show(new String[] {
                            "Abu Rafi: You want to beat The Shark?",
                            "Abu Rafi: Don't fight with fists. Fight with respect.",
                            "Abu Rafi: Earn the shchuna's trust, and Liran has nothing."
                        });
                        break;
                    case 3:
                        game.getUIManager().getDialogBox().show(new String[] {
                            "Abu Rafi: Your savta would be proud, ya ibn.",
                            "Abu Rafi: Now go. The amba won't find itself."
                        });
                        break;
                }
                talkCount++;
            }
        };
        world.getEntityManager().addEntity(abuRafi);

        // ===== QUEST 2 NPCS: THE LOST GEL =====
        
        // Tzion the Barber — near the buildings, waiting for help
        NPC tzion = new NPC(game, world, 6 * 64, 18 * 64, "Tzion the Barber", Assets.tzion_barber) {
            @Override
            public void interact() {
                // Prerequisite: "The Sacred Shawarma" (Miri's Quest) must be completed first
                com.ashkelord.quests.Quest prevQuest = questManager.getQuest("The Sacred Shawarma");
                if (prevQuest != null && !prevQuest.isCompleted()) {
                    game.getUIManager().getDialogBox().show("Tzion: Nadav! Miri is looking for you. Go help her first!");
                    return;
                }
                
                com.ashkelord.quests.Quest q = questManager.getQuest("The Lost Gel");
                if (q.getState() == com.ashkelord.quests.Quest.STATE_NOT_STARTED) {
                    q.start();
                } else if (q.getState() == com.ashkelord.quests.Quest.STATE_IN_PROGRESS) {
                    com.ashkelord.quests.QuestTheLostGel gelQuest = (com.ashkelord.quests.QuestTheLostGel) q;
                    if (gelQuest.isAviDefeated()) {
                        game.getUIManager().getDialogBox().show(new String[] {
                            "Tzion: You got the wax! Yallah, sit down!",
                            "Tzion: *snip snip* Look at this... MAGNIFICENT.",
                            "Tzion: Now you look like a KING, not a nebech.",
                            "Tzion: Here's a tip about The Shark...",
                            "Tzion: He meets his guys at the port warehouse. Every Friday.",
                            "+10 Charisma! +15 Street Creds! + Strong Hold Wax!"
                        });
                        player.setBald(true);
                        q.complete();
                    } else {
                        game.getUIManager().getDialogBox().show("Tzion: Nu? Did you find Avi yet? He hangs out near the walls.");
                    }
                } else {
                    game.getUIManager().getDialogBox().show("Tzion: Looking sharp, kapara! The ladies are watching.");
                }
            }
        };
        world.getEntityManager().addEntity(tzion);

        // Avi the Gym Boss — near the top wall area
        NPC avi = new NPC(game, world, 24 * 64, 7 * 64, "Avi the Gym Boss", Assets.avi_gym) {
            @Override
            public void interact() {
                com.ashkelord.quests.Quest q = questManager.getQuest("The Lost Gel");
                if (q == null || q.getState() != com.ashkelord.quests.Quest.STATE_IN_PROGRESS) {
                    game.getUIManager().getDialogBox().show(new String[] {
                        "Avi: What do you want, skinny?",
                        "Avi: Go eat some protein. Come back when you bench 100."
                    });
                    return;
                }
                com.ashkelord.quests.QuestTheLostGel gelQuest = (com.ashkelord.quests.QuestTheLostGel) q;
                if (gelQuest.isAviDefeated()) {
                    game.getUIManager().getDialogBox().show("Avi: Just... go. Take the stupid wax.");
                    return;
                }
                // Confrontation check — need 10+ street creds
                if (player.getStreetCreds() >= 10) {
                    game.getUIManager().getDialogBox().show(new String[] {
                        "Nadav: Give back Tzion's wax, Avi.",
                        "Avi: Or what? You'll cry about your bike?",
                        "Nadav: The whole shchuna knows what I did for Miri.",
                        "Nadav: You really want to test me?",
                        "Avi: ...Tsk. Fine. Take the stupid wax.",
                        "Avi: But stay out of the gym. This is MY territory.",
                        "Nadav: Pleasure doing business."
                    });
                    gelQuest.setAviDefeated(true);
                } else {
                    game.getUIManager().getDialogBox().show(new String[] {
                        "Nadav: Hey... give back Tzion's wax.",
                        "Avi: HAHAHAHA! Who are you? Nobody knows you.",
                        "Avi: Come back when you have some cred, kid.",
                        "(Need at least 10 Street Creds to intimidate Avi)"
                    });
                }
            }
        };
        world.getEntityManager().addEntity(avi);

        // ===== QUEST 3 NPC: ESCAPE FROM PERIPHERY =====
        
        // Yotam the Tel-Avivian — near the edge of town, lost
        NPC yotam = new NPC(game, world, 3 * 64, 20 * 64, "Yotam", Assets.yotam_telaviv) {
            @Override
            public void interact() {
                // Prerequisite: "The Lost Gel" (Tzion's Quest) must be completed first
                com.ashkelord.quests.Quest prevQuest = questManager.getQuest("The Lost Gel");
                if (prevQuest != null && !prevQuest.isCompleted()) {
                    game.getUIManager().getDialogBox().show("Yotam: Have you seen Tzion? He might need help with his gel... Go check on him!");
                    return;
                }

                com.ashkelord.quests.Quest q = questManager.getQuest("Escape from Periphery");
                if (q.getState() == com.ashkelord.quests.Quest.STATE_NOT_STARTED) {
                    q.start();
                } else if (q.getState() == com.ashkelord.quests.Quest.STATE_IN_PROGRESS) {
                    game.getUIManager().getDialogBox().show("Yotam: Please don't leave me here! I saw a guy in a tracksuit!");
                } else {
                    game.getUIManager().getDialogBox().show("Yotam: If you're ever in Tel Aviv, I know the BEST hummus spot.");
                }
            }
        };
        world.getEntityManager().addEntity(yotam);

        // Start background music
        com.ashkelord.audio.AudioManager.getInstance().playMusic("bgm_main");

        game.getUIManager().getDialogBox().show("Welcome to Ashkelon! Watch out for traffic!");
    }

    @Override
    public void tick() {
        world.tick();
        questManager.tick();
        
        // Portal check
        int ptx = (int) (player.getX() + player.getWidth() / 2) / Tile.TILEWIDTH;
        int pty = (int) (player.getY() + player.getHeight() / 2) / Tile.TILEHEIGHT;
        Portal p = portalManager.checkTrigger(ptx, pty);
        if (p != null) {
            loadWorld(p.getTargetWorldPath());
            player.setX(p.getDestSpawnX() * Tile.TILEWIDTH);
            player.setY(p.getDestSpawnY() * Tile.TILEHEIGHT);
        }
    }

    // World Switching
    public void loadWorld(String path) {
        // Clear portals from previous world
        portalManager.clear();
        
        // Save player stats/inventory if needed (here just transferring reference)
        world = new World(game, path);
        
        // Re-add player to new world
        player.setWorld(world);
        // Set player position to world spawn point
        player.setX(world.getSpawnX() * 64);
        player.setY(world.getSpawnY() * 64);
        
        // Override for specific maps if needed (optional)
        if (path.contains("port")) {
            player.setX(15 * 64);
            player.setY(17 * 64);
        }
        
        world.getEntityManager().addEntity(player);
        
        // Re-add quest stuff if needed (e.g. NPCs)
        // For main world, we need to re-add EVERYTHING if we are coming back
        if (path.contains("world1")) {
            initWorld1Entities();
        } else if (path.contains("port")) {
            initPortNPCs();
        }
    }
    
    private void initWorld1Entities() {
        // Miri and Stand references
        initMiri();
        
        // Quest Item: Golden Amba (Only if not collected? For now just re-add, unique ID logic handles dupes or simply checks if exists)
        // Actually, QuestItem logic handles "active". If we create new ones, they are active by default.
        // We should check quest state? 
        // For simplicity, we re-add everything. If the quest is done, interacting might just say "Done".
        // But for items, we might spawn duplicates.
        // Start simple: Re-add everything.
        
        // Quest Item: Golden Amba (This is actually in the PORT usually? Wait.
        // The constructor code placed Amba at "24 * 64" in world1?
        // Ah, the constructor lines 44-46 placed Amba in world1 at 24,24?
        // But the QuestTheSacredShawarma places it in Port?
        // Let's stick to the constructor logic for now.
        /* 
        com.ashkelord.entities.QuestItem amba = new com.ashkelord.entities.QuestItem(game, questManager, 
             24 * 64, 24 * 64, 64, 64, Assets.golden_amba, "The Sacred Shawarma");
        world.getEntityManager().addEntity(amba);
        */
        // Actually, QuestTheSacredShawarma handles spawning Amba in the Port when port loads.
        // So we don't need to spawn it in World1 unless it was there originally.
        // The constructor had it. I'll keep it commented out or remove if it was debug.
        // I will trust the Quest logic for items.
        
        // Cars on roads (spawning at road coordinates)
        Car car1 = new Car(world, 2 * 64, 11 * 64, 0); // White
        world.getEntityManager().addEntity(car1);

        Car car2 = new Car(world, 20 * 64, 12 * 64, 1); // Red
        world.getEntityManager().addEntity(car2);

        // Vertical cars
        Car car3 = new Car(world, 11 * 64, 2 * 64, 0); // White
        world.getEntityManager().addEntity(car3);

        Car car4 = new Car(world, 12 * 64, 25 * 64, 1); // Red
        world.getEntityManager().addEntity(car4);

        // Social NPCs (Savtas and Kids) wandering the neighborhood
        world.getEntityManager().addEntity(new SocialNPC(world, 14 * 64, 14 * 64, 0)); // Savta
        world.getEntityManager().addEntity(new SocialNPC(world, 18 * 64, 15 * 64, 1)); // Kid

        // New Ars NPC
        NPC trackArs = new NPC(game, world, 12 * 64, 7 * 64, "Tracksuit Ars", Assets.ars_walk[0][0]) {
            @Override
            public void interact() {
                game.getUIManager().getDialogBox().show("Ars: What you lookin' at, kapara?");
            }
        };
        world.getEntityManager().addEntity(trackArs);

        // ===== EASTER EGGS =====
        
        // 1. Nadav's Stolen Bike Frame — right near spawn, hard to miss
        NPC bikeFrame = new NPC(game, world, 4 * 64, 16 * 64, "Rusty Bike Frame", Assets.rusty_bike) {
            @Override
            public void interact() {
                game.getUIManager().getDialogBox().show(new String[] {
                    "Your old electric bike... the battery is gone.",
                    "Liran took everything.",
                    "The frame is rusted. The tires are flat.",
                    "But you still remember the sunset rides with Shirel."
                });
            }
        };
        world.getEntityManager().addEntity(bikeFrame);

        // 2. Shirel's Selfie Wall — opposite side of spawn
        NPC shirelWall = new NPC(game, world, 17 * 64, 16 * 64, "Graffiti Wall", Assets.graffiti_wall) {
            private boolean found = false;
            @Override
            public void interact() {
                if (!found) {
                    game.getUIManager().getDialogBox().show(new String[] {
                        "Someone spray-painted 'Shirel + Liran' inside a heart.",
                        "Your blood boils.",
                        "Underneath it says: 'The Prince is FINISHED'",
                        "You clench your fist. Not yet.",
                        "+2 Street Creds"
                    });
                    // Find player and add creds
                    for (com.ashkelord.entities.Entity e : world.getEntityManager().getEntities()) {
                        if (e instanceof Player) {
                            ((Player) e).addStreetCreds(2);
                            break;
                        }
                    }
                    found = true;
                } else {
                    game.getUIManager().getDialogBox().show("The graffiti stares back at you. Motivation.");
                }
            }
        };
        world.getEntityManager().addEntity(shirelWall);

        // 3. Abu Rafi — wise old man sitting on bench near spawn
        NPC abuRafi = new NPC(game, world, 8 * 64, 19 * 64, "Abu Rafi", Assets.abu_rafi) {
            private int talkCount = 0;
            @Override
            public void interact() {
                switch (talkCount % 4) {
                    case 0:
                        game.getUIManager().getDialogBox().show(new String[] {
                            "Abu Rafi: Ah, Nadav. Sit, sit.",
                            "Abu Rafi: You know what your father used to say?",
                            "Abu Rafi: 'The strongest chain is honor, not gold.'"
                        });
                        break;
                    case 1:
                        game.getUIManager().getDialogBox().show(new String[] {
                            "Abu Rafi: I see you looking for trouble.",
                            "Abu Rafi: Liran thinks he owns the port.",
                            "Abu Rafi: But the port remembers who was there first."
                        });
                        break;
                    case 2:
                        game.getUIManager().getDialogBox().show(new String[] {
                            "Abu Rafi: You want to beat The Shark?",
                            "Abu Rafi: Don't fight with fists. Fight with respect.",
                            "Abu Rafi: Earn the shchuna's trust, and Liran has nothing."
                        });
                        break;
                    case 3:
                        game.getUIManager().getDialogBox().show(new String[] {
                            "Abu Rafi: Your savta would be proud, ya ibn.",
                            "Abu Rafi: Now go. The amba won't find itself."
                        });
                        break;
                }
                talkCount++;
            }
        };
        world.getEntityManager().addEntity(abuRafi);

        // ===== QUEST 2 NPCS: THE LOST GEL =====
        
        // Tzion the Barber — near the buildings, waiting for help
        NPC tzion = new NPC(game, world, 6 * 64, 18 * 64, "Tzion the Barber", Assets.tzion_barber) {
            @Override
            public void interact() {
                // Prerequisite: "The Sacred Shawarma" (Miri's Quest) must be completed first
                com.ashkelord.quests.Quest prevQuest = questManager.getQuest("The Sacred Shawarma");
                if (prevQuest != null && !prevQuest.isCompleted()) {
                    game.getUIManager().getDialogBox().show("Tzion: Nadav! Miri is looking for you. Go help her first!");
                    return;
                }
                
                com.ashkelord.quests.Quest q = questManager.getQuest("The Lost Gel");
                if (q.getState() == com.ashkelord.quests.Quest.STATE_NOT_STARTED) {
                    q.start();
                } else if (q.getState() == com.ashkelord.quests.Quest.STATE_IN_PROGRESS) {
                    com.ashkelord.quests.QuestTheLostGel gelQuest = (com.ashkelord.quests.QuestTheLostGel) q;
                    if (gelQuest.isAviDefeated()) {
                        game.getUIManager().getDialogBox().show(new String[] {
                            "Tzion: You got the wax! Yallah, sit down!",
                            "Tzion: *snip snip* Look at this... MAGNIFICENT.",
                            "Tzion: Now you look like a KING, not a nebech.",
                            "Tzion: Here's a tip about The Shark...",
                            "Tzion: He meets his guys at the port warehouse. Every Friday.",
                            "+10 Charisma! +15 Street Creds! +Strong Hold Wax!"
                        });
                        player.setBald(true);
                        q.complete();
                    } else {
                        game.getUIManager().getDialogBox().show("Tzion: Nu? Did you find Avi yet? He hangs out near the walls.");
                    }
                } else {
                    game.getUIManager().getDialogBox().show("Tzion: Looking sharp, kapara! The ladies are watching.");
                }
            }
        };
        world.getEntityManager().addEntity(tzion);

        // Avi the Gym Boss — near the top wall area
        NPC avi = new NPC(game, world, 24 * 64, 7 * 64, "Avi the Gym Boss", Assets.avi_gym) {
            @Override
            public void interact() {
                com.ashkelord.quests.Quest q = questManager.getQuest("The Lost Gel");
                if (q == null || q.getState() != com.ashkelord.quests.Quest.STATE_IN_PROGRESS) {
                    game.getUIManager().getDialogBox().show(new String[] {
                        "Avi: What do you want, skinny?",
                        "Avi: Go eat some protein. Come back when you bench 100."
                    });
                    return;
                }
                com.ashkelord.quests.QuestTheLostGel gelQuest = (com.ashkelord.quests.QuestTheLostGel) q;
                if (gelQuest.isAviDefeated()) {
                    game.getUIManager().getDialogBox().show("Avi: Just... go. Take the stupid wax.");
                    return;
                }
                // Confrontation check — need 10+ street creds
                if (player.getStreetCreds() >= 10) {
                    game.getUIManager().getDialogBox().show(new String[] {
                        "Nadav: Give back Tzion's wax, Avi.",
                        "Avi: Or what? You'll cry about your bike?",
                        "Nadav: The whole shchuna knows what I did for Miri.",
                        "Nadav: You really want to test me?",
                        "Avi: ...Tsk. Fine. Take the stupid wax.",
                        "Avi: But stay out of the gym. This is MY territory.",
                        "Nadav: Pleasure doing business."
                    });
                    gelQuest.setAviDefeated(true);
                } else {
                    game.getUIManager().getDialogBox().show(new String[] {
                        "Nadav: Hey... give back Tzion's wax.",
                        "Avi: HAHAHAHA! Who are you? Nobody knows you.",
                        "Avi: Come back when you have some cred, kid.",
                        "(Need at least 10 Street Creds to intimidate Avi)"
                    });
                }
            }
        };
        world.getEntityManager().addEntity(avi);

        // ===== QUEST 3 NPC: ESCAPE FROM PERIPHERY =====
        
        // Yotam the Tel-Avivian — near the edge of town, lost
        NPC yotam = new NPC(game, world, 3 * 64, 20 * 64, "Yotam", Assets.yotam_telaviv) {
            @Override
            public void interact() {
                // Prerequisite: "The Lost Gel" (Tzion's Quest) must be completed first
                com.ashkelord.quests.Quest prevQuest = questManager.getQuest("The Lost Gel");
                if (prevQuest != null && !prevQuest.isCompleted()) {
                    game.getUIManager().getDialogBox().show("Yotam: Have you seen Tzion? He might need help with his gel... Go check on him!");
                    return;
                }

                com.ashkelord.quests.Quest q = questManager.getQuest("Escape from Periphery");
                if (q.getState() == com.ashkelord.quests.Quest.STATE_NOT_STARTED) {
                    q.start();
                } else if (q.getState() == com.ashkelord.quests.Quest.STATE_IN_PROGRESS) {
                    game.getUIManager().getDialogBox().show("Yotam: Please don't leave me here! I saw a guy in a tracksuit!");
                } else {
                    game.getUIManager().getDialogBox().show("Yotam: If you're ever in Tel Aviv, I know the BEST hummus spot.");
                }
            }
            
            // Make Yotam Ethereal so he doesn't block the player on return
            @Override
            public java.awt.Rectangle getCollisionBounds(float xOffset, float yOffset) {
                return new java.awt.Rectangle(0, 0, 0, 0);
            }
        };
        world.getEntityManager().addEntity(yotam);
    }
    
    private void initPortNPCs() {
        // Original port soldier
        NPC soldier = new NPC(game, world, 18 * 64, 5 * 64, "Soldier", Assets.soldier_walk[0][0]) {
            @Override
            public void interact() {
                game.getUIManager().getDialogBox().show("Soldier: Rough day at the port. Have you seen any kebabs?");
            }
        };
        world.getEntityManager().addEntity(soldier);

        // === PORT EASTER EGGS ===

        // 4. Honda Civic Shrine — Liran's blacked-out car at the docks
        NPC hondaShrine = new NPC(game, world, 20 * 64, 14 * 64, "Blacked-Out Honda Civic", Assets.cars[1][0]) {
            @Override
            public void interact() {
                game.getUIManager().getDialogBox().show(new String[] {
                    "A blacked-out Honda Civic with tinted windows.",
                    "The license plate reads: 'SHARK-1'",
                    "There's a Maccabi Ashkelon sticker on the bumper.",
                    "This is Liran's ride. He's around here somewhere."
                });
            }
        };
        world.getEntityManager().addEntity(hondaShrine);

        // 5. Fisherman Yossi — soldier fishing off the dock
        NPC yossi = new NPC(game, world, 22 * 64, 4 * 64, "Fisherman Yossi", Assets.soldier_walk[0][0]) {
            private int chatIndex = 0;
            @Override
            public void interact() {
                switch (chatIndex % 3) {
                    case 0:
                        game.getUIManager().getDialogBox().show(new String[] {
                            "Yossi: Sheket! I'm fishing!",
                            "Yossi: You know what they say about Ashkelon fish?",
                            "Yossi: They taste like golden amba. Wallah."
                        });
                        break;
                    case 1:
                        game.getUIManager().getDialogBox().show(new String[] {
                            "Yossi: My commander sent me to guard the port.",
                            "Yossi: I'm guarding it... from a chair... with a fishing rod.",
                            "Yossi: Military intelligence, achi."
                        });
                        break;
                    case 2:
                        game.getUIManager().getDialogBox().show(new String[] {
                            "Yossi: You're that Nadav guy, right?",
                            "Yossi: I heard The Shark stores stuff in the warehouse.",
                            "Yossi: Not that I saw anything. I'm just a fisherman."
                        });
                        break;
                }
                chatIndex++;
            }
        };
        world.getEntityManager().addEntity(yossi);

        // === E-BIKE BATTERY (findable quest item in warehouse area) ===
        com.ashkelord.entities.QuestItem battery = new com.ashkelord.entities.QuestItem(
            game, questManager, 14 * 64, 8 * 64, 64, 64, Assets.ebike_battery, null) {
            @Override
            public void interact() {
                game.getUIManager().getDialogBox().show(new String[] {
                    "You found an E-Bike Battery!",
                    "Wait... this is YOUR battery. The one Liran stole!",
                    "It still has charge. Your hands tremble.",
                    "+1 E-Bike Battery"
                });
                // Add to inventory
                for (com.ashkelord.entities.Entity e : world.getEntityManager().getEntities()) {
                    if (e instanceof Player) {
                        ((Player) e).getInventory().addItem(
                            com.ashkelord.items.Item.ebikeBatteryItem.createNew(1));
                        break;
                    }
                }
                // Remove from world
                setActive(false);
            }
        };
        world.getEntityManager().addEntity(battery);
    }
    
    // Add quest item to current world (called by Quest)
    public void setQuestItem(com.ashkelord.entities.QuestItem item) {
        world.getEntityManager().addEntity(item);
    }

    private void initMiri() {
         NPC shawarmaGuy = new NPC(game, world, 15 * 64, 16 * 64,
                "Miri Kapara", Assets.miri_kapara) {
            @Override
            public void interact() {
                com.ashkelord.quests.Quest q = questManager.getQuest("The Sacred Shawarma");
                if (q.getState() == com.ashkelord.quests.Quest.STATE_NOT_STARTED) {
                     q.start();
                } else if (q.getState() == com.ashkelord.quests.Quest.STATE_IN_PROGRESS) {
                     game.getUIManager().getDialogBox().show("Miri: Don't forget the Amba! It's at the port.");
                } else if (q.getState() == com.ashkelord.quests.Quest.STATE_RETURN_TO_NPC) {
                     game.getUIManager().getDialogBox().show(new String[] {
                        "Miri: Start of the line!",
                        "Miri: Wait... is that the Golden Amba?",
                        "Miri: Toda raba kapara! You saved the business!",
                        "Miri: Take this heavy laffa as a token of my gratitude.",
                        "Miri: The whole shchuna is talking about you. +10 Street Creds!"
                     });
                     q.complete();
                     // Add rewards here if needed
                } else {
                     game.getUIManager().getDialogBox().show("Miri: You are a true tzadik, Nadav.");
                }
            }
        };
        world.getEntityManager().addEntity(shawarmaGuy);
        
        NPC shawarmaStand = new NPC(game, world, 15 * 64, 15 * 64, "Shawarma Stand", Assets.shawarma_stand) {
             @Override
             public void interact() {
                 game.getUIManager().getDialogBox().show("Best Shawarma in Ashkelon.");
             }
        };
        world.getEntityManager().addEntity(shawarmaStand);
    }

    @Override
    public void render(Renderer r) {
        Graphics g = r.getRawGraphics();
        world.render(g);
        questManager.render(g);
        if (game.getUIManager() != null) {
            game.getUIManager().render(g);
        }
        if (hud != null && player != null) {
            hud.render(g, player);
            player.getInventory().render(g);
        }
        
        // Mission Passed Screen REMOVED
    }

    public World getWorld() {
        return world;
    }
    
    public QuestManager getQuestManager() {
        return questManager;
    }
    
    public PortalManager getPortalManager() {
        return portalManager;
    }
    
    public Player getPlayer() {
        return player;
    }
}
