package com.ashkelord.worlds;

import com.ashkelord.core.Utils;
import com.ashkelord.entities.EntityManager;
import com.ashkelord.main.Game;
import com.ashkelord.tiles.Tile;
import java.awt.Graphics;

public class World {

    private Game game;
    private int width, height;
    private int spawnX, spawnY;
    private int[][] tiles;
    private EntityManager entityManager;

    public World(Game game, String path) {
        this.game = game;
        entityManager = new EntityManager();
        loadWorld(path);
        // Tell the camera about world boundaries
        game.getGameCamera().setWorldSize(width, height);
    }

    public void tick() {
        entityManager.tick();
    }

    public void render(Graphics g) {
        // Optimization: only render tiles visible to the camera
        int xStart = (int) Math.max(0, game.getGameCamera().getxOffset() / Tile.TILEWIDTH);
        int xEnd = (int) Math.min(width, (game.getGameCamera().getxOffset() + game.width) / Tile.TILEWIDTH + 1);
        int yStart = (int) Math.max(0, game.getGameCamera().getyOffset() / Tile.TILEHEIGHT);
        int yEnd = (int) Math.min(height, (game.getGameCamera().getyOffset() + game.height) / Tile.TILEHEIGHT + 1);

        for (int y = yStart; y < yEnd; y++) {
            for (int x = xStart; x < xEnd; x++) {
                getTile(x, y).render(g,
                        (int) (x * Tile.TILEWIDTH - game.getGameCamera().getxOffset()),
                        (int) (y * Tile.TILEHEIGHT - game.getGameCamera().getyOffset()));
            }
        }

        // Render entities on top of tiles (Y-sorted for depth)
        entityManager.render(g);
    }

    public Tile getTile(int x, int y) {
        if (x < 0 || y < 0 || x >= width || y >= height)
            return Tile.grassTile;

        Tile t = Tile.tiles[tiles[x][y]];
        if (t == null)
            return Tile.concreteTile;
        return t;
    }

    private void loadWorld(String path) {
        String file = Utils.loadFileAsString(path);

        if (file == null || file.isEmpty()) {
            System.err.println("Warning: Could not load world from " + path + ". Using fallback.");
            loadFallbackWorld();
            return;
        }

        String[] tokens = file.split("\\s+");

        // Header: width height spawnX spawnY
        width = Utils.parseInt(tokens[0]);
        height = Utils.parseInt(tokens[1]);
        spawnX = Utils.parseInt(tokens[2]);
        spawnY = Utils.parseInt(tokens[3]);

        tiles = new int[width][height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                tiles[x][y] = Utils.parseInt(tokens[4 + (y * width) + x]);
            }
        }
    }

    /**
     * Fallback hardcoded map in case the file can't be loaded.
     */
    private void loadFallbackWorld() {
        width = 20;
        height = 20;
        spawnX = 5;
        spawnY = 5;
        tiles = new int[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                tiles[x][y] = 1;
                if (x > 10)
                    tiles[x][y] = 0;
                if (x == 5 && y == 5)
                    tiles[x][y] = 2;
            }
        }
    }

    // --- Getters ---

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getSpawnX() {
        return spawnX;
    }

    public int getSpawnY() {
        return spawnY;
    }

    public Game getGame() {
        return game;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }
}
