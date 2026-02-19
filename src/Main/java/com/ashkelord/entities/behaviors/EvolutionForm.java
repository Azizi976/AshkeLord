package com.ashkelord.entities.behaviors;

import java.awt.image.BufferedImage;
import com.ashkelord.gfx.Assets;

public interface EvolutionForm {
    void attack();

    double getSpeedModifier();

    BufferedImage getAppearanceTexture();

    String getSpecialMoveName();
}

class BabyArsForm implements EvolutionForm {
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
        return Assets.player_baby;
    }

    @Override
    public String getSpecialMoveName() {
        return "Ma Yesh'cha?";
    }
}

class IntermediateArsForm implements EvolutionForm {
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
        return Assets.player_intermediate;
    }

    @Override
    public String getSpecialMoveName() {
        return "Achi, yesh lecha esh?";
    }
}