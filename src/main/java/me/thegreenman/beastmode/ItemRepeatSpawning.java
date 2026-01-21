package me.thegreenman.beastmode;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import me.thegreenman.Main;

public class ItemRepeatSpawning implements Listener {
	public ItemStack brickBronze = new ItemStack(Material.BRICK, 1);
	public ItemStack ironSilver = new ItemStack(Material.IRON_INGOT, 1);
	public ItemStack goldGold = new ItemStack(Material.GOLD_INGOT, 1);
    public ItemStack diamondDiamond = new ItemStack(Material.DIAMOND, 1);

	public Location blueItemSpawnLoc = null;
	public Location redItemSpawnLoc = null;
	public Location greenItemSpawnLoc = null;
	public Location pinkItemSpawnLoc = null;
	public Location middleItemSpawnLoc = null;

	public ItemRepeatSpawning() {

        // FÃ¶rdrÃ¶jning fÃ¶r att sÃ¤kerstÃ¤lla att vÃ¤rlden Ã¤r laddad
        if (BeastModeData.selectedMapindex == 0) {
            Bukkit.getScheduler().runTaskLater(Main.plugin, this::startSpawnBricks, 20L);
            Bukkit.getScheduler().runTaskLater(Main.plugin, this::startSpawnIron, 20L);
            Bukkit.getScheduler().runTaskLater(Main.plugin, this::startSpawnGold, 20L);
            Bukkit.getScheduler().runTaskLater(Main.plugin, this::startSpawnGoldInTheMiddle, 20L);
            Bukkit.getScheduler().runTaskLater(Main.plugin, this::startSpawnDiamondInTheMiddle, 20L);

            blueItemSpawnLoc = new Location(Main.WORLD, 74.5, 75.5, 225.5); // BLUE?
            redItemSpawnLoc = new Location(Main.WORLD, 7.5, 80.5, 227.5); // RÃ–D?
            greenItemSpawnLoc = new Location(Main.WORLD, 53.5, 92.5, 289.5); // GRÃ–N?
            pinkItemSpawnLoc = new Location(Main.WORLD, 46.5, 75.5, 180.5); // PINK?
            middleItemSpawnLoc = new Location(Main.WORLD, 49.5, 122, 229.5); // mitten
        }
        else if (BeastModeData.selectedMapindex == 1) {
            Bukkit.getScheduler().runTaskLater(Main.plugin, this::startSpawnDiamondInTheMiddle, 20L);
            middleItemSpawnLoc = new Location(Main.WORLD, -2.5, 45, -2.5); // mitten
        }
        else if (BeastModeData.selectedMapindex == 2) {
            Bukkit.getScheduler().runTaskLater(Main.plugin, this::startSpawnBricks, 20L);
            Bukkit.getScheduler().runTaskLater(Main.plugin, this::startSpawnIron, 20L);
            Bukkit.getScheduler().runTaskLater(Main.plugin, this::startSpawnGold, 20L);
            Bukkit.getScheduler().runTaskLater(Main.plugin, this::startSpawnGoldInTheMiddle, 20L);
            Bukkit.getScheduler().runTaskLater(Main.plugin, this::startSpawnDiamondInTheMiddle, 20L);

            blueItemSpawnLoc = new Location(Main.WORLD, -1077.5, -56, -111.5); // BLUE?
            redItemSpawnLoc = new Location(Main.WORLD, -1077.5, -56, -25.5); // RÃ–D?
            greenItemSpawnLoc = new Location(Main.WORLD, -1034.5, -56, -68.5); // GRÃ–N?
            pinkItemSpawnLoc = new Location(Main.WORLD, -1120.5, -56, -68.5); // PINK?
            middleItemSpawnLoc = new Location(Main.WORLD, -1077.5, -56, -68.5); // mitten
        }

	}

	public void startSpawnBricks () {
		new BukkitRunnable() {
			@Override
			public void run() {
//				Main.log("BM Bricks: checkSurvivalPlayers=" + checkSurvivalPlayers());
				if (!checkSurvivalPlayers()) {
                    return;
                }
				spawnItemAtLocation(blueItemSpawnLoc, brickBronze);
				spawnItemAtLocation(redItemSpawnLoc, brickBronze);
				spawnItemAtLocation(greenItemSpawnLoc, brickBronze);
				spawnItemAtLocation(pinkItemSpawnLoc, brickBronze);
			}
		}.runTaskTimer(Main.plugin, 100L, 20L); // 20 L = 1s
	}

	public void startSpawnIron () {
		new BukkitRunnable() {
			@Override
			public void run() {
				if (!checkSurvivalPlayers()) { return; }
				spawnItemAtLocation(blueItemSpawnLoc, ironSilver);
				spawnItemAtLocation(redItemSpawnLoc, ironSilver);
				spawnItemAtLocation(greenItemSpawnLoc, ironSilver);
				spawnItemAtLocation(pinkItemSpawnLoc, ironSilver);
			}
		}.runTaskTimer(Main.plugin, 100L, 300L); // 300 L = 15s
	}

	public void startSpawnGold () {
		new BukkitRunnable() {
			@Override
			public void run() {
				if (!checkSurvivalPlayers()) { return; }
				spawnItemAtLocation(blueItemSpawnLoc, goldGold);
				spawnItemAtLocation(redItemSpawnLoc, goldGold);
				spawnItemAtLocation(greenItemSpawnLoc, goldGold);
				spawnItemAtLocation(pinkItemSpawnLoc, goldGold);
			}
		}.runTaskTimer(Main.plugin, 100L, 1200L); // 1200 L = 60s
	}

	public void startSpawnGoldInTheMiddle () {
		new BukkitRunnable() {
			@Override
			public void run() {
				if (!checkSurvivalPlayers()) { return; }
				spawnItemAtLocation(middleItemSpawnLoc, goldGold);
			}
		}.runTaskTimer(Main.plugin, 100L, 400L); // 400 L = 20s
	}

    public void startSpawnDiamondInTheMiddle () {
        if (!BeastModeData.minkenEditionThusFalseIsOriginalRules) {
            return;
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                if (checkSurvivalPlayers() == false) { return; }
                spawnItemAtLocation(middleItemSpawnLoc, diamondDiamond);
            }
        }.runTaskTimer(Main.plugin, 20 * 60, 20L * BeastModeData.middleDiamondDropDelayInSeconds); // 400 L = 20s
    }

	public void spawnItemAtLocation(Location location, ItemStack itemStack) {
		World world = location.getWorld();
		if (world != null) {
			world.dropItem(location, itemStack);
		} else {
			Main.log("Invalid world for the specified location.");
		}
	}

	public boolean checkSurvivalPlayers() {
//		Main.log("[PVPBM] Bukkit.getOnlinePlayers().size() = " + Bukkit.getOnlinePlayers().size());
		if (Bukkit.getOnlinePlayers().isEmpty()) {
            return false;
        }
		for (Player pl : Bukkit.getOnlinePlayers())	{
			if (pl.getGameMode().equals(GameMode.SURVIVAL)) {
//				Main.log("[PVPBM] Player=" + pl.getName() + " has GM=" + pl.getGameMode());
				return true;
			}
			else continue;
		}
		return false;
	}
}
