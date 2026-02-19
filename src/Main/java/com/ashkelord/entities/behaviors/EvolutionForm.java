package com.ashkelord.entities.behaviors;

import java.awt.image.BufferedImage;

/**
 * Strategy interface for the player's evolution forms.
 * Each form provides a unique texture, speed modifier, attack, and special
 * move.
 */
public interface EvolutionForm {

    void attack();

    double getSpeedModifier();

    BufferedImage getAppearanceTexture();

    String getSpecialMoveName();
}