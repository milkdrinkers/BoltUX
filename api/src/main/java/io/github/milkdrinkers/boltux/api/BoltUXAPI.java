package io.github.milkdrinkers.boltux.api;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

/**
 * Main entry point BoltUX API.
 */
public abstract class BoltUXAPI {
    private static BoltUXAPI INSTANCE;

    /**
     * Gets the instance of the BoltUX API.
     *
     * @return the BoltUX API instance
     * @since 1.0.0
     */
    public static BoltUXAPI getInstance() {
        if (INSTANCE == null)
            throw new RuntimeException("API was accessed before being initialized!");
        return INSTANCE;
    }

    /**
     * Sets the instance of the BoltUX API.
     * This method is intended for internal use by the api provider only.
     *
     * @param api the instance
     * @since 1.0.0
     */
    @ApiStatus.Internal
    protected static void setInstance(BoltUXAPI api) {
        INSTANCE = api;
    }

    /**
     * Get the lock item.
     *
     * @param amount the amount of lock items in the resulting itemstack
     * @return the lock item from the configured item plugin, or the default (native) lock item
     * @since 1.0.0
     */
    public abstract @NotNull ItemStack getLockItem(int amount);

    /**
     * Get the lock item.
     *
     * @return the lock item from the configured item plugin, or the default (native) lock item
     * @since 1.0.0
     */
    public @NotNull ItemStack getLockItem() {
        return getLockItem(1);
    }

    /**
     * Get whether the provided ItemStack is a lock
     *
     * @param itemStack the itemstack to check
     * @return whether the ItemStack consists of lock items
     * @since 1.0.0
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean isLockItem(@NotNull ItemStack itemStack) {
        return itemStack.isSimilar(getLockItem());
    }
}
