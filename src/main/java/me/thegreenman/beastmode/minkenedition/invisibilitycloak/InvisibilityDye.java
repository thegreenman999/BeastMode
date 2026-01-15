package me.thegreenman.beastmode.minkenedition.invisibilitycloak;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import me.thegreenman.Main;

public class InvisibilityDye implements Listener, CommandExecutor {
	private final Map<UUID, BukkitRunnable> activeTasks = new HashMap<>();
	
	public static ItemStack invisDye = new ItemStack(Material.WHITE_DYE, 1) {{
		ItemMeta meta = this.getItemMeta();
		if (meta != null) {
			meta.setDisplayName(ChatColor.LIGHT_PURPLE + "Invisibility Cloak");
			ArrayList<String> lore = new ArrayList<>();
			lore.add(ChatColor.YELLOW + "§aInvisibility Cloak");
			meta.setLore(lore);
			this.setItemMeta(meta);
		}
	}};

//	public static ItemStack invisxxx = new ItemStack(Material.xxx, 1);

	//	public static ItemStack cloak = new ItemStack(Material.ELYTRA);

	//	public static ItemStack clay = new ItemStack(Material.GRAY_DYE);

	/*
	 * Ersätt Elytra med xxx?
	 * Right click = aktivera invis.
	 * Cooldown i XP-baren? 3...2....1... SYNLIG (visa ej 0s).
	 */

	//	public ItemStack getCloak() {
	//		ItemMeta meta = cloak.getItemMeta();
	//		meta.setDisplayName(ChatColor.LIGHT_PURPLE + "Invisibility Cloak"); // Gorunmezlik Pelerini <-- whatever that means? Probably Invisibility Cloak.
	//		ArrayList<String> lore = new ArrayList<>();
	//		lore.add(ChatColor.YELLOW + "RIGHT CLICK");
	//		lore.add(ChatColor.GREEN + "Invisible" + ChatColor.WHITE + " for " + ChatColor.GREEN + "X seconds");
	//		meta.setLore(lore);
	//		cloak.setItemMeta(meta);
	//		return cloak;
	//	}

	//	public void removeCloak(Player player) {
	//		player.getInventory().remove(cloak);
	//	}

	//	public ItemStack getClay() {
	//		ItemMeta meta = clay.getItemMeta();
	//		meta.setDisplayName(ChatColor.AQUA + "Cooldown");
	//		ArrayList<String> lore = new ArrayList<>();
	//		lore.add(ChatColor.WHITE + "Wait for X seconds");
	//		meta.setLore(lore);
	//		clay.setItemMeta(meta);
	//		return clay;
	//	}

	//	public void removeClay(Player player) {
	//		player.getInventory().remove(clay);
	//	}

	////////////////////////////////////////// CHATGPT CODE STARTS HERE ///////////////////////////////////////////
	///

	// MInken was here
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player player) {
			invisDye.setAmount(1);
			player.getInventory().addItem(invisDye);
			Bukkit.broadcastMessage(Main.pvpbm + player.getName() + " ran the command /" + cmd.getLabel());			

			return true;
		}
		else
		{
			sender.sendMessage("Not in use by this sender.");
			return false;
		}
	}
	// MInken was here END

	private final Map<UUID, Boolean> cooldown = new HashMap<>();
	private final int DURATION = 10; // seconds


	@EventHandler
	public void onRightClick(PlayerInteractEvent event) {
		if (event.getHand() != EquipmentSlot.HAND) return;

		Action action = event.getAction();

		if (action != Action.RIGHT_CLICK_AIR && action != Action.RIGHT_CLICK_BLOCK) return;

		Player player = event.getPlayer();
		ItemStack item = event.getItem();

		if (item == null || item.getType() != invisDye.getType()) return;
		if (!isInvisibilityDye(item)) return;

		// If on cooldown, do nothing
		if (cooldown.getOrDefault(player.getUniqueId(), false)) {
			return;
		}

		activateInvisibility(player, item);
		if (player.getInventory().getItemInMainHand().getAmount() > 1)
		{
			ItemStack bubu = invisDye; // ERROR: new ItemStack (player.getInventory().getItemInMainHand().getType(), player.getInventory().getItemInMainHand().getAmount() - 1);
			bubu.setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
			player.getInventory().setItemInMainHand(bubu);
		}
		else
		{
			player.getInventory().setItemInMainHand(null);
		}
	}

	private void activateInvisibility(Player player, ItemStack dye) {
		cooldown.put(player.getUniqueId(), true);


		// Set lore to cooldown
		//		setLore(xxx, "§cCooldown");

		// Apply invisibility
		player.addPotionEffect(new PotionEffect(
				PotionEffectType.INVISIBILITY,
				(DURATION + 1) * 20,
				0,
				false,
				false
				));

		player.setLevel(DURATION);
		player.setExp(0);

		BukkitRunnable task = new BukkitRunnable() {
			int timeLeft = DURATION;

			@Override
			public void run() {
				if (!player.isOnline() || player.isDead()) {
					cleanup(player);
					cancel();
					return;
				}

				if (timeLeft <= 0) {
					player.removePotionEffect(PotionEffectType.INVISIBILITY);
					player.setLevel(0);
					player.setExp(0);

					//		            updateXXXLore(player, "§aInvisibility Cloak");
					cooldown.put(player.getUniqueId(), false);

					activeTasks.remove(player.getUniqueId());
					cancel();
					return;
				}

				player.setLevel(timeLeft);
				timeLeft--;
			}
		};

		activeTasks.put(player.getUniqueId(), task);
		task.runTaskTimer(Main.plugin, 0L, 20L);
	}

	private boolean isInvisibilityDye(ItemStack item) {
		if (!item.hasItemMeta()) return false;
		ItemMeta meta = item.getItemMeta();
		if (!meta.hasLore()) return false;

		return meta.getLore().contains("§aInvisibility Cloak"); // || meta.getLore().contains("§cCooldown");
	}

	//	private void setLore(ItemStack item, String loreText) {
	//		ItemMeta meta = item.getItemMeta();
	//		meta.setLore(Collections.singletonList(loreText));
	//		item.setItemMeta(meta);
	//	}

	////////////////////////////////// GPT ANSWER 2 ////////////////////////////////////////

	@EventHandler
	public void onDrop(PlayerDropItemEvent event) {
		ItemStack item = event.getItemDrop().getItemStack();

		if (item.getType() != invisDye.getType()) return;
		if (!isInvisibilityDye(item)) return;

		event.setCancelled(true);
	}

	@EventHandler
	public void onPlace(BlockPlaceEvent event) {
		ItemStack item = event.getItemInHand();

		if (item.getType() != invisDye.getType()) return;
		if (!isInvisibilityDye(item)) return;

		event.setCancelled(true);
	}

	@EventHandler
	public void onDeath(PlayerDeathEvent event) {
		cleanup(event.getEntity());
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		cleanup(event.getPlayer());
	}

	private void cleanup(Player player) {
		UUID uuid = player.getUniqueId();

		cooldown.remove(uuid);

		BukkitRunnable task = activeTasks.remove(uuid);
		if (task != null) {
			task.cancel();
		}

		player.removePotionEffect(PotionEffectType.INVISIBILITY);
		player.setLevel(0);
		player.setExp(0);
	}

	//	private void updateXXXLore(Player player, String lore) {
	//	    for (ItemStack item : player.getInventory().getContents()) {
	//	        if (item == null || item.getType() != Material.XXX) continue;
	//	        if (!isInvisibilityXXX(item)) continue;
	//
	//	        ItemMeta meta = item.getItemMeta();
	//	        if (meta == null) continue;
	//
	//	        meta.setLore(List.of(lore));
	//	        item.setItemMeta(meta);
	//	        return; // only update one
	//	    }
	//	}

	@EventHandler
	public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
		//	    if (!(event.getDamager() instanceof Player)) { return; }
		if (!(event.getEntity() instanceof Player)) { return; }

		//	    Player attacker = (Player) event.getDamager();
		Player defender = (Player) event.getEntity();

		//	    if (attacker.hasPotionEffect(PotionEffectType.INVISIBILITY)) { 
		//	    	attacker.removePotionEffect(PotionEffectType.INVISIBILITY);
		//	    }

		if (defender.hasPotionEffect(PotionEffectType.INVISIBILITY)) { 
			cleanup(defender);
//			defender.removePotionEffect(PotionEffectType.INVISIBILITY);
//			defender.setLevel(0);
//			defender.setExp(0);
		}
	}


}
