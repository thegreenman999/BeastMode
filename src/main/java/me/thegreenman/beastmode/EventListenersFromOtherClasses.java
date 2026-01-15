package me.thegreenman.beastmode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import me.thegreenman.Main;

public class EventListenersFromOtherClasses implements Listener {

	// SlashDoeSuicideBombEgg
	public List<Player> hasTakenSuicideEgg = new ArrayList<Player>();

	public static ItemStack suicideEgg = new ItemStack(Material.EGG, 1) {{
		ItemMeta meta = this.getItemMeta();
		if (meta != null) {
			meta.setDisplayName("Suicide Egg");
			meta.setLore((Arrays.asList("BLOW", "YOURSELF", "UP! :D")));
			this.setItemMeta(meta);
		}
	}};

	// FROM DEFAULT/CUSTOM VILLAGERTRADES

	@EventHandler
	public void onPlayerJoinEvent (PlayerJoinEvent event) {
		Player player = event.getPlayer();

		new BukkitRunnable() {
			@Override
			public void run() {
				// player.getServer().dispatchCommand(player.getServer().getConsoleSender(), "effect give @p minecraft:night_vision infinite 0 true");
				player.sendMessage(Main.pvpbm + "Switch to/from Night Vision with " + ChatColor.GREEN + "/fb");
			}
		}.runTaskLater(Main.plugin, 20);
	}


	@EventHandler
	public void preventRestartCommand (PlayerCommandPreprocessEvent event) {
		if (!event.getPlayer().isOp()) { return; }

		if (event.getMessage().equalsIgnoreCase("/restart") || event.getMessage().equalsIgnoreCase("/spigot:restart") || event.getMessage().equalsIgnoreCase("/bukkit:restart")) {
			Bukkit.broadcastMessage("[Server] Tell " + event.getPlayer().getName() + " to NOT run /restart, please! Use /stop");
            event.setCancelled(true);
        }
		else if (event.getMessage().equalsIgnoreCase("/minecraft:reload")) {
			Bukkit.broadcastMessage("[Server] Tell " + event.getPlayer().getName() + " to NOT run /reload, please! Use /stop");
            event.setCancelled(true);
        }
	}

	// PrositIForgotToSetSpawnColorsForTP
	@EventHandler
	public void iForgotToSetSpawn (PlayerCommandPreprocessEvent event) {
		if (!BeastModeData.gameIsRunning) {
            return;
        } // I debugsyfte (typ med modifiering av Villagers) kan jag tycka att det är OK att köra /<color>.

		String msg = event.getMessage().toLowerCase();
		if (msg.equals("/red") || msg.equals("/blue") || msg.equals("/green") || msg.equals("/pink")) {

			event.setCancelled(true);

			Player player = event.getPlayer();

			// RED
			// /tp @a[distance=..7] -1.5 52 -62.5 0 14

			// BLUE
			// /tp @a[distance=..7] -62.5 52 -1.5 -90 14

			// GREEN
			// /tp @a[distance=..7] 59.5 52 -1.5 90 14

			// PINK
			// /tp @a[distance=..7] -1.5 52 59.5 180 14

			new BukkitRunnable() {
				@Override
				public void run() {
					if (event.getMessage().equalsIgnoreCase("/red")) {
						player.teleport(BeastModeData.redTeamSpawn[BeastModeData.selectedMapindex]);
					}

					if (event.getMessage().equalsIgnoreCase("/blue")) {
						player.teleport(BeastModeData.blueTeamSpawn[BeastModeData.selectedMapindex]);
					}

					if (event.getMessage().equalsIgnoreCase("/green")) {
						player.teleport(BeastModeData.greenTeamSpawn[BeastModeData.selectedMapindex]);
					}

					if (event.getMessage().equalsIgnoreCase("/pink")) {
						player.teleport(BeastModeData.pinkTeamSpawn[BeastModeData.selectedMapindex]);
					}

//					Bukkit. event.getMessage(). //
					
					player.setGameMode(GameMode.SURVIVAL);
				}
			}.runTaskLater(Main.plugin, 10);
		};
	};


	// PLBlocker
	/////////////////////////// PREVENT /PL IF NOT OP ////////////////////////

	@EventHandler
	public void plBlocker(PlayerCommandPreprocessEvent event) {
		String message = event.getMessage().toLowerCase();

		if (
				message.toLowerCase().contentEquals("/pl") ||
				message.toLowerCase().startsWith("/plugin") ||
				message.toLowerCase().startsWith("/about") ||
				message.toLowerCase().startsWith("/?") ||
				message.toLowerCase().startsWith("/ver") ||
				message.toLowerCase().startsWith("/bukkit") ||
				message.toLowerCase().startsWith("/icanhasbukkit") ||
				message.toLowerCase().startsWith("/paper") ||
				message.toLowerCase().startsWith("/about")) {
			if (!event.getPlayer().isOp()) {
				event.setCancelled(true);
				event.getPlayer().sendMessage(ChatColor.GREEN + "Plugins: (1)\n" + ChatColor.YELLOW + "MinkensPVPBeastMode");
			}
		}
	}


	// Minkens replacement for EssentialsX /fly :)
	@EventHandler
	public void slashFly (PlayerCommandPreprocessEvent event) {
		Player player = event.getPlayer();

		if (!event.getMessage().equalsIgnoreCase("/fly")) { return; }
		event.setCancelled(true);

		if (!player.getAllowFlight()) {
			player.setAllowFlight(true);
			player.setFlying(true);
		}
        else {
			player.setAllowFlight(false);
			player.setFlying(false);
		}

//		player.setFlying(!player.isFlying());
		if (player.getName().startsWith("Minken")) { return; }
		event.getPlayer().getServer().broadcastMessage(player.getName() + " ran " + ChatColor.GREEN + "/fly" + ChatColor.RESET);
	}
}
