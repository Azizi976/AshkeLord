package com.ashkelord.combat;

import com.ashkelord.entities.Entity;
import com.ashkelord.entities.creatures.Creature;
import com.ashkelord.worlds.World;
import java.awt.Rectangle;

/**
 * Directional melee attack — creates a hitbox in front of the source
 * creature and damages all Creatures within it.
 */
public class MeleeAttack implements Attack {

    private final String name;
    private final int damage;
    private final float cooldownSeconds;

    private int cooldownTicks;
    private int ticksSinceLastUse;

    // Hitbox dimensions (reach in front, width perpendicular)
    private static final int REACH = 32;
    private static final int WIDTH = 48;

    public MeleeAttack(String name, int damage, float cooldownSeconds) {
        this.name = name;
        this.damage = damage;
        this.cooldownSeconds = cooldownSeconds;
        this.cooldownTicks = (int) (cooldownSeconds * 60); // 60fps
        this.ticksSinceLastUse = cooldownTicks; // Ready immediately
    }

    @Override
    public String getName() { return name; }

    @Override
    public int getDamage() { return damage; }

    @Override
    public float getCooldownSeconds() { return cooldownSeconds; }

    @Override
    public AttackType getType() { return AttackType.MELEE; }

    @Override
    public boolean isReady() { return ticksSinceLastUse >= cooldownTicks; }

    @Override
    public void tick() { ticksSinceLastUse++; }

    @Override
    public void execute(Creature source, World world, int direction) {
        if (!isReady()) return;
        ticksSinceLastUse = 0;

        // Build hitbox in front of the source based on direction
        float sx = source.getX();
        float sy = source.getY();
        int sw = source.getWidth();
        int sh = source.getHeight();

        Rectangle hitbox;
        switch (direction) {
            case 0: // Down
                hitbox = new Rectangle((int) sx - 8, (int) sy + sh, WIDTH, REACH);
                break;
            case 1: // Up
                hitbox = new Rectangle((int) sx - 8, (int) sy - REACH, WIDTH, REACH);
                break;
            case 2: // Left
                hitbox = new Rectangle((int) sx - REACH, (int) sy - 8, REACH, WIDTH);
                break;
            case 3: // Right
                hitbox = new Rectangle((int) sx + sw, (int) sy - 8, REACH, WIDTH);
                break;
            default:
                return;
        }

        // Check all entities for intersection
        for (Entity e : world.getEntityManager().getEntities()) {
            if (e.equals(source)) continue;
            if (e instanceof Creature) {
                Creature target = (Creature) e;
                if (target.getCollisionBounds(0, 0).intersects(hitbox)) {
                    target.hurt(damage);
                }
            }
        }
    }
}
