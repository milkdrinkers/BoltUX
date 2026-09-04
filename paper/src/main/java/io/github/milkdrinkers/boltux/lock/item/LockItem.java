package io.github.milkdrinkers.boltux.lock.item;

import io.github.milkdrinkers.itemutil.ItemUtils;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public sealed interface LockItem {
    /**
     * Build a stack of lock items.
     *
     * @param amount the stack size
     * @return a new lock item stack
     */
    @NotNull ItemStack create(int amount);

    /**
     * Check whether a stack is this lock item.
     *
     * @param itemStack the stack to test
     * @return whether the stack is a lock
     */
    boolean matches(@NotNull ItemStack itemStack);

    /**
     * A lock item owned by a custom item plugin, addressed as {@code namespace:id}.
     *
     * @param itemId the namespaced item id, for example {@code nexo:my_lock}
     */
    record Custom(@NotNull String itemId) implements LockItem {
        @Override
        public @NotNull ItemStack create(int amount) {
            final ItemStack itemStack = ItemUtils.parse(itemId);
            if (itemStack == null)
                throw new IllegalStateException("Lock item \"%s\" no longer resolves".formatted(itemId));

            itemStack.setAmount(amount);
            return itemStack;
        }

        @Override
        public boolean matches(@NotNull ItemStack itemStack) {
            return ItemUtils.match(itemStack, itemId);
        }
    }

    /**
     * A lock item built by this plugin from the fallback config section.
     *
     * @param prototype the built item, of amount one
     */
    record Vanilla(@NotNull ItemStack prototype) implements LockItem {
        @Override
        public @NotNull ItemStack create(int amount) {
            final ItemStack itemStack = prototype.clone();
            itemStack.setAmount(amount);
            return itemStack;
        }

        @Override
        public boolean matches(@NotNull ItemStack itemStack) {
            return itemStack.isSimilar(prototype);
        }
    }
}
