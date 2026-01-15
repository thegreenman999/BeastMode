package me.thegreenman.NOTinUSE;

import java.util.*;


import java.util.stream.Collectors;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

// KOPIERAT FRÅN INFERNOTEST/BIRA.JAVA 29/12-24 @ 16:44
// FÖRMODLIGEN MODIFIERAT EFTER DET :)

public class Bira implements Listener {
	private static Map<UUID, Long> drunkPlayers = new HashMap<>();
	private static Map<UUID, Long> effectCooldown = new HashMap<>();
	public static String biraName = ChatColor.GOLD + "Bira" + ChatColor.RESET;

	public static ItemStack createBira() {
		ItemStack biraItem = new ItemStack(Material.POTION, 1);
		biraItem.addUnsafeEnchantment(Enchantment.VANISHING_CURSE, 1);
		ItemMeta biraItemMeta = biraItem.getItemMeta();
		biraItemMeta.setDisplayName(biraName);
		biraItemMeta.setLore(Arrays.asList("Bira", ":)"));
		biraItem.setItemMeta(biraItemMeta);
		return biraItem;
	}

	@EventHandler
	public static void onPlayerToggleSneak(PlayerToggleSneakEvent event) {
		Player player = event.getPlayer();
		if (player.isSneaking()) return;
		if (player.getInventory().firstEmpty() != -1) {
			player.getInventory().addItem(createBira());
			player.sendMessage(player.getName() + ", du fick just en bira av Minkens dorkiga fylleplugin :D");
		}
		else {
			player.sendMessage("Fylltratt! Ditt inventory är fullt - ingen plats för " + biraName + ".");
		}
	}

	@EventHandler
	public static void onPlayerConsume(PlayerItemConsumeEvent event) {
		ItemStack item = event.getItem();
		if (!(item.getType() == Material.POTION) &&
				item.hasItemMeta() &&
				item.getItemMeta().hasDisplayName() &&
				item.getItemMeta().getDisplayName().equals(biraName)) { return; }

		Player player = event.getPlayer();
		if (player.getName().startsWith("Minken")) {
//			player.sendMessage("Ditt namn börjar med Minken, så du slipper PotionEffectType.NAUSEA :D");
		} else {
			player.addPotionEffect(new PotionEffect(PotionEffectType.NAUSEA, 20 * 5, 0));
		}
		player.sendMessage(ChatColor.RED + "Du känner dig en smula groggy (yr) av biran!");

		long drunkDuration = System.currentTimeMillis() + (12 * 1000);
		drunkPlayers.put(player.getUniqueId(), drunkDuration);
	}

	@EventHandler
	public static void onPlayerMove(PlayerMoveEvent event) {
	    Player player = event.getPlayer();

	    if (!drunkPlayers.containsKey(player.getUniqueId())) { return; }
	    long endTime = drunkPlayers.get(player.getUniqueId());

	    if (System.currentTimeMillis() < endTime) {
	        // Kontrollera om spelaren har en aktiv cooldown
	        long currentTime = System.currentTimeMillis();
	        if (effectCooldown.containsKey(player.getUniqueId())) {
	            long lastEffectTime = effectCooldown.get(player.getUniqueId());
	            if (currentTime - lastEffectTime < 800) { // Xms = X ticks
	                return; // För tidigt för en ny effekt
	            }
	        }

	        // Uppdatera tid för senaste effekt
	        effectCooldown.put(player.getUniqueId(), currentTime);

	        // Vinglingseffekt
	        if (Math.random() < 0.30) {
	            double offset = 0.05 + (Math.random() * 2.95);
	            double offsetX = (Math.random() - 0.5) * offset;
	            double offsetZ = (Math.random() - 0.5) * offset;
	            event.setTo(event.getTo().add(offsetX, 0, offsetZ));
	        }

	        // Blixt
	        if (Math.random() < 0.01) { 
	            player.getWorld().strikeLightningEffect(player.getLocation().add(
	                    (Math.random() - 0.5) * 10, 0, (Math.random() - 0.5) * 10));
	        }

	        // Partiklar
	        if (Math.random() < 0.35) {
	            player.getWorld().spawnParticle(Particle.SMOKE, player.getLocation(), 10);
	        }

	        // Blindhetseffekt
	        if (Math.random() < 0.11) {
	            player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * 1, 0, true));
	            player.sendMessage(ChatColor.GRAY + "Dina ögon blir suddiga...");
	        }

	        // Ljud
	        if (Math.random() < 0.03) {
	            player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ZOMBIE_AMBIENT, 1, 1);
	            player.sendMessage(ChatColor.DARK_GRAY + "Du hör konstiga ljud...");
	        }

	        // Skakning
	        if (Math.random() < 0.01) {
	            Location location = player.getLocation();
	            float yaw = location.getYaw() + (float)((Math.random() - 0.5) * 30);
	            float pitch = location.getPitch() + (float)((Math.random() - 0.5) * 30);
	            location.setYaw(yaw);
	            location.setPitch(pitch);
	            player.teleport(location);
	            player.sendMessage(ChatColor.RED + "Ditt huvud känns tungt...");
	        }

	    } else {
	        drunkPlayers.remove(player.getUniqueId());
	        effectCooldown.remove(player.getUniqueId()); // Ta bort cooldown när spelaren nyktrar
	        player.sendMessage(ChatColor.GREEN + "Du börjar känna dig nykter igen.");
	    }
	}

	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		if (!drunkPlayers.containsKey(player.getUniqueId())) { return; }
		String message = event.getMessage();
		String scrambledMessage = Arrays.stream(message.split(" "))
				.map(this::scrambleWord)
				.collect(Collectors.joining(" "));
		event.setMessage(scrambledMessage);
		player.sendMessage(ChatColor.YELLOW + "Ditt meddelande låter lite sluddrigt...");
	}

	public String scrambleWord(String word) {
		List<Character> characters = word.chars()
				.mapToObj(c -> (char) c)
				.collect(Collectors.toList());
		Collections.shuffle(characters);
		StringBuilder scrambled = new StringBuilder();
		for (char c : characters) {
			scrambled.append(c);
		}
		return scrambled.toString();
	}
}
