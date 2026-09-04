package io.github.milkdrinkers.boltux.config;

import io.github.milkdrinkers.boltux.config.migration.Migration;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.NodePath;
import org.spongepowered.configurate.interfaces.meta.Exclude;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Comment;
import org.spongepowered.configurate.transformation.ConfigurationTransformation;

import java.util.List;
import java.util.Locale;
import java.util.Map;

@ConfigSerializable
public class PluginConfig implements VersionedConfig {
    @Comment("Do not change this value!")
    public int configVersion = 2;

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
                .build(),
            2, Migration.builder()
                .withTransform(
                    ConfigurationTransformation.builder()
                        .addAction(NodePath.path("lock-item"), (path, lockItem) -> {
                            final String plugin = lockItem.node("item-plugin").getString("");
                            final String itemId = lockItem.node("custom-lock-item-id").getString("");

                            if (!plugin.isBlank() && !plugin.equalsIgnoreCase("None") && !itemId.isBlank())
                                lockItem.node("item").set("%s:%s".formatted(plugin.toLowerCase(Locale.ROOT), itemId));

                            final ConfigurationNode oldFallback = lockItem.node("default-lock-item");
                            if (!oldFallback.virtual()) {
                                final ConfigurationNode fallback = lockItem.node("fallback");
                                for (final Map.Entry<Object, ? extends ConfigurationNode> entry : oldFallback.childrenMap().entrySet())
                                    fallback.node(entry.getKey()).from(entry.getValue());
                            }

                            return null;
                        })
                        .build()
                )
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
            The item used as a lock. Prefix the id with the plugin that owns it: nexo:my_lock, oraxen:my_lock, itemsadder:my_lock, ia:my_lock
            A bare id or a minecraft: prefix is a vanilla material, for example "iron_ingot".
            Leave this blank to always use the fallback item defined below.""")
        public String item = "";

        @Comment("The item used when \"item\" is blank or when it cannot be resolved because the owning plugin is missing or the id is unknown. Uses minimessage color formats.")
        public Fallback fallback = new Fallback();

        @ConfigSerializable
        public static class Fallback {
            @Comment("Turn this off to disable locks entirely instead of using fallback")
            public boolean enabled = true;

            public String material = "IRON_INGOT";

            @Comment("An item model key, like \"boltux:lock\". Recommended instead of custom model data on 1.21.4 and above. Leave blank to not use.")
            public String itemModel = "";

            @Comment("Legacy model selector. Set to 0 to disable it. The bundled resourcepack uses 8792")
            public int customModelData = 8792;

            @Comment("""
                The fallback needs at least one of item model, custom model data or display name.
                Without any of them it would be an ordinary material, and every one of them on the server would be usable as a lock.""")
            public String displayName = "<gray>Iron Lock</gray>";

            public List<String> lore = List.of(
                "<yellow>Shift-Right Click to Use</yellow>",
                "<yellow>Lockable Things: Containers, Doors, Gates, Trapdoors</yellow>"
            );
        }

        @Comment("""
            If the default lock recipe should be registered when the plugin is enabled
            Only applies when "item" is blank, custom plugin item is expected to bring its own recipe.""")
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
