package io.github.milkdrinkers.boltux.lock;

import io.github.milkdrinkers.boltux.AbstractBoltUX;
import io.github.milkdrinkers.boltux.BoltUX;
import io.github.milkdrinkers.boltux.Reloadable;
import io.github.milkdrinkers.boltux.lock.listener.ListenerHandler;

public final class LockHandler implements Reloadable {
    private ListenerHandler listenerHandler;
    private CraftingHandler craftingHandler;

    public LockHandler(BoltUX plugin) {
    }

    @Override
    public void onLoad(AbstractBoltUX plugin) {
        listenerHandler = new ListenerHandler(plugin);
        craftingHandler = new CraftingHandler(plugin);
        listenerHandler.onLoad(plugin);
        craftingHandler.onLoad(plugin);
    }

    @Override
    public void onEnable(AbstractBoltUX plugin) {
        listenerHandler.onEnable(plugin);
        craftingHandler.onEnable(plugin);
    }

    @Override
    public void onDisable(AbstractBoltUX plugin) {
        listenerHandler.onDisable(plugin);
        craftingHandler.onDisable(plugin);
        listenerHandler = null;
        craftingHandler = null;
    }
}
