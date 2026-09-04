package io.github.milkdrinkers.boltux;

import io.github.milkdrinkers.boltux.api.BoltUXAPI;
import io.github.milkdrinkers.boltux.lock.item.LockItem;
import io.github.milkdrinkers.boltux.lock.item.LockItemHandler;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

class BoltUXAPIProvider extends BoltUXAPI implements Reloadable {
    private final LockItemHandler lockItemHandler;

    BoltUXAPIProvider(LockItemHandler lockItemHandler) {
        super();
        this.lockItemHandler = lockItemHandler;
        setInstance(this);
    }

    @Override
    public @NotNull ItemStack getLockItem(int amount) {
        final LockItem lockItem = lockItemHandler.get();
        if (lockItem == null)
            throw new IllegalStateException("No lock item is configured or it could not be resolved, see the server log");

        return lockItem.create(amount);
    }

    @Override
    public boolean isLockItem(@NotNull ItemStack itemStack) {
        return lockItemHandler.matches(itemStack);
    }
}
