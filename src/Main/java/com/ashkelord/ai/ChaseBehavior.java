package com.ashkelord.ai;

import com.ashkelord.entities.Entity;
import com.ashkelord.entities.creatures.Creature;
import com.ashkelord.tiles.Tile;
import java.util.List;

/**
 * AI behavior that uses A* pathfinding to chase a target entity.
 * Falls back to idle when target is out of aggro range.
 * Recalculates path periodically to avoid stale routes.
 */
public class ChaseBehavior {

    private Pathfinder pathfinder;
    private List<Node> currentPath;
    private int pathIndex;

    private int recalcInterval = 30;  // Recalc every 0.5s at 60fps
    private int tickCounter;
    private float aggroRange;         // Pixel range to start chasing

    public ChaseBehavior(Pathfinder pathfinder, float aggroRange) {
        this.pathfinder = pathfinder;
        this.aggroRange = aggroRange;
    }

    /**
     * Called each tick — sets xMove/yMove on the creature to follow the target.
     * @param creature The creature being controlled
     * @param target   The entity to chase (usually the player)
     */
    public void update(Creature creature, Entity target) {
        if (target == null) return;

        // Check aggro range
        float dx = creature.getX() - target.getX();
        float dy = creature.getY() - target.getY();
        float distSq = dx * dx + dy * dy;

        if (distSq > aggroRange * aggroRange) {
            // Out of range — idle
            creature.setXMove(0);
            creature.setYMove(0);
            currentPath = null;
            return;
        }

        // Recalculate path periodically
        tickCounter++;
        if (tickCounter >= recalcInterval || currentPath == null) {
            recalcPath(creature, target);
            tickCounter = 0;
        }

        followPath(creature);
    }

    private void recalcPath(Creature creature, Entity target) {
        // Convert pixel coords to tile coords
        int startTX = (int) (creature.getX() + creature.getWidth() / 2) / Tile.TILEWIDTH;
        int startTY = (int) (creature.getY() + creature.getHeight() / 2) / Tile.TILEHEIGHT;
        int goalTX = (int) (target.getX() + target.getWidth() / 2) / Tile.TILEWIDTH;
        int goalTY = (int) (target.getY() + target.getHeight() / 2) / Tile.TILEHEIGHT;

        currentPath = pathfinder.findPath(startTX, startTY, goalTX, goalTY);
        pathIndex = 1; // Skip index 0 (current tile)
    }

    private void followPath(Creature creature) {
        if (currentPath == null || pathIndex >= currentPath.size()) {
            creature.setXMove(0);
            creature.setYMove(0);
            return;
        }

        Node next = currentPath.get(pathIndex);

        // Target pixel center of the next tile
        float targetX = next.tileX * Tile.TILEWIDTH + Tile.TILEWIDTH / 2f - creature.getWidth() / 2f;
        float targetY = next.tileY * Tile.TILEHEIGHT + Tile.TILEHEIGHT / 2f - creature.getHeight() / 2f;

        float dx = targetX - creature.getX();
        float dy = targetY - creature.getY();

        float speed = creature.getSpeed();

        // Move toward waypoint
        if (Math.abs(dx) > speed) {
            creature.setXMove(dx > 0 ? speed : -speed);
        } else {
            creature.setXMove(0);
        }

        if (Math.abs(dy) > speed) {
            creature.setYMove(dy > 0 ? speed : -speed);
        } else {
            creature.setYMove(0);
        }

        // Check if we've reached the waypoint (within threshold)
        if (Math.abs(dx) <= speed && Math.abs(dy) <= speed) {
            pathIndex++;
        }
    }
}
