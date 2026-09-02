package io.github.milkdrinkers.boltux;

import io.github.milkdrinkers.boltux.config.ConfigHandler;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractBoltUX extends JavaPlugin {
    private static AbstractBoltUX instance;

    /**
     * Gets plugin instance.
     *
     * @return the plugin instance
     */
    public static AbstractBoltUX getInstance() {
        return AbstractBoltUX.instance;
    }

    AbstractBoltUX() {
        AbstractBoltUX.instance = this;
    }

    /**
     * Gets config handler.
     *
     * @return the config handler
     */
    public abstract @NotNull ConfigHandler getConfigHandler();
}
