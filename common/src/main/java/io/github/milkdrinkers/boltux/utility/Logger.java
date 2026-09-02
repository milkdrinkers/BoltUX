package io.github.milkdrinkers.boltux.utility;


import io.github.milkdrinkers.boltux.AbstractBoltUX;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import org.jetbrains.annotations.NotNull;

/**
 * A class that provides shorthand access to {@link AbstractBoltUX#getComponentLogger}.
 */
public class Logger {
    /**
     * Get component logger. Shorthand for:
     *
     * @return the component logger {@link AbstractBoltUX#getComponentLogger}.
     */
    @NotNull
    public static ComponentLogger get() {
        return AbstractBoltUX.getInstance().getComponentLogger();
    }
}
