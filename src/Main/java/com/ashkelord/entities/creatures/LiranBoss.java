package com.ashkelord.entities.creatures;

import com.ashkelord.gfx.Assets;
import com.ashkelord.main.Game;
import com.ashkelord.worlds.World;
import com.ashkelord.entities.projectiles.Spit;
import java.awt.Graphics;
import java.util.Random;

public class LiranBoss extends Creature {

    private Game game;
    private long lastAttackTimer, attackCooldown = 1500, attackTimer = attackCooldown;
    private Random random;
    private int moveTimer = 0;
    
    public LiranBoss(Game game, World world, float x, float y) {
        super(world, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);
        this.game = game;
        this.random = new Random();
        this.health = 100; // Boss Health
        
        bounds.x = 16;
        bounds.y = 32;
        bounds.width = 32;
        bounds.height = 32;
    }

    @Override
    public void tick() {
        // AI Movement
        moveTimer++;
        if (moveTimer > 60) { // Change direction every second roughly
            moveTimer = 0;
            // Random movement or chase logic
            // Simple random for now
            xMove = random.nextInt(3) - 1; // -1, 0, 1
            yMove = random.nextInt(3) - 1;
            
            if (random.nextBoolean()) { // Sometimes stop
                xMove = 0;
                yMove = 0;
            }
        }
        
        // Chase player if close
        // ... (Maybe later)
        
        move();
        
        // Attack Logic (Spit)
        attackTimer += System.currentTimeMillis() - lastAttackTimer;
        lastAttackTimer = System.currentTimeMillis();
        
        if (attackTimer > attackCooldown) {
            // Spit at player
            // Need player reference?
            // For now, random direction or try to face player?
            // Let's just spit in random direction or current facing direction
            
            // Actually, let's target player roughly
            // Assuming we can get player from world?
            // world.getEntityManager().getPlayer() ... need to add getPlayer() to EntityManager
            
            // Fallback: Random spit
            int dir = random.nextInt(4);
            spit(dir);
            attackTimer = 0;
        }
    }
    
    private void spit(int dir) {
        float sx = x + width / 2 - 12;
        float sy = y + height / 2 - 12;
        
        // Offset
        if (dir == 0) sy += 10;
        else if (dir == 1) sy -= 10;
        else if (dir == 2) sx -= 10;
        else if (dir == 3) sx += 10;
        
        Spit s = new Spit(world, sx, sy, dir);
        // Mark as enemy projectile?
        // We need to differentiate player spit vs enemy spit
        // Add source/owner to Spit?
        s.setOwner(this);
        world.getEntityManager().addEntity(s);
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.liran_boss, (int) (x - game.getGameCamera().getxOffset()), 
                (int) (y - game.getGameCamera().getyOffset()), width, height, null);
        
        // Health Bar above head?
        // Or global boss bar?
    }

    @Override
    public void die() {
        System.out.println("Liran Defeated!");
        // Trigger win condition
    }
}
