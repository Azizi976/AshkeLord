package com.ashkelord.gfx;

import com.ashkelord.entities.Entity;
import com.ashkelord.main.Game;
import com.ashkelord.tiles.Tile;

public class GameCamera {

    private Game game;
    private float xOffset, yOffset;

    // World bounds (set by World when loaded)
    private int worldWidthPixels, worldHeightPixels;

    public GameCamera(Game game, float xOffset, float yOffset) {
        this.game = game;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    /**
     * Set the world size so the camera can clamp itself within bounds.
     */
    public void setWorldSize(int widthInTiles, int heightInTiles) {
        this.worldWidthPixels = widthInTiles * Tile.TILEWIDTH;
        this.worldHeightPixels = heightInTiles * Tile.TILEHEIGHT;
    }

    public void centerOnEntity(Entity e) {
        xOffset = e.getX() - game.width / 2 + e.getWidth() / 2;
        yOffset = e.getY() - game.height / 2 + e.getHeight() / 2;
        clamp();
    }

    public void move(float xAmt, float yAmt) {
        xOffset += xAmt;
        yOffset += yAmt;
        clamp();
    }

    /**
     * Clamp camera so it never shows outside the world.
     */
    private void clamp() {
        if (xOffset < 0)
            xOffset = 0;
        if (yOffset < 0)
            yOffset = 0;
        if (worldWidthPixels > 0 && xOffset > worldWidthPixels - game.width)
            xOffset = worldWidthPixels - game.width;
        if (worldHeightPixels > 0 && yOffset > worldHeightPixels - game.height)
            yOffset = worldHeightPixels - game.height;
    }

    public float getxOffset() {
        return xOffset;
    }

    public float getyOffset() {
        return yOffset;
    }
}
