package io.github.milkdrinkers.boltux.config;

import io.github.milkdrinkers.boltux.BoltUX;
import io.github.milkdrinkers.boltux.utility.Cfg;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.intellij.lang.annotations.Subst;

import java.util.List;
import java.util.Objects;

public class Settings {
    /**
     * @return The time in seconds a locked block/entity glows for when interact is denied
     */
    public static int getGlowBlockTime() {
        return Cfg.get().general.blockGlowTime;
    }

    public static int getNearbyPlayersRange() {
        return Cfg.get().gui.nearbyPlayersRange;
    }

    public static boolean isLockItemEnabled() {
        return BoltUX.getInstance().getLockItemHandler().isEnabled();
    }

    public static boolean isDefaultLockCraftingRecipeEnabled() {
        return Cfg.get().lockItem.enableCraftingRecipe && Cfg.get().lockItem.item.isBlank();
    }

    public static boolean isLockDroppingEnabled() {
        return Cfg.get().lockItem.protectionsDropLocks;
    }

    public static boolean isLockingSoundEnabled() {
        return Cfg.get().lockItem.sound.enabled;
    }

    public static Sound getLockingSound() {
        final PluginConfig.LockItem.Sound sound = Cfg.get().lockItem.sound;
        @Subst("minecraft:entity.zombie.attack_iron_door") final String soundID = sound.effect;

        return Sound.sound()
            .type(Key.key(soundID))
            .source(Sound.Source.BLOCK)
            .volume((float) sound.volume)
            .pitch((float) sound.pitch)
            .build();
    }

    public static List<World> getLockItemEnabledWorlds() {
        return Cfg.get().lockItem.enabledWorlds
            .stream()
            .map(Bukkit::getWorld)
            .filter(Objects::nonNull)
            .toList();
    }

    public static boolean isLockingDisabledInOtherTowns() {
        return Cfg.get().towny.disableLockingInOtherTowns;
    }

    public static boolean canMayorsAccessProtections() {
        return Cfg.get().towny.allowMayorsToAccessLocked;
    }

    public static boolean isQuickShopLockingDisabled() {
        return Cfg.get().quickShop.disableLockingShops;
    }
}
