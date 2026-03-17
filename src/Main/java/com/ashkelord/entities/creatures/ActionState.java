package com.ashkelord.entities.creatures;

/**
 * Tracks the current visual action state of a creature.
 * Used by Player to select the correct animation set.
 */
public enum ActionState {
    IDLE,         // Standing still
    WALKING,      // Moving
    ATTACKING,    // Melee/ranged attack animation
    HIT,          // Taking damage
    INTERACTING   // NPC interaction, receiving items
}
