package com.ashkelord.ai;

/**
 * A single node in the A* pathfinding grid.
 * Each node maps to one tile in the world.
 */
public class Node implements Comparable<Node> {

    public int tileX, tileY;
    public int gCost;   // Distance from start
    public int hCost;   // Heuristic distance to goal
    public Node parent;
    public boolean walkable;

    public Node(int tileX, int tileY, boolean walkable) {
        this.tileX = tileX;
        this.tileY = tileY;
        this.walkable = walkable;
    }

    /** f = g + h (total estimated cost). */
    public int fCost() {
        return gCost + hCost;
    }

    @Override
    public int compareTo(Node other) {
        int cmp = Integer.compare(this.fCost(), other.fCost());
        if (cmp == 0) {
            cmp = Integer.compare(this.hCost, other.hCost);
        }
        return cmp;
    }
}
