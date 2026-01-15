package me.thegreenman.beastmode.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class MinkenMenu implements CommandExecutor, Listener {
	private boolean toolsUnbreakable = true;
	private boolean armorUnbreakable = true;
	private boolean bowUnbreakable = true;
//	private boolean fallDamageEnabled = true;
	public static boolean raising = false;
	private static boolean itemsToInventory = false;

	public String minkensAdminMenuTest = ChatColor.LIGHT_PURPLE + "Minkens" + ChatColor.GOLD + " " + ChatColor.AQUA + "Menu";
	public Inventory menu = Bukkit.createInventory(null, 36, minkensAdminMenuTest);

	public ItemStack chest = new ItemStack(Material.CHEST, 1);
	public ItemStack elytra = new ItemStack(Material.ELYTRA, 1);
	public ItemStack fireworks = new ItemStack(Material.FIREWORK_ROCKET, 64);
	public ItemStack dirt = new ItemStack(Material.DIRT, 64);
	public ItemStack cobblestone = new ItemStack(Material.COBBLESTONE, 64);
	public ItemStack tnt = new ItemStack(Material.TNT, 16);
	public ItemStack enderpearl = new ItemStack(Material.ENDER_PEARL, 16);

	public ItemStack netherrack = new ItemStack(Material.NETHERRACK, 1);
	public ItemStack bricks = new ItemStack(Material.BRICKS, 1);
	public ItemStack stonebricks = new ItemStack(Material.STONE_BRICKS, 1);
	public ItemStack obsidian = new ItemStack(Material.OBSIDIAN, 1);
	public ItemStack bedrock = new ItemStack(Material.BEDROCK, 1);
	public ItemStack sandstone = new ItemStack(Material.SANDSTONE, 1);
	public ItemStack magma = new ItemStack(Material.MAGMA_BLOCK, 1);
	public ItemStack sealantern = new ItemStack(Material.SEA_LANTERN, 1);
	public ItemStack shroomlight = new ItemStack(Material.SHROOMLIGHT, 1);

	public ItemStack gold_ingot3 = new ItemStack(Material.GOLD_INGOT, 3);
	public ItemStack iron_ingot3 = new ItemStack(Material.IRON_INGOT, 3);
	public ItemStack bronze_ingot3 = new ItemStack(Material.BRICK, 3);

	public ItemStack gold_ingot10 = new ItemStack(Material.GOLD_INGOT, 10);
	public ItemStack iron_ingot10 = new ItemStack(Material.IRON_INGOT, 10);
	public ItemStack bronze_ingot10 = new ItemStack(Material.BRICK, 10);

	public ItemStack gold_ingot42 = new ItemStack(Material.GOLD_INGOT, 42);
	public ItemStack iron_ingot42 = new ItemStack(Material.IRON_INGOT, 42);
	public ItemStack bronze_ingot42 = new ItemStack(Material.BRICK, 42);
	public ItemStack glass = new ItemStack(Material.GLASS, 1);
	// GLOW INC SAK // Eehh, hur tänkte jag här? Debug glow? Minns inte.
	public static ItemStack diamond = new ItemStack(Material.DIAMOND, 1); // TA INTE BORT DENNA RAD! Används av nån klass.
	//	public ItemStack diaspak = new ItemStack(Material.LEVER, 1);
	public ItemStack luftBlock = new ItemStack(Material.BARRIER, 1);

	public int zMin = -49;
	public int zMax = 45;
	public int xMin = -49;
	public int xMax = 45;

	private final int[][] positions = {
			{-2, 51, -62}, // Röd
			{58, 51, -2}, // Grön
			{-2, 51, 58}, // Rosa (tidigare Gul)
			{-62, 51, -2} // Blå
	};

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("[PvPBeastMode] Sorry, players only.");
			return false;
		}

		Player player = ((Player) sender).getPlayer();

//		if (!command.getName().equalsIgnoreCase("minken")) { return false; }
		// if (!player.getWorld().getName().contains("beast")) {
		//   player.sendMessage("[PvPBeastMode] Felaktig värld! " + player.getWorld().getName() + " innehåller inte \"beast\".");
		//   return false;
		// }

		if (player.hasPermission("minken")) { // PREVIOUS CODE:    if (player.isOp()) { openAdminMenu(player); return true; }
			if (!player.getName().contentEquals("Minken")) { Bukkit.broadcastMessage(ChatColor.GOLD + player.getDisplayName() + " körde kommandot " + ChatColor.DARK_GREEN + "/minken"); }
			openAdminMenu(player);
			return true;
		}
		else
		{
			player.sendMessage("[PvPBeastMode] Nö! Permission denied. Åtkomst nekad.");
			return false;
		}
	}

	public MinkenMenu() { }

	private void openAdminMenu(Player player) {
		menu.setItem(0, chest);
		menu.setItem(1, elytra);
		menu.setItem(2, fireworks);
		menu.setItem(3, dirt);
		menu.setItem(4, cobblestone);
		menu.setItem(5, tnt);
		menu.setItem(6, enderpearl);
		menu.setItem(8, luftBlock);
		//		menu.setItem(7, diamond); // Behövs inte, körs per automatik
		//		menu.setItem(8, diaspak); // Behövs inte, körs per automatik

		menu.setItem(9, netherrack);
		menu.setItem(10, bricks);
		menu.setItem(11, stonebricks);
		menu.setItem(12, obsidian);
		menu.setItem(13, bedrock);
		menu.setItem(14, sandstone);
		menu.setItem(15, magma);
		menu.setItem(16, sealantern);
		menu.setItem(17, shroomlight);

		menu.setItem(18, gold_ingot3);
		menu.setItem(19, iron_ingot3);
		menu.setItem(20, bronze_ingot3);

		menu.setItem(21, gold_ingot10);
		menu.setItem(22, iron_ingot10);
		menu.setItem(23, bronze_ingot10);

		menu.setItem(24, gold_ingot42);
		menu.setItem(25, iron_ingot42);
		menu.setItem(26, bronze_ingot42);

		//		ItemStack lavaRising = new ItemStack(Material.LAVA_BUCKET);
		//		ItemMeta lavaMeta = lavaRising.getItemMeta();
		//		lavaMeta.setDisplayName("Start Lava Rising");
		//		lavaRising.setItemMeta(lavaMeta);
		//		menu.setItem(27, lavaRising);

		//		ItemStack glaset = new ItemStack(Material.GLASS);
		//		ItemMeta glasetMeta = glaset.getItemMeta();
		//		glasetMeta.setDisplayName("Replace Lava -> Air");
		//		glaset.setItemMeta(glasetMeta);
		//		menu.setItem(28, glaset);

		//		ItemStack waterRising = new ItemStack(Material.WATER_BUCKET);
		//		ItemMeta waterMeta = waterRising.getItemMeta();
		//		waterMeta.setDisplayName("Start Water Tsunami");
		//		waterRising.setItemMeta(waterMeta);
		//		menu.setItem(29, waterRising);

		//		ItemStack fallDamageToggle = new ItemStack(Material.IRON_BOOTS);
		//		ItemMeta fallMeta = fallDamageToggle.getItemMeta();
		//		fallMeta.setDisplayName("Fall Damage: " + (fallDamageEnabled ? "On" : "Off"));
		//		fallDamageToggle.setItemMeta(fallMeta);
		//		menu.setItem(15, fallDamageToggle);

		//		ItemStack pvpItem = new ItemStack(pvpEnabled ? Material.DIAMOND_SWORD : Material.WOODEN_SWORD);
		//		var pvpMeta = pvpItem.getItemMeta();
		//		pvpMeta.setDisplayName("PvP: " + (pvpEnabled ? "På" : "Av"));
		//		pvpItem.setItemMeta(pvpMeta);
		//		menu.setItem(16, pvpItem);

		////		if (startCountdownPressed == false) {
		//		ItemStack startCountdown = new ItemStack(Material.GREEN_WOOL);
		//		ItemMeta countdownMeta = startCountdown.getItemMeta();
		//		countdownMeta.setDisplayName("Visa scoreboard");
		//		startCountdown.setItemMeta(countdownMeta);
		//		menu.setItem(17, startCountdown);
		////		}

		ItemStack bowItem = new ItemStack(bowUnbreakable ? Material.BOW : Material.CROSSBOW);
		var bowMeta = bowItem.getItemMeta();
		bowMeta.setDisplayName("Pilbågar kan gå sönder: " + (bowUnbreakable ? "Nej" : "Ja"));
		bowItem.setItemMeta(bowMeta);
		menu.setItem(31, bowItem);

		ItemStack toolsItem = new ItemStack(toolsUnbreakable ? Material.DIAMOND_PICKAXE : Material.STONE_PICKAXE);
		var toolsMeta = toolsItem.getItemMeta();
		toolsMeta.setDisplayName("Verktyg kan gå sönder: " + (toolsUnbreakable ? "Nej" : "Ja"));
		toolsItem.setItemMeta(toolsMeta);
		menu.setItem(32, toolsItem);

		ItemStack armorItem = new ItemStack(armorUnbreakable ? Material.DIAMOND_CHESTPLATE : Material.LEATHER_CHESTPLATE);
		var armorMeta = armorItem.getItemMeta();
		armorMeta.setDisplayName("Rustning kan gå sönder: " + (armorUnbreakable ? "Nej" : "Ja"));
		armorItem.setItemMeta(armorMeta);
		menu.setItem(33, armorItem);

		ItemStack playerSkull = new ItemStack(itemsToInventory ? Material.PLAYER_HEAD : Material.SKELETON_SKULL);
		var playerSkullMeta = playerSkull.getItemMeta();
		playerSkullMeta.setDisplayName("Items till inventory: " + (itemsToInventory ? "Ja" : "Nej"));
		playerSkull.setItemMeta(playerSkullMeta);
		menu.setItem(34, playerSkull);

		player.openInventory(menu);
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (event.getView().getTitle().equals(minkensAdminMenuTest)) {
			event.setCancelled(true);

			Player player = (Player) event.getWhoClicked();
			ItemStack clickedItem = event.getCurrentItem();

			if (clickedItem != null) {
				Material material = clickedItem.getType();
				World world = player.getWorld();

				switch (material) {

				case CHEST -> {
					Block redChest = new Location(world, -2, 51, -63).getBlock(); // Röd
					Block greenChest = new Location(world, 59, 51, -2).getBlock(); // Grön
					Block pinkChest = new Location(world, -2, 51, 59).getBlock(); // Rosa (tidigare Gul)
					Block blueChest = new Location(world, -63, 51, -2).getBlock(); // Blå

					// Sätt blockets material till kista
					redChest.setType(Material.CHEST);
					greenChest.setType(Material.CHEST);
					pinkChest.setType(Material.CHEST);
					blueChest.setType(Material.CHEST);
				}

				case ELYTRA -> { spawnItems(world, elytra); }
				case FIREWORK_ROCKET -> { spawnItems(world, fireworks); }
				case DIRT -> { spawnItems(world, dirt); }
				case COBBLESTONE -> { spawnItems(world, cobblestone); }
				case TNT -> { spawnItems(world, tnt); }
				case ENDER_PEARL -> { spawnItems(world, enderpearl); }
				//				case DIAMOND -> { spawnItemsInTheMiddle(world, diamond); }
				//				case LEVER -> {
				//					new BukkitRunnable() {
				//						@Override
				//						public void run() {
				//							spawnItemsInTheMiddle(world, diamond);
				//						}
				//					}.runTaskTimer(Main.plugin, 0, 20 * 30); // 20 ticks * 30 sekunder
				//				}

				case GOLD_INGOT -> {
					ItemStack guldfusk = new ItemStack(Material.GOLD_INGOT, event.getCurrentItem().getAmount());
					// ItemMeta gfim = guldfusk.getItemMeta();
					// if (gfim != null) {
					// gfim.setDisplayName("§bGold"); // Sätter visningsnamnet till "Gold" med §b (ljusblå färg)
					// guldfusk.setItemMeta(gfim);
					// }

					// spawnItems(world, guldfusk);
					spawnItems(world, guldfusk);
				}

				case IRON_INGOT -> {
					ItemStack ironfusk = new ItemStack(Material.IRON_INGOT, event.getCurrentItem().getAmount());
					//					ItemMeta gfim = guldfusk.getItemMeta();
					//					if (gfim != null) {
					//					    gfim.setDisplayName("§bSilver"); // Sätter visningsnamnet till "Gold" med §b (ljusblå färg)
					//					    guldfusk.setItemMeta(gfim);
					//					}

					spawnItems(world, ironfusk);
				}

				case BRICK -> {
					ItemStack brickfusk = new ItemStack(Material.BRICK, event.getCurrentItem().getAmount());
					//					ItemMeta gfim = guldfusk.getItemMeta();
					//					if (gfim != null) {
					//					    gfim.setDisplayName("§bGold"); // Sätter visningsnamnet till "Gold" med §b (ljusblå färg)
					//					    guldfusk.setItemMeta(gfim);
					//					}

					spawnItems(world, brickfusk);
				}

				//				case GOLD_INGOT -> {
				//					ItemStack guldfusk = new ItemStack(Material.GOLD_INGOT, 42);
				//					ItemMeta gfim = guldfusk.getItemMeta();
				//					gfim.setItemName("§bGold"); // {"text":"§bGold"}
				//					guldfusk.setItemMeta(gfim);
				//					spawnItems(world, guldfusk); }

				case BOW, CROSSBOW -> {
					bowUnbreakable = !bowUnbreakable;
					player.sendMessage("Pilbågar/Crossbows kan gå sönder: " + (bowUnbreakable ? "nej" : "ja"));
					openAdminMenu(player); // Uppdatera menyn
				}

				//				case DIAMOND_SWORD, WOODEN_SWORD -> {
				//					pvpEnabled = !pvpEnabled;
				//					world.setPVP(pvpEnabled);
				//					player.sendMessage("PvP: " + (pvpEnabled ? "på" : "av"));
				//					openAdminMenu(player); // Uppdatera menyn
				//				}

				case DIAMOND_PICKAXE, STONE_PICKAXE -> {
					toolsUnbreakable = !toolsUnbreakable;
					player.sendMessage("Verktyg kan gå sönder: " + (toolsUnbreakable ? "nej" : "ja"));
					openAdminMenu(player); // Uppdatera menyn
				}

				case DIAMOND_CHESTPLATE, LEATHER_CHESTPLATE -> {
					armorUnbreakable = !armorUnbreakable;
					player.sendMessage("Rustning kan gå sönder: " + (armorUnbreakable ? "nej" : "ja"));
					openAdminMenu(player); // Uppdatera menyn
				}

				case BARRIER -> {
					replaceMiddleBlocks(Material.AIR, player);
				}

				case NETHERRACK -> {
					replaceMiddleBlocks(Material.NETHERRACK, player);
				}

				case BRICKS -> {
					replaceMiddleBlocks(Material.BRICKS, player);
				}

				case STONE_BRICKS -> {
					replaceMiddleBlocks(Material.STONE_BRICKS, player);
				}

				case OBSIDIAN -> {
					replaceMiddleBlocks(Material.OBSIDIAN, player);
				}

				case BEDROCK -> {
					replaceMiddleBlocks(Material.BEDROCK, player);
				}

				case SANDSTONE -> {
					replaceMiddleBlocks(Material.SANDSTONE, player);
				}

				case MAGMA_BLOCK -> {
					replaceMiddleBlocks(Material.MAGMA_BLOCK, player);
				}

				case SEA_LANTERN -> {
					replaceMiddleBlocks(Material.SEA_LANTERN, player);
				}

				case SHROOMLIGHT -> {
					replaceMiddleBlocks(Material.SHROOMLIGHT, player);
				}

				//				case IRON_BOOTS -> {
				//					fallDamageEnabled = !fallDamageEnabled;
				//					player.sendMessage("Fall Damage is now " + (fallDamageEnabled ? "enabled!" : "disabled!"));
				//					openAdminMenu(player); // Uppdatera menyn
				//				}

				//				case GREEN_WOOL -> {
				//				    ScreBrd scoreboard = new ScreBrd();
				//				    scoreboard.startCountdown(); // Starta nedräkningen
				//				    menu.setItem(13, null);
				//				    startCountdownPressed = true;
				//				}

				//				case LAVA_BUCKET -> {
				//					if (checkIfRaising() == true) { return; };
				//					
				//					ScreBrd scoreboard = new ScreBrd();
				//					scoreboard.startLavaRising(); // Starta Lava Rising-läget
				//				}

				//				case GLASS -> {
				//					if (checkIfRaising() == true) { return; };
				//
				//					ScreBrd scoreboard = new ScreBrd();
				//					scoreboard.startLavaToAirReplace(); // Starta Lava Rising-läget
				//				}

				//				case WATER_BUCKET -> {
				//					if (checkIfRaising() == true) { return; };
				//					
				//					ScreBrd scoreboard = new ScreBrd();
				//					scoreboard.startWaterTsunami(); // Starta Lava Rising-läget
				//				}

				case PLAYER_HEAD, SKELETON_SKULL -> {
					itemsToInventory = !itemsToInventory;
					player.sendMessage("Items to Inventory = " + (itemsToInventory ? "disabled!" : "enabled!"));
					openAdminMenu(player); // Uppdatera menyn
				}

				default -> { player.sendMessage("Ogiltigt val!"); }
				}
			}
		}
	}

	//	public boolean checkIfRaising() {
	//		if (AdminMenu.raising == true) {
	//			Bukkit.broadcastMessage("Already rising, please wait.");
	//			return true;
	//		}
	//		
	//		return false;
	//	}

	public void replaceMiddleBlocks(Material material, Player player) {
		for (int z = zMin; z <= zMax; z++) {
			Block block = player.getWorld().getBlockAt(-2, 49, z);
			block.setType(material);
		}

		for (int x = xMin; x <= xMax; x++) {
			Block block = player.getWorld().getBlockAt(x, 49, -2);
			block.setType(material);
		}
	}

	@EventHandler
	public void onPlayerItemDamage(PlayerItemDamageEvent event) {
		ItemStack item = event.getItem();
		if (bowUnbreakable && isBow(item.getType()) || (toolsUnbreakable && isTool(item.getType())) || (armorUnbreakable && isArmor(item.getType()))) {
			event.setCancelled(true);
		}
	}

	private boolean isBow(Material material) {
		return material.equals(Material.BOW) ||
				material.equals(Material.CROSSBOW);
	}

	private boolean isTool(Material material) {
		return material.name().endsWith("_PICKAXE") ||
				material.name().endsWith("_AXE") ||
				material.name().endsWith("_SHOVEL") ||
				material.name().endsWith("_HOE") ||
				material.name().endsWith("_SWORD");
	}

	private boolean isArmor(Material material) {
		return material.name().endsWith("_HELMET") ||
				material.name().endsWith("_CHESTPLATE") ||
				material.name().endsWith("_LEGGINGS") ||
				material.name().endsWith("_BOOTS") ||
				material == Material.SHIELD;
	}

	private void spawnItems(World world, ItemStack itemstack) {
		if (!itemsToInventory) {
			for (int[] pos : positions) {
				world.dropItemNaturally(new Location(world, pos[0], pos[1], pos[2]), new ItemStack(itemstack));
			}
		}
		// else
		if (itemsToInventory) {
			for (Player pl : Bukkit.getOnlinePlayers()) {
				pl.getInventory().addItem(itemstack);
			}
		}
	}

	public static void spawnItemsInTheMiddle (World world, ItemStack itemstack) {
		if (itemsToInventory == false) {
			// -1.5 45 -1.5
			world.dropItem(new Location(world, 49.5, 122, 229.5), new ItemStack(itemstack));
		}
		// else
		if (itemsToInventory == true) {
			for (Player pl : Bukkit.getOnlinePlayers()) {
				pl.getInventory().addItem(itemstack);
			}
		}
	}

//	public boolean isFallDamageEnabled() {
//		return fallDamageEnabled;
//	}
}