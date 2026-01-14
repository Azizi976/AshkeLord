package Main.java.com.ashkelord.tiles;

import Main.java.com.ashkelord.gfx.Assets;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Tile {

    // מערך סטטי שמחזיק את כל סוגי האריחים (כמו ID)
    public static Tile[] tiles = new Tile[256];
    public static Tile grassTile = new GrassTile(0);
    public static Tile concreteTile = new ConcreteTile(1);
    public static Tile shawarmaTile = new ShawarmaTile(2);

    // מחלקה בסיסית
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

    // האם אפשר ללכת על זה? (Collision detection בעתיד)
    public boolean isSolid() {
        return false;
    }

    public int getId() {
        return id;
    }
}

// מחלקות פנימיות או בקבצים נפרדים - לשם פשטות אני שם כאן
class GrassTile extends Tile {
    public GrassTile(int id) {
        super(Assets.grass, id);
    }
}

class ConcreteTile extends Tile {
    public ConcreteTile(int id) {
        super(Assets.concrete, id);
    }
}

class ShawarmaTile extends Tile {
    public ShawarmaTile(int id) {
        super(Assets.shawarma_stand, id);
    }

    @Override
    public boolean isSolid() {
        return true;
    } // אי אפשר לעבור דרך דוכן שווארמה
}
