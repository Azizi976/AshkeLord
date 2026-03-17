package com.ashkelord.inventory;

import com.ashkelord.gfx.Assets;
import com.ashkelord.items.Item;
import com.ashkelord.main.Game;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Inventory {

    private Game game;
    private boolean active = false;
    private ArrayList<Item> inventoryItems;

    public Inventory(Game game) {
        this.game = game;
        inventoryItems = new ArrayList<Item>();
        
        // Testing items
        addItem(Item.woodItem.createNew(5));
        addItem(Item.rockItem.createNew(3));
    }

    public void tick() {
        if (game.getKeyManager().keyJustPressed(KeyEvent.VK_TAB))
            active = !active;
        
        if (!active)
            return;
        
        // Navigation logic could go here later
    }

    public void render(Graphics g) {
        if (!active)
            return;

        int invX = 50;
        int invY = 50;
        int invWidth = 512;
        int invHeight = 384;
        int invListCenterX = invX + 171;
        int invListCenterY = invY + invHeight / 2 + 5;
        int invListSpacing = 30;

        // Background
        g.setColor(new Color(0, 0, 0, 200));
        g.fillRect(invX, invY, invWidth, invHeight);
        
        // Title
        g.setColor(Color.WHITE);
        g.drawString("INVENTORY", invX + 20, invY + 30);

        // Render Items
        int len = inventoryItems.size();
        if (len == 0)
            return;

        for (int i = 0; i < len; i++) {
            /* 
            // Simple list for now
            if (inventoryItems.get(i).isSelected())
                g.drawImage(Assets.selected, invListCenterX, invListCenterY + i * invListSpacing, null);
            else
                g.drawImage(Assets.unselected, invListCenterX, invListCenterY + i * invListSpacing, null);
            */
            
            // Grid layout
            int col = i % 5;
            int row = i / 5;
            
            int slotX = invX + 40 + (col * (Item.ITEMWIDTH + 20));
            int slotY = invY + 60 + (row * (Item.ITEMHEIGHT + 20));
            
            // Draw Slot
            g.setColor(Color.GRAY);
            g.drawRect(slotX, slotY, Item.ITEMWIDTH, Item.ITEMHEIGHT);
            
            // Draw Item
            inventoryItems.get(i).render(g, slotX, slotY);
            
            // Draw Count
            g.setColor(Color.WHITE);
            g.drawString(Integer.toString(inventoryItems.get(i).getCount()), slotX + 20, slotY + 30);
        }
    }

    // Inventory methods

    public void addItem(Item item) {
        for (Item i : inventoryItems) {
            if (i.getId() == item.getId()) {
                i.setCount(i.getCount() + item.getCount());
                return;
            }
        }
        inventoryItems.add(item);
    }
    
    /** Remove an item by its ID. Removes the entire stack. */
    public void removeItem(int itemId) {
        for (int i = 0; i < inventoryItems.size(); i++) {
            if (inventoryItems.get(i).getId() == itemId) {
                inventoryItems.remove(i);
                return;
            }
        }
    }
    
    /** Check if the inventory contains an item with the given ID. */
    public boolean hasItem(int itemId) {
        for (Item i : inventoryItems) {
            if (i.getId() == itemId) return true;
        }
        return false;
    }
    
    // Getters and Setters

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    
    public ArrayList<Item> getItems() {
        return inventoryItems;
    }
}
