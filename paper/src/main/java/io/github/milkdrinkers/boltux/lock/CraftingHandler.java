package io.github.milkdrinkers.boltux.lock;

import io.github.milkdrinkers.boltux.AbstractBoltUX;
import io.github.milkdrinkers.boltux.BoltUX;
import io.github.milkdrinkers.boltux.Reloadable;
import io.github.milkdrinkers.boltux.api.BoltUXAPI;
import io.github.milkdrinkers.boltux.config.Settings;
import io.github.milkdrinkers.boltux.data.ItemPlugin;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

public final class CraftingHandler implements Reloadable {
    private final AbstractBoltUX plugin;

    public CraftingHandler(AbstractBoltUX plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onEnable(AbstractBoltUX plugin) {
        if (Settings.isDefaultLockCraftingRecipeEnabled() && Settings.isLockItemEnabled() && Settings.getItemPlugin().equals(ItemPlugin.NONE)) {
            if (plugin.getServer().getRecipe(new NamespacedKey(BoltUX.getInstance(), "lock")) == null) {
                plugin.getServer().addRecipe(getLockRecipe());
            }
        }
    }

    public static Recipe getLockRecipe() {
        final NamespacedKey key = new NamespacedKey(BoltUX.getInstance(), "lock");
        final ShapedRecipe lockRecipe = new ShapedRecipe(key, BoltUXAPI.getInstance().getLockItem());
        lockRecipe.shape(" % ", "@ @", "@@@");
        lockRecipe.setIngredient('@', Material.IRON_INGOT);
        lockRecipe.setIngredient('%', Material.IRON_NUGGET);
        return lockRecipe;
    }
}
