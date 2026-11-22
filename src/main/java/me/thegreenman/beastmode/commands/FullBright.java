package me.thegreenman.beastmode.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;
import me.thegreenman.Main;

import java.util.ArrayList;
import java.util.List;

public class FullBright implements CommandExecutor, Listener {
    public List<Player> fullBrightPlayers = new ArrayList<>();

    public PotionEffectType nightvision = PotionEffectType.NIGHT_VISION;
    public int infinite = PotionEffect.INFINITE_DURATION;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!(sender instanceof Player player)) {
            return false;
        }


        if (player.hasPotionEffect(nightvision)) {
            player.removePotionEffect(nightvision);
            if (fullBrightPlayers.contains(player)) {
                fullBrightPlayers.remove(player);
            }
            player.sendMessage(Main.pvpbm + "Night Vision removed.");
        }
        else {
            player.addPotionEffect(new PotionEffect(nightvision, infinite, 0, true));
            if (!fullBrightPlayers.contains(player)) {
                fullBrightPlayers.add(player);
            }
            player.sendMessage(Main.pvpbm + "Night Vision activated.");
        }
        return true;
    }

    @EventHandler
    public void ReAdd(PlayerDeathEvent event) {
        if (fullBrightPlayers.contains(event.getPlayer())) {
            event.getPlayer().addPotionEffect(new PotionEffect(nightvision, infinite, 0, true));
        }
    }
}
