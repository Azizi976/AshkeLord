package com.ashkelord.ai;

import com.ashkelord.worlds.World;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * Grid-based A* pathfinder operating on the world's tile map.
 * 4-directional (no diagonals), matching the game's movement model.
 */
public class Pathfinder {

    private World world;
    private int gridWidth, gridHeight;

    public Pathfinder(World world) {
        this.world = world;
        this.gridWidth = world.getWidth();
        this.gridHeight = world.getHeight();
    }

    /** Rebuild after world switch. */
    public void setWorld(World world) {
        this.world = world;
        this.gridWidth = world.getWidth();
        this.gridHeight = world.getHeight();
    }

    /**
     * Find a path from (startX,startY) to (goalX,goalY) in tile coordinates.
     * @return List of Nodes from start to goal (inclusive), or null if no path.
     */
    public List<Node> findPath(int startX, int startY, int goalX, int goalY) {
        // Bounds check
        if (startX < 0 || startX >= gridWidth || startY < 0 || startY >= gridHeight) return null;
        if (goalX < 0 || goalX >= gridWidth || goalY < 0 || goalY >= gridHeight) return null;

        // If goal is solid, no path
        if (world.getTile(goalX, goalY).isSolid()) return null;

        // Build fresh grid each call (small maps, avoids stale data)
        Node[][] grid = new Node[gridWidth][gridHeight];
        for (int x = 0; x < gridWidth; x++) {
            for (int y = 0; y < gridHeight; y++) {
                grid[x][y] = new Node(x, y, !world.getTile(x, y).isSolid());
            }
        }

        Node start = grid[startX][startY];
        Node goal = grid[goalX][goalY];

        PriorityQueue<Node> openSet = new PriorityQueue<>();
        Set<Node> closedSet = new HashSet<>();

        start.gCost = 0;
        start.hCost = manhattan(startX, startY, goalX, goalY);
        openSet.add(start);

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();

            if (current == goal) {
                return reconstructPath(goal);
            }

            closedSet.add(current);

            // 4-directional neighbors
            int[][] dirs = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
            for (int[] d : dirs) {
                int nx = current.tileX + d[0];
                int ny = current.tileY + d[1];

                if (nx < 0 || nx >= gridWidth || ny < 0 || ny >= gridHeight) continue;

                Node neighbor = grid[nx][ny];
                if (!neighbor.walkable || closedSet.contains(neighbor)) continue;

                int tentativeG = current.gCost + 10; // Cost of 10 per tile

                if (tentativeG < neighbor.gCost || !openSet.contains(neighbor)) {
                    neighbor.gCost = tentativeG;
                    neighbor.hCost = manhattan(nx, ny, goalX, goalY);
                    neighbor.parent = current;

                    if (!openSet.contains(neighbor)) {
                        openSet.add(neighbor);
                    }
                }
            }
        }

        return null; // No path found
    }

    /** Manhattan distance heuristic (no diagonals). */
    private int manhattan(int x1, int y1, int x2, int y2) {
        return (Math.abs(x1 - x2) + Math.abs(y1 - y2)) * 10;
    }

    /** Trace parent chain from goal back to start, then reverse. */
    private List<Node> reconstructPath(Node goal) {
        List<Node> path = new ArrayList<>();
        Node current = goal;
        while (current != null) {
            path.add(current);
            current = current.parent;
        }
        Collections.reverse(path);
        return path;
    }
}
