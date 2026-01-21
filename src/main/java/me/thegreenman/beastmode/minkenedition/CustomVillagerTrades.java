package me.thegreenman.beastmode.minkenedition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import me.thegreenman.Main;
import me.thegreenman.beastmode.BeastModeData;
import me.thegreenman.beastmode.VillagersTrades;

public class CustomVillagerTrades extends VillagersTrades {
    public static ItemStack jump = new ItemStack(Material.POTION);
    
	//	Color leatherColor = Color.BLACK;

	//	ItemStack trident = new ItemStack(Material.TRIDENT, 1);
	//	trident.addEnchantment(Enchantment.LOYALTY, 3);

	//	public static ItemStack trident = new ItemStack(Material.TRIDENT, 1) {{
	//		ItemMeta meta = this.getItemMeta();
	//		if (meta != null) {
	////			meta.setDisplayName("Startbåge");
	//			meta.addEnchant(Enchantment.LOYALTY, 3, false);
	////			meta.setLore((Arrays.asList("Den", "fina", "startbågen")));
	//			this.setItemMeta(meta);
	//		}
	//	}};

	public CustomVillagerTrades() {
	}

	public static void spawnTheVillagers() {
		// Spawna Villagern vid världens spawnplats
		Bukkit.getScheduler().runTaskLater(Main.plugin, CustomVillagerTrades::spawnCustomVillagerBow, 5L); // Fördröjning för att säkerställa att världen är laddad
		Bukkit.getScheduler().runTaskLater(Main.plugin, CustomVillagerTrades::spawnCustomVillagerSword, 10L);
		Bukkit.getScheduler().runTaskLater(Main.plugin, CustomVillagerTrades::spawnCustomVillagerArmor, 15L);
		Bukkit.getScheduler().runTaskLater(Main.plugin, CustomVillagerTrades::spawnCustomVillagerFood, 20L);
		Bukkit.getScheduler().runTaskLater(Main.plugin, CustomVillagerTrades::spawnCustomVillagerBlocks, 25L);
		Bukkit.getScheduler().runTaskLater(Main.plugin, CustomVillagerTrades::spawnCustomVillagerPotionsplatsen, 30L);

		Bukkit.getScheduler().runTaskLater(Main.plugin, CustomVillagerTrades::placeTheMiddleNPCWithCustomSettings, 35L);

		// Bukkit.getScheduler().runTaskLater(Main.plugin, this::spawnCustomVillagerInTheMiddle, 100L); // FLYTTAD TILL ANNAN KLASS (ADMINMENUTMD)
		// Bukkit.getScheduler().runTaskLater(Main.plugin, this::spawnCustomVillagerInTheMiddleBelow, 110L);
	}

	public static void spawnCustomVillagerBow() {
		// Hämta världen
		var world = Bukkit.getWorld("world"); // Justera "world" om din värld heter något annat

		if (world == null) {
			Main.log("[CustomVillagerTrades] Världen world hittades inte!");
			return;
		}


		for (int i=1; i<5; i++) {


			// Spawna Villagern
			Villager villager = (Villager) world.spawnEntity(BeastModeData.villagerBowLocation[i-1], EntityType.VILLAGER);
            if (Main.DEBUG) {
                Main.log("Spawaning Bow-NPC at " + villager.getLocation());
            }
			villager.setProfession(Villager.Profession. FLETCHER); // FLETCHER är mer lämplig för bågar
			villager.setVillagerLevel(2); // Sätt Villager-nivån till 2
			//			villager.setCustomName("Bow Merchant");
			//			villager.setCustomNameVisible(true);
			villager.setAI(false);
			villager.setInvulnerable(true);


			// Skapa handelsalternativen
			List<MerchantRecipe> recipes = new ArrayList<>();

			// Trade 1: Bow med Infinity
			recipes.add(createRecipe(
					createBow("Level 1", "Level 1", new Enchantment[]{Enchantment.INFINITY}, new int[]{1}),
					Material.IRON_INGOT, 1
					));

			//			// Trade 1: Bow med Infinity
			//			recipes.add(createRecipe(
			//					createBow("Level 1", "Level 1", new Enchantment[]{Enchantment.INFINITY}, new int[]{1}),
			////					Material.GOLD_INGOT, 2
			//					Material.GOLD_INGOT, 1
			//					));

			// Trade 2: Bow med Infinity och Power 1
			recipes.add(createRecipe(
					createBow("Level 2", "Level 2", new Enchantment[]{Enchantment.INFINITY, Enchantment.POWER}, new int[]{1, 1}),
					Material.GOLD_INGOT, 6
					));

			// Trade 3: Bow med Infinity och Power 2
			recipes.add(createRecipe(
					createBow("Level 3", "Level 3", new Enchantment[]{Enchantment.INFINITY, Enchantment.POWER}, new int[]{1, 2}),
					Material.GOLD_INGOT, 12
					));

			// Trade 4: Bow med Infinity, Power 2 och Punch 1
			recipes.add(createRecipe(
					createBow("Level 4", "Level 4", new Enchantment[]{Enchantment.INFINITY, Enchantment.POWER, Enchantment.PUNCH}, new int[]{1, 2, 1}),
					Material.GOLD_INGOT, 24
					));

			// Trade 5: xxx
			recipes.add(createRecipe(
					//								new ItemStack (Material.ARROW, 1), Material.GOLD_INGOT, 1));
					new ItemStack (Material.ARROW, 1), Material.BRICK, 1));

			// Trade 6: xxx
			recipes.add(createRecipe(
					new ItemStack (Material.LADDER, 3), Material.IRON_INGOT, 1));

			// Trade 7: xxx
			recipes.add(createRecipe(
					new ItemStack (Material.COBWEB, 3), Material.IRON_INGOT, 1));

			// Tilldela Villagern recepten
			villager.setRecipes(recipes);
		}
	}


	public static void spawnCustomVillagerSword() {
		// Hämta världen
		var world = Bukkit.getWorld("world"); // Justera "world" om din värld heter något annat

		if (world == null) {
			Main.log("[CustomVillagerTrades] Världen world hittades inte!");
			return;
		}



		for (int i=1; i<5; i++) {

			// Spawna Villagern
			Villager villager = (Villager) world.spawnEntity(BeastModeData.villagerSwordLocation[i-1], EntityType.VILLAGER);
            if (Main.DEBUG) {
                Main.log("Spawaning Sword-NPC at " + villager.getLocation());
            }
			villager.setProfession(Villager.Profession.WEAPONSMITH); // INGENKOLL är mer lämplig för svärd
			villager.setVillagerLevel(2); // Sätt Villager-nivån till 2
			//			villager.setCustomName("Sword Merchant");
			//			villager.setCustomNameVisible(true);
			villager.setAI(false);
			villager.setInvulnerable(true);
            Main.log("villager spawn at " + villager.getLocation());


			// Skapa handelsalternativen
			List<MerchantRecipe> recipes = new ArrayList<>();

			// Trade 1: Bow med Infinity
			recipes.add(createRecipe(
					createSword("Level 1", null, null),
					Material.IRON_INGOT, 2
					));

			// Trade 2: Bow med Infinity och Power 1
			recipes.add(createRecipe(
					createSword("Level 2", new Enchantment[]{Enchantment.SHARPNESS}, new int[]{1}),
					Material.IRON_INGOT, 8
					));

			// Trade 3: Bow med Infinity och Power 2
			recipes.add(createRecipe(
					createSword("Level 3", new Enchantment[]{Enchantment.SHARPNESS}, new int[]{2}),
					Material.IRON_INGOT, 16
					));

			// Trade 4: Bow med Infinity, Power 2 och Punch 1
			recipes.add(createRecipe(
					createSword("Level 4", new Enchantment[]{Enchantment.SHARPNESS, Enchantment.KNOCKBACK}, new int[]{2, 1}),
					Material.IRON_INGOT, 48
					));

			// Trade 5: xxx
			recipes.add(createRecipe(
					new ItemStack (Material.TNT, 1), Material.IRON_INGOT, 6));

			// Trade 6: xxx
			recipes.add(createRecipe(
					new ItemStack (Material.FLINT_AND_STEEL, 1), Material.GOLD_INGOT, 1));

			// Trade 7: Minken testar med Shield
			//			ItemStack shield = new ItemStack(Material.SHIELD, 1);
			//			ItemMeta shieldMeta = shield.getItemMeta();
			//			shieldMeta.setDisplayName("Sköld");
			//			shield.setItemMeta(shieldMeta);
			//			recipes.add(createRecipe(
			//					new ItemStack (shield), Material.TNT, 1));

			// Tilldela Villagern recepten
			villager.setRecipes(recipes);
		}
	}

	public static void spawnCustomVillagerArmor() {
		// Hämta världen
		var world = Bukkit.getWorld("world"); // Justera "world" om din värld heter något annat

		if (world == null) {
			Main.log("[CustomVillagerTrades] Världen world hittades inte!");
			return;
		}


		for (int i=1; i<5; i++) {


			// Spawna Villagern
			Villager villager = (Villager) world.spawnEntity(BeastModeData.villagerArmorLocation[i-1], EntityType.VILLAGER);
            if (Main.DEBUG) {
                Main.log("Spawaning Armor-NPC at " + villager.getLocation());
            }
			villager.setProfession(Villager.Profession.ARMORER);
			villager.setVillagerLevel(2); // Sätt Villager-nivån till 2
			// villager.setCustomName("Armor Merchant");
			// villager.setCustomNameVisible(true);
			villager.setAI(false);
			villager.setInvulnerable(true);


			// Skapa handelsalternativen
			List<MerchantRecipe> recipes = new ArrayList<>();


			// GRÖNIS

            int[] colorRGB = new int[]{255, 255, 255};
            colorRGB = switch (i) {
                case 1 -> parseHexColor("5E7C16");
                case 2 -> parseHexColor("B02E26");
                case 3 -> parseHexColor("3C44AA");
                case 4 -> parseHexColor("F38BAA");
                default -> colorRGB;
            };

			Color leatherColor = Color.fromRGB(colorRGB[0], colorRGB[1], colorRGB[2]);

			// /GRÖNIS


			recipes.add(createRecipe(
					createLeatherArmor(leatherColor, "Level 1", Material.LEATHER_HELMET, 1, new Enchantment[]{Enchantment.PROTECTION}, new int[]{1}),
					Material.BRICK, 1
					));

			recipes.add(createRecipe(
					createLeatherArmor(leatherColor, "Level 1", Material.LEATHER_CHESTPLATE, 1, new Enchantment[]{Enchantment.PROTECTION}, new int[]{1}),
					Material.BRICK, 1
					));

			recipes.add(createRecipe(
					createLeatherArmor(leatherColor, "Level 1", Material.LEATHER_LEGGINGS, 1, new Enchantment[]{Enchantment.PROTECTION}, new int[]{1}),
					Material.BRICK, 1
					));

			recipes.add(createRecipe(
					createLeatherArmor(leatherColor, "Level 1", Material.LEATHER_BOOTS, 1, new Enchantment[]{Enchantment.PROTECTION}, new int[]{1}),
					Material.BRICK, 1
					));

			recipes.add(createRecipe(
					// new ItemStack (Material.CHAINMAIL_CHESTPLATE, 1), Material.IRON_INGOT, 2));
					createChainmailArmor("Level 1", Material.CHAINMAIL_CHESTPLATE, 1, new Enchantment[]{Enchantment.PROTECTION}, new int[]{1}),
					Material.IRON_INGOT, 2
					));

			recipes.add(createRecipe(
					createChainmailArmor("Level 2", Material.CHAINMAIL_CHESTPLATE, 1, new Enchantment[]{Enchantment.PROTECTION}, new int[]{2}),
					Material.IRON_INGOT, 8
					));

			recipes.add(createRecipe(
					createChainmailArmor("Level 3", Material.CHAINMAIL_CHESTPLATE, 1, new Enchantment[]{Enchantment.PROTECTION}, new int[]{3}),
					Material.IRON_INGOT, 16
					));

			recipes.add(createRecipe(
					createChainmailArmor("Level 4", Material.CHAINMAIL_CHESTPLATE, 1, new Enchantment[]{Enchantment.PROTECTION}, new int[]{5}),
					Material.IRON_INGOT, 48
					));

			villager.setRecipes(recipes);
		}
	}

	public static void spawnCustomVillagerFood() {
		// Hämta världen
		var world = Bukkit.getWorld("world"); // Justera "world" om din värld heter något annat

		if (world == null) {
			Main.log("[CustomVillagerTrades] Världen world hittades inte!");
			return;
		}


		for (int i=1; i<5; i++) {


			// Spawna Villagern
			Villager villager = (Villager) world.spawnEntity(BeastModeData.villagerFoodLocation[i-1], EntityType.VILLAGER);
            if (Main.DEBUG) {
                Main.log("Spawaning Food-NPC at " + villager.getLocation());
            }
			villager.setProfession(Villager.Profession.FARMER); // INGENKOLL är mer lämplig för svärd
			villager.setVillagerLevel(2); // Sätt Villager-nivån till 2
			//			villager.setCustomName("Food Merchant");
			//			villager.setCustomNameVisible(true);
			villager.setAI(false);
			villager.setInvulnerable(true);


			// Skapa handelsalternativen
			List<MerchantRecipe> recipes = new ArrayList<>();

			//			recipes.add(createRecipe(
			//					new ItemStack (Material.APPLE, 4),
			//					Material.BRICK, 16
			//					));

			recipes.add(createRecipe(
					new ItemStack (Material.COOKIE, 8),
					Material.BRICK, 16
					));

			recipes.add(createRecipe(
					new ItemStack (Material.COOKED_BEEF, 2),
					Material.BRICK, 32
					));

			recipes.add(createRecipe(
					new ItemStack (Material.GOLDEN_APPLE, 1),
					Material.IRON_INGOT, 3
					));

			recipes.add(createRecipe(
					new ItemStack (Material.IRON_INGOT, 1),
					Material.BRICK, 64
					));

			recipes.add(createRecipe(
					new ItemStack (Material.GOLD_INGOT, 1),
					Material.IRON_INGOT, 16
					));

			villager.setRecipes(recipes);
		}
	}

	public static void spawnCustomVillagerBlocks() {
		// Hämta världen
		var world = Bukkit.getWorld("world"); // Justera "world" om din värld heter något annat

		if (world == null) {
			Main.log("[CustomVillagerTrades] Världen world hittades inte!");
			return;
		}



		for (int i=1; i<5; i++) {

			// Spawna Villagern
			Villager villager = (Villager) world.spawnEntity(BeastModeData.villagerBlocksLocation[i-1], EntityType.VILLAGER);
            if (Main.DEBUG) {
                Main.log("Spawaning Blocks-NPC at " + villager.getLocation());
            }
			villager.setProfession(Villager.Profession.MASON); // INGENKOLL är mer lämplig för svärd
			villager.setVillagerLevel(2); // Sätt Villager-nivån till 2
			//			villager.setCustomName("Block Merchant");
			//			villager.setCustomNameVisible(true);
			villager.setAI(false);
			villager.setInvulnerable(true);
            Main.log("villager spawn at " + villager.getLocation());


			// Skapa handelsalternativen
			List<MerchantRecipe> recipes = new ArrayList<>();

			recipes.add(createRecipe(
					new ItemStack (Material.NETHERRACK, 32),
					Material.BRICK, 8
					));

			// REMOVED SINCE NO ONE EVER USES IT
			recipes.add(createRecipe(
					new ItemStack (Material.STONE_BRICKS, 16),
					Material.BRICK, 16
					));

			// REMOVED SINCE NO ONE EVER USES IT
			recipes.add(createRecipe(
					new ItemStack (Material.GLASS, 8),
					Material.BRICK, 16
					));

			recipes.add(createRecipe(
					new ItemStack (Material.HONEYCOMB_BLOCK, 8),
					Material.BRICK, 16
					));
			
			recipes.add(createRecipe(
					new ItemStack (Material.HONEY_BLOCK, 4),
					Material.IRON_INGOT, 1
					));

			recipes.add(createRecipe(
					new ItemStack (Material.WOODEN_PICKAXE, 1),
					Material.BRICK, 6
					));

			//			ItemStack ironpickeff1 = new ItemStack(Material.IRON_PICKAXE, 1);
			//			ItemMeta ironpickeff1Meta = ironpickeff1.getItemMeta();
			//			//			ironpickeff1Meta.setDisplayName("Minken testar");
			//			ironpickeff1Meta.addEnchant(Enchantment.EFFICIENCY, 1, false);
			//			ironpickeff1.setItemMeta(ironpickeff1Meta);
			//			recipes.add(createRecipe(ironpickeff1, Material.IRON_INGOT, 3));

			ItemStack goldpickeff3 = new ItemStack(Material.GOLDEN_PICKAXE, 1);
			ItemMeta goldpickeff3Meta = goldpickeff3.getItemMeta();
			//			goldpickeff3Meta.setDisplayName("Minken testar");
			goldpickeff3Meta.addEnchant(Enchantment.EFFICIENCY, 3, false);
			goldpickeff3.setItemMeta(goldpickeff3Meta);
			recipes.add(createRecipe(goldpickeff3, Material.GOLD_INGOT, 1));

			// ADDE abuseade och byggde in hela basen i fem miljarder LÅDOR. Därför får varje spelare EN låda vid /start (efter kort delay).
			//			recipes.add(createRecipe(
			//					new ItemStack (Material.CHEST, 1),
			////					Material.IRON_INGOT, 1
			//					Material.BRICK, 1
			//					));

			villager.setRecipes(recipes);
		}
	}
	
	public static void spawnCustomVillagerPotionsplatsen() {
		// Hämta världen
		var world = Bukkit.getWorld("world"); // Justera "world" om din värld heter något annat

		if (world == null) {
			Main.log("[CustomVillagerTrades] Världen world hittades inte!");
			return;
		}


		for (int i=1; i<5; i++)
		{

			// Spawna Villagern
			Villager villager = (Villager) world.spawnEntity(BeastModeData.villagerPotionLocation[i-1], EntityType.VILLAGER);
            if (Main.DEBUG) {
                Main.log("Spawaning Potionsplatsen-NPC at " + villager.getLocation());
            }
			villager.setProfession(Villager.Profession.BUTCHER);
			villager.setVillagerLevel(2); // Sätt Villager-nivån till 2
//			villager.setCustomName("Broken :("); // NOT ANYMORE :D
//			villager.setCustomNameVisible(true);
			villager.setAI(false);
			villager.setInvulnerable(true);
//
//			PotionMeta speed2Meta = (PotionMeta) speed2.getItemMeta();
//			speed2Meta.setBasePotionType(PotionType.SWIFTNESS);
//			speed2.setItemMeta(speed2Meta);
//			
//			PotionMeta haste1Meta = (PotionMeta) haste1.getItemMeta();
//			haste1Meta.setBasePotionType(PotionType.SWIFTNESS);
//			haste1.setItemMeta(haste1Meta);
//			
//			PotionMeta strength1Meta = (PotionMeta) strength1.getItemMeta();
//			strength1Meta.setBasePotionType(PotionType.LEAPING);
//			strength1.setItemMeta(strength1Meta);
//			
//			PotionMeta resistance1Meta = (PotionMeta) resistance1.getItemMeta();
//			resistance1Meta.setBasePotionType(PotionType.);
//			resistance1.setItemMeta(resistance1Meta);
//			
//			PotionMeta beastmodeMeta = (PotionMeta) beastmode.getItemMeta();
//		    // Create effects
//			// ALL EFFECTS IS TWO MINUTES, AKA 120 SECONDS: Speed 2, JumpBoost 2, NightVision, Haste, Resistance, Strength
//			
//		    PotionEffect speed = new PotionEffect(PotionEffectType.SPEED, 120 * 20, 1);  // 60 seconds, amplifier 1 (i.e. Speed II)
//		    PotionEffect jump = new PotionEffect(PotionEffectType.JUMP_BOOST, 120 * 20, 1);
//		    PotionEffect nightvision = new PotionEffect(PotionEffectType.NIGHT_VISION, 120 * 20, 0);
//		    PotionEffect haste = new PotionEffect(PotionEffectType.HASTE, 120 * 20, 0);
//		    PotionEffect resistance = new PotionEffect(PotionEffectType.RESISTANCE, 120 * 20, 0);
//		    PotionEffect strength = new PotionEffect(PotionEffectType.STRENGTH, 120 * 20, 0);
//
//		    // Add them
//		    beastmodeMeta.addCustomEffect(speed, true);
//		    beastmodeMeta.addCustomEffect(jump, true);
//		    beastmodeMeta.addCustomEffect(nightvision, true);
//		    beastmodeMeta.addCustomEffect(haste, true);
//		    beastmodeMeta.addCustomEffect(resistance, true);
//		    beastmodeMeta.addCustomEffect(strength, true);
//
//		    // Optionally change color
//		    beastmodeMeta.setColor(org.bukkit.Color.ORANGE);
//
//		    beastmode.setItemMeta(beastmodeMeta);

			// Skapa handelsalternativen
			List<MerchantRecipe> recipes = new ArrayList<>();
			
			PotionEffect invis1 = new PotionEffect(PotionEffectType.INVISIBILITY, 30 * 20, 0);
			
			PotionEffect hunger2 = new PotionEffect(PotionEffectType.HUNGER, 90 * 20, 1);
			
			PotionEffect speed2 = new PotionEffect(PotionEffectType.SPEED, 90 * 20, 1);
			PotionEffect haste1 = new PotionEffect(PotionEffectType.HASTE, 90 * 20, 0);
			PotionEffect strength1 = new PotionEffect(PotionEffectType.STRENGTH, 90 * 20, 0);
			PotionEffect resistance1 = new PotionEffect(PotionEffectType.RESISTANCE, 90 * 20, 0);
			
			ItemStack invis1Potion = createPotion(
			        ChatColor.AQUA + "Invis 1 with hunger",
			        Arrays.asList(ChatColor.GRAY + "Invisibility"),
			        new PotionEffect[]{hunger2, invis1},
			        Color.SILVER
			);
			
			ItemStack speed2Potion = createPotion(
			        ChatColor.AQUA + "Speed 2 with hunger",
			        Arrays.asList(ChatColor.GRAY + "Speed"),
			        new PotionEffect[]{hunger2, speed2},
			        Color.AQUA
			);
			
			ItemStack haste1Potion = createPotion(
			        ChatColor.DARK_AQUA + "Haste 1 with hunger",
			        Arrays.asList(ChatColor.GRAY + "Haste"),
			        new PotionEffect[]{hunger2, haste1},
			        Color.OLIVE
			);
			
			ItemStack strength1Potion = createPotion(
			        ChatColor.RED + "Strength 1 with hunger",
			        Arrays.asList(ChatColor.GRAY + "Strength"),
			        new PotionEffect[]{hunger2, strength1},
			        Color.RED
			);
			
			ItemStack resistance1Potion = createPotion(
			        ChatColor.GREEN + "Resistance 1 with hunger",
			        Arrays.asList(ChatColor.GRAY + "Resistance"),
			        new PotionEffect[]{hunger2, resistance1},
			        Color.GREEN
			);
			
			recipes.add(createRecipe(
					invis1Potion,
					Material.BEDROCK, 1
					));
			
			recipes.add(createRecipe(
					speed2Potion,
					Material.BRICK, 32
					));
			
			recipes.add(createRecipe(
					haste1Potion,
					Material.IRON_INGOT, 1
					));
			
			recipes.add(createRecipe(
					strength1Potion,
					Material.IRON_INGOT, 1
					));
			
			recipes.add(createRecipe(
					resistance1Potion,
					Material.IRON_INGOT, 1
					));
						
		    PotionEffect speed2bm = new PotionEffect(PotionEffectType.SPEED, 120 * 20, 1); // 60 seconds, amplifier 1 (i.e. Speed II)
		    PotionEffect jump2bm = new PotionEffect(PotionEffectType.JUMP_BOOST, 120 * 20, 1);
		    PotionEffect nightvision1bm = new PotionEffect(PotionEffectType.NIGHT_VISION, 120 * 20, 0);
		    PotionEffect haste1bm = new PotionEffect(PotionEffectType.HASTE, 120 * 20, 0);
		    PotionEffect resistance1bm = new PotionEffect(PotionEffectType.RESISTANCE, 120 * 20, 0);
		    PotionEffect strength1bm = new PotionEffect(PotionEffectType.STRENGTH, 120 * 20, 0);

			ItemStack beastMode = createPotion(
			        ChatColor.DARK_PURPLE + "BeastMode",
			        Arrays.asList(ChatColor.GRAY + "woot"),
			        new PotionEffect[]{speed2bm, jump2bm, nightvision1bm, haste1bm, resistance1bm, strength1bm},
			        Color.BLACK
			);

//			player.getInventory().addItem(trollPotion);


			recipes.add(createRecipe(
					beastMode,
					Material.EMERALD, 1
					));
			
//			recipes.add(createRecipe(
//					beastmode,
//					Material.BRICK, 2
//					));
//			
//			recipes.add(createRecipe(
//					beastmode,
//					Material.BRICK, 2
//					));
//			
//			recipes.add(createRecipe(
//					beastmode,
//					Material.BRICK, 2
//					));
//			
//			recipes.add(createRecipe(
//					beastmode,
//					Material.BRICK, 2
//					));

			ItemStack knuffpinne = new ItemStack(Material.STICK, 1);
			ItemMeta knuffpinneMeta = knuffpinne.getItemMeta();
			knuffpinneMeta.setDisplayName("Knuffpinne :D");
			knuffpinneMeta.setLore((Arrays.asList("Hacks", "och", "knuff :D")));
			knuffpinneMeta.addEnchant(Enchantment.KNOCKBACK, 2, true);
			knuffpinne.setItemMeta(knuffpinneMeta);
			// recipes.add(createRecipe(knuffpinne, Material.GOLD_INGOT, 5));
			recipes.add(createRecipe(knuffpinne, Material.GOLD_INGOT, 1));

			villager.setRecipes(recipes);
		}
	}
	



//	public static void spawnCustomVillagerPotionsplatsen() {
//		// Hämta världen
//		var world = Bukkit.getWorld("world"); // Justera "world" om din värld heter något annat
//
//		if (world == null) {
//			Main.log("[CustomVillagerTrades] Världen world hittades inte!");
//			return;
//		}
//
//		Location villagerloc = null;
//
//		for (int i=1; i<5; i++)
//		{
//			if (i==1) villagerloc = new Location (world, 5.5, 53, -62.5);
//			if (i==2) villagerloc = new Location (world, 59.5, 53, 5.5, 90, 14);
//			if (i==3) villagerloc = new Location (world, -8.5, 53, 59.5, 180, 14);
//			if (i==4) villagerloc = new Location (world, -62.5, 53, -8.5, -90, 14);		
//
//			// Spawna Villagern
//			Villager villager = (Villager) world.spawnEntity(villagerloc, EntityType.VILLAGER);
//			//			Main.log("Spawaning Potionsplatsen-NPC at " + villagerloc);
//			villager.setProfession(Villager.Profession.LIBRARIAN);
//			villager.setVillagerLevel(2); // Sätt Villager-nivån till 2
//			//			villager.setCustomName("Arcadia :D");
//			//			villager.setCustomNameVisible(true);
//			villager.setAI(false);
//			villager.setInvulnerable(true);
//
//			PotionMeta jumpMeta = (PotionMeta) jump.getItemMeta();
//			jumpMeta.setBasePotionType(PotionType.LEAPING);
//			jump.setItemMeta(jumpMeta);
//
//			// Skapa handelsalternativen
//			List<MerchantRecipe> recipes = new ArrayList<>();
//
//			//			recipes.add(createRecipe(
//			//					new ItemStack (Material.HOPPER, 1),
//			//					Material.BRICK, 64
//			//					));
//
//			ItemStack crossbowinf = new ItemStack(Material.CROSSBOW, 1);
//			ItemMeta crossbowinfMeta = crossbowinf.getItemMeta();
//			//			goldpickeff3Meta.setDisplayName("Minken testar");
//			crossbowinfMeta.addEnchant(Enchantment.INFINITY, 1, false);
//			crossbowinf.setItemMeta(crossbowinfMeta);
//			recipes.add(createRecipe(crossbowinf, Material.GOLD_INGOT, 3));
//
//			//			ItemStack crossbowinfmultishot = new ItemStack(Material.CROSSBOW, 1);
//			//			ItemMeta crossbowinfmultishotMeta = crossbowinfmultishot.getItemMeta();
//			////			goldpickeff3Meta.setDisplayName("Minken testar");
//			//			crossbowinfmultishotMeta.addEnchant(Enchantment.INFINITY, 1, false);
//			//			crossbowinfmultishotMeta.addEnchant(Enchantment.MULTISHOT, 1, false);
//			//			crossbowinfmultishot.setItemMeta(crossbowinfmultishotMeta);
//			//			recipes.add(createRecipe(crossbowinfmultishot, Material.GOLD_INGOT, 6));
//
//			recipes.add(createRecipe(
//					new ItemStack (Material.STONE_AXE, 1),
//					Material.GOLD_INGOT, 2
//					));
//
//			ItemStack yxa = new ItemStack(Material.DIAMOND_AXE, 1);
//			ItemMeta yxaMeta = yxa.getItemMeta();
//			// yxaMeta.setDisplayName("Vilken sorts yxa skulle det vara?");
//			// yxaMeta.setLore((Arrays.asList("Minken", "är", "glömsk")));
//			yxaMeta.addEnchant(Enchantment.SHARPNESS, 5, true);
//			yxa.setItemMeta(yxaMeta);
//			recipes.add(createRecipe(yxa, Material.GOLD_INGOT, 42));
//
//			ItemStack knuffpinne = new ItemStack(Material.STICK, 1);
//			ItemMeta knuffpinneMeta = knuffpinne.getItemMeta();
//			knuffpinneMeta.setDisplayName("Knuffpinne :D");
//			knuffpinneMeta.setLore((Arrays.asList("Hacks", "och", "knuff :D")));
//			knuffpinneMeta.addEnchant(Enchantment.KNOCKBACK, 2, true);
//			knuffpinne.setItemMeta(knuffpinneMeta);
//			//			recipes.add(createRecipe(knuffpinne, Material.GOLD_INGOT, 5));
//			recipes.add(createRecipe(knuffpinne, Material.GOLD_INGOT, 1));
//
//			villager.setRecipes(recipes);
//		}
//	}

	public static void spawnCustomVillagerInTheMiddleBelow() {

		// Hämta världen
		var world = Bukkit.getWorld("world"); // Justera "world" om din värld heter något annat

		if (world == null) {
			Main.log("[CustomVillagerTrades] Världen world hittades inte!");
			return;
		}

		Location villagerloc = new Location (world, -1.5, 10, -1.5);

		// Spawna Villagern
		Villager villager = (Villager) world.spawnEntity(villagerloc, EntityType.VILLAGER);

		villager.setProfession(Villager.Profession.FARMER); // INGENKOLL är mer lämplig för svärd
		villager.setVillagerLevel(2); // Sätt Villager-nivån till 2
		villager.setCustomName("HACKS II :D");
		villager.setCustomNameVisible(true);
		villager.setAI(false);
		villager.setInvulnerable(true);

		// Skapa handelsalternativen
		List<MerchantRecipe> recipes = new ArrayList<>();

		recipes.add(createRecipe(
				new ItemStack (Material.ENDER_PEARL, 1),
				Material.BRICK, 1
				));

		villager.setRecipes(recipes);
	}




	public static void placeTheMiddleNPCWithCustomSettings() {
		new BukkitRunnable() {
			@Override
			public void run() {

				// Hämta världen
				var world = Bukkit.getWorld("world"); // Justera "world" om din värld heter något annat

				if (world == null) {
					Main.log("[CustomVillagerTrades] Världen world hittades inte!");
					return;
				}



				// Spawna Villagern
				Villager villager = (Villager) world.spawnEntity(BeastModeData.villagerMiddleLocation, EntityType.VILLAGER);
                if (Main.DEBUG) {
                    Main.log("Spawning Middlesplatsen-NPC at " + villager.getLocation());
                }
				villager.setProfession(Villager.Profession.SHEPHERD); // INGENKOLL är mer lämplig för svärd
				villager.setVillagerLevel(2); // Sätt Villager-nivån till 2
				villager.setCustomName("HACKS :D");
				villager.setCustomNameVisible(true);
				villager.setAI(false);
				villager.setInvulnerable(true);

				// Skapa handelsalternativen
				List<MerchantRecipe> recipes = new ArrayList<>();

				recipes.add(CustomVillagerTrades.createRecipe(
						new ItemStack (Material.SHIELD, 1),
						Material.DIAMOND, BeastModeData.diaForShield
						));

				recipes.add(CustomVillagerTrades.createRecipe(
						new ItemStack (Material.WATER_BUCKET, 1),
						Material.DIAMOND, BeastModeData.diaForWaterBucket
						));

				recipes.add(CustomVillagerTrades.createRecipe(
						new ItemStack (Material.ENCHANTED_GOLDEN_APPLE, 1),
						//				Material.DIAMOND, 5
						Material.DIAMOND, BeastModeData.diaForSuperGoldenApple
						));

				recipes.add(CustomVillagerTrades.createRecipe(
						new ItemStack (Material.ENDER_PEARL, 1),
						//				Material.DIAMOND, 1
						Material.DIAMOND, BeastModeData.diaForEnderPearl
						));

				// Trade 4: Bow med Infinity, Power 4 och Punch 2
				recipes.add(CustomVillagerTrades.createRecipe(
						CustomVillagerTrades.createBow("Addes båge", "Addes båge", new Enchantment[]{Enchantment.INFINITY, Enchantment.POWER, Enchantment.PUNCH}, new int[]{1, 4, 2}),
						//				Material.DIAMOND, 5
						Material.DIAMOND, BeastModeData.diaForAddeBow
						));

				// recipes.add(createRecipe(
				// new ItemStack (trident),
				// Material.DIAMOND, 3
				// ));

				recipes.add(CustomVillagerTrades.createRecipe(
						new ItemStack (Material.GOLD_INGOT, BeastModeData.goldsForOneDia),
						Material.DIAMOND, 1
						));

				ItemStack mace = new ItemStack(Material.MACE, 1);
				ItemMeta maceMeta = mace.getItemMeta();
				maceMeta.addEnchant(Enchantment.FIRE_ASPECT, 2, true);
				maceMeta.addEnchant(Enchantment.DENSITY, 2, true);
//				maceMeta.addEnchant(Enchantment.WIND_BURST, 2, true); // Fungerar inte enligt Adde.
				// maceMeta.addEnchant(Enchantment., 2, true);
				mace.setItemMeta(maceMeta);
				recipes.add(CustomVillagerTrades.createRecipe(mace, Material.DIAMOND, BeastModeData.diaForMace));

				recipes.add(CustomVillagerTrades.createRecipe(
						new ItemStack (Material.WIND_CHARGE, 5),
						Material.GOLD_INGOT, BeastModeData.goldForWindCharge
						));

				//		recipes.add(createRecipe(
				//				new ItemStack (Bira.createBira()),
				//				Material.DIAMOND, 3
				//				));

				//		ItemStack shield = new ItemStack(Material.SHIELD, 1);
				//		// ItemMeta shieldMeta = shield.getItemMeta();
				//		// shieldMeta.addEnchant(Enchantment.PROJECTILE_PROTECTION, 1, true);
				//		// shield.setItemMeta(shieldMeta);
				//		recipes.add(createRecipe(shield, Material.DIAMOND, 2));

				recipes.add(CustomVillagerTrades.createRecipe(
						new ItemStack (Material.BRICK, 32),
						Material.IRON_INGOT, 1
						));

				recipes.add(CustomVillagerTrades.createRecipe(
						new ItemStack (Material.IRON_INGOT, 10),
						Material.GOLD_INGOT, 1
						));

				villager.setRecipes(recipes);
			}
		}.runTaskLater(Main.plugin, 20L);
	}
}
