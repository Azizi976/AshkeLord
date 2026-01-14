package Main.java.com.ashkelord.gfx;

import Main.java.com.ashkelord.entities.Entity;
import Main.java.com.ashkelord.main.Game;

public class GameCamera {

    private Game game;
    private float xOffset, yOffset;

    public GameCamera(Game game, float xOffset, float yOffset) {
        this.game = game;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    // הפונקציה שדואגת שהמצלמה תהיה ממוקדת על הישות (השחקן)
    public void centerOnEntity(Entity e) {
        xOffset = e.getX() - game.width / 2 + e.getWidth() / 2;
        yOffset = e.getY() - game.height / 2 + e.getHeight() / 2;
    }

    public void move(float xAmt, float yAmt) {
        xOffset += xAmt;
        yOffset += yAmt;
    }

    public float getxOffset() {
        return xOffset;
    }

    public float getyOffset() {
        return yOffset;
    }
}
