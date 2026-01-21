package me.thegreenman.beastmode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import me.thegreenman.Main;

public class DefaultVillagerTrades extends VillagersTrades {
//	public static ItemStack tackGronis = new ItemStack(Material.POTION);
	
//	public static ItemStack speed2 = new ItemStack(Material.POTION);
//	public static ItemStack haste1 = new ItemStack(Material.POTION);
//	public static ItemStack strength1 = new ItemStack(Material.POTION);
//	public static ItemStack resistance1 = new ItemStack(Material.POTION);
//	public static ItemStack beastmode = new ItemStack(Material.POTION);

	public DefaultVillagerTrades() {
	}

	public static void spawnTheVillagers() {
		// Spawna Villagern vid världens spawnplats
		Bukkit.getScheduler().runTaskLater(Main.plugin, DefaultVillagerTrades::spawnDefaultVillagerBow, 5L); // Fördröjning för att säkerställa att världen är laddad
		Bukkit.getScheduler().runTaskLater(Main.plugin, DefaultVillagerTrades::spawnDefaultVillagerSword, 10L);
		Bukkit.getScheduler().runTaskLater(Main.plugin, DefaultVillagerTrades::spawnDefaultVillagerArmor, 15L);
		Bukkit.getScheduler().runTaskLater(Main.plugin, DefaultVillagerTrades::spawnDefaultVillagerFood, 20L);
		Bukkit.getScheduler().runTaskLater(Main.plugin, DefaultVillagerTrades::spawnDefaultVillagerBlocks, 25L);
		Bukkit.getScheduler().runTaskLater(Main.plugin, DefaultVillagerTrades::spawnDefaultVillagerPotionsplatsen, 30L);
	}

	public static void spawnDefaultVillagerBow() {
		// Hämta världen
		var world = Bukkit.getWorld("world"); // Justera "world" om din värld heter något annat

		if (world == null) {
			Main.log("[CustomVillagerTrades] Världen world hittades inte!");
			return;
		}


		for (int i=1; i<5; i++) {


			// Spawna Villagern
			Villager villager = (Villager) world.spawnEntity(BeastModeData.villagerBowLocation[i-1], EntityType.VILLAGER);
			//			Main.log("Spawaning Bow-NPC at " + villagerloc);
			villager.setProfession(Villager.Profession.CLERIC);
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
					Material.GOLD_INGOT, 2
					));

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

			// Trade 5: Arrow
			recipes.add(createRecipe(
					new ItemStack (Material.ARROW, 1), Material.GOLD_INGOT, 1));

			// Trade 6: Ladder
			recipes.add(createRecipe(
					new ItemStack (Material.LADDER, 3), Material.IRON_INGOT, 1));

			// Trade 7: xxx
			recipes.add(createRecipe(
					new ItemStack (Material.COBWEB, 3), Material.IRON_INGOT, 1));

			// Tilldela Villagern recepten
			villager.setRecipes(recipes);
		}
	}

	public static void spawnDefaultVillagerSword() {
		// Hämta världen
		var world = Bukkit.getWorld("world"); // Justera "world" om din värld heter något annat

		if (world == null) {
			Main.log("[CustomVillagerTrades] Världen world hittades inte!");
			return;
		}


		for (int i=1; i<5; i++) {


			// Spawna Villagern
			Villager villager = (Villager) world.spawnEntity(BeastModeData.villagerSwordLocation[i-1], EntityType.VILLAGER);
			//			Main.log("Spawaning Sword-NPC at " + villagerloc);
			villager.setProfession(Villager.Profession.ARMORER);
			villager.setVillagerLevel(2); // Sätt Villager-nivån till 2
			//			villager.setCustomName("Sword Merchant");
			//			villager.setCustomNameVisible(true);
			villager.setAI(false);
			villager.setInvulnerable(true);


			// Skapa handelsalternativen
			List<MerchantRecipe> recipes = new ArrayList<>();

			// Trade 1: Sword
			recipes.add(createRecipe(
					createSword("Level 1", null, null),
					Material.IRON_INGOT, 2
					));

			// Trade 2: Sword med Sharpness 1
			recipes.add(createRecipe(
					createSword("Level 2", new Enchantment[]{Enchantment.SHARPNESS}, new int[]{1}),
					Material.IRON_INGOT, 8
					));

			// Trade 3: Sword med Sharpness 2
			recipes.add(createRecipe(
					createSword("Level 3", new Enchantment[]{Enchantment.SHARPNESS}, new int[]{2}),
					Material.IRON_INGOT, 16
					));

			// Trade 4: Sword med Sharpness 2 och Knockback 1
			recipes.add(createRecipe(
					createSword("Level 4", new Enchantment[]{Enchantment.SHARPNESS, Enchantment.KNOCKBACK}, new int[]{2, 1}),
					Material.IRON_INGOT, 48
					));

			// Trade 5: TNT :D
			recipes.add(createRecipe(
					new ItemStack (Material.TNT, 1), Material.IRON_INGOT, 6));
			
			recipes.add(createRecipe(
					new ItemStack (Material.LAVA_BUCKET, 1), Material.GOLD_INGOT, 3));

			// Trade 6: FaS
			recipes.add(createRecipe(
					new ItemStack (Material.FLINT_AND_STEEL, 1), Material.GOLD_INGOT, 1));

			// Tilldela Villagern recepten
			villager.setRecipes(recipes);
		}
	}

	public static void spawnDefaultVillagerArmor() {
		// Hämta världen
		var world = Bukkit.getWorld("world"); // Justera "world" om din värld heter något annat

		if (world == null) {
			Main.log("[CustomVillagerTrades] Världen world hittades inte!");
			return;
		}

		for (int i=1; i<5; i++) {

			// Spawna Villagern
			Villager villager = (Villager) world.spawnEntity(BeastModeData.villagerArmorLocation[i-1], EntityType.VILLAGER);

			villager.setProfession(Villager.Profession.NITWIT);
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

			// THE CHESTPLATE ISN'T IN THE ORIGINAL MAP, BUT WHO CARES?
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

	public static void spawnDefaultVillagerFood() {
		// Hämta världen
		var world = Bukkit.getWorld("world"); // Justera "world" om din värld heter något annat

		if (world == null) {
			Main.log("[CustomVillagerTrades] Världen world hittades inte!");
			return;
		}


		for (int i=1; i<5; i++) {

			// Spawna Villagern
			Villager villager = (Villager) world.spawnEntity(BeastModeData.villagerFoodLocation[i-1], EntityType.VILLAGER);
			//			Main.log("Spawaning Food-NPC at " + villagerloc);
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
					new ItemStack (Material.APPLE, 4),
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

	public static void spawnDefaultVillagerBlocks() {
		// Hämta världen
		var world = Bukkit.getWorld("world"); // Justera "world" om din värld heter något annat

		if (world == null) {
			Main.log("[CustomVillagerTrades] Världen world hittades inte!");
			return;
		}


		for (int i=1; i<5; i++) {

			// Spawna Villagern
			Villager villager = (Villager) world.spawnEntity(BeastModeData.villagerBlocksLocation[i-1], EntityType.VILLAGER);
			//			Main.log("Spawaning Blocks-NPC at " + villagerloc);
			villager.setProfession(Villager.Profession.BUTCHER); // INGENKOLL är mer lämplig för svärd
			villager.setVillagerLevel(2); // Sätt Villager-nivån till 2
			//			villager.setCustomName("Block Merchant");
			//			villager.setCustomNameVisible(true);
			villager.setAI(false);
			villager.setInvulnerable(true);


			// Skapa handelsalternativen
			List<MerchantRecipe> recipes = new ArrayList<>();

			recipes.add(createRecipe(
					new ItemStack (Material.NETHERRACK, 32),
					Material.BRICK, 8
					));

			recipes.add(createRecipe(
					new ItemStack (Material.STONE_BRICKS, 16),
					Material.BRICK, 16
					));

			recipes.add(createRecipe(
					new ItemStack (Material.EMERALD_BLOCK, 8),
					Material.BRICK, 32
					));

			recipes.add(createRecipe(
					new ItemStack (Material.GLOWSTONE, 8),
					Material.BRICK, 8
					));

			recipes.add(createRecipe(
					new ItemStack (Material.WOODEN_PICKAXE, 1),
					Material.BRICK, 6
					));

			ItemStack ironpickeff1 = new ItemStack(Material.IRON_PICKAXE, 1);
			ItemMeta ironpickeff1Meta = ironpickeff1.getItemMeta();
			//			ironpickeff1Meta.setDisplayName("Minken testar");
			ironpickeff1Meta.addEnchant(Enchantment.EFFICIENCY, 1, false);
			ironpickeff1.setItemMeta(ironpickeff1Meta);
			recipes.add(createRecipe(ironpickeff1, Material.IRON_INGOT, 3));

			ItemStack goldpickeff3 = new ItemStack(Material.GOLDEN_PICKAXE, 1);
			ItemMeta goldpickeff3Meta = goldpickeff3.getItemMeta();
			//			goldpickeff3Meta.setDisplayName("Minken testar");
			goldpickeff3Meta.addEnchant(Enchantment.EFFICIENCY, 3, false);
			goldpickeff3.setItemMeta(goldpickeff3Meta);
			recipes.add(createRecipe(goldpickeff3, Material.GOLD_INGOT, 1));

			recipes.add(createRecipe(
					new ItemStack (Material.CHEST, 1),
					Material.IRON_INGOT, 1
					));

			villager.setRecipes(recipes);
		}
	}

	public static void spawnDefaultVillagerPotionsplatsen() {
		// Hämta världen
		var world = Bukkit.getWorld("world"); // Justera "world" om din värld heter något annat

		if (world == null) {
			Main.log("[CustomVillagerTrades] Världen world hittades inte!");
			return;
		}



		for (int i=1; i<5; i++) {


			// Spawna Villagern
			Villager villager = (Villager) world.spawnEntity(BeastModeData.villagerPotionLocation[i-1], EntityType.VILLAGER);
			//			Main.log("Spawaning Potionsplatsen-NPC at " + villagerloc);
			villager.setProfession(Villager.Profession.BUTCHER);
			villager.setVillagerLevel(2); // Sätt Villager-nivån till 2
			villager.setCustomName("Broken :(");
			villager.setCustomNameVisible(true);
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
			
			PotionEffect hunger2 = new PotionEffect(PotionEffectType.HUNGER, 90 * 20, 1);
			
			PotionEffect speed2 = new PotionEffect(PotionEffectType.SPEED, 90 * 20, 1);
			PotionEffect haste1 = new PotionEffect(PotionEffectType.HASTE, 90 * 20, 0);
			PotionEffect strength1 = new PotionEffect(PotionEffectType.STRENGTH, 90 * 20, 0);
			PotionEffect resistance1 = new PotionEffect(PotionEffectType.RESISTANCE, 90 * 20, 0);
			
			ItemStack speed2Potion = createPotion(
			        ChatColor.AQUA + "Speed 2 with hunger",
			        Arrays.asList(ChatColor.GRAY + "a"),
			        new PotionEffect[]{hunger2, speed2},
			        Color.AQUA
			);
			
			ItemStack haste1Potion = createPotion(
			        ChatColor.DARK_AQUA + "Haste 1 with hunger",
			        Arrays.asList(ChatColor.GRAY + "b"),
			        new PotionEffect[]{hunger2, haste1},
			        Color.OLIVE
			);
			
			ItemStack strength1Potion = createPotion(
			        ChatColor.RED + "Strength 1 with hunger",
			        Arrays.asList(ChatColor.GRAY + "c"),
			        new PotionEffect[]{hunger2, strength1},
			        Color.RED
			);
			
			ItemStack resistance1Potion = createPotion(
			        ChatColor.GREEN + "Resistance 1 with hunger",
			        Arrays.asList(ChatColor.GRAY + "d"),
			        new PotionEffect[]{hunger2, resistance1},
			        Color.GREEN
			);
			
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

}
