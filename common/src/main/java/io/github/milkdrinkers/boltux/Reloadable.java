package io.github.milkdrinkers.boltux;

/**
 * Implemented in classes that should support being reloaded IE executing the methods during runtime after startup.
 */
public interface Reloadable {
    /**
     * On plugin load.
     */
    default void onLoad(AbstractBoltUX plugin) {
    }

    /**
     * On plugin enable.
     */
    default void onEnable(AbstractBoltUX plugin) {
    }

    /**
     * On plugin disable.
     */
    default void onDisable(AbstractBoltUX plugin) {
    }

}
