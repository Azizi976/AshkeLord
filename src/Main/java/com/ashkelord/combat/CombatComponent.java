package com.ashkelord.combat;

import com.ashkelord.entities.creatures.Creature;
import com.ashkelord.worlds.World;

/**
 * Manages a creature's equipped attacks with three slots:
 * primary (Space), secondary (Shift), and special (Q).
 * Ticks all cooldowns automatically.
 */
public class CombatComponent {

    private Attack primaryAttack;
    private Attack secondaryAttack;
    private Attack specialAttack;

    public CombatComponent(Attack primary, Attack secondary, Attack special) {
        this.primaryAttack = primary;
        this.secondaryAttack = secondary;
        this.specialAttack = special;
    }

    /** Tick all cooldown timers — call once per frame. */
    public void tickCooldowns() {
        if (primaryAttack != null) primaryAttack.tick();
        if (secondaryAttack != null) secondaryAttack.tick();
        if (specialAttack != null) specialAttack.tick();
    }

    public void executePrimary(Creature source, World world, int direction) {
        if (primaryAttack != null && primaryAttack.isReady()) {
            primaryAttack.execute(source, world, direction);
        }
    }

    public void executeSecondary(Creature source, World world, int direction) {
        if (secondaryAttack != null && secondaryAttack.isReady()) {
            secondaryAttack.execute(source, world, direction);
        }
    }

    public void executeSpecial(Creature source, World world, int direction) {
        if (specialAttack != null && specialAttack.isReady()) {
            specialAttack.execute(source, world, direction);
        }
    }

    // --- Getters / Setters ---

    public Attack getPrimaryAttack() { return primaryAttack; }
    public void setPrimaryAttack(Attack primary) { this.primaryAttack = primary; }

    public Attack getSecondaryAttack() { return secondaryAttack; }
    public void setSecondaryAttack(Attack secondary) { this.secondaryAttack = secondary; }

    public Attack getSpecialAttack() { return specialAttack; }
    public void setSpecialAttack(Attack special) { this.specialAttack = special; }
}
