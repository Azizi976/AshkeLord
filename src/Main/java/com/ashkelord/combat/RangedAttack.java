package com.ashkelord.combat;

import com.ashkelord.entities.creatures.Creature;
import com.ashkelord.entities.projectiles.Spit;
import com.ashkelord.worlds.World;

/**
 * Ranged attack — spawns a Spit projectile in the given direction.
 * Wraps the existing Spit entity logic into the Attack interface.
 */
public class RangedAttack implements Attack {

    private final String name;
    private final int damage;
    private final float cooldownSeconds;

    private int cooldownTicks;
    private int ticksSinceLastUse;

    public RangedAttack(String name, int damage, float cooldownSeconds) {
        this.name = name;
        this.damage = damage;
        this.cooldownSeconds = cooldownSeconds;
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
    public AttackType getType() { return AttackType.RANGED; }

    @Override
    public boolean isReady() { return ticksSinceLastUse >= cooldownTicks; }

    @Override
    public void tick() { ticksSinceLastUse++; }

    @Override
    public void execute(Creature source, World world, int direction) {
        if (!isReady()) return;
        ticksSinceLastUse = 0;

        // Calculate spawn position (same logic as old Player.spit())
        float sx = source.getX() + source.getWidth() / 2 - 8;
        float sy = source.getY() + source.getHeight() / 2 - 8;

        if (direction == 0) sy += 10;
        else if (direction == 1) sy -= 10;
        else if (direction == 2) sx -= 10;
        else if (direction == 3) sx += 10;

        Spit spit = new Spit(world, sx, sy, direction);
        spit.setOwner(source);
        world.getEntityManager().addEntity(spit);
    }
}
