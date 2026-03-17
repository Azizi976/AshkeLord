package com.ashkelord.combat;

import com.ashkelord.entities.creatures.Creature;
import com.ashkelord.worlds.World;

/**
 * Strategy interface for all attack types.
 * Each attack manages its own cooldown and execution logic.
 */
public interface Attack {

    /** Display name of the attack. */
    String getName();

    /** Base damage per hit. */
    int getDamage();

    /** Cooldown in seconds between uses. */
    float getCooldownSeconds();

    /** Category of attack. */
    AttackType getType();

    /**
     * Execute the attack from the given source creature in the given direction.
     * @param source The creature performing the attack
     * @param world  The world context (for spawning entities, collision checks)
     * @param direction 0=down, 1=up, 2=left, 3=right
     */
    void execute(Creature source, World world, int direction);

    /** Whether the cooldown has elapsed and the attack can fire. */
    boolean isReady();

    /** Tick the internal cooldown timer. Call once per frame. */
    void tick();
}
