package io.github.milkdrinkers.boltux;

import io.github.milkdrinkers.boltux.api.BoltUXAPI;
import io.github.milkdrinkers.boltux.config.Settings;
import io.github.milkdrinkers.boltux.hook.Hook;
import io.github.milkdrinkers.boltux.utility.Logger;
import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.CustomModelData;
import io.papermc.paper.datacomponent.item.ItemLore;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

class BoltUXAPIProvider extends BoltUXAPI implements Reloadable {
    private final BoltUX plugin;

    BoltUXAPIProvider(BoltUX plugin) {
        super();
        this.plugin = plugin;
        setInstance(this);
    }

    @Override
    public @NotNull ItemStack getLockItem(int amount) {
        @Nullable ItemStack itemStack = switch (Settings.getItemPlugin()) {
            case ITEMSADDER ->
                Hook.ItemsAdder.isLoaded() ? Hook.getItemsAdderHook().getLockItem() : getDefaultLockItem();
            case MMOITEMS -> Hook.MMOItems.isLoaded() ? Hook.getMMOItemsHook().getLockItem() : getDefaultLockItem();
            case NEXO -> Hook.Nexo.isLoaded() ? Hook.getNexoHook().getLockItem() : getDefaultLockItem();
            case ORAXEN -> Hook.Oraxen.isLoaded() ? Hook.getOraxenHook().getLockItem() : getDefaultLockItem();
            default -> getDefaultLockItem();
        };

        if (itemStack == null) {
            Logger.get().error("Lock item failed to load! If you are using an item plugin make sure the ID is correct");
            Logger.get().warn("Loading default lock item...");
            itemStack = getDefaultLockItem();
        }

        itemStack.setAmount(amount);
        return itemStack;
    }

    /**
     * Get the default lock item.
     *
     * @return the default (native) lock item used when no item plugin is specified
     */
    private static @NotNull ItemStack getDefaultLockItem() {
        final ItemStack item = new ItemStack(Settings.getDefaultLockItemMaterial());
        item.setData(DataComponentTypes.CUSTOM_NAME, Settings.getDefaultLockItemDisplayName());
        item.setData(DataComponentTypes.LORE, ItemLore.lore(Settings.getDefaultLockItemLore()));
        item.setData(DataComponentTypes.CUSTOM_MODEL_DATA, CustomModelData.customModelData()
            .addFloat(Settings.getDefaultLockItemCustomModelData())
            .build()
        );
        return item;
    }
}
