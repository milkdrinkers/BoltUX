package io.github.milkdrinkers.boltux.lock.listener;

import io.github.milkdrinkers.boltux.AbstractBoltUX;
import io.github.milkdrinkers.boltux.Reloadable;

public final class ListenerHandler implements Reloadable {
    private final AbstractBoltUX plugin;

    public ListenerHandler(AbstractBoltUX plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onEnable(AbstractBoltUX plugin) {
        plugin.getServer().getPluginManager().registerEvents(new LockUseListener(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new LockDropListener(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new LockCraftingListener(), plugin);
    }

}
