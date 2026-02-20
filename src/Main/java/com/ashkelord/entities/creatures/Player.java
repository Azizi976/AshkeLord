package com.ashkelord.entities.creatures;

import com.ashkelord.gfx.Animation;
import com.ashkelord.gfx.Assets;
import com.ashkelord.main.Game;
import com.ashkelord.worlds.World;
import com.ashkelord.entities.behaviors.EvolutionForm;
import com.ashkelord.entities.behaviors.BabyArsForm;
import com.ashkelord.entities.behaviors.IntermediateArsForm;
import java.awt.Graphics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import com.ashkelord.entities.projectiles.Spit;

public class Player extends Creature {

    private Game game;

    // Walk animations: 0=down, 1=up, 2=left, 3=right
    private Animation animDown, animUp, animLeft, animRight;
    private int lastDir = 0; // 0=down by default

    // Stats
    private int streetCreds = 0;
    private int nervim = 10;
    private int charisma = 0;
    
    // Evolution
    private EvolutionForm currentForm;
    private boolean evolved = false;
    
    // Speed buff (Cortado)
    private float speedMultiplier = 1.0f;
    private int speedBuffTimer = 0; // ticks remaining
    
    // Inventory
    private com.ashkelord.inventory.Inventory inventory;
    
    // Spitting
    private long lastSpitTime;
    private static final long SPIT_COOLDOWN = 500; // ms

    public Player(Game game, World world, float x, float y) {
        super(world, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);
        this.game = game;
        
        inventory = new com.ashkelord.inventory.Inventory(game);
        currentForm = new BabyArsForm();

        // Hitbox covers only the feet area
        bounds.x = 22;
        bounds.y = 44;
        bounds.width = 19;
        bounds.height = 19;

        // Init walk animations (120ms per frame for smooth walk)
        animDown = new Animation(120, Assets.player_walk[0]);
        animUp = new Animation(120, Assets.player_walk[1]);
        animLeft = new Animation(120, Assets.player_walk[2]);
        animRight = new Animation(120, Assets.player_walk[3]);
    }

    @Override
    public void tick() {
        // Tick all animations
        animDown.tick();
        animUp.tick();
        animLeft.tick();
        animRight.tick();
        
        // Inventory
        inventory.tick();
        
        // Speed buff countdown
        if (speedBuffTimer > 0) {
            speedBuffTimer--;
            if (speedBuffTimer <= 0) {
                speedMultiplier = 1.0f;
            }
        }
        
        // Evolution check
        checkEvolution();

        getInput();
        move();
        game.getGameCamera().centerOnEntity(this);
    }

    private void checkEvolution() {
        if (evolved) return;
        if (streetCreds < 500) return;
        
        // Check for e-bike battery in inventory
        boolean hasBattery = false;
        for (int i = 0; i < inventory.getItems().size(); i++) {
            if (inventory.getItems().get(i).getName().equals("E-Bike Battery")) {
                hasBattery = true;
                break;
            }
        }
        
        if (hasBattery) {
            evolve();
        }
    }
    
    private void evolve() {
        evolved = true;
        currentForm = new IntermediateArsForm();
        // Apply permanent speed boost
        speed = DEFAULT_SPEED * (float) currentForm.getSpeedModifier();
        
        game.getUIManager().getDialogBox().show(new String[] {
            "Something stirs inside you...",
            "The battery hums with power. Your cred radiates.",
            "You feel the streets RESPECT you now.",
            "*** EVOLUTION: INTERMEDIATE ARS FORM ***",
            "Speed x1.5 | New Attack: Techno Music (AoE)",
            "New Special: Achi, yesh lecha esh? (Stun)",
            "The Shark better watch out."
        });
    }

    private void getInput() {
        xMove = 0;
        yMove = 0;

        // Block movement if dialogue is active
        if (game.getUIManager() != null && game.getUIManager().getDialogBox().isActive()) {
            // Check for advance input (e.g. Enter)
            if (game.getKeyManager().enterJustPressed) {
                 game.getKeyManager().enterJustPressed = false;
                 game.getUIManager().getDialogBox().advance();
            }
            return;
        }
        
        if (inventory.isActive())
            return;

        float effectiveSpeed = speed * speedMultiplier * (float) currentForm.getSpeedModifier();
        // Prevent double-applying form modifier if already baked into speed
        if (evolved) {
            effectiveSpeed = speed * speedMultiplier; // speed already has form modifier
        }

        if (game.getKeyManager().up) {
            yMove = -effectiveSpeed;
            lastDir = 1;
        }
        if (game.getKeyManager().down) {
            yMove = effectiveSpeed;
            lastDir = 0;
        }
        if (game.getKeyManager().left) {
            xMove = -effectiveSpeed;
            lastDir = 2;
        }
        if (game.getKeyManager().right) {
            xMove = effectiveSpeed;
            lastDir = 3;
        }
        
        // Interaction
        if (game.getKeyManager().enterJustPressed) {
            game.getKeyManager().enterJustPressed = false;
            checkInteraction();
        }
        
        // Spitting
        if (game.getKeyManager().space) {
            long now = System.currentTimeMillis();
            if (now - lastSpitTime > SPIT_COOLDOWN) {
                spit();
                lastSpitTime = now;
            }
        }
    }
    
    private void spit() {
        // Spawn spit projectile
        // Adjust spawn position to be near mouth/center
        float sx = x + width / 2 - 8;
        float sy = y + height / 2 - 8;
        
        // Offset based on direction
        if (lastDir == 0) sy += 10;
        else if (lastDir == 1) sy -= 10;
        else if (lastDir == 2) sx -= 10;
        else if (lastDir == 3) sx += 10;
        
        world.getEntityManager().addEntity(new Spit(world, sx, sy, lastDir));
        
        // Optional: Play sound
    }
    
    private void checkInteraction() {
        for (com.ashkelord.entities.Entity e : world.getEntityManager().getEntities()) {
            if (e.equals(this)) continue;
            
            float dx = (x + width / 2) - (e.getX() + e.getWidth() / 2);
            float dy = (y + height / 2) - (e.getY() + e.getHeight() / 2);
            if (Math.abs(dx) < 48 && Math.abs(dy) < 48) {
                e.interact();
                return;
            }
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(getCurrentFrame(),
                (int) (x - game.getGameCamera().getxOffset()),
                (int) (y - game.getGameCamera().getyOffset()),
                width, height, null);
    }

    private BufferedImage getCurrentFrame() {
        // Spitting Animation Override (show for 300ms)
        if (System.currentTimeMillis() - lastSpitTime < 300) {
            return Assets.player_spit[lastDir];
        }
    
        if (xMove == 0 && yMove == 0) {
            return Assets.player_walk[lastDir][0];
        }

        switch (lastDir) {
            case 1:
                return animUp.getCurrentFrame();
            case 2:
                return animLeft.getCurrentFrame();
            case 3:
                return animRight.getCurrentFrame();
            default:
                return animDown.getCurrentFrame();
        }
    }
    
    // Speed buff
    public void applySpeedBuff(float multiplier, int durationTicks) {
        this.speedMultiplier = multiplier;
        this.speedBuffTimer = durationTicks;
    }

    public Game getGame() {
        return game;
    }

    // Stats Getters/Setters
    public int getStreetCreds() {
        return streetCreds;
    }

    public void setStreetCreds(int streetCreds) {
        this.streetCreds = streetCreds;
    }

    public void addStreetCreds(int amount) {
        this.streetCreds += amount;
    }

    public int getNervim() {
        return nervim;
    }

    public void setNervim(int nervim) {
        this.nervim = nervim;
    }

    public void addNervim(int amount) {
        this.nervim += amount;
        if (this.nervim < 0)
            this.nervim = 0;
        if (this.nervim > 100)
            this.nervim = 100;
    }
    
    public int getCharisma() {
        return charisma;
    }
    
    public void addCharisma(int amount) {
        this.charisma += amount;
    }
    
    public EvolutionForm getCurrentForm() {
        return currentForm;
    }
    
    public boolean hasEvolved() {
        return evolved;
    }
    
    public boolean hasSpeedBuff() {
        return speedBuffTimer > 0;
    }
    
    public com.ashkelord.inventory.Inventory getInventory() {
        return inventory;
    }
}