package io.github.milkdrinkers.boltux.hook.nexo;

import com.nexomc.nexo.api.NexoItems;
import io.github.milkdrinkers.boltux.AbstractBoltUX;
import io.github.milkdrinkers.boltux.BoltUX;
import io.github.milkdrinkers.boltux.config.Settings;
import io.github.milkdrinkers.boltux.hook.AbstractHook;
import io.github.milkdrinkers.boltux.hook.Hook;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class NexoHook extends AbstractHook {

    public NexoHook(BoltUX plugin) {
        super(plugin);
    }

    @Override
    public boolean isHookLoaded() {
        return isPluginEnabled(Hook.Nexo.getPluginName());
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
        return Objects.requireNonNull(NexoItems.itemFromId(Settings.getCustomLockItemID())).build();
    }
}
