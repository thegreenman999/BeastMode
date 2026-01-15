package me.thegreenman.NOTinUSE;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import me.thegreenman.Main;

public class BeastModeArchery implements Listener {
	/*
	Minkens notes: 2DO

	COPIED FROM ARCHERYMINIGAME @ 2024-12-27 @ 22:22.

	 */

	public String namn = "[BMA] ";
	private final Map<Location, Integer> targets = new HashMap<>();
	private final Map<Player, Integer> scores = new HashMap<>();
	private final Map<Player, Integer> shotsFired = new HashMap<>(); // Spåra antal skjutna pilar
	private final Map<Player, Integer> hits = new HashMap<>(); // Spåra antal träffar
	private final Map<Player, Integer> misses = new HashMap<>(); // Spåra antal missar
	private boolean gameRunning = false;

	public Random random = new Random();
	public int x = 0; // At red base, near exit towards the middle gold spawner
	public int y = 0;
	public int z = 0;
	public ItemStack bow = new ItemStack(Material.BOW);
	public ItemStack arrow = new ItemStack(Material.ARROW);
	public boolean shuffleMode = false;

	@EventHandler
	public void onPlayerCommandPreprocessEvent (PlayerCommandPreprocessEvent event) {
		Player player = event.getPlayer();

		if (event.getMessage().startsWith("/bma")) {
			if (!player.getName().contentEquals("Minken")) return;
			startGame();
			event.setCancelled(true);
		}
	}

	@SuppressWarnings("deprecation")
	public BeastModeArchery() {
		bow.addEnchantment(Enchantment.INFINITY, 1);
		bow.setDurability((short) 3);
		ItemMeta bowMeta = bow.getItemMeta();
		bowMeta.setDisplayName("Båge");
		bow.setItemMeta(bowMeta);

		ItemMeta arrowMeta = arrow.getItemMeta();
		arrowMeta.setDisplayName("Silverpilen");
		arrow.setItemMeta(arrowMeta);
	}

	private void startGame() {
		if (gameRunning) {
			Bukkit.broadcastMessage(namn + "Spelet är redan igång!");
			return;
		}

		gameRunning = true;
		scores.clear();
		shotsFired.clear(); // Återställ antal skjutna pilar
		hits.clear(); // Återställ träffar
		misses.clear(); // Återställ missar
		targets.clear();
		setupTargets();

		Bukkit.broadcastMessage(namn + "Pilbågsspelet startar! Träffa blocken för att samla poäng.");
		new BukkitRunnable() {
			@Override
			public void run() {
				endGame();
			}
		}.runTaskLater(Main.plugin, 20 * 60);
	}

	private void endGame() {
		gameRunning = false;
		Bukkit.broadcastMessage(namn + "Spelet är slut! Här är resultaten:");

		scores.forEach((player, score) -> {
			int shots = shotsFired.getOrDefault(player, 0);
			int successfulHits = hits.getOrDefault(player, 0);
			// int failedHits = misses.getOrDefault(player, 0);
			int failedHits = shots - successfulHits;

			Bukkit.broadcastMessage(player.getName() + ": " + score + " poäng, " + shots + " skjutna pilar, " +
					successfulHits + " träffar, " + failedHits + " missar");
		});

		targets.clear();
		x = -2; // At red base, near exit towards the middle gold spawner
		y = 53;
		z = -47;
	}

	public void setupTargets() {
		Location base = new Location(Bukkit.getWorld("world"), -2, 53, -47);

		int xminusellerplus = random.nextInt(3) + 1;
		if (xminusellerplus == 1) x--;
		if (xminusellerplus == 2) x = 0;
		if (xminusellerplus == 3) x++;

		int yminusellerplus = random.nextInt(3) + 1;
		if (yminusellerplus == 1) y--;
		if (yminusellerplus == 2) y = 0;
		if (yminusellerplus == 3) y++;

		Location targetLocation = base.clone().add(x, y, z);
		targets.put(targetLocation, x);
		targetLocation.getBlock().setType(Material.TARGET);
	}

	@EventHandler
	public void onPlayerJoinEvent(PlayerJoinEvent event) {
		Player player = event.getPlayer();

		new BukkitRunnable() {
			@Override
			public void run() {
				player.getInventory().clear();
				player.getInventory().addItem(bow);
				player.getInventory().setItem(9, arrow);
			}
		}.runTaskLater(Main.plugin, 10);
	}

	@EventHandler
	public void onProjectileLaunchEvent(ProjectileLaunchEvent event) {
		if (!(event.getEntity().getShooter() instanceof Player)) return;

		Player player = (Player) event.getEntity().getShooter();

		// Öka antal skjutna pilar för spelaren
		shotsFired.put(player, shotsFired.getOrDefault(player, 0) + 1);

		if (shuffleMode == true) {
			// Slumpa en ny position för pilbågen
			int newSlot;
			int currentSlot = player.getInventory().getHeldItemSlot() + 1; // Hotbar slot är 1-9
			do {
				newSlot = random.nextInt(9) + 1; // Slumpa 1 till 9
			} while (newSlot == currentSlot);

			ItemStack bow = player.getInventory().getItem(currentSlot - 1); // Hämta pilbågen
			if (bow != null && bow.getType() == Material.BOW) {
				player.getInventory().setItem(currentSlot - 1, null); // Ta bort från nuvarande slot
				player.getInventory().setItem(newSlot - 1, bow); // Lägg till i ny slot
			}
		}

		// Ta bort pilen efter 3 sekunder
		new BukkitRunnable() {
			@Override
			public void run() {
				if (event.getEntity().getType() == EntityType.ARROW) {
					event.getEntity().remove();
				}
			}
		}.runTaskLater(Main.plugin, 20 * 3); // 3 sekunder (60 ticks)
	}

	@EventHandler
	public void onProjectileHit(ProjectileHitEvent event) {
		if (!gameRunning || !(event.getEntity().getShooter() instanceof Player)) return;

		Player player = (Player) event.getEntity().getShooter();
		Location hitLocation = event.getHitBlock() != null ? event.getHitBlock().getLocation() : null;

		if (hitLocation != null && targets.containsKey(hitLocation)) {
			int points = targets.get(hitLocation);
			targets.remove(hitLocation);
			hitLocation.getBlock().setType(Material.AIR);
			scores.put(player, scores.getOrDefault(player, 0) + points);

			hits.put(player, hits.getOrDefault(player, 0) + 1); // Lägg till en träff
			Bukkit.broadcastMessage(namn + player.getName() + " träffade ett mål och fick " + points + " poäng!");
			x++;
			setupTargets();
		} else {
			misses.put(player, misses.getOrDefault(player, 0) + 1); // Lägg till en miss
		}
	}

	@EventHandler
	public void onPlayerItemDamage(PlayerItemDamageEvent event) {
		if (gameRunning == false) return;
		
		ItemStack item = event.getItem();
		if (item.getType().equals(Material.BOW) || item.getType().equals(Material.CROSSBOW)) {
			event.setCancelled(true);
		}
	}
}
