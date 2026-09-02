package io.github.milkdrinkers.boltux.updatechecker;

import io.github.milkdrinkers.boltux.AbstractBoltUX;
import io.github.milkdrinkers.boltux.BoltUX;
import io.github.milkdrinkers.boltux.Reloadable;
import io.github.milkdrinkers.boltux.utility.Cfg;
import io.github.milkdrinkers.boltux.utility.Logger;
import io.github.milkdrinkers.colorparser.paper.ColorParser;
import io.github.milkdrinkers.javasemver.Version;
import io.github.milkdrinkers.javasemver.exception.VersionParseException;
import io.github.milkdrinkers.versionwatch.Platform;
import io.github.milkdrinkers.versionwatch.VersionWatcher;
import io.github.milkdrinkers.wordweaver.Translation;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.translation.Argument;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.VisibleForTesting;

/**
 * Run update checks against your release platform.
 */
public final class UpdateHandler implements Reloadable {
    private final static String GITHUB_USER = "milkdrinkers"; // The GitHub user/organization name
    private final static String GITHUB_REPO = "BoltUX"; // The GitHub repository

    private final VersionWatcher watcher;

    public UpdateHandler(BoltUX plugin) {
        this.watcher = buildWatcher(plugin.getName(), plugin.getPluginMeta().getVersion());
    }

    /**
     * Builds the version watcher.
     *
     * <p>Split out of the constructor so it can be built without a running server.
     * {@code VersionWatcher.Builder.build()} validates its required fields at runtime, so a missing
     * one would only surface when a server starts.
     *
     * @param pluginName    the plugin name, used for the user agent
     * @param pluginVersion the raw version string from the plugin descriptor
     * @return a configured watcher
     */
    @VisibleForTesting
    static @NotNull VersionWatcher buildWatcher(@NotNull String pluginName, @NotNull String pluginVersion) {
        final Version version = parseVersion(pluginVersion);

        return VersionWatcher.builder()
            .withPlatform(Platform.GitHub)
            .withVersion(version)
            .withResourceOwner(GITHUB_USER)
            .withResourceSlug(GITHUB_REPO)
            .withAgent(pluginName + version)
            .build();
    }

    /**
     * On plugin enable.
     */
    @Override
    public void onEnable(AbstractBoltUX plugin) {
        final boolean shouldLog = Cfg.get().updateChecker.enabled && Cfg.get().updateChecker.console;

        // Fetch the latest version and send message to console
        watcher.fetchLatestAsync().thenAccept(version -> {
            if (version == null)
                return;

            if (!shouldLog)
                return;

            if (watcher.isLatest()) {
                Logger.get().info(
                    ColorParser.of(Translation.of("boltux.update-checker.running-latest"))
                        .with("plugin_name", plugin.getName())
                        .build()
                );
            } else {
                Logger.get().info(
                    ColorParser.of(Translation.of("boltux.update-checker.update-found-console"))
                        .with("plugin_name", plugin.getName())
                        .with("version_current", watcher.getCurrentVersion().getVersionFull())
                        .with("version_latest", version.getVersionFull())
                        .with("download_link", watcher.getDownloadURL())
                        .build()
                );
            }
        }).exceptionally(throwable -> {
            if (shouldLog)
                Logger.get().warn(ColorParser.of(Translation.of("boltux.update-checker.update-failed")).with("error", throwable.getMessage()).build());
            return null;
        });

        // Register version check message listener for opped player joins
        plugin.getServer().getPluginManager().registerEvents(new Listener() {
            @EventHandler
            @SuppressWarnings("unused")
            public void onPlayerJoin(PlayerJoinEvent e) {
                final Player p = e.getPlayer();

                if (watcher.isLatest())
                    return;

                if (!Cfg.get().updateChecker.enabled || !Cfg.get().updateChecker.op)
                    return;

                if (!p.isOp())
                    return;

                if (watcher.getLatestVersion() == null)
                    return;

                p.sendMessage(
                    Component.translatable(
                        "boltux.update-checker.update-found-player",
                        Argument.string("plugin_name", plugin.getName()),
                        Argument.string("version_current", watcher.getCurrentVersion().getVersionFull()),
                        Argument.string("version_latest", watcher.getLatestVersion().getVersionFull()),
                        Argument.tagResolver(Placeholder.parsed("download_link", watcher.getDownloadURL()))
                    )
                );
            }
        }, plugin);
    }

    /**
     * Parses the plugin's version string, falling back to 0.0.1 when it cannot be read.
     *
     * <p>The fallback matters here: the descriptor version is git derived and looks like
     * {@code 0.6.0-SNAPSHOT+5453A44}, so a strict parse would reject it.
     *
     * @param pluginVersion the raw version string from the plugin descriptor
     * @return the parsed version, or 0.0.1
     */
    @VisibleForTesting
    static @NotNull Version parseVersion(@NotNull String pluginVersion) {
        try {
            return Version.parseLoose(pluginVersion);
        } catch (VersionParseException e) {
            return Version.builder()
                .withMajor(0)
                .withMinor(0)
                .withPatch(1)
                .build();
        }
    }
}
