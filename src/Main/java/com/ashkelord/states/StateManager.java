package com.ashkelord.states;

import com.ashkelord.gfx.Renderer;
import java.util.Stack;

/**
 * Stack-based state manager. Supports push/pop (for overlays like mini-games
 * and pause menus) and swap (replacing the current state, like the old
 * State.setState behavior).
 *
 * Only the top state receives tick() and render() calls.
 */
public class StateManager {

    private final Stack<State> stateStack = new Stack<>();

    /**
     * Push a new state on top. The previous state is paused (not ticked/rendered)
     * until this one is popped.
     */
    public void push(State state) {
        stateStack.push(state);
    }

    /**
     * Pop the current state off the stack and return to the previous one.
     * Returns the popped state, or null if the stack was empty.
     */
    public State pop() {
        if (stateStack.isEmpty()) return null;
        return stateStack.pop();
    }

    /**
     * Replace the top state (equivalent to the old State.setState behavior).
     * If the stack is empty, just pushes. Otherwise pops and pushes.
     */
    public void swap(State state) {
        if (!stateStack.isEmpty()) {
            stateStack.pop();
        }
        stateStack.push(state);
    }

    /**
     * Get the current active state (top of stack).
     */
    public State peek() {
        if (stateStack.isEmpty()) return null;
        return stateStack.peek();
    }

    /**
     * Tick the active (top) state.
     */
    public void tick() {
        State current = peek();
        if (current != null) {
            current.tick();
        }
    }

    /**
     * Render the active (top) state.
     */
    public void render(Renderer r) {
        State current = peek();
        if (current != null) {
            current.render(r);
        }
    }

    /**
     * Check if the stack is empty.
     */
    public boolean isEmpty() {
        return stateStack.isEmpty();
    }

    /**
     * Get the number of states on the stack.
     */
    public int size() {
        return stateStack.size();
    }
}
