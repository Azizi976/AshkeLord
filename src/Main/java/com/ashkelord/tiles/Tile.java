package com.ashkelord.tiles;

import com.ashkelord.gfx.Assets;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Tile {

    public static Tile[] tiles = new Tile[256];

    // IDs: 0=concrete, 1=grass, 2=shawarma(solid), 3=road, 4=sand, 5=water(solid),
    // 6=wall(solid), 7=curb, 8=wall_window(solid), 9=wall_door(solid)
    public static Tile concreteTile = new Tile(Assets.concrete, 0);
    public static Tile grassTile = new Tile(Assets.grass, 1);
    public static Tile shawarmaTile = new SolidTile(Assets.shawarma_stand, 2);
    public static Tile roadTile = new Tile(Assets.road, 3);
    public static Tile sandTile = new Tile(Assets.sand, 4);
    public static Tile waterTile = new SolidTile(Assets.water, 5);
    public static Tile wallTile = new SolidTile(Assets.wall, 6);
    public static Tile curbTile = new Tile(Assets.curb, 7);

    // New wall variants
    public static Tile wallWindowTile = new SolidTile(Assets.wall_window, 8);
    public static Tile wallDoorTile = new SolidTile(Assets.wall_door, 9);

    public static final int TILEWIDTH = 64, TILEHEIGHT = 64;

    protected BufferedImage texture;
    protected final int id;

    public Tile(BufferedImage texture, int id) {
        this.texture = texture;
        this.id = id;
        tiles[id] = this;
    }

    public void tick() {
    }

    public void render(Graphics g, int x, int y) {
        g.drawImage(texture, x, y, TILEWIDTH, TILEHEIGHT, null);
    }

    public boolean isSolid() {
        return false;
    }

    public int getId() {
        return id;
    }
}

class SolidTile extends Tile {
    public SolidTile(BufferedImage texture, int id) {
        super(texture, id);
    }

    @Override
    public boolean isSolid() {
        return true;
    }
}
