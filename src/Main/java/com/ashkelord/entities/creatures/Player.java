package com.ashkelord.entities.creatures;

import com.ashkelord.gfx.Animation;
import com.ashkelord.gfx.Assets;
import com.ashkelord.main.Game;
import com.ashkelord.worlds.World;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Player extends Creature {

    private Game game;

    // Walk animations: 0=down, 1=up, 2=left, 3=right
    private Animation animDown, animUp, animLeft, animRight;
    private int lastDir = 0; // 0=down by default

    // Stats
    private int streetCreds = 0;
    private int nervim = 10; // Starts with some nerves? Or 0? Let's say 0 is calm. 100 is rage.
    // User said "number of... nervim".
    // I'll init at 0.

    public Player(Game game, World world, float x, float y) {
        super(world, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);
        this.game = game;

        // Hitbox covers only the feet area
        bounds.x = 22;
        bounds.y = 44;
        bounds.width = 19;
        bounds.height = 19;

        // Init walk animations (150ms per frame for snappy walk)
        animDown = new Animation(150, Assets.player_walk[0]);
        animUp = new Animation(150, Assets.player_walk[1]);
        animLeft = new Animation(150, Assets.player_walk[2]);
        animRight = new Animation(150, Assets.player_walk[3]);
    }

    @Override
    public void tick() {
        // Tick all animations
        animDown.tick();
        animUp.tick();
        animLeft.tick();
        animRight.tick();

        getInput();
        move();
        game.getGameCamera().centerOnEntity(this);

        // Auto-dismiss dialog on first movement
        if ((xMove != 0 || yMove != 0) && game.getUIManager().getDialogBox().isActive()) {
            game.getUIManager().getDialogBox().hide();
        }
    }

    private void getInput() {
        xMove = 0;
        yMove = 0;

        float effectiveSpeed = speed;

        if (game.getKeyManager().up) {
            yMove = -effectiveSpeed;
            lastDir = 1;
        }
        if (game.getKeyManager().down) {
            yMove = effectiveSpeed;
            lastDir = 0;
        }
        if (game.getKeyManager().left) {
            xMove = -effectiveSpeed;
            lastDir = 2;
        }
        if (game.getKeyManager().right) {
            xMove = effectiveSpeed;
            lastDir = 3;
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(getCurrentFrame(),
                (int) (x - game.getGameCamera().getxOffset()),
                (int) (y - game.getGameCamera().getyOffset()),
                width, height, null);
    }

    /**
     * Returns the correct animation frame based on direction and movement.
     */
    private BufferedImage getCurrentFrame() {
        // If not moving, return standing frame (frame 0) for last direction
        if (xMove == 0 && yMove == 0) {
            return Assets.player_walk[lastDir][0];
        }

        // If moving, return current animation frame for direction
        switch (lastDir) {
            case 1:
                return animUp.getCurrentFrame();
            case 2:
                return animLeft.getCurrentFrame();
            case 3:
                return animRight.getCurrentFrame();
            default:
                return animDown.getCurrentFrame();
        }
    }

    public Game getGame() {
        return game;
    }

    // Stats Getters/Setters
    public int getStreetCreds() {
        return streetCreds;
    }

    public void setStreetCreds(int streetCreds) {
        this.streetCreds = streetCreds;
    }

    public void addStreetCreds(int amount) {
        this.streetCreds += amount;
    }

    public int getNervim() {
        return nervim;
    }

    public void setNervim(int nervim) {
        this.nervim = nervim;
    }

    public void addNervim(int amount) {
        this.nervim += amount;
        if (this.nervim < 0)
            this.nervim = 0;
        if (this.nervim > 100)
            this.nervim = 100;
    }
}