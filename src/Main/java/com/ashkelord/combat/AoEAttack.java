package com.ashkelord.combat;

import com.ashkelord.entities.Entity;
import com.ashkelord.entities.creatures.Creature;
import com.ashkelord.worlds.World;

/**
 * Area-of-Effect attack — damages all creatures within a circular radius
 * centered on the source creature.
 * Used for the "Techno Music" ability in Intermediate Ars form.
 */
public class AoEAttack implements Attack {

    private final String name;
    private final int damage;
    private final float cooldownSeconds;
    private final int radius; // In pixels

    private int cooldownTicks;
    private int ticksSinceLastUse;

    public AoEAttack(String name, int damage, float cooldownSeconds, int radius) {
        this.name = name;
        this.damage = damage;
        this.cooldownSeconds = cooldownSeconds;
        this.radius = radius;
        this.cooldownTicks = (int) (cooldownSeconds * 60);
        this.ticksSinceLastUse = cooldownTicks;
    }

    @Override
    public String getName() { return name; }

    @Override
    public int getDamage() { return damage; }

    @Override
    public float getCooldownSeconds() { return cooldownSeconds; }

    @Override
    public AttackType getType() { return AttackType.AOE; }

    @Override
    public boolean isReady() { return ticksSinceLastUse >= cooldownTicks; }

    @Override
    public void tick() { ticksSinceLastUse++; }

    @Override
    public void execute(Creature source, World world, int direction) {
        if (!isReady()) return;
        ticksSinceLastUse = 0;

        // Center of the source creature
        float cx = source.getX() + source.getWidth() / 2f;
        float cy = source.getY() + source.getHeight() / 2f;

        // Check all entities within radius
        for (Entity e : world.getEntityManager().getEntities()) {
            if (e.equals(source)) continue;
            if (e instanceof Creature) {
                Creature target = (Creature) e;
                float tx = target.getX() + target.getWidth() / 2f;
                float ty = target.getY() + target.getHeight() / 2f;

                float dx = cx - tx;
                float dy = cy - ty;
                float distSq = dx * dx + dy * dy;

                if (distSq <= (float) radius * radius) {
                    target.hurt(damage);
                }
            }
        }
    }

    public int getRadius() { return radius; }
}
