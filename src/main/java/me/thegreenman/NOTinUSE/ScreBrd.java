package me.thegreenman.NOTinUSE;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
//import org.bukkit.event.Listener;
//import org.bukkit.scoreboard.DisplaySlot;
//import org.bukkit.scoreboard.Objective;
//import org.bukkit.scoreboard.Scoreboard;
//import org.bukkit.scoreboard.ScoreboardManager;
import me.thegreenman.Main;
import me.thegreenman.beastmode.commands.MinkenMenu;

public class ScreBrd { // implements Listener 
//	private ScoreboardManager manager;
//	private Scoreboard scoreboard;
//	private Objective objective;
	private int waterY = 2; // Startnivån för water
	private int lavaY = 2; // 10      Startnivån för lava
	private int maxY = 124; // Maxnivå för lavan
	private int waveSize = 1; // Hur många nivåer som höjs per våg
	private int pvpTime = 120; // 5 minuter till PvP
	private long interval = 20L; // 200L = 10 sekunders intervall för lava
	private long waterInterval = 20L;
	final Set<Player> playersWithSpawn = new HashSet<>();

	public ScreBrd() {
//		World world = Bukkit.getWorlds().get(0);
//		world.setPVP(AdminMenu.pvpEnabled);
		// setupScoreboard(); // TODO: Denna bestämmer om Scoreboard ska visas eller förbli osynligt
//		startTimers();
	}
	
	public void startScoreboardUpdater() {
	    Bukkit.getLogger().info("Startar kontinuerlig uppdatering av scoreboard.");
//	    Bukkit.getScheduler().runTaskTimer(Main.plugin, () -> {
//	        updateScoreboard();
//	    }, 0L, 20L); // Uppdatera varje sekund
	}

//	public void updateSpawnAfterTeleport(Player player) {
//	    if (!playersWithSpawn.contains(player)) {
//	        playersWithSpawn.add(player);
//	        player.sendMessage(ChatColor.YELLOW + "Din spawn är nu registrerad!");
//	    }
//	}

//	public boolean haveAllPlayersSetSpawn() {
//	    boolean allSet = true;
//	    for (Player player : Bukkit.getOnlinePlayers()) {
//	        boolean hasSetSpawn = playersWithSpawn.contains(player);
//	        Bukkit.getLogger().info("Spelare: " + player.getName() + ", Satt spawn: " + hasSetSpawn);
//	        if (!hasSetSpawn) {
//	            allSet = false;
//	        }
//	    }
//	    Bukkit.getLogger().info("Alla spelare har satt spawn: " + allSet);
//	    return allSet;
//	}

//	@SuppressWarnings({ "deprecation", "unused" })
//	private void setupScoreboard() {
//		manager = Bukkit.getScoreboardManager();
//		scoreboard = manager.getNewScoreboard();
//
//		objective = scoreboard.registerNewObjective(
//				"beastmode",
//				"dummy",
//				ChatColor.RED + "Beast Mode"
//				);
//		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
//
////		updateScoreboard();
//	}

//	private void startPvpCountdown() {
//	    Bukkit.getLogger().info("PVP-timer startar...");
//	    Bukkit.getScheduler().runTaskTimer(Main.plugin, new Runnable() {
//	        @Override
//	        public void run() {
//	            try {
//	                if (pvpTime > 0) {
//	                    pvpTime--;
//	                    Bukkit.getLogger().info("Tid kvar till PVP: " + pvpTime + " sekunder.");
//	                    updateScoreboard(); // Uppdatera scoreboarden med tiden
//	                } else {
//	                    Bukkit.broadcastMessage(ChatColor.RED + "PVP är nu aktiverat!");
//	                    Bukkit.getLogger().info("PVP aktiverades. Avslutar schemaläggaren.");
//	                    Bukkit.getScheduler().cancelTasks(Main.plugin); // Avsluta denna schemaläggare
//	                }
//	            } catch (Exception e) {
//	                Bukkit.getLogger().severe("Ett fel uppstod i startPvpCountdown: " + e.getMessage());
//	                e.printStackTrace();
//	            }
//	        }
//	    }, 0L, 20L); // Kör varje sekund
//	}

//	public void startCountdown() {
//	    Bukkit.getLogger().info("Startar nedräkning för PVP...");
//	    Bukkit.getScheduler().runTaskTimer(Main.plugin, () -> {
//	        try {
//	            if (!haveAllPlayersSetSpawn()) {
//	                Bukkit.getLogger().info("Alla spelare har inte satt spawn ännu. Väntar...");
//	                return; // Fortsätt schemaläggaren utan avbrott
//	            }
//
//	            Bukkit.getLogger().info("Alla spelare har satt sin spawn. Startar PVP-timern.");
//	            startPvpCountdown();
//	        } catch (Exception e) {
//	            Bukkit.getLogger().severe("Ett fel uppstod i startCountdown: " + e.getMessage());
//	            e.printStackTrace();
//	        }
//	    }, 0L, 100L); // Kontrollera var 5:e sekund (100 ticks)
//	}
	
//	public void ensureSchedulerRunning() {
//	    if (Bukkit.getScheduler().getPendingTasks().stream()
//	            .noneMatch(task -> task.getOwner().equals(Main.plugin))) {
//	        Bukkit.getLogger().warning("Schemaläggaren körs inte! Startar om...");
//	        startScoreboardUpdater();
//	    }
//	}

	public void startLavaRising() {
		World world = Bukkit.getWorlds().get(0); // Ändra om du har flera världar

		Bukkit.getScheduler().runTaskTimer(Main.plugin, new Runnable() {
			@Override
			public void run() {
				if (lavaY < maxY) {
					raiseLava(world, lavaY, (lavaY + waveSize)); // Höjer lavan en våg
					lavaY++;
//					updateScoreboard(); // Uppdatera scoreboarden
				} else {
					Bukkit.broadcastMessage(ChatColor.RED + "Lavan har nått maxhöjd!");
					Bukkit.getScheduler().cancelTasks(Main.plugin); // Stoppa Lava Rising
					MinkenMenu.raising = false;
				}
			}
		}, 0L, interval); // Kör enligt det angivna intervallet
	}
	
	public void startLavaToAirReplace() {
		World world = Bukkit.getWorlds().get(0); // Ändra om du har flera världar
		
		Bukkit.getScheduler().runTaskTimer(Main.plugin, new Runnable() {
			@Override
			public void run() {
				if (waterY < maxY) {
					raiseAir(world, waterY, (waterY + waveSize)); // Höjer air en våg
					waterY++;
//					updateScoreboard(); // Uppdatera scoreboarden
				} else {
					Bukkit.broadcastMessage(ChatColor.RED + "Air har nått maxhöjd!");
					Bukkit.getScheduler().cancelTasks(Main.plugin); // Stoppa water Rising
					MinkenMenu.raising = false;
				}
			}
		}, 0L, 1L); // Kör enligt det angivna intervallet
	}
	
	public void startWaterTsunami() {
		World world = Bukkit.getWorlds().get(0); // Ändra om du har flera världar
		
		Bukkit.getScheduler().runTaskTimer(Main.plugin, new Runnable() {
			@Override
			public void run() {
				if (waterY < maxY) {
					raiseWater(world, waterY, (waterY + waveSize)); // Höjer water en våg
					waterY++;
//					updateScoreboard(); // Uppdatera scoreboarden
				} else {
					Bukkit.broadcastMessage(ChatColor.RED + "Vattnet har nått maxhöjd!");
					Bukkit.getScheduler().cancelTasks(Main.plugin); // Stoppa water Rising
					MinkenMenu.raising = false;
				}
			}
		}, 0L, waterInterval); // Kör enligt det angivna intervallet
	}

// SPARA, NEDRÄKNING i scoreboard-kod.
//	private void updateScoreboard() {
//	    scoreboard.getEntries().forEach(scoreboard::resetScores);
//
////	    if (!haveAllPlayersSetSpawn()) {
////	        objective.getScore(ChatColor.RED + "Sov i en säng!").setScore(6);
////	    } else 
//	    if (pvpTime > 0) {
//	        objective.getScore(ChatColor.GOLD + "PVP om: " + formatTime(pvpTime)).setScore(5);
//	    } else {
//	        objective.getScore(ChatColor.RED + "PVP = " + AdminMenu.pvpEnabled).setScore(5);
//	        World world = Bukkit.getWorlds().get(0);
//			world.setPVP(AdminMenu.pvpEnabled);
//	    }
//
//	    objective.getScore(ChatColor.YELLOW + "Lava på: " + ChatColor.RED + "Y: " + lavaY).setScore(4);
//	    objective.getScore(ChatColor.GREEN + "Jaa vad roligt").setScore(3);
//	    objective.getScore(ChatColor.GREEN + "att du ser detta!").setScore(2);
//	    objective.getScore(ChatColor.RED + "Sömn är viktigt!").setScore(1);
//
//	    for (Player player : Bukkit.getOnlinePlayers()) {
//	        player.setScoreboard(scoreboard);
//	    }
//	}

//	private void updateScoreboard() {
//	    scoreboard.getEntries().forEach(scoreboard::resetScores);
////        objective.getScore(ChatColor.RED + "PVP = " + AdminMenu.pvpEnabled).setScore(5);
//	    objective.getScore(ChatColor.YELLOW + "Lava: " + ChatColor.RED + "Y: " + lavaY).setScore(4);
//	    objective.getScore(ChatColor.GREEN + "Jaa vad roligt").setScore(3);
//	    objective.getScore(ChatColor.GREEN + "att du ser detta!").setScore(2);
//	    objective.getScore(ChatColor.RED + "Sömn är viktigt!").setScore(1);
//
//	    for (Player player : Bukkit.getOnlinePlayers()) {
//	        player.setScoreboard(scoreboard);
//	    }
//	}

//	private void startTimers() {
////		World world = Bukkit.getWorlds().get(0);
//
//		Bukkit.getScheduler().runTaskTimer(Main.plugin, () -> {
//			if (pvpTime > 0) {
//				pvpTime--;
//			
////			} else if (lavaY < maxY) {
////				raiseLava(world, lavaY, lavaY + waveSize);
////				lavaY++;
//			} else {
//				Bukkit.getScheduler().cancelTasks(Main.plugin);
//			}
//			updateScoreboard();
//		}, 0L, 20L);
//	}

	private void raiseAir(World world, int waterY, int endY) {
//		endY = 12;
		int minX = -66;
		int maxX = 62;
		int minZ = -65;
		int maxZ = 63;

		for (int x = minX; x <= maxX; x++) {
			for (int z = minZ; z <= maxZ; z++) {
				for (int y = waterY; y < endY; y++) {
					Location loc = new Location(world, x, y, z);
//					if (world.getBlockAt(loc).getType() == Material.LAVA ||
//							world.getBlockAt(loc).getType() == Material.WATER ||
//							world.getBlockAt(loc).getType() == Material.OAK_WALL_SIGN ||
//							world.getBlockAt(loc).getType() == Material.LADDER ||
//							world.getBlockAt(loc).getType() == Material.COBWEB) {
						world.getBlockAt(loc).setType(Material.AIR);
//					}
				}
			}
		}

//			    Bukkit.getLogger().info("Raising lava from Y: " + waterY + " to Y: " + (endY - 1));
//			    Bukkit.getLogger().info("Processing area: " + minX + " to " + maxX + ", " + minZ + " to " + maxZ);
	}

	private void raiseWater(World world, int waterY, int endY) {
		int minX = -66;
		int maxX = 62;
		int minZ = -65;
		int maxZ = 63;

		for (int x = minX; x <= maxX; x++) {
			for (int z = minZ; z <= maxZ; z++) {
				for (int y = waterY; y < endY; y++) {
					Location loc = new Location(world, x, y, z);
//					if (world.getBlockAt(loc).getType() == Material.AIR || 
//							world.getBlockAt(loc).getType() == Material.LAVA ||
//							world.getBlockAt(loc).getType() == Material.LADDER ||
//							world.getBlockAt(loc).getType() == Material.OBSIDIAN) {
						world.getBlockAt(loc).setType(Material.WATER);
//					}
				}
			}
		}

//			    Bukkit.getLogger().info("Raising lava from Y: " + waterY + " to Y: " + (endY - 1));
//			    Bukkit.getLogger().info("Processing area: " + minX + " to " + maxX + ", " + minZ + " to " + maxZ);
	}

	private void raiseLava(World world, int startY, int endY) {
		int minX = -66;
		int maxX = 62;
		int minZ = -65;
		int maxZ = 63;

		for (int x = minX; x <= maxX; x++) {
			for (int z = minZ; z <= maxZ; z++) {
				for (int y = startY; y < endY; y++) {
					Location loc = new Location(world, x, y, z);
//					if (world.getBlockAt(loc).getType() == Material.AIR || 
//							world.getBlockAt(loc).getType() == Material.WATER ||
//							world.getBlockAt(loc).getType() == Material.OAK_WALL_SIGN ||
//							world.getBlockAt(loc).getType() == Material.LADDER ||
//							world.getBlockAt(loc).getType() == Material.COBWEB) {
						world.getBlockAt(loc).setType(Material.LAVA);
//					}
				}
			}
		}

		//	    Bukkit.getLogger().info("Raising lava from Y: " + startY + " to Y: " + (endY - 1));
		//	    Bukkit.getLogger().info("Processing area: " + minX + " to " + maxX + ", " + minZ + " to " + maxZ);
	}

//	private String formatTime(int seconds) {
//		int minutes = seconds / 60;
//		int secs = seconds % 60;
//		return minutes + "m " + secs + "s";
//	}

	@SuppressWarnings("unused")
	private void raiseLavaAsync(World world, int startY, int endY) { // TODO: Va fan är detta?
		Bukkit.getScheduler().runTaskAsynchronously(Main.plugin, () -> {
			int minX = world.getWorldBorder().getCenter().getBlockX() - 100;
			int maxX = world.getWorldBorder().getCenter().getBlockX() + 100;
			int minZ = world.getWorldBorder().getCenter().getBlockZ() - 100;
			int maxZ = world.getWorldBorder().getCenter().getBlockZ() + 100;

			for (int x = minX; x <= maxX; x++) {
				for (int z = minZ; z <= maxZ; z++) {
					for (int y = startY; y < endY; y++) {
						Location loc = new Location(world, x, y, z);
						Bukkit.getScheduler().runTask(Main.plugin, () -> {
							if (world.getBlockAt(loc).getType() == Material.AIR || 
									world.getBlockAt(loc).getType() == Material.WATER) {
								world.getBlockAt(loc).setType(Material.LAVA);
							}
						});
					}
				}
			}
		});
	}

	public void startCountdown() {
	    Bukkit.getScheduler().runTaskTimer(Main.plugin, new Runnable() {
	        @Override
	        public void run() {
	            if (pvpTime > 0) {
	                pvpTime--;
//	                updateScoreboard(); // Uppdatera scoreboarden med ny tid
	            } else {
	                Bukkit.broadcastMessage(ChatColor.RED + "PVP är nu aktiverat!");
	                Bukkit.getScheduler().cancelTasks(Main.plugin); // Stoppa nedräkningen
	            }
	        }
	    }, 0L, 20L); // Kör varje sekund
	}
}