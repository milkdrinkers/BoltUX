package io.github.milkdrinkers.boltux.lock;

import io.github.milkdrinkers.boltux.AbstractBoltUX;
import io.github.milkdrinkers.boltux.BoltUX;
import io.github.milkdrinkers.boltux.Reloadable;
import io.github.milkdrinkers.boltux.api.BoltUXAPI;
import io.github.milkdrinkers.boltux.config.Settings;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.jetbrains.annotations.NotNull;

public final class CraftingHandler implements Reloadable {
    private final AbstractBoltUX plugin;

    public CraftingHandler(AbstractBoltUX plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onEnable(AbstractBoltUX plugin) {
        if (Settings.isDefaultLockCraftingRecipeEnabled() && Settings.isLockItemEnabled()) {
            if (plugin.getServer().getRecipe(lockRecipeKey()) == null) {
                plugin.getServer().addRecipe(getLockRecipe());
            }
        }
    }

    public static @NotNull NamespacedKey lockRecipeKey() {
        return new NamespacedKey(BoltUX.getInstance(), "lock");
    }

    public static Recipe getLockRecipe() {
        final ShapedRecipe lockRecipe = new ShapedRecipe(lockRecipeKey(), BoltUXAPI.getInstance().getLockItem());
        lockRecipe.shape(" % ", "@ @", "@@@");
        lockRecipe.setIngredient('@', Material.IRON_INGOT);
        lockRecipe.setIngredient('%', Material.IRON_NUGGET);
        return lockRecipe;
    }
}
