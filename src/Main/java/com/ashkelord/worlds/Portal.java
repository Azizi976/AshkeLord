package com.ashkelord.worlds;

/**
 * A portal connects a trigger tile in one world to a spawn point in another.
 * Stepping on (triggerTileX, triggerTileY) teleports the player to
 * (destSpawnX, destSpawnY) in targetWorldPath.
 */
public class Portal {

    private int triggerTileX, triggerTileY;
    private String targetWorldPath;
    private int destSpawnX, destSpawnY;

    /**
     * @param triggerTileX Tile X that activates this portal
     * @param triggerTileY Tile Y that activates this portal
     * @param targetWorldPath Resource path of the destination world
     * @param destSpawnX Tile X to spawn player in destination
     * @param destSpawnY Tile Y to spawn player in destination
     */
    public Portal(int triggerTileX, int triggerTileY, String targetWorldPath,
                  int destSpawnX, int destSpawnY) {
        this.triggerTileX = triggerTileX;
        this.triggerTileY = triggerTileY;
        this.targetWorldPath = targetWorldPath;
        this.destSpawnX = destSpawnX;
        this.destSpawnY = destSpawnY;
    }

    public int getTriggerTileX() { return triggerTileX; }
    public int getTriggerTileY() { return triggerTileY; }
    public String getTargetWorldPath() { return targetWorldPath; }
    public int getDestSpawnX() { return destSpawnX; }
    public int getDestSpawnY() { return destSpawnY; }
}
