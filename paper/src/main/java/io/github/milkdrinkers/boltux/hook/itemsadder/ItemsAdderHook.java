package io.github.milkdrinkers.boltux.hook.itemsadder;

import dev.lone.itemsadder.api.CustomStack;
import io.github.milkdrinkers.boltux.AbstractBoltUX;
import io.github.milkdrinkers.boltux.BoltUX;
import io.github.milkdrinkers.boltux.config.Settings;
import io.github.milkdrinkers.boltux.hook.AbstractHook;
import io.github.milkdrinkers.boltux.hook.Hook;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

public class ItemsAdderHook extends AbstractHook {

    public ItemsAdderHook(BoltUX plugin) {
        super(plugin);
    }

    @Override
    public boolean isHookLoaded() {
        return isPluginEnabled(Hook.ItemsAdder.getPluginName());
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
        CustomStack customStack = CustomStack.getInstance(Settings.getCustomLockItemID());
        if (customStack == null) {
            return null;
        }
        return customStack.getItemStack();
    }
}
