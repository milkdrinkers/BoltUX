package io.github.milkdrinkers.boltux.lock.item;

import io.github.milkdrinkers.boltux.AbstractBoltUX;
import io.github.milkdrinkers.boltux.Reloadable;
import io.github.milkdrinkers.boltux.config.PluginConfig;
import io.github.milkdrinkers.boltux.utility.Cfg;
import io.github.milkdrinkers.boltux.utility.Logger;
import io.github.milkdrinkers.colorparser.paper.ColorParser;
import io.github.milkdrinkers.itemutil.ItemUtils;
import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.CustomModelData;
import io.papermc.paper.datacomponent.item.ItemLore;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class LockItemHandler implements Reloadable {
    private @Nullable LockItem lockItem;
    private boolean resolved;

    @Override
    public void onLoad(AbstractBoltUX plugin) {
        reset();
    }

    @Override
    public void onDisable(AbstractBoltUX plugin) {
        reset();
    }

    private void reset() {
        lockItem = null;
        resolved = false;
    }

    /**
     * Whether the lock item feature is on and has something usable to hand out.
     *
     * @return whether locks are usable
     */
    public boolean isEnabled() {
        return Cfg.get().lockItem.enabled && get() != null;
    }

    /**
     * Gets the resolved lock item.
     *
     * @return the lock item, or null if it could not be resolved
     */
    public @Nullable LockItem get() {
        if (!resolved) {
            lockItem = resolve();
            resolved = true;
        }
        return lockItem;
    }

    /**
     * Checks whether a stack is the lock item.
     *
     * @param itemStack the stack to test
     * @return whether the stack is a lock
     */
    public boolean matches(@NotNull ItemStack itemStack) {
        if (itemStack.isEmpty())
            return false;

        final LockItem item = get();
        return item != null && item.matches(itemStack);
    }

    private @Nullable LockItem resolve() {
        final PluginConfig.LockItem cfg = Cfg.get().lockItem;
        final String itemId = cfg.item.trim();

        if (!itemId.isEmpty()) {
            if (ItemUtils.exists(itemId))
                return new LockItem.Custom(itemId);

            Logger.get().warn(ColorParser.of("<yellow>Could not resolve lock item \"<item>\". Either the plugin providing it is not installed, or no item with that id exists.")
                .with("item", itemId)
                .build());
        }

        if (!cfg.fallback.enabled) {
            Logger.get().error(ColorParser.of("<red>No lock item is available and the fallback is disabled. Locks have been turned off.").build());
            return null;
        }

        return buildFallback(cfg.fallback);
    }

    private @Nullable LockItem buildFallback(PluginConfig.LockItem.Fallback cfg) {
        final @Nullable Material material = Material.matchMaterial(cfg.material);
        if (material == null || !material.isItem()) {
            Logger.get().error(ColorParser.of("<red>Fallback lock item material \"<material>\" is not a valid item. Locks have been turned off.")
                .with("material", cfg.material)
                .build());
            return null;
        }

        final @Nullable Key itemModel = parseItemModel(cfg.itemModel);
        final boolean hasName = !cfg.displayName.isBlank();

        if (itemModel == null && cfg.customModelData <= 0 && !hasName) {
            Logger.get().error(ColorParser.of("<red>The fallback lock item needs at least one of item-model, custom-model-data or display-name, otherwise every <material> would count as a lock. Locks have been turned off.")
                .with("material", material.key().asString())
                .build());
            return null;
        }

        final ItemStack item = new ItemStack(material);

        if (hasName)
            item.setData(DataComponentTypes.CUSTOM_NAME, parse(cfg.displayName));

        if (!cfg.lore.isEmpty())
            item.setData(DataComponentTypes.LORE, ItemLore.lore(cfg.lore.stream().map(LockItemHandler::parse).toList()));

        if (itemModel != null)
            item.setData(DataComponentTypes.ITEM_MODEL, itemModel);

        if (cfg.customModelData > 0)
            item.setData(DataComponentTypes.CUSTOM_MODEL_DATA, CustomModelData.customModelData()
                .addFloat(cfg.customModelData)
                .build());

        return new LockItem.Vanilla(item);
    }

    private @Nullable Key parseItemModel(String itemModel) {
        if (itemModel.isBlank())
            return null;

        if (!Key.parseable(itemModel)) {
            Logger.get().warn(ColorParser.of("<yellow>Ignoring item-model \"<model>\", it is not a valid namespaced key.")
                .with("model", itemModel)
                .build());
            return null;
        }

        return Key.key(itemModel);
    }

    private static Component parse(String text) {
        return ColorParser.of(text).build().decoration(TextDecoration.ITALIC, false);
    }
}
