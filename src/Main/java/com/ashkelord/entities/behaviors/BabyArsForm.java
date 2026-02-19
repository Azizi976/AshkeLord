package com.ashkelord.entities.behaviors;

import java.awt.image.BufferedImage;
import com.ashkelord.gfx.Assets;

/**
 * The starting evolution form — Baby Ars.
 * Base speed, basic attack.
 */
public class BabyArsForm implements EvolutionForm {

    @Override
    public void attack() {
        System.out.println("Used Yirika!");
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
