package me.thegreenman.beastmode;

import com.destroystokyo.paper.event.player.PlayerLaunchProjectileEvent;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.eclipse.sisu.Priority;

public class PlayerDataHandler implements Listener {

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        new PlayerData(event.getPlayer());
    }

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent event) {
        PlayerData playerData = PlayerData.getPlayerData(event.getPlayer().getUniqueId());
        playerData.saveData();
    }

    @EventHandler
    public void onPlayerDeathEvent(PlayerDeathEvent event) {
        if (event.getPlayer().getKiller() == null) {
            return;
        }
        PlayerData.getPlayerData(event.getPlayer().getKiller().getUniqueId()).addTotalKills();
    }

    @EventHandler
    public void onLaunchProjectileEvent(ProjectileLaunchEvent event) {
        if (!(event.getEntity() instanceof Arrow arrow)) {
            return;
        }
        if (!(event.getEntity().getShooter() instanceof Player player)) {
            return;
        }

        PlayerData.getPlayerData(player.getUniqueId()).addTotalShotArrows();

    }

    @EventHandler
    public void onProjectileHitEvent(ProjectileHitEvent event) {
        if (!(event.getEntity().getShooter() instanceof Player player)) {
            return;
        }
        if (!(event.getHitEntity() instanceof Player)) {
            return;
        }

        PlayerData.getPlayerData(player.getUniqueId()).addArrowsHit();

    }


}
