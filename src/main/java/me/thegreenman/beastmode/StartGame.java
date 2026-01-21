package me.thegreenman.beastmode;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import me.thegreenman.beastmode.commands.StartMenu;
import me.thegreenman.beastmode.minkenedition.CustomVillagerTrades;
import me.thegreenman.beastmode.minkenedition.TntBow;

import java.util.ArrayList;
import java.util.List;

import static me.thegreenman.Main.*;

public class StartGame {

    public static int countdown;

    public static void startaSpelet() {
        if (!BeastModeData.gameIsRunning) { BeastModeData.gameIsRunning = true; }

        countdown = BeastModeData.countdown;

        if (BeastModeData.TNTBowCoolDown != 0) {
            TntBow.setCoolDowntime(BeastModeData.TNTBowCoolDown);
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                if (countdown > 0) {
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        player.sendTitle(ChatColor.GREEN + BeastModeData.StylizedTime[countdown - 1], ChatColor.YELLOW + "Game starts in", 5, 10, 5);
                    }
                    countdown--;
                } else {
                    Bukkit.broadcastMessage(ChatColor.GREEN + "BEASTMODE har startats! Lycka till!");
                    this.cancel();
                }
            }
        }.runTaskTimer(plugin, 0L, 20L);

        for (Player spelareonline : Bukkit.getOnlinePlayers()) {
            spelareonline.setHealth(BeastModeData.startHP);
            spelareonline.setFoodLevel(BeastModeData.startFoodlevel);

            spelareonline.setExhaustion(0); // From 0 to 4. If 4, decreses one food level and the resets to 0 again.
            spelareonline.setSaturation(0); // Mättnad, så det plockas av denna i stället för av FoodLevel

            spelareonline.setExp(0);
            spelareonline.setLevel(0);

            spelareonline.setArrowCooldown(0);
            spelareonline.setFireTicks(0);
            spelareonline.setFreezeTicks(0);
            spelareonline.setGlowing(false);

            spelareonline.getInventory().setArmorContents(null);
            spelareonline.getInventory().setItemInOffHand(null);
            spelareonline.getInventory().clear();

        }


        new BukkitRunnable() {
            @Override
            public void run() {
                tpPlayers();
            }
        }.runTaskLater(plugin, 20); // LEAVE THIS AS IT IS! IF LOWER, PLAYERS WILL NOT GET TELEPORTED!


        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (BeastModeData.freeStartBow) { player.getInventory().addItem(StartMenu.startBow); player.getInventory().addItem(new ItemStack(Material.ARROW, 1)); }
                    if (BeastModeData.minkenEditionThusFalseIsOriginalRules) { player.getInventory().addItem(new ItemStack(Material.HOPPER, 1)); player.getInventory().addItem(new ItemStack(Material.CHEST, 1)); }
                    if (BeastModeData.freeStartBlocks) { player.getInventory().addItem(new ItemStack(Material.OAK_LEAVES, 64)); } // 48 räcker // NETHERRACK / PINK_WOOL
                    //
                }
            }
        }.runTaskLater(plugin, 10); // 60


        // Load Map
        if (BeastModeData.selectedMapindex == 0) {
            BeastModeData.loadVanillaMap();
        }
        else if (BeastModeData.selectedMapindex == 2) {
            BeastModeData.loadSillenMap();
        }
        new ItemRepeatSpawning();

        // BeastModeData.minkenEditionThusFalseIsOriginalRules TRUE or FALSE
        if (BeastModeData.minkenEditionThusFalseIsOriginalRules) {
            CustomVillagerTrades.spawnTheVillagers();

        }
        else {
            DefaultVillagerTrades.spawnTheVillagers();
        }

        if (BeastModeData.selectedGlowtime != 5) {
            new BukkitRunnable() {

                @Override
                public void run() {
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        BeastModeData.glowEnabled = true;
                        player.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, Integer.MAX_VALUE, 1));
                    }
                }
            }.runTaskLater(plugin, switch (BeastModeData.selectedGlowtime) {
                case 0 -> 20L;
                case 1 -> 18000L;
                case 2 -> 12000L;
                case 3 -> 6000L;
                case 4 -> 2400L;
                default -> throw new IllegalStateException("Unexpected value: " + BeastModeData.selectedGlowtime);
            });
        }


    }

    private static void tpPlayers() {
        List<Player> greenTeam = getPlayersInBox(
                new Location(WORLD, -234, 49, 137),
                new Location(WORLD, -228, 51, 140));
        for (Player player : greenTeam) {
            player.teleport(BeastModeData.greenTeamSpawn[BeastModeData.selectedMapindex]);
            player.setGameMode(GameMode.SURVIVAL);
        }

        List<Player> pinkTeam = getPlayersInBox(
                new Location(WORLD, -225, 49, 143),
                new Location(WORLD, -222, 51, 151));
        for (Player player : pinkTeam) {
            player.teleport(BeastModeData.pinkTeamSpawn[BeastModeData.selectedMapindex]);
            player.setGameMode(GameMode.SURVIVAL);
        }

        List<Player> blueTeam = getPlayersInBox(
                new Location(WORLD, -228, 51, 154),
                new Location(WORLD, -236, 49, 157));
        for (Player player : blueTeam) {
            player.teleport(BeastModeData.blueTeamSpawn[BeastModeData.selectedMapindex]);
            player.setGameMode(GameMode.SURVIVAL);
        }
        List<Player> redTeam = getPlayersInBox(
                new Location(WORLD, -242, 49, 143),
                new Location(WORLD, -239, 51, 151));
        for (Player player : redTeam) {
            player.teleport(BeastModeData.redTeamSpawn[BeastModeData.selectedMapindex]);
            player.setGameMode(GameMode.SURVIVAL);
        }

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (blueTeam.contains(player) || redTeam.contains(player) || greenTeam.contains(player) || pinkTeam.contains(player)) {
                player.setGameMode(GameMode.SURVIVAL);
                if (TntBow.autoGiveBow.contains(player)) {
                    player.getInventory().addItem(TntBow.TNTBOW);
                }
                PlayerData.getPlayerData(player.getUniqueId()).addTotalGames();
            }
            else {
                player.setGameMode(GameMode.SPECTATOR);
                player.teleport(BeastModeData.villagerMiddleLocation);
            }
        }
    }



    public static List<Player> getPlayersInBox(Location pos1 , Location pos2) {


        int minX = Math.min(pos1.getBlockX(), pos2.getBlockX());
        int minY = Math.min(pos1.getBlockY(), pos2.getBlockY());
        int minZ = Math.min(pos1.getBlockZ(), pos2.getBlockZ());
        int maxX = Math.max(pos1.getBlockX(), pos2.getBlockX());
        int maxY = Math.max(pos1.getBlockY(), pos2.getBlockY());
        int maxZ = Math.max(pos1.getBlockZ(), pos2.getBlockZ());

        Location location = new Location(WORLD, 0,0,0);

        List<Player> players = new ArrayList<>();

        for (Player player : Bukkit.getOnlinePlayers()) {

            for (int X = minX; X <= maxX; X++) {

                if (player.getLocation().getBlockX() != X) {
                    continue;
                }
                for (int Y = minY; Y <= maxY; Y++) {

                    if (player.getLocation().getBlockY() != Y) {
                        continue;
                    }
                    for (int Z = minZ; Z <= maxZ; Z++) {

                        if (player.getLocation().getBlockZ() == Z) {

                            players.add(player);
                        }
                    }
                }
            }
        }


        return players;
    }

}
