package io.github.milkdrinkers.boltux.hook.oraxen;

import io.github.milkdrinkers.boltux.AbstractBoltUX;
import io.github.milkdrinkers.boltux.BoltUX;
import io.github.milkdrinkers.boltux.config.Settings;
import io.github.milkdrinkers.boltux.hook.AbstractHook;
import io.github.milkdrinkers.boltux.hook.Hook;
import io.th0rgal.oraxen.api.OraxenItems;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

public class OraxenHook extends AbstractHook {

    public OraxenHook(BoltUX plugin) {
        super(plugin);
    }

    @Override
    public boolean isHookLoaded() {
        return isPluginEnabled(Hook.Oraxen.getPluginName());
    }

    @Override
    public void onLoad(AbstractBoltUX plugin) {
        if (!isHookLoaded()) {
        }
    }

    @Override
    public void onEnable(AbstractBoltUX plugin) {
        if (!isHookLoaded()) {
        }
    }

    @Override
    public void onDisable(AbstractBoltUX plugin) {
        if (!isHookLoaded()) {
        }
    }

    public @Nullable ItemStack getLockItem() {
        if (OraxenItems.exists(Settings.getCustomLockItemID())) {
            return (OraxenItems.getItemById(Settings.getCustomLockItemID()).build());
        }
        return null;
    }
}
