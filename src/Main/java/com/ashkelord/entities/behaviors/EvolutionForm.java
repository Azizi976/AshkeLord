package Main.java.com.ashkelord.entities.behaviors;

import java.awt.Graphics;

// Interface defining what an Ars Evolution must implement
public interface EvolutionForm {
    void attack();

    double getSpeedModifier();

    String getAppearanceTexture(); // Returns asset key (e.g., "baby_ars", "king_ars")

    String getSpecialMoveName();
}

// Concrete Strategy: Baby Ars
class BabyArsForm implements EvolutionForm {
    @Override
    public void attack() {
        System.out.println("Used Yirika! (Low Damage, High Disgust)");
    }

    @Override
    public double getSpeedModifier() {
        return 1.0;
    } // Slow, walking with flip-flops

    @Override
    public String getAppearanceTexture() {
        return "player_baby";
    }

    @Override
    public String getSpecialMoveName() {
        return "Ma Yesh'cha?";
    }
}

// Concrete Strategy: Intermediate Ars
class IntermediateArsForm implements EvolutionForm {
    @Override
    public void attack() {
        System.out.println("Used Techno Music! (Area Damage)");
    }

    @Override
    public double getSpeedModifier() {
        return 1.5;
    } // Electric Bike

    @Override
    public String getAppearanceTexture() {
        return "player_intermediate";
    }

    @Override
    public String getSpecialMoveName() {
        return "Achi, yesh lecha esh?";
    }
}

// The Player Context
public class Player extends Creature {

    private EvolutionForm currentForm;
    private int streetCred; // XP
    private int nervim; // HP

    public Player(Game game, float x, float y) {
        super(game, x, y, Creature.DEFAULT_WIDTH, Creature.DEFAULT_HEIGHT);

        // Default State
        this.currentForm = new BabyArsForm();
        this.streetCred = 0;
        this.nervim = 100;
    }

    @Override
    public void tick() {
        // Input logic here calling move()
        checkEvolution();
    }

    // Dynamic Evolution Logic
    private void checkEvolution() {
        // Upgrade to Intermediate if conditions met
        if (streetCred >= 500 && !(currentForm instanceof IntermediateArsForm)
                && !(currentForm instanceof KingArsForm)) {
            evolve(new IntermediateArsForm());
        }
        // Logic for King Ars would go here (requires beating Liran)
    }

    private void evolve(EvolutionForm newForm) {
        System.out.println("EVOLUTION! Nader is changing form...");
        this.currentForm = newForm;
        // Play sound: "assets.sounds.psytrance_drop"
    }

    // Polymorphic Movement Speed
    @Override
    public void move() {
        moveX(xMove * currentForm.getSpeedModifier());
        moveY(yMove * currentForm.getSpeedModifier());
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.get(currentForm.getAppearanceTexture()), (int) x, (int) y, width, height, null);
    }
}