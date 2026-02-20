package com.ashkelord.entities;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * Manages all entities in the world.
 * Handles ticking, Y-sort rendering (depth illusion), and entity lifecycle.
 */
public class EntityManager {

    private List<Entity> entities;
    private List<Entity> entitiesToAdd; // Deferred addition queue
    private Comparator<Entity> renderSorter;

    public EntityManager() {
        entities = new ArrayList<>();
        entitiesToAdd = new ArrayList<>();

        // Sort by bottom edge (y + height) for proper depth/Z-ordering
        renderSorter = (a, b) -> {
            if ((a.getY() + a.getHeight()) < (b.getY() + b.getHeight()))
                return -1;
            if ((a.getY() + a.getHeight()) > (b.getY() + b.getHeight()))
                return 1;
            return 0;
        };
    }

    public void tick() {
        Iterator<Entity> it = entities.iterator();
        while (it.hasNext()) {
            Entity e = it.next();
            e.tick();
            if (!e.isActive()) {
                it.remove();
            }
        }
        
        // Flush new entities
        if (!entitiesToAdd.isEmpty()) {
            entities.addAll(entitiesToAdd);
            entitiesToAdd.clear();
        }
    }

    public void render(Graphics g) {
        // Sort entities by Y position before rendering for depth illusion
        entities.sort(renderSorter);
        for (Entity e : entities) {
            e.render(g);
        }
    }

    public void addEntity(Entity e) {
        entitiesToAdd.add(e);
    }

    public void removeEntity(Entity e) {
        // Just mark inactive, let tick cleanup
        e.setActive(false);
    }

    public List<Entity> getEntities() {
        return entities;
    }
}
