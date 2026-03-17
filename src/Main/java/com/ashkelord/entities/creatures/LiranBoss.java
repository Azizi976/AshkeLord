package com.ashkelord.entities.creatures;

import com.ashkelord.gfx.Assets;
import com.ashkelord.main.Game;
import com.ashkelord.worlds.World;
import com.ashkelord.combat.CombatComponent;
import com.ashkelord.combat.RangedAttack;
import com.ashkelord.ai.Pathfinder;
import com.ashkelord.ai.ChaseBehavior;
import com.ashkelord.entities.Entity;
import java.awt.Graphics;
import java.util.Random;

public class LiranBoss extends Creature {

    private Game game;
    private CombatComponent combatComponent;
    private ChaseBehavior chaseBehavior;
    private Entity target; // Player to chase
    private Random random;
    
    public LiranBoss(Game game, World world, float x, float y, Entity target) {
        super(world, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);
        this.game = game;
        this.target = target;
        this.random = new Random();
        this.health = 100;
        
        // Boss combat
        combatComponent = new CombatComponent(
            new RangedAttack("Shark Spit", 10, 1.5f),
            null, null
        );
        
        // A* pathfinding chase (256px aggro range)
        chaseBehavior = new ChaseBehavior(new Pathfinder(world), 256f);
        
        bounds.x = 16;
        bounds.y = 32;
        bounds.width = 32;
        bounds.height = 32;
    }

    @Override
    public void tick() {
        combatComponent.tickCooldowns();
        
        // A* chase AI
        chaseBehavior.update(this, target);
        move();
        
        // Attack toward player if in range
        if (target != null) {
            float dx = target.getX() - x;
            float dy = target.getY() - y;
            int dir;
            if (Math.abs(dx) > Math.abs(dy)) {
                dir = dx > 0 ? 3 : 2; // Right or Left
            } else {
                dir = dy > 0 ? 0 : 1; // Down or Up
            }
            combatComponent.executePrimary(this, world, dir);
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.liran_boss, (int) (x - game.getGameCamera().getxOffset()), 
                (int) (y - game.getGameCamera().getyOffset()), width, height, null);
    }

    @Override
    public void die() {
        System.out.println("Liran Defeated!");
    }
}
