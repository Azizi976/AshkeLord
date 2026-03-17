package com.ashkelord.entities.behaviors;

import java.awt.image.BufferedImage;
import com.ashkelord.gfx.Assets;
import com.ashkelord.combat.Attack;
import com.ashkelord.combat.RangedAttack;

/**
 * The starting evolution form — Baby Ars.
 * Base speed, ranged spit attack.
 */
public class BabyArsForm implements EvolutionForm {

    private final Attack primaryAttack = new RangedAttack("Yarok Spit", 5, 0.5f);

    @Override
    public Attack getPrimaryAttack() {
        return primaryAttack;
    }

    @Override
    public Attack getSpecialAttack() {
        return null; // Not unlocked yet
    }

    @Override
    public double getSpeedModifier() {
        return 1.0;
    }

    @Override
    public BufferedImage getAppearanceTexture() {
        return Assets.player_walk[0][0];
    }

    @Override
    public String getSpecialMoveName() {
        return "Ma Yesh'cha?";
    }
}
