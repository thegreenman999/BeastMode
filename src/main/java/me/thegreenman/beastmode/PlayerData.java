package me.thegreenman.beastmode;


import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import me.thegreenman.Main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayerData {
    private static List<PlayerData> playerDataList = new ArrayList<>();

    public static File playerDataFile;
    public static YamlConfiguration playerDataConfig;

    private int totalKills;
    private int totalWins;
    private int totalGames;

    private final UUID uuid;

    public PlayerData(Player player) {
        uuid = player.getUniqueId();
        loadData();

        playerDataList.add(this);

    }

    private PlayerData(UUID uuid) {
        this.uuid = uuid;
        loadData();
    }

    public void saveData() {
        try {
            playerDataConfig.set(uuid.toString() + ".totalKills", totalKills);
            playerDataConfig.set(uuid.toString() + ".totalGames", totalGames);
            playerDataConfig.set(uuid.toString() + ".latestName", Bukkit.getOfflinePlayer(uuid).getName());
        }
        catch (Exception e) {
            Main.severe("Error saving data for " + uuid.toString());
        }

        playerDataList.remove(this);
    }

    public void loadData() {
        if (playerDataConfig.getConfigurationSection(uuid.toString()) == null) {
            totalKills = 0;
            totalWins = 0;
            totalGames = 0;

            saveData();
            playerDataList.add(this);
        }
        else {
            try {
                totalKills = playerDataConfig.getInt(uuid.toString() + ".totalKills");
                totalGames = playerDataConfig.getInt(uuid.toString() + ".totalGames");
            }
            catch (Exception e) {
                Main.severe("Error Loading data for " + uuid.toString());
            }
        }
    }

    public int getTotalKills() {
        return totalKills;
    }

    public void addTotalKills() {
        this.totalKills++;
    }

    public int getTotalGames() {
        return totalGames;
    }

    public void addTotalGames() {
        this.totalGames++;
    }

    public static PlayerData getPlayerData(UUID playerUUID) {
        for (PlayerData playerData : playerDataList) {
            if (playerData.uuid.equals(playerUUID)) {
                return playerData;
            }
        }
        return new PlayerData(playerUUID);
    }

    public static void saveAll() {
        Main.log("Saving PlayerData");

        List<PlayerData> DataList = new ArrayList<>(playerDataList);
        for (PlayerData playerData : DataList) {
            playerData.saveData();
        }

        try {
            playerDataConfig.save(playerDataFile);
        } catch (IOException e) {
            Main.severe("Error player data" + new RuntimeException(e));
        }

    }


    public static void loadConfig() {
        // load game config
        playerDataFile = new File(Main.plugin.getDataFolder(), "PlayerData.yml");
        if (!playerDataFile.exists()) {
            Main.plugin.saveResource("PlayerData.yml", true);
            loadConfig();
            return;
        }
        playerDataConfig = YamlConfiguration.loadConfiguration(playerDataFile);
    }



}
