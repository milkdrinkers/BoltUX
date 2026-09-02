package io.github.milkdrinkers.boltux.config;

import io.github.milkdrinkers.boltux.config.migration.Migration;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.NodePath;
import org.spongepowered.configurate.interfaces.meta.Exclude;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Comment;

import java.util.List;
import java.util.Map;

@ConfigSerializable
public class PluginConfig implements VersionedConfig {
    @Comment("Do not change this value!")
    public int configVersion = 1;

    @Override
    @Exclude
    public int configVersion() {
        return configVersion;
    }

    @Override
    @Exclude
    public @NotNull Map<Integer, Migration> migrations() {
        return Map.of(
            1, Migration.builder()
                .rename(NodePath.path("update-checker", "enable"), "enabled")
                .relocate(NodePath.path("GeneralSettings", "blockGlowTime"), "general", "block-glow-time")
                .relocate(NodePath.path("GuiSettings", "nearbyPlayersRange"), "gui", "nearby-players-range")
                .relocate(NodePath.path("LockItem", "enabled"), "lock-item", "enabled")
                .relocate(NodePath.path("LockItem", "enableCraftingRecipe"), "lock-item", "enable-crafting-recipe")
                .relocate(NodePath.path("LockItem", "protectionsDropLocks"), "lock-item", "protections-drop-locks")
                .relocate(NodePath.path("LockItem", "enabledWorlds"), "lock-item", "enabled-worlds")
                .relocate(NodePath.path("LockItem", "sound"), "lock-item", "sound")
                .relocate(NodePath.path("LockItem", "itemPlugin"), "lock-item", "item-plugin")
                .relocate(NodePath.path("LockItem", "customLockItemID"), "lock-item", "custom-lock-item-id")
                .relocate(NodePath.path("LockItem", "DefaultLockItem", "material"), "lock-item", "default-lock-item", "material")
                .relocate(NodePath.path("LockItem", "DefaultLockItem", "customModelData"), "lock-item", "default-lock-item", "custom-model-data")
                .relocate(NodePath.path("LockItem", "DefaultLockItem", "displayName"), "lock-item", "default-lock-item", "display-name")
                .relocate(NodePath.path("LockItem", "DefaultLockItem", "lore"), "lock-item", "default-lock-item", "lore")
                .relocate(NodePath.path("TownyCompatibility", "disableLockingInOtherTowns"), "towny", "disable-locking-in-other-towns")
                .relocate(NodePath.path("TownyCompatibility", "allowMayorsToAccessLocked"), "towny", "allow-mayors-to-access-locked")
                .relocate(NodePath.path("QuickShopCompatibility", "disableLockingShops"), "quick-shop", "disable-locking-shops")
                .build()
        );
    }

    @Comment("Update Checker Settings")
    public UpdateChecker updateChecker = new UpdateChecker();

    @ConfigSerializable
    public static class UpdateChecker {
        @Comment("Should the plugin check for plugin updates on startup?")
        public boolean enabled = true;

        @Comment("Send update notifications to the console?")
        public boolean console = true;

        @Comment("Send update notifications to opped players on join?")
        public boolean op = true;
    }

    @Comment("Language, specify the language file to use, for example `en_US` which will load `/lang/en_US.json`")
    public String language = "en_US";

    @Comment("General Settings")
    public General general = new General();

    @ConfigSerializable
    public static class General {
        @Comment("Amount of seconds the block glow effect lasts when the use of a locked item is denied")
        public int blockGlowTime = 5;
    }

    @Comment("GUI Settings")
    public Gui gui = new Gui();

    @ConfigSerializable
    public static class Gui {
        @Comment("When a menu is grabbing nearby players for suggestions, this is how far it will look around the player")
        public int nearbyPlayersRange = 100;
    }

    @Comment("Lock Item Settings")
    public LockItem lockItem = new LockItem();

    @ConfigSerializable
    public static class LockItem {
        @Comment("If the lock item is enabled. Setting this to false will make lock items do nothing")
        public boolean enabled = true;

        @Comment("""
            If the default lock recipe should be enabled (loaded) when the plugin is enabled
            If using an item plugin this will automatically be disabled""")
        public boolean enableCraftingRecipe = true;

        @Comment("If protected blocks/entities will drop a lock item when broken")
        public boolean protectionsDropLocks = true;

        @Comment("What worlds players will be able to use locks in")
        public List<String> enabledWorlds = List.of("world", "world_nether", "world_the_end");

        @Comment("The sound made when a player creates a new protection with a lock")
        public Sound sound = new Sound();

        @ConfigSerializable
        public static class Sound {
            public boolean enabled = true;
            public String effect = "minecraft:entity.zombie.attack_iron_door";
            public double volume = 0.25;
            public double pitch = 1.0;
        }

        @Comment("Possible entries are \"ItemsAdder\", \"MMOItems\", \"Nexo\", \"Oraxen\" or \"None\". Leave blank or use \"None\" to use the default lock item")
        public String itemPlugin = "";

        @Comment("The identifier of the custom lock item. Only applicable if using an item plugin defined above")
        public String customLockItemId = "";

        @Comment("""
            The default lock item if no item plugin is specified
            Uses MiniMessage color formats""")
        public DefaultLockItem defaultLockItem = new DefaultLockItem();

        @ConfigSerializable
        public static class DefaultLockItem {
            public String material = "IRON_INGOT";
            public int customModelData = 8792;
            public String displayName = "<gray><b>Iron Lock</b></gray>";
            public List<String> lore = List.of(
                "<yellow>Shift-Right Click to Use</yellow>",
                "<yellow>Lockable Things: Containers, Doors, Gates, Trapdoors</yellow>"
            );
        }
    }

    @Comment("Towny Compatibility Settings")
    public Towny towny = new Towny();

    @ConfigSerializable
    public static class Towny {
        @Comment("Disable locking in towns that a player is not a part of")
        public boolean disableLockingInOtherTowns = true;

        @Comment("Allow Town mayors to access protections within their town claims")
        public boolean allowMayorsToAccessLocked = true;
    }

    @Comment("QuickShop Compatibility Settings")
    public QuickShop quickShop = new QuickShop();

    @ConfigSerializable
    public static class QuickShop {
        @Comment("Disable locking of QuickShop shops")
        public boolean disableLockingShops = true;
    }
}
