package io.github.milkdrinkers.boltux.hook.quickshop;

import com.ghostchu.quickshop.api.QuickShopAPI;
import io.github.milkdrinkers.boltux.AbstractBoltUX;
import io.github.milkdrinkers.boltux.BoltUX;
import io.github.milkdrinkers.boltux.hook.AbstractHook;
import io.github.milkdrinkers.boltux.hook.Hook;
import org.bukkit.Location;

public final class QuickShopHook extends AbstractHook {
    public QuickShopHook(BoltUX plugin) {
        super(plugin);
    }

    @Override
    public boolean isHookLoaded() {
        return isPluginEnabled(Hook.QuickShop.getPluginName());
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

    public boolean isQuickShop(Location location) {
        return QuickShopAPI.getInstance().getShopManager().getShop(location) != null;
    }

}
