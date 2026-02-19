package com.ashkelord.entities.behaviors;

import java.awt.image.BufferedImage;
import com.ashkelord.gfx.Assets;

/**
 * The second evolution form — Intermediate Ars.
 * Faster speed, stronger attack.
 */
public class IntermediateArsForm implements EvolutionForm {

    @Override
    public void attack() {
        System.out.println("Used Techno Music!");
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
