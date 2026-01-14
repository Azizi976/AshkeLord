package Main.java.com.ashkelord.entities.creatures;

import Main.java.com.ashkelord.gfx.Assets;
import Main.java.com.ashkelord.main.Game;
import Main.java.com.awt.Graphics;

public class Player extends Creature {

    private Game game; // רפרנס למשחק כדי לגשת למקלדת

    public Player(Game game, float x, float y) {
        super(x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);
        this.game = game;
    }

    @Override
    public void tick() {
        getInput();
        move();
    }

    private void getInput() {
        xMove = 0;
        yMove = 0;

        if (game.getKeyManager().up)
            yMove = -speed;
        if (game.getKeyManager().down)
            yMove = speed;
        if (game.getKeyManager().left)
            xMove = -speed;
        if (game.getKeyManager().right)
            xMove = speed;
    }

    @Override
    public void render(Graphics g) {
        // כרגע נצייר את ה-Baby Ars כדיפולט
        g.drawImage(Assets.player_baby, (int) x, (int) y, width, height, null);
    }
}
