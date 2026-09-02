package io.github.milkdrinkers.boltux.listener;

import io.github.milkdrinkers.boltux.packets.GlowingEntityTracker;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public final class PacketEventsListeners implements Listener {
    @SuppressWarnings("unused")
    public void onPlayerQuit(PlayerQuitEvent e) {
        GlowingEntityTracker.getInstance().untrack(e.getPlayer());
    }
}
