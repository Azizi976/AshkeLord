package com.ashkelord.entities.behaviors;

import java.awt.image.BufferedImage;
import com.ashkelord.combat.Attack;

/**
 * Strategy interface for the player's evolution forms.
 * Each form provides a unique texture, speed modifier, and combat abilities.
 */
public interface EvolutionForm {

    /** The form's primary attack (bound to Space key). */
    Attack getPrimaryAttack();

    /** The form's special attack (bound to Q key). May return null if not unlocked. */
    Attack getSpecialAttack();

    double getSpeedModifier();

    BufferedImage getAppearanceTexture();

    String getSpecialMoveName();
}