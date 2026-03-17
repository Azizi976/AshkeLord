package com.ashkelord.worlds;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages portal triggers for the current world.
 * Call checkTrigger() each tick with the player's tile position.
 * Clear when loading a new world to reset portals.
 */
public class PortalManager {

    private List<Portal> portals;

    public PortalManager() {
        portals = new ArrayList<>();
    }

    /** Register a portal for the current world. */
    public void registerPortal(Portal portal) {
        portals.add(portal);
    }

    /**
     * Check if the player is standing on a portal trigger tile.
     * @return The matching Portal, or null if none.
     */
    public Portal checkTrigger(int playerTileX, int playerTileY) {
        for (Portal p : portals) {
            if (p.getTriggerTileX() == playerTileX && p.getTriggerTileY() == playerTileY) {
                return p;
            }
        }
        return null;
    }

    /** Clear all portals (call when loading a new world). */
    public void clear() {
        portals.clear();
    }
}
