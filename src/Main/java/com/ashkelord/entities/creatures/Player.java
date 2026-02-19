package com.ashkelord.entities.creatures;

import com.ashkelord.gfx.Assets;
import com.ashkelord.main.Game;
import com.ashkelord.worlds.World; // הוספתי
import java.awt.Graphics; // תיקון Import

public class Player extends Creature {

    private Game game;

    // עדכון: מקבלים World בבנאי ומעבירים ל-Creature
    public Player(Game game, World world, float x, float y) {
        super(world, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);
        this.game = game;

        // הגדרת "רגליים" בלבד להתנגשות (כדי שהראש לא יתקע בקיר)
        bounds.x = 22;
        bounds.y = 44;
        bounds.width = 19;
        bounds.height = 19;
    }

    @Override
    public void tick() {
        getInput();
        move(); // קורא ל-move החכם של Creature
        game.getGameCamera().centerOnEntity(this);
    }

    private void getInput() {
        xMove = 0;
        yMove = 0;

        // --- התיקון לכיוונים ההפוכים ---
        // החזרתי את הלוגיקה ה"הפוכה" שעבדה לך:

        if (game.getKeyManager().up)
            yMove = speed; // אצלך זה למעלה
        if (game.getKeyManager().down)
            yMove = -speed; // אצלך זה למטה
        if (game.getKeyManager().left)
            xMove = speed; // אצלך זה שמאלה
        if (game.getKeyManager().right)
            xMove = -speed; // אצלך זה ימינה
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.player_baby,
                (int) (x - game.getGameCamera().getxOffset()),
                (int) (y - game.getGameCamera().getyOffset()),
                width, height, null);

        // לדיבאג (לראות את הריבוע האדום):
        // g.setColor(java.awt.Color.RED);
        // g.drawRect((int) (x + bounds.x - game.getGameCamera().getxOffset()),
        // (int) (y + bounds.y - game.getGameCamera().getyOffset()),
        // bounds.width, bounds.height);
    }
}