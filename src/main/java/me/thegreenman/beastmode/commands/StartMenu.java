package me.thegreenman.beastmode.commands;

import java.util.*;

import me.thegreenman.Main;
import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import me.thegreenman.beastmode.BeastModeData;
import me.thegreenman.beastmode.StartGame;
import org.jetbrains.annotations.NotNull;

import static me.thegreenman.Main.*;

@SuppressWarnings("deprecation")
public class StartMenu implements CommandExecutor, Listener {
	public static Random random = new Random();

	private static final String BeastmodeMenu = ChatColor.LIGHT_PURPLE + "Beast" + ChatColor.GOLD + "Mode" + ChatColor.AQUA + "Menu";
    private static final Component BeastmodeGameRuleMenu = Component.text("§dBeast§6Mode§bMenu §cGameRules");
    private static final Component BeastmodeTNTbowMenu = Component.text("§dBeast§6Mode §4T§cN§4T§rBow §bMenu");
    private static final Component BeastmodeDorkMenu = Component.text("Â§dBeastÂ§6Mode Â§4DÂ§cOÂ§4RÂ§cK Â§bMenu");
    public static boolean menuRunner = false;


    Location spakloc = null;

	public StartMenu() {
	}

	public static ItemStack startBow = new ItemStack(Material.BOW, 1) {{
		ItemMeta meta = this.getItemMeta();
		if (meta != null) {
			meta.setItemName("StartBow");
			meta.addEnchant(Enchantment.INFINITY, 1, false);
			//meta.setLore((Arrays.asList("Smock", "", "")));
			this.setItemMeta(meta);
		}
	}};

	public static ItemStack startGame = new ItemStack(Material.GREEN_WOOL, 1) {{
		ItemMeta meta = this.getItemMeta();
		if (meta != null) {
			meta.setItemName("Start game");
			meta.setLore((Arrays.asList("Starts", "Beast", "Mode")));
			this.setItemMeta(meta);
		}
	}};

    public static ItemStack back = new ItemStack(Material.BARRIER) {{
       ItemMeta meta = this.getItemMeta();
        if (meta != null) {
            meta.itemName(Component.text("§fBack"));
            this.setItemMeta(meta);
        }
    }};

    private static final ItemStack GameRuleItem = new ItemStack(Material.COMMAND_BLOCK) {{
        ItemMeta meta = this.getItemMeta();

        meta.itemName(Component.text("§fGameRule"));
        meta.lore(List.of(Component.text("§7Click to Change")));
        this.setItemMeta(meta);
    }};
    public static final ItemStack TNTBow = new ItemStack(Material.BOW) {{
        ItemMeta meta = this.getItemMeta();

        meta.itemName(Component.text("TNT Bow settings"));
        meta.lore(List.of(Component.text("§7Click to Change")));
        this.setItemMeta(meta);
    }};

    private static final ItemStack dorkMenu = new ItemStack(Material.DRAGON_EGG) {{
        ItemMeta meta = this.getItemMeta();
        meta.itemName(Component.text("Dork settings"));
        meta.lore(List.of(Component.text("Â§7Click to Change")));
        this.setItemMeta(meta);
    }};

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
		if (!(sender instanceof Player player)) {
			sender.sendMessage("[Server] Nope, players only.");
			return false;
		}


		if (WORLD.isAutoSave()) {
            WORLD.setAutoSave(false);
        }

		if (BeastModeData.gameIsRunning) {
            Bukkit.broadcastMessage("[Server] Spelet körs redan.");
            return false;
        }

		if (!menuRunner) {
			menuRunner = true;
		}
		else {
			player.sendMessage("Sorry, men någon annan (kolla chatten, borde stå där vem) är redan inne i menyn.");
			return false;
		}

		if (!player.getName().contentEquals("BubuWhatever179")) {
            Bukkit.broadcastMessage(ChatColor.GOLD + player.getDisplayName() + " körde kommandot " + ChatColor.DARK_GREEN + "/start");
        }

		openAdminMenu(player);
		return true;
	}

	public static void openAdminMenu(Player player) {
		Inventory menu = Bukkit.createInventory(null, 45, BeastmodeMenu);

		//  0   1   2   3   4   5   6   7   8
		//  9  10  11  12  13  14  15  16  17
		// 18  19  20  21  22  23  24  25  26
		// 27  28  29  30  31  32  33  34  35
		// 36  37  38  39  40  41  42  43  44

		menu.setItem(0, createToggleItem("Minken Edition Rules", Material.CHEST, BeastModeData.minkenEditionThusFalseIsOriginalRules));
		menu.setItem(1, createToggleItem("Free Start Bow", Material.BOW, BeastModeData.freeStartBow));
		menu.setItem(2, createToggleItem("Free Start Blocks", Material.OAK_LEAVES, BeastModeData.freeStartBlocks));

		menu.setItem(3, createToggleItem("Return death items", Material.SKELETON_SKULL, BeastModeData.returnDeathItems));
		menu.setItem(4, createToggleItem("Super Snowball", Material.SNOWBALL, BeastModeData.includeSuperSnowball));
		// addGameRuleToggler(menu, player);
		menu.setItem(6, createToggleItem("Beds can be picked up", Material.RED_BED, BeastModeData.bedsCanBePickedUp));

		menu.setItem(9, createValueItem("Shield price (dia)", Material.SHIELD, BeastModeData.diaForShield));
		menu.setItem(10, createValueItem("WaterBucket price", Material.WATER_BUCKET, BeastModeData.diaForWaterBucket));
		menu.setItem(11, createValueItem("Enchanted Golden Apple price", Material.ENCHANTED_GOLDEN_APPLE, BeastModeData.diaForSuperGoldenApple));
		menu.setItem(12, createValueItem("EnderPearl price", Material.ENDER_PEARL, BeastModeData.diaForEnderPearl));
		menu.setItem(13, createValueItem("AddeBow price", Material.ARROW, BeastModeData.diaForAddeBow));
		menu.setItem(14, createValueItem("Golds for 1 Dia", Material.GOLD_INGOT, BeastModeData.goldsForOneDia));
		menu.setItem(15, createValueItem("Mace price", Material.MACE, BeastModeData.diaForMace));
		menu.setItem(16, createValueItem("Wind Charge price (gold)", Material.WIND_CHARGE, BeastModeData.goldForWindCharge));

		menu.setItem(18, createValueItem("Start HP", Material.GOLDEN_APPLE, BeastModeData.startHP));
		menu.setItem(19, createValueItem("Start foodlevel", Material.BEEF, BeastModeData.startFoodlevel));
		menu.setItem(20, createValueItem("Middle Diamond Drop delay (seconds)", Material.DIAMOND, BeastModeData.middleDiamondDropDelayInSeconds));

        menu.setItem(27, uppdateMapItem());
        menu.setItem(28, uppdateGlowItem());
        menu.setItem(29, GameRuleItem);
        menu.setItem(30, TNTBow);
        // 31
        menu.setItem(32, createToggleItem("DorkMode", Material.DEAD_BUSH, BeastModeData.dorkMode));
        if (BeastModeData.dorkMode) {
            menu.setItem(33, dorkMenu);
        }
        // ...
		menu.setItem(44, startGame);

		player.openInventory(menu);
	}

    public static void openGameRuleMenu(Player player) {
        Inventory menu = Bukkit.createInventory(player, 36, BeastmodeGameRuleMenu);

        menu.addItem(createToggleItem("Spawn mob", Material.ZOMBIE_SPAWN_EGG, BeastModeData.SpawnMobs));
        menu.addItem(createToggleItem(Component.text("§fAnnounce Advancements"), Material.KNOWLEDGE_BOOK, BeastModeData.AnnounceAdvancements));
        menu.addItem(createDifficultyItem(Component.text("§fDifficulty"), WORLD.getDifficulty()));
        menu.addItem(createToggleItem(Component.text("Fall Damage"), Material.IRON_BOOTS, BeastModeData.FallDamage));
        menu.addItem(createToggleItem(Component.text("Fire Damage"), Material.FLINT_AND_STEEL, BeastModeData.FireDamage));
        menu.addItem(createToggleItem(Component.text("Keep Inventory"), Material.ENDER_CHEST, BeastModeData.KeepInventory));
        menu.addItem(createToggleItem(Component.text("Locatorbar"), Material.COMPASS, BeastModeData.Locatorbar));
        menu.addItem(createToggleItem(Component.text("Natural Regeneration"), Material.APPLE, BeastModeData.NaturalRegeneration));

        menu.setItem(35, back);

        player.openInventory(menu);
    }

    public static void openTNTBow(Player player) {
        Inventory menu = Bukkit.createInventory(player, 9, BeastmodeTNTbowMenu);

        menu.setItem(0, createValueItem("Explosion Power", Material.TNT, BeastModeData.TNTBowExplosionPower));
        menu.setItem(1, createValueItem("Fuse Time", Material.GUNPOWDER, BeastModeData.TNTBowfuseTime));
        menu.setItem(2, createValueItem("Cooldown", Material.CLOCK, BeastModeData.TNTBowCoolDown));
        menu.setItem(3, createValueItem("Max Damage", Material.NETHERITE_SWORD, BeastModeData.TNTBowMaxDamage));
        menu.setItem(4, createToggleItem("Glowing TNT", Material.GLOW_INK_SAC, BeastModeData.TNTBowGlowingTnt));
        menu.setItem(5, createToggleItem("Keep TNTBow when death", Material.SKELETON_SKULL, BeastModeData.TNTBowKeepOnDeath));
        menu.setItem(8, back);

        player.openInventory(menu);
    }

    public static void openDorkMenu(Player player) {
        Inventory menu = Bukkit.createInventory(player, 9, BeastmodeDorkMenu);
        // InvisDye - gives on death
        // TNTBow
        // TNTEggBow
        // PunishSneaking
        // MegaMiningPickaxe
        // Sticks
        // WoolGameBoots
        // NPCs2026
        menu.setItem(0, createToggleItem("Invisibility Cloak", Material.WHITE_DYE, BeastModeData.useInvisibilityCloak));
        menu.setItem(1, createToggleItem("TNT Bow", Material.TNT, BeastModeData.useTNTBow));
        menu.setItem(2, createToggleItem("TNT Egg Bow", Material.EGG, BeastModeData.useTNTEggBow));
        menu.setItem(3, createToggleItem("WoolGameBoots (disabled)", Material.PINK_WOOL, BeastModeData.useWoolGameBoots));
        menu.setItem(8, back);
        player.openInventory(menu);
    }


    public static ItemStack createToggleItem(String name, Material material, boolean state) {
		ItemStack item = new ItemStack(material);
		ItemMeta meta = item.getItemMeta();
		meta.setItemName(name);
        meta.lore(Arrays.asList(
                Component.text("§7Current State: " + (state ? "§aEnabled" : "§cDisabled")),
                Component.text("§7Click to toggle!")
        ));
		item.setItemMeta(meta);
		return item;
	}

    public static ItemStack createToggleItem(Component name, Material material, boolean state) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.itemName(name);
        meta.lore(Arrays.asList(
                Component.text("§7Current State: " + (state ? "§aEnabled" : "§cDisabled")),
                Component.text("§7Click to toggle!")
        ));
        item.setItemMeta(meta);
        return item;
    }

	public static ItemStack createValueItem(String name, Material material, int value) {
		ItemStack item = new ItemStack(material);
		ItemMeta meta = item.getItemMeta();
		meta.setItemName(name);
		meta.setLore(Arrays.asList(
				ChatColor.GRAY + "Current Value: " + ChatColor.AQUA + value,
				ChatColor.GRAY + "Right-click to increase, Left-click to decrease"
				));
		item.setItemMeta(meta);
		return item;
	}

    public static ItemStack createValueItem(String name, Material material, float value) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setItemName(name);
        meta.setLore(Arrays.asList(
                ChatColor.GRAY + "Current Value: " + ChatColor.AQUA + value,
                ChatColor.GRAY + "Right-click to increase, Left-click to decrease"
        ));
        item.setItemMeta(meta);
        return item;
    }


	public static ItemStack createDifficultyItem(Component name, Difficulty difficulty) {
		ItemStack item = new ItemStack(Material.WITHER_SKELETON_SKULL);
		ItemMeta meta = item.getItemMeta();
		meta.itemName(name);
		meta.setLore(Arrays.asList(
				ChatColor.GRAY + "Current Mode: " + ChatColor.AQUA + difficulty.name(),
				ChatColor.GRAY + "Click to cycle through difficulty"
				));
		item.setItemMeta(meta);
		return item;
	}


	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (!(event.getWhoClicked() instanceof Player player)) return;

        if (!event.getView().getTitle().equals(BeastmodeMenu) && !event.getView().title().equals(BeastmodeGameRuleMenu) && !event.getView().title().equals(BeastmodeTNTbowMenu) && !event.getView().title().equals(BeastmodeDorkMenu)) return;

		event.setCancelled(true);
		ItemStack clicked = event.getCurrentItem();
		if (clicked == null || !clicked.hasItemMeta()) return;

		String displayName = clicked.getItemMeta().getItemName();
		switch (displayName) {

            case "Minken Edition Rules":
                BeastModeData.minkenEditionThusFalseIsOriginalRules = !BeastModeData.minkenEditionThusFalseIsOriginalRules;
                break;
            case "Free Start Bow":
                BeastModeData.freeStartBow = !BeastModeData.freeStartBow;
                break;
            case "Free Start Blocks":
                BeastModeData.freeStartBlocks = !BeastModeData.freeStartBlocks;
                break;
            case "Return death items":
                BeastModeData.returnDeathItems = !BeastModeData.returnDeathItems;
                break;
            case "Beds can be picked up":
                BeastModeData.bedsCanBePickedUp = !BeastModeData.bedsCanBePickedUp;
                break;
            case "Super Snowball":
                BeastModeData.includeSuperSnowball = !BeastModeData.includeSuperSnowball;
                break;

            case "Shield price (dia)":
                adjustValue(event, player, "Shield price (dia)");
                break;
            case "WaterBucket price":
                adjustValue(event, player, "WaterBucket price");
                break;
            case "Enchanted Golden Apple price":
                adjustValue(event, player, "Enchanted Golden Apple price");
                break;
            case "EnderPearl price":
                adjustValue(event, player, "EnderPearl price");
                break;
            case "AddeBow price":
                adjustValue(event, player, "AddeBow price");
                break;

            case "Golds for 1 Dia":
                adjustValue(event, player, "Golds for 1 Dia");
                break;
            case "Mace price":
                adjustValue(event, player, "Mace price");
                break;
            case "Wind Charge price (gold)":
                adjustValue(event, player, "Wind Charge price (gold)");
                break;

            case "Start HP":
                adjustValue(event, player, "Start HP");
                break;
            case "Start foodlevel":
                adjustValue(event, player, "Start foodlevel");
                break;
            case "Middle Diamond Drop delay (seconds)":
                adjustValue(event, player, "Middle Diamond Drop delay (seconds)");
                break;

            // TNT BOW
            case "TNT Bow settings":
                player.closeInventory();
                openTNTBow(player);
                return;
            case "Glowing TNT":
                BeastModeData.TNTBowGlowingTnt = !BeastModeData.TNTBowGlowingTnt;
                break;
            case "Explosion Power":
                adjustValue(event, player, "Explosion Power");
                break;
            case "Fuse Time":
                adjustValue(event, player, "Fuse Time");
                break;
            case "Cooldown":
                adjustValue(event, player, "Cooldown");
                break;
            case "Max Damage":
                adjustValue(event, player, "Max Damage");
                break;
            case "Keep TNTBow when death":
                BeastModeData.TNTBowKeepOnDeath = !BeastModeData.TNTBowKeepOnDeath;
                break;
            case "Selected map":
                cycleMap();
                break;
            case "Glow after":
                cycleGlowTime();
                break;
            // DorkMenu :)
            case "Dork settings":
                player.closeInventory();
                openDorkMenu(player);
                return;

            // Dork settings
            case "DorkMode":
                BeastModeData.dorkMode = !BeastModeData.dorkMode;
                break;
            case "Invisibility Cloak":
                BeastModeData.useInvisibilityCloak = !BeastModeData.useInvisibilityCloak;
                break;
            case "TNT Bow":
                BeastModeData.useTNTBow = !BeastModeData.useTNTBow;
                break;
            case "TNT Egg Bow":
                BeastModeData.useTNTEggBow = !BeastModeData.useTNTEggBow;
                break;
            case "WoolGameBoots (disabled)":
                // BeastModeData.useWoolGameBoots = !BeastModeData.useWoolGameBoots;
                break;

            // GameRules
            case "§fGameRule":
                player.closeInventory();
                openGameRuleMenu(player);
                return;
            case "Spawn mob":
                BeastModeData.SpawnMobs = !BeastModeData.SpawnMobs;
                break;
            case "§fAnnounce Advancements":
                BeastModeData.AnnounceAdvancements = !BeastModeData.AnnounceAdvancements;
                break;
            case "§fDifficulty":
                cycleDifficulty();
                WORLD.setDifficulty(BeastModeData.Difficulty);
                break;
            case "Fall Damage":
                BeastModeData.FallDamage = !BeastModeData.FallDamage;
                break;
            case "Fire Damage":
                BeastModeData.FireDamage = !BeastModeData.FireDamage;
                break;
            case "Keep Inventory":
                BeastModeData.KeepInventory = !BeastModeData.KeepInventory;
                break;
            case "Locatorbar":
                BeastModeData.Locatorbar = !BeastModeData.Locatorbar;
                break;
            case "Natural Regeneration":
                BeastModeData.NaturalRegeneration = !BeastModeData.NaturalRegeneration;
                break;

            case "§fBack":
                player.closeInventory();
                openAdminMenu(player);
                return;
            case "Start game":
                // player.sendMessage(ChatColor.GREEN + "Starting the game..."); // Do this later.
                player.closeInventory();
                StartGame.startaSpelet();
                break;
        }

        if (displayName.equals("Start game")) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    player.closeInventory(); // Explicitly close the inventory for "Start game"
                }
            }.runTaskLater(plugin, 5L);
        }
        else if (event.getView().title().equals(BeastmodeTNTbowMenu)) {
            openTNTBow(player);
        }
        else if (event.getView().title().equals(BeastmodeGameRuleMenu)) {
            openGameRuleMenu(player);
        }
        else if (event.getView().title().equals(BeastmodeDorkMenu)) {
            openDorkMenu(player);
        }
        else {
            openAdminMenu(player); // Refresh menu unless "Start Game" is clicked
        }
    }

	public void adjustValue(InventoryClickEvent event, Player player, String settingName) {
		boolean increase = event.isRightClick();
        boolean shift = event.isShiftClick();
        // TODO: add  && ((increase && BeastModeData.TNTBowExplosionPower < 118) || (!increase && BeastModeData.TNTBowExplosionPower > 10)) till alla adjustvalue
		switch (settingName) {
            case "Shield price (dia)":
                if ((increase && BeastModeData.diaForShield < 20) || (!increase && BeastModeData.diaForShield > 1)) {
                    if (shift && ((increase && BeastModeData.diaForShield < 10) || (!increase && BeastModeData.diaForShield > 10))) {
                        BeastModeData.diaForShield += increase ? 10 : -10;
                    }
                    else {
                        BeastModeData.diaForShield += increase ? 1 : -1;
                    }
                }
                break;
            case "WaterBucket price":
                if ((increase && BeastModeData.diaForWaterBucket < 20) || (!increase && BeastModeData.diaForWaterBucket > 1)) {
                    if (shift && ((increase && BeastModeData.diaForWaterBucket < 10) || (!increase && BeastModeData.diaForWaterBucket > 10))) {
                        BeastModeData.diaForWaterBucket += increase ? 10 : -10;
                    }
                    else {
                        BeastModeData.diaForWaterBucket += increase ? 1 : -1;
                    }
                }
                break;
            case "Enchanted Golden Apple price":
                if ((increase && BeastModeData.diaForSuperGoldenApple < 20) || (!increase && BeastModeData.diaForSuperGoldenApple > 1)) {
                    if (shift && ((increase && BeastModeData.diaForSuperGoldenApple < 10) || (!increase && BeastModeData.diaForSuperGoldenApple > 10))) {
                        BeastModeData.diaForSuperGoldenApple += increase ? 10 : -10;
                    }
                    else {
                        BeastModeData.diaForSuperGoldenApple += increase ? 1 : -1;
                    }
                }
                break;
            case "EnderPearl price":
                if ((increase && BeastModeData.diaForEnderPearl < 20) || (!increase && BeastModeData.diaForEnderPearl > 1)) {
                    if (shift && ((increase && BeastModeData.diaForEnderPearl < 10) || (!increase && BeastModeData.diaForEnderPearl > 10))) {
                        BeastModeData.diaForEnderPearl += increase ? 10 : -10;
                    }
                    else {
                        BeastModeData.diaForEnderPearl += increase ? 1 : -1;
                    }
                }
                break;
            case "AddeBow price":
                if ((increase && BeastModeData.diaForAddeBow < 20) || (!increase && BeastModeData.diaForAddeBow > 1)) {
                    if (shift && ((increase && BeastModeData.diaForAddeBow < 10) || (!increase && BeastModeData.diaForAddeBow > 10))) {
                        BeastModeData.diaForAddeBow += increase ? 10 : -10;
                    }
                    else {
                        BeastModeData.diaForAddeBow += increase ? 1 : -1;
                    }
                }
                break;

            case "Golds for 1 Dia":
                if ((increase && BeastModeData.goldsForOneDia < 20) || (!increase && BeastModeData.goldsForOneDia > 1)) {
                    if (shift && ((increase && BeastModeData.goldsForOneDia < 10) || (!increase && BeastModeData.goldsForOneDia > 10))) {
                        BeastModeData.goldsForOneDia += increase ? 10 : -10;
                    }
                    else {
                        BeastModeData.goldsForOneDia += increase ? 1 : -1;
                    }
                }
                break;
            case "Mace price":
                if ((increase && BeastModeData.diaForMace < 20) || (!increase && BeastModeData.diaForMace > 1)) {
                    if (shift && ((increase && BeastModeData.diaForMace < 10) || (!increase && BeastModeData.diaForMace > 10))) {
                        BeastModeData.diaForMace += increase ? 10 : -10;
                    }
                    else {
                        BeastModeData.diaForMace += increase ? 1 : -1;
                    }
                }
                break;
            case "Wind Charge price (gold)":
                if ((increase && BeastModeData.goldForWindCharge < 20) || (!increase && BeastModeData.goldForWindCharge > 1)) {
                    if (shift && ((increase && BeastModeData.goldForWindCharge < 10) || (!increase && BeastModeData.goldForWindCharge > 10))) {
                        BeastModeData.goldForWindCharge += increase ? 10 : -10;
                    }
                    else {
                        BeastModeData.goldForWindCharge += increase ? 1 : -1;
                    }
                }
                break;

            case "Start HP":
                if ((increase && BeastModeData.startHP < 20) || (!increase && BeastModeData.startHP > 1)) {
                    if (shift && ((increase && BeastModeData.startHP < 10) || (!increase && BeastModeData.startHP > 10))) {
                        BeastModeData.startHP += increase ? 10 : -10;
                    }
                    else {
                        BeastModeData.startHP += increase ? 1 : -1;
                    }
                }
                break;
            case "Start foodlevel":
                if ((increase && BeastModeData.startFoodlevel < 20) || (!increase && BeastModeData.startFoodlevel > 0)) {
                    if (shift && ((increase && BeastModeData.startFoodlevel < 10) || (!increase && BeastModeData.startFoodlevel > 9))) {
                        BeastModeData.startFoodlevel += increase ? 10 : -10;
                    }
                    else {
                        BeastModeData.startFoodlevel += increase ? 1 : -1;
                    }
                }
                break;
            case "Middle Diamond Drop delay (seconds)":
                if ((increase && BeastModeData.middleDiamondDropDelayInSeconds < 180) || (!increase && BeastModeData.middleDiamondDropDelayInSeconds > 1)) {
                    if (shift && ((increase && BeastModeData.middleDiamondDropDelayInSeconds < 170) || (!increase && BeastModeData.middleDiamondDropDelayInSeconds > 10))) {
                        BeastModeData.middleDiamondDropDelayInSeconds += increase ? 10 : -10;
                    }
                    else {
                        BeastModeData.middleDiamondDropDelayInSeconds += increase ? 1 : -1;
                    }

                }
                break;
            case "Explosion Power":
                if ((increase && BeastModeData.TNTBowExplosionPower < 128) || (!increase && BeastModeData.TNTBowExplosionPower > 0)) {
                    if (shift && ((increase && BeastModeData.TNTBowExplosionPower < 118) || (!increase && BeastModeData.TNTBowExplosionPower > 10))) {
                        BeastModeData.TNTBowExplosionPower += increase ? 10 : -10;
                    }
                    else {
                        BeastModeData.TNTBowExplosionPower += increase ? 1 : -1;
                    }

                }
                break;
            case "Fuse Time":
                if ((increase && BeastModeData.TNTBowfuseTime < 180) || (!increase && BeastModeData.TNTBowfuseTime > 0)) {
                    if (shift && ((increase && BeastModeData.TNTBowfuseTime < 170) || (!increase && BeastModeData.TNTBowfuseTime > 9))) {
                        BeastModeData.TNTBowfuseTime += increase ? 10 : -10;
                    }
                    else {
                        BeastModeData.TNTBowfuseTime += increase ? 1 : -1;
                    }

                }
                break;
            case "Cooldown":
                if ((increase && BeastModeData.TNTBowCoolDown < 180) || (!increase && BeastModeData.TNTBowCoolDown > 0)) {
                    if (shift && ((increase && BeastModeData.TNTBowCoolDown < 170) || (!increase && BeastModeData.TNTBowCoolDown > 9))) {
                        BeastModeData.TNTBowCoolDown += increase ? 10 : -10;
                    }
                    else {
                        BeastModeData.TNTBowCoolDown += increase ? 1 : -1;
                    }

                }
                break;
            case "Max Damage":
                if ((increase && BeastModeData.TNTBowMaxDamage < 100) || (!increase && BeastModeData.TNTBowMaxDamage > 0)) {
                    if (shift && ((increase && BeastModeData.TNTBowMaxDamage < 90) || (!increase && BeastModeData.TNTBowMaxDamage > 9))) {
                        BeastModeData.TNTBowMaxDamage += increase ? 10 : -10;
                    }
                    else {
                        BeastModeData.TNTBowMaxDamage += increase ? 1 : -1;
                    }

                }
                break;
        }
	}

    public void cycleDifficulty() {
        List<Difficulty> difficulties = List.of(
                Difficulty.EASY,
                Difficulty.NORMAL,
                Difficulty.HARD
        );

        int DifficultyIndex = difficulties.indexOf(BeastModeData.Difficulty) + 1;
        if (DifficultyIndex >= 3) {
            DifficultyIndex = 0;
        }

        BeastModeData.Difficulty = difficulties.get(DifficultyIndex);
    }

    public void cycleMap() {
        BeastModeData.selectedMapindex++;
        if (BeastModeData.selectedMapindex >= BeastModeData.Maps.size()) {
            BeastModeData.selectedMapindex = 0;
        }
        log(String.valueOf(BeastModeData.selectedMapindex));
    }

    private static ItemStack uppdateMapItem() {
        ItemStack item = new ItemStack(Material.FILLED_MAP);
        ItemMeta meta = item.getItemMeta();
        meta.setItemName("Selected map");
        List<String> Lore = new ArrayList<>();
        for (int i = 0; i < BeastModeData.Maps.size(); i++) {
            if (BeastModeData.selectedMapindex == i) {
                Lore.add(ChatColor.AQUA + BeastModeData.Maps.get(i));
            }
            else {
                Lore.add(ChatColor.GRAY + BeastModeData.Maps.get(i));
            }
        }
        meta.setLore(Lore);

        item.setItemMeta(meta);
        return item;
    }



    public void cycleGlowTime() {
        BeastModeData.selectedGlowtime++;
        if (BeastModeData.selectedGlowtime >= BeastModeData.GlowTimeLore.size()) {
            BeastModeData.selectedGlowtime = 0;
        }

    }

    private static ItemStack uppdateGlowItem() {
        ItemStack item = new ItemStack(Material.GLOW_INK_SAC);
        ItemMeta meta = item.getItemMeta();
        meta.setItemName("Glow after");
        List<String> Lore = new ArrayList<>();
        for (int i = 0; i < BeastModeData.GlowTimeLore.size(); i++) {
            if (BeastModeData.selectedGlowtime == i) {
                Lore.add(ChatColor.AQUA + BeastModeData.GlowTimeLore.get(i));
            }
            else {
                Lore.add(ChatColor.GRAY + BeastModeData.GlowTimeLore.get(i));
            }
        }
        meta.setLore(Lore);

        item.setItemMeta(meta);
        return item;
    }



	@EventHandler
	public void onPlayerRespawnEvent (PlayerRespawnEvent event) {
		Player player = event.getPlayer();

		new BukkitRunnable() {
			@Override
			public void run() {
				// player.getInventory().addItem(new ItemStack(Material.CHEST, 1)); // PREVENTS ADDE-ABUSE (die, throw out chest, die, ...)
				//				player.getInventory().addItem(bow);
				//				player.getInventory().addItem(new ItemStack(Material.ARROW, 1));
				//				player.getInventory().addItem(new ItemStack(Material.PINK_WOOL, 48));
                //				player.getInventory().addItem(new ItemStack(Material.DIRT, 1));

                if (BeastModeData.glowEnabled) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, Integer.MAX_VALUE, 1));
                }
			}
		}.runTaskLater(plugin, 5); // 2
	}


    @EventHandler
    public void onInventoryCloseEvent(InventoryCloseEvent event) {
        //		if (menuRunner.contains(event.getPlayer())) {
        //			menuRunner.remove(event.getPlayer());
        //		}

        if (!event.getView().getTitle().equals(BeastmodeMenu) && !event.getView().title().contains(BeastmodeTNTbowMenu) && !event.getView().title().contains(BeastmodeGameRuleMenu)) {
            return;
        }

        if (menuRunner) { menuRunner = false; }
    }

}