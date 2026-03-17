package com.ashkelord.entities.creatures;

import com.ashkelord.gfx.Animation;
import com.ashkelord.gfx.Assets;
import com.ashkelord.main.Game;
import com.ashkelord.worlds.World;
import com.ashkelord.entities.behaviors.EvolutionForm;
import com.ashkelord.entities.behaviors.BabyArsForm;
import com.ashkelord.entities.behaviors.IntermediateArsForm;
import com.ashkelord.combat.CombatComponent;
import com.ashkelord.combat.RangedAttack;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Player extends Creature {

    private Game game;

    // Walk animations: 0=down, 1=up, 2=left, 3=right
    private Animation animDown, animUp, animLeft, animRight;
    private Animation animBaldDown, animBaldUp, animBaldLeft, animBaldRight;
    private int lastDir = 0; // 0=down by default
    private boolean isBald = false;

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
    
    // Combat
    private CombatComponent combatComponent;
    private long lastAttackTime; // For animation display
    
    // Action State for animation switching
    private ActionState actionState = ActionState.IDLE;

    public Player(Game game, World world, float x, float y) {
        super(world, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);
        this.game = game;
        
        inventory = new com.ashkelord.inventory.Inventory(game);
        currentForm = new BabyArsForm();
        
        // Initialize combat from form
        combatComponent = new CombatComponent(
            currentForm.getPrimaryAttack(),
            new RangedAttack("Spit", 5, 0.5f),  // Secondary: always spit
            currentForm.getSpecialAttack()
        );

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
        
        animBaldDown = new Animation(120, Assets.player_bald_walk[0]);
        animBaldUp = new Animation(120, Assets.player_bald_walk[1]);
        animBaldLeft = new Animation(120, Assets.player_bald_walk[2]);
        animBaldRight = new Animation(120, Assets.player_bald_walk[3]);
    }

    @Override
    public void tick() {
        // Tick all animations
        animDown.tick();
        animUp.tick();
        animLeft.tick();
        animRight.tick();
        
        animBaldDown.tick();
        animBaldUp.tick();
        animBaldLeft.tick();
        animBaldRight.tick();
        
        // Inventory
        inventory.tick();
        
        // Combat cooldowns
        combatComponent.tickCooldowns();
        
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
        
        // Update action state based on movement
        if (xMove != 0 || yMove != 0) {
            actionState = ActionState.WALKING;
        } else if (actionState == ActionState.WALKING) {
            actionState = ActionState.IDLE;
        }
        
        // Auto-reset from ATTACKING back to IDLE (after a short delay)
        if (actionState == ActionState.ATTACKING) {
            if (System.currentTimeMillis() - lastAttackTime > 300) {
                actionState = ActionState.IDLE;
            }
        }
        
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
        
        // Update combat attacks from new form
        combatComponent.setPrimaryAttack(currentForm.getPrimaryAttack());
        combatComponent.setSpecialAttack(currentForm.getSpecialAttack());
        
        game.getUIManager().getDialogBox().show(new String[] {
            "Something stirs inside you...",
            "The battery hums with power. Your cred radiates.",
            "You feel the streets RESPECT you now.",
            "*** EVOLUTION: INTERMEDIATE ARS FORM ***",
            "Speed x1.5 | New Attack: Slap (Melee)",
            "New Special: Techno Music (AoE)",
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
        
        // Combat: Primary (Space), Secondary (Shift), Special (Q)
        if (game.getKeyManager().spaceJustPressed) {
            combatComponent.executePrimary(this, world, lastDir);
            lastAttackTime = System.currentTimeMillis();
            actionState = ActionState.ATTACKING;
        }
        if (game.getKeyManager().shiftJustPressed) {
            combatComponent.executeSecondary(this, world, lastDir);
            lastAttackTime = System.currentTimeMillis();
            actionState = ActionState.ATTACKING;
        }
        if (game.getKeyManager().qJustPressed) {
            combatComponent.executeSpecial(this, world, lastDir);
            lastAttackTime = System.currentTimeMillis();
            actionState = ActionState.ATTACKING;
        }
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
        // Attack Animation Override (show for 300ms)
        if (System.currentTimeMillis() - lastAttackTime < 300) {
            return isBald ? Assets.player_bald_spit[lastDir] : Assets.player_spit[lastDir];
        }
    
        if (xMove == 0 && yMove == 0) {
            return isBald ? Assets.player_bald_walk[lastDir][0] : Assets.player_walk[lastDir][0];
        }

        switch (lastDir) {
            case 1:
                return isBald ? animBaldUp.getCurrentFrame() : animUp.getCurrentFrame();
            case 2:
                return isBald ? animBaldLeft.getCurrentFrame() : animLeft.getCurrentFrame();
            case 3:
                return isBald ? animBaldRight.getCurrentFrame() : animRight.getCurrentFrame();
            default:
                return isBald ? animBaldDown.getCurrentFrame() : animDown.getCurrentFrame();
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
    
    public void setBald(boolean bald) {
        this.isBald = bald;
    }
    
    public boolean isBald() {
        return this.isBald;
    }
}