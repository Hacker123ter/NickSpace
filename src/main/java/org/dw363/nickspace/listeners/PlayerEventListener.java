package org.dw363.nickspace.listeners;

import org.dw363.nickspace.utils.DisplayUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import org.dw363.nickspace.utils.TeamManager;

public class PlayerEventListener implements Listener {

    private final Plugin plugin;

    public PlayerEventListener(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        TeamManager.addPlayerToTeam(player);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () -> {
            Player target = DisplayUtils.getPlayerLookingAt(player);
            plugin.getServer().getScheduler().runTask(plugin, () -> {
                if (target != null) {
                    if (!DisplayUtils.hasActiveTextDisplay(player, target)) {
                        DisplayUtils.createTextDisplay(player, target);
                    }
                } else {
                    DisplayUtils.removeTextDisplay(player);
                }
            });
        });
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        DisplayUtils.removeAllTextDisplaysForPlayer(player);
    }
}