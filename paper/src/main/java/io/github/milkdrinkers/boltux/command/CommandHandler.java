package io.github.milkdrinkers.boltux.command;

import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIPaperConfig;
import io.github.milkdrinkers.boltux.AbstractBoltUX;
import io.github.milkdrinkers.boltux.BoltUX;
import io.github.milkdrinkers.boltux.Reloadable;

/**
 * A class to handle registration of commands.
 */
public class CommandHandler implements Reloadable {
    private final BoltUX plugin;

    /**
     * Instantiates the Command handler.
     *
     * @param plugin the plugin
     */
    public CommandHandler(BoltUX plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onLoad(AbstractBoltUX plugin) {
        CommandAPI.onLoad(
            new CommandAPIPaperConfig(plugin)
                .silentLogs(true)
        );
    }

    @Override
    public void onEnable(AbstractBoltUX plugin) {
        if (!CommandAPI.isLoaded())
            return;

        CommandAPI.onEnable();

        // Register commands here
        new BoltUXCommand()
            .command()
            .withAliases()
            .register();
    }

    @Override
    public void onDisable(AbstractBoltUX plugin) {
        if (!CommandAPI.isLoaded())
            return;

        CommandAPI.onDisable();
    }
}