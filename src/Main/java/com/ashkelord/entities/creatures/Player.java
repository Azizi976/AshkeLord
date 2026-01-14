package Main.java.com.ashkelord.entities.creatures;

import Main.java.com.ashkelord.entities.behaviors.EvolutionForm;
import Main.java.com.ashkelord.gfx.Assets;
import Main.java.com.ashkelord.main.Game;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Player extends Creature {

    private Game game;
    private EvolutionForm currentForm; // ה-Strategy של האבולוציה

    // זמני: כרגע נגדיר מימוש פנימי פשוט עד שנסדר את ה-Imports של EvolutionForm
    // (הנחתי שהקובץ EvolutionForm.java קיים ותקין בחבילה behaviors)

    public Player(Game game, float x, float y) {
        super(x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);
        this.game = game;

        // אתחול מצב דיפולטיבי (צריך להיות תואם לקובץ Behaviors)
        // לצורך הדוגמה אני מייצר כאן אנונימית אם ה-Import לא עובד,
        // אבל עדיף להשתמש במחלקות האמיתיות שיצרת.
    }

    @Override
    public void tick() {
        getInput();
        move();
        // עדכון המצלמה שתתמקד על השחקן
        game.getGameCamera().centerOnEntity(this);
    }

    private void getInput() {
        xMove = 0;
        yMove = 0;

        float speedMod = 1.0f;
        // if (currentForm != null) speedMod = currentForm.getSpeedModifier();

        if (game.getKeyManager().up)
            yMove = -speed * speedMod;
        if (game.getKeyManager().down)
            yMove = speed * speedMod;
        if (game.getKeyManager().left)
            xMove = -speed * speedMod;
        if (game.getKeyManager().right)
            xMove = speed * speedMod;
    }

    @Override
    public void render(Graphics g) {
        // ציור השחקן במיקום יחסי למצלמה!
        // שים לב לחיסור של getxOffset

        BufferedImage texture = Assets.player_baby; // Default
        // if (currentForm != null) texture = Assets.get(currentForm.getTextureName());

        g.drawImage(texture,
                (int) (x - game.getGameCamera().getxOffset()),
                (int) (y - game.getGameCamera().getyOffset()),
                width, height, null);
    }
}