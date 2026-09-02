package io.github.milkdrinkers.boltux.config;

import io.github.milkdrinkers.boltux.data.ItemPlugin;
import io.github.milkdrinkers.boltux.utility.Cfg;
import io.github.milkdrinkers.boltux.utility.Logger;
import io.github.milkdrinkers.colorparser.paper.ColorParser;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
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
        return Cfg.get().lockItem.enabled;
    }

    public static boolean isDefaultLockCraftingRecipeEnabled() {
        return Cfg.get().lockItem.enableCraftingRecipe;
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

    public static ItemPlugin getItemPlugin() {
        // Default to empty String, no plugin
        final String itemPluginString = Cfg.get().lockItem.itemPlugin;
        if (itemPluginString.isEmpty() || itemPluginString.equalsIgnoreCase("None")) {
            return ItemPlugin.NONE;
        } else if (itemPluginString.equalsIgnoreCase("ItemsAdder")) {
            return ItemPlugin.ITEMSADDER;
        } else if (itemPluginString.equalsIgnoreCase("MMOItems")) {
            return ItemPlugin.MMOITEMS;
        } else if (itemPluginString.equalsIgnoreCase("Nexo")) {
            return ItemPlugin.NEXO;
        } else if (itemPluginString.equalsIgnoreCase("Oraxen")) {
            return ItemPlugin.ORAXEN;
        } else {
            Logger.get().warn("Invalid 'item-plugin' defined in config.yml. Defaulting to none...");
            return ItemPlugin.NONE;
        }
    }

    public static String getCustomLockItemID() {
        return Cfg.get().lockItem.customLockItemId;
    }

    public static Material getDefaultLockItemMaterial() {
        try {
            return Material.valueOf(Cfg.get().lockItem.defaultLockItem.material);
        } catch (IllegalArgumentException e) {
            return Material.IRON_INGOT;
        }
    }

    public static int getDefaultLockItemCustomModelData() {
        return Cfg.get().lockItem.defaultLockItem.customModelData;
    }

    public static Component getDefaultLockItemDisplayName() {
        return ColorParser.of(Cfg.get().lockItem.defaultLockItem.displayName)
            .build()
            .decoration(TextDecoration.ITALIC, false);
    }

    public static List<Component> getDefaultLockItemLore() {
        final List<String> loreStrings = Cfg.get().lockItem.defaultLockItem.lore;
        if (loreStrings.isEmpty())
            return List.of(Component.empty());

        return loreStrings.stream()
            .map(line -> ColorParser.of(line).build().decoration(TextDecoration.ITALIC, false))
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
