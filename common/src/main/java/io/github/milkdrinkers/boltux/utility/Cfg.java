package io.github.milkdrinkers.boltux.utility;

import io.github.milkdrinkers.boltux.AbstractBoltUX;
import io.github.milkdrinkers.boltux.config.ConfigHandler;
import io.github.milkdrinkers.boltux.config.PluginConfig;
import org.jetbrains.annotations.NotNull;

/**
 * Convenience class for accessing {@link ConfigHandler#getConfig}
 */
public final class Cfg {
    /**
     * Convenience method for {@link ConfigHandler#getConfig} to getConnection {@link PluginConfig}
     *
     * @return the config
     */
    @NotNull
    public static PluginConfig get() {
        return AbstractBoltUX.getInstance().getConfigHandler().getConfig();
    }
}
