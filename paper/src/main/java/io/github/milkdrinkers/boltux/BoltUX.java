package io.github.milkdrinkers.boltux;

import io.github.milkdrinkers.boltux.api.BoltUXAPI;
import io.github.milkdrinkers.boltux.command.CommandHandler;
import io.github.milkdrinkers.boltux.config.ConfigHandler;
import io.github.milkdrinkers.boltux.hook.Hook;
import io.github.milkdrinkers.boltux.hook.HookManager;
import io.github.milkdrinkers.boltux.listener.ListenerHandler;
import io.github.milkdrinkers.boltux.lock.CraftingHandler;
import io.github.milkdrinkers.boltux.lock.LockHandler;
import io.github.milkdrinkers.boltux.threadutil.SchedulerHandler;
import io.github.milkdrinkers.boltux.translation.TranslationHandler;
import io.github.milkdrinkers.boltux.updatechecker.UpdateHandler;
import io.github.milkdrinkers.boltux.utility.Logger;
import io.github.milkdrinkers.colorparser.paper.ColorParser;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;
import org.popcraft.bolt.BoltPlugin;

import java.util.List;

public final class BoltUX extends AbstractBoltUX {
    private static BoltUX instance;
    private static BoltPlugin boltPlugin;

    // Handlers/Managers
    private ConfigHandler configHandler;
    private TranslationHandler translationHandler;
    private UpdateHandler updateHandler;
    private SchedulerHandler schedulerHandler;
    private CraftingHandler craftingHandler;
    private HookManager hookManager;
    private CommandHandler commandHandler;
    private ListenerHandler listenerHandler;
    private LockHandler lockHandler;
    private BoltUXAPIProvider apiHandler;

    // Handlers list (defines order of load/enable/disable)
    private List<? extends Reloadable> handlers;


    public static BoltUX getInstance() {
        return instance;
    }

    @Override
    public void onLoad() {
        instance = this;
        configHandler = new ConfigHandler(instance);
        translationHandler = new TranslationHandler(configHandler);
        updateHandler = new UpdateHandler(this);
        schedulerHandler = new SchedulerHandler();
        craftingHandler = new CraftingHandler(instance);
        hookManager = new HookManager(this);
        commandHandler = new CommandHandler(instance);
        listenerHandler = new ListenerHandler(instance);
        lockHandler = new LockHandler(instance);
        apiHandler = new BoltUXAPIProvider(instance);

        handlers = List.of(
            configHandler,
            translationHandler,
            updateHandler,
            schedulerHandler,
            craftingHandler,
            hookManager,
            commandHandler,
            listenerHandler,
            lockHandler,
            apiHandler
        );

        for (Reloadable handler : handlers)
            handler.onLoad(instance);
    }

    @Override
    public void onEnable() {
        // Bolt initialization
        boltPlugin = (BoltPlugin) Bukkit.getServer().getPluginManager().getPlugin("Bolt");

        for (Reloadable handler : handlers)
            handler.onEnable(instance);

        if (!Hook.PacketEvents.isLoaded()) {
            Logger.get().warn(ColorParser.of("You are running BoltUX but you do not have PacketEvents installed. Plugin functionality will be limited!").build());
        }
    }

    @Override
    public void onDisable() {
        for (Reloadable handler : handlers.reversed())
            handler.onDisable(instance);
    }

    /**
     * Use to reload the entire plugin.
     */
    public void onReload() {
        onDisable();
        onLoad();
        onEnable();
    }


    public static BoltPlugin getBoltPlugin() {
        return boltPlugin;
    }

    @Override
    @NotNull
    public ConfigHandler getConfigHandler() {
        return configHandler;
    }

    public @NotNull HookManager getHookManager() {
        return hookManager;
    }

    public @NotNull UpdateHandler getUpdateHandler() {
        return updateHandler;
    }

    public @NotNull BoltUXAPI getApiHandler() {
        return apiHandler;
    }
}
