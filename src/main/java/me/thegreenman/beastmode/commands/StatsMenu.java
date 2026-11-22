package me.thegreenman.beastmode.commands;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.checkerframework.checker.units.qual.C;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import me.thegreenman.beastmode.PlayerData;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.bukkit.Bukkit.getOfflinePlayer;

public class StatsMenu implements CommandExecutor, TabCompleter, Listener {

    public static void openMenu(Player player, UUID findedPlayerUUID) {
//        if (PlayerData.playerDataConfig.getKeys(false).contains(findedPlayerUUID.toString())) {
//            player.sendMessage("That player has not played before");
//            return;
//        }
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

        Inventory meny = Bukkit.createInventory(player, 9, Component.text(PlayerData.playerDataConfig.getString(findedPlayerUUID + ".latestName") + " Stats"));
        PlayerData playerData = PlayerData.getPlayerData(findedPlayerUUID);


        ItemStack diaSword = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta diaSwordMeta = diaSword.getItemMeta();

        diaSwordMeta.itemName(Component.text("Totala dödande"));
        diaSwordMeta.lore(List.of(Component.text("§7<player> totala dödande <amount>".replace("<player>", getOfflinePlayer(findedPlayerUUID).getName()).replace("<amount>", String.valueOf(playerData.getTotalKills())))));

        diaSword.setItemMeta(diaSwordMeta);
        meny.setItem(2, diaSword);


        ItemStack games = new ItemStack(Material.TRIAL_KEY);
        ItemMeta gamesMeta = games.getItemMeta();

        gamesMeta.itemName(Component.text("Totalt spel"));
        gamesMeta.lore(
                List.of(
                        Component.text("§7<player> totala spelade <amount> matcher".replace("<player>", getOfflinePlayer(findedPlayerUUID).getName()).replace("<amount>", String.valueOf(playerData.getTotalGames())))
                ));

        games.setItemMeta(gamesMeta);
        meny.setItem(3, games);


        ItemStack HitRate = new ItemStack(Material.BOW);
        ItemMeta HitRateMeta = HitRate.getItemMeta();

        HitRateMeta.itemName(Component.text("Hit rate"));
        List<Component> HitRateLore = new ArrayList<>();

        if (playerData.getTotalShotArrows() != 0.0) {
            double rate = (double) playerData.getArrowsHit() / playerData.getTotalShotArrows() * 100.0;
            rate = Math.round(rate);
            HitRateLore.add(Component.text("§r§7Bow Hit rate: " + rate + "%"));
        }
        else {
            HitRateLore.add(Component.text("§r§7Bow Hit rate: 0%"));
        }
        HitRateLore.add(Component.text("§r§7Total arrows shot: " + playerData.getTotalShotArrows()));
        HitRateLore.add(Component.text("§r§7Total arrows hit: " + playerData.getArrowsHit()));

        HitRateMeta.lore(HitRateLore);
        HitRate.setItemMeta(HitRateMeta);
        meny.setItem(5, HitRate);


        ItemStack PlayTime = new ItemStack(Material.CLOCK);
        ItemMeta PlayTimeMeta = PlayTime.getItemMeta();

        PlayTimeMeta.itemName(Component.text("Total speltid"));
        PlayTimeMeta.lore(
                List.of(
                        Component.text("§7<player> totala speltid <amount>"
                                .replace("<player>", getOfflinePlayer(findedPlayerUUID).getName())
                                .replace("<amount>", formatPlayTime(playerData.getPlaytime()))
                )));

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
        else {
            for (String uuid : PlayerData.playerDataConfig.getKeys(false)) {
                String name = PlayerData.playerDataConfig.getString(uuid + ".latestName");

                if (name != null && !name.isEmpty() && name.equals(args[0])) {
                    openMenu(player, UUID.fromString(uuid));
                    break;
                }
            }
        }



        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return getAllPlayers();
    }

    private List<String> getAllPlayers() {
        List<String> playernames = new ArrayList<>();

        for (String uuid : PlayerData.playerDataConfig.getKeys(false)) {
            String name = PlayerData.playerDataConfig.getString(uuid + ".latestName");

            if (name != null && !name.isEmpty()) {
                playernames.add(name);
            }
        }

        return playernames;
    }

    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent event) {
        if (event.getView().getTitle().endsWith(" Stats")) {
            event.setCancelled(true);
            return;
        }
    }
}