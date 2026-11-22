package me.thegreenman.beastmode.minkenedition;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import me.thegreenman.Main;

public class VoidFix implements Listener {
//	-231.5 52 147.5

    private Location antiVoid = new Location(Main.WORLD, -231.5, 52, 147.5, -180, 14);

    @EventHandler
    public void onAntiVoidPlayerLogin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String spelarnamn = player.getName();

        if (!player.hasPlayedBefore()) {
            // Spelaren har INTE spelat tidigare
            Bukkit.getScheduler().runTaskLater(Main.plugin, () -> {
                player.teleport(antiVoid);
                player.sendMessage("[AntiVoid] Hej " + spelarnamn + "! Du blev just TP:ad. AntiVoid!");
            }, 5L);
        }
    }
}