package me.thegreenman.beastmode.commands;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import me.thegreenman.beastmode.PlayerData;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.bukkit.Bukkit.getOfflinePlayer;

public class StatsMenu implements CommandExecutor, TabCompleter {

    public static void openMenu(Player player, UUID findedPlayerUUID) {
        if (!getOfflinePlayer(findedPlayerUUID).hasPlayedBefore()) {
            player.sendMessage("That player has not played before");
            return;
        }
        /*
          Kills:
    name:
    lore:
  Games:
    name:
    lore:
  Wins:
    name: Totala vinster
    lore: <player> totala vinster <amount>
  playTime:
    name:
    lore:
         */

        Inventory meny = Bukkit.createInventory(player, 9, Component.text(getOfflinePlayer(findedPlayerUUID).getName() + " Stats"));
        PlayerData playerData = PlayerData.getPlayerData(findedPlayerUUID);

        ItemStack diaSword = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta diaSwordMeta = diaSword.getItemMeta();

        diaSwordMeta.itemName(Component.text("Totala dödande"));
        diaSwordMeta.lore(List.of(Component.text("<player> totala dödande <amount>".replace("<player>", getOfflinePlayer(findedPlayerUUID).getName()).replace("<amount>", String.valueOf(playerData.getTotalKills())))));

        diaSword.setItemMeta(diaSwordMeta);
        meny.setItem(2, diaSword);

        ItemStack games = new ItemStack(Material.TRIAL_KEY);
        ItemMeta gamesMeta = games.getItemMeta();

        gamesMeta.itemName(Component.text("Totalt spel"));
        gamesMeta.lore(
                List.of(
                        Component.text("<player> totala spelade <amount> matcher".replace("<player>", getOfflinePlayer(findedPlayerUUID).getName()).replace("<amount>", String.valueOf(playerData.getTotalGames())))
                ));

        games.setItemMeta(gamesMeta);
        meny.setItem(3, games);

//        ItemStack wins = new ItemStack(Material.DIAMOND_BLOCK);
//        ItemMeta winsMeta = wins.getItemMeta();
//
//        winsMeta.setItemName(lang.getString("stats.Wins.name"));
//        winsMeta.setLore(
//                List.of(
//                        ChatColor.GRAY + lang.getString("stats.Wins.lore").replace("<player>", getOfflinePlayer(findedPlayerUUID).getName()).replace("<amount>", String.valueOf(playerData.getTotalWins()))
//                ));
//
//        wins.setItemMeta(winsMeta);
//        meny.setItem(5, wins);

        ItemStack PlayTime = new ItemStack(Material.CLOCK);
        ItemMeta PlayTimeMeta = PlayTime.getItemMeta();

        PlayTimeMeta.itemName(Component.text("Total speltid"));
        PlayTimeMeta.lore(
                List.of(
                        Component.text("<player> totala speltid <amount>"
                                .replace("<player>", getOfflinePlayer(findedPlayerUUID).getName())
                                .replace("<amount>", formatPlayTime(getOfflinePlayer(findedPlayerUUID).getStatistic(Statistic.PLAY_ONE_MINUTE))))
                ));

        PlayTime.setItemMeta(PlayTimeMeta);
        meny.setItem(6, PlayTime);

        player.openInventory(meny);
    }

    public static String formatPlayTime(long ticks) {
        long seconds = ticks / 20;

        long days = seconds / 86400;               // 60 * 60 * 24
        long hours = (seconds % 86400) / 3600;
        long minutes = (seconds % 3600) / 60;
        long secs = seconds % 60;

        return String.format("%d days %02dh %02dm %02ds", days, hours, minutes, secs);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            return false;
        }

        if (args.length == 0) {
            openMenu(player, player.getUniqueId());
        }
        else if (Bukkit.getOfflinePlayer(args[0]).hasPlayedBefore()) {
            openMenu(player, Bukkit.getOfflinePlayer(args[0]).getUniqueId());
        }
        else {
            player.sendMessage(args[0]);
        }



        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        List<String> playername = new ArrayList<>();
        for (OfflinePlayer offlinePlayer : Bukkit.getOfflinePlayers()) {
            playername.add(offlinePlayer.getName());
        }
        return playername;
    }
}