package io.github.milkdrinkers.boltux.hook.mmoitems;

import io.github.milkdrinkers.boltux.AbstractBoltUX;
import io.github.milkdrinkers.boltux.BoltUX;
import io.github.milkdrinkers.boltux.config.Settings;
import io.github.milkdrinkers.boltux.hook.AbstractHook;
import io.github.milkdrinkers.boltux.hook.Hook;
import net.Indyuce.mmoitems.MMOItems;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

public class MMOItemsHook extends AbstractHook {

    public MMOItemsHook(BoltUX plugin) {
        super(plugin);
    }

    @Override
    public boolean isHookLoaded() {
        return isPluginEnabled(Hook.MMOItems.getPluginName());
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
        if (!Settings.getCustomLockItemID().contains(".")) {
            return null;
        }
        String[] inputs = Settings.getCustomLockItemID().split("\\.", 2); // split result into TYPE.ID
        return MMOItems.plugin.getItem(inputs[0], inputs[1]);
    }
}
