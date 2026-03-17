package com.ashkelord.entities.behaviors;

import java.awt.image.BufferedImage;
import com.ashkelord.gfx.Assets;
import com.ashkelord.combat.Attack;
import com.ashkelord.combat.MeleeAttack;
import com.ashkelord.combat.AoEAttack;

/**
 * The second evolution form — Intermediate Ars.
 * Faster speed, melee primary + AoE special.
 */
public class IntermediateArsForm implements EvolutionForm {

    private final Attack primaryAttack = new MeleeAttack("Slap", 10, 0.5f);
    private final Attack specialAttack = new AoEAttack("Techno Music", 15, 3.0f, 96);

    @Override
    public Attack getPrimaryAttack() {
        return primaryAttack;
    }

    @Override
    public Attack getSpecialAttack() {
        return specialAttack;
    }

    @Override
    public double getSpeedModifier() {
        return 1.5;
    }

    @Override
    public BufferedImage getAppearanceTexture() {
        return Assets.player_walk[0][0];
    }

    @Override
    public String getSpecialMoveName() {
        return "Achi, yesh lecha esh?";
    }
}
