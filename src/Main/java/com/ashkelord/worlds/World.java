package Main.java.com.ashkelord.worlds;

import Main.java.com.ashkelord.main.Game;
import Main.java.com.ashkelord.tiles.Tile;
import java.awt.Graphics;

public class World {

    private Game game;
    private int width, height; // גודל המפה באריחים
    private int spawnX, spawnY;
    private int[][] tiles; // המטריצה של המפה

    public World(Game game, String path) {
        this.game = game;
        loadWorld(path);
    }

    public void tick() {
        // כאן נעדכן דברים שקשורים לעולם (אנימציות של מים וכו')
    }

    public void render(Graphics g) {
        // אופטימיזציה: מרנדרים רק מה שהמצלמה רואה!
        // Start & End variables מגדירים את גבולות הרינדור
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
    }

    public Tile getTile(int x, int y) {
        // הגנה מפני קריסה אם יצאנו מהמפה
        if (x < 0 || y < 0 || x >= width || y >= height)
            return Tile.grassTile; // ברירת מחדל

        Tile t = Tile.tiles[tiles[x][y]];
        if (t == null)
            return Tile.concreteTile;
        return t;
    }

    private void loadWorld(String path) {
        // כרגע נשתמש במפה הארד-קודד (Hard Coded) לבדיקה
        // בהמשך נקרא קובץ טקסט אמיתי דרך Utils
        width = 20;
        height = 20;
        tiles = new int[width][height];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                tiles[x][y] = 1; // הכל בטון (אשקלון או לא?)
                if (x > 10)
                    tiles[x][y] = 0; // קצת דשא בצד
                if (x == 5 && y == 5)
                    tiles[x][y] = 2; // דוכן שווארמה
            }
        }
    }
}
