package com.ashkelord.items;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import com.ashkelord.gfx.Assets;

public class Item {

    // Handler reference (if needed later, but Item should be lightweight)
    
    public static Item[] items = new Item[256];
    public static Item woodItem = new Item(Assets.wood, "Wood", 0);
    // Placeholder rock item reusing wood texture or if we had rock texture
    public static Item rockItem = new Item(Assets.concrete, "Rock", 1); 
    public static Item ambaItem = new Item(Assets.golden_amba, "Golden Amba", 2);
    public static Item laffaItem = new Item(Assets.laffa, "Miri's Heavy Laffa", 3);
    public static Item strongHoldWaxItem = new Item(Assets.strong_hold_wax, "Strong Hold Wax", 4);
    public static Item oatMilkCortadoItem = new Item(Assets.oat_milk_cortado, "Oat Milk Cortado", 5);
    public static Item ebikeBatteryItem = new Item(Assets.ebike_battery, "E-Bike Battery", 6);

    public static final int ITEMWIDTH = 32, ITEMHEIGHT = 32;

    protected BufferedImage texture;
    protected String name;
    protected final int id;

    protected int count;

    public Item(BufferedImage texture, String name, int id) {
        this.texture = texture;
        this.name = name;
        this.id = id;
        count = 1;

        items[id] = this;
    }

    public void tick() {
        
    }

    public void render(Graphics g, int x, int y) {
        if(texture == null) return;
        g.drawImage(texture, x, y, ITEMWIDTH, ITEMHEIGHT, null);
    }
    
    // For rendering in inventory with quantity
    public void render(Graphics g, int x, int y, int qty) {
         if(texture == null) return;
         g.drawImage(texture, x, y, ITEMWIDTH, ITEMHEIGHT, null);
         // Draw quantity?
    }

    public Item createNew(int count) {
        Item i = new Item(texture, name, id);
        i.setCount(count);
        return i;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    
    public BufferedImage getTexture() {
        return texture;
    }

}
