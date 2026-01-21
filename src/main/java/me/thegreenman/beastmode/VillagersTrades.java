package me.thegreenman.beastmode;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;

import java.util.Collections;
import java.util.List;


public class VillagersTrades {




    /**
     * Creates a custom potion item with the given name, lore, and effects.
     *
     * @param name    Display name of the potion (can use ChatColor)
     * @param lore    List of lore lines (descriptions)
     * @param effects Array of potion effects to add
     * @param color   Optional color for the potion (null for default)
     * @return An ItemStack representing the potion
     */
    public static ItemStack createPotion(String name, List<String> lore, PotionEffect[] effects, Color color) {
        ItemStack potion = new ItemStack(Material.POTION);
        PotionMeta potionMeta = (PotionMeta) potion.getItemMeta();

        if (potionMeta != null) {
            // Add effects
            for (PotionEffect effect : effects) {
                potionMeta.addCustomEffect(effect, true);
            }

            // Name
            potionMeta.setDisplayName(ChatColor.RESET + name);

            // Lore
            if (lore != null && !lore.isEmpty()) {
                potionMeta.setLore(lore);
            }

            // Color (optional)
            if (color != null) {
                potionMeta.setColor(color);
            }

            potion.setItemMeta(potionMeta);
        }

        return potion;
    }

    public static ItemStack createBow(String name, String lore, Enchantment[] enchantments, int[] levels) {
        ItemStack bow = new ItemStack(Material.BOW);
        var meta = bow.getItemMeta();

        if (meta != null) {
            // Lägg till enchantments
            for (int i = 0; i < enchantments.length; i++) {
                meta.addEnchant(enchantments[i], levels[i], true);
            }

            // Adde ville ha namn...
            meta.setDisplayName(name);

            // Lägg till lore
            meta.setLore(Collections.singletonList(lore));
            bow.setItemMeta(meta);
        }

        return bow;
    }

    public static ItemStack createSword(String lore, Enchantment[] enchantments, int[] levels) {
        ItemStack sword = new ItemStack(Material.GOLDEN_SWORD);
        var meta = sword.getItemMeta();

        if (enchantments != null && meta != null) {
            // Lägg till enchantments
            for (int i = 0; i < enchantments.length; i++) {
                meta.addEnchant(enchantments[i], levels[i], true);
            }

            // Lägg till lore
            meta.setLore(Collections.singletonList(lore));
            sword.setItemMeta(meta);
        }

        return sword;
    }

    public static ItemStack createLeatherArmor(Color color, String lore, Material material, int amount, Enchantment[] enchantments, int[] levels) {
        ItemStack itemstack = new ItemStack(material, amount);
        //		var meta = itemstack.getItemMeta();
        LeatherArmorMeta meta = (LeatherArmorMeta) itemstack.getItemMeta();
        meta.setColor(color);

        if (enchantments != null && meta != null) {
            // Lägg till enchantments
            for (int i = 0; i < enchantments.length; i++) {
                meta.addEnchant(enchantments[i], levels[i], true);
            }

            // Lägg till lore
            meta.setLore(Collections.singletonList(lore));
            itemstack.setItemMeta(meta);
        }

        return itemstack;
    }

    public static ItemStack createChainmailArmor(String lore, Material material, int amount, Enchantment[] enchantments, int[] levels) {
        ItemStack itemstack = new ItemStack(material, amount);
        var meta = itemstack.getItemMeta();

        if (enchantments != null && meta != null) {
            // Lägg till enchantments
            for (int i = 0; i < enchantments.length; i++) {
                meta.addEnchant(enchantments[i], levels[i], true);
            }

            // Lägg till lore
            meta.setLore(Collections.singletonList(lore));
            itemstack.setItemMeta(meta);
        }

        return itemstack;
    }

    public static MerchantRecipe createRecipe(ItemStack result, Material costType, int costAmount) {
        MerchantRecipe recipe = new MerchantRecipe(result, 9999999); // Max antal gånger handeln kan användas
        recipe.addIngredient(new ItemStack(costType, costAmount));
        return recipe;
    }

    public static int[] parseHexColor(String hex) {
        if (hex == null) {
            throw new IllegalArgumentException("Color string is null.");
        }

        String s = hex.trim();
        if (s.startsWith("#")) s = s.substring(1);

        // Validate characters
        if (!s.matches("(?i)^[0-9a-f]{3}([0-9a-f]{3})?$")) {
            throw new IllegalArgumentException("Invalid hex color: " + hex);
        }

        // Expand 3-digit shorthand to 6-digit
        if (s.length() == 3) {
            s = new StringBuilder()
                    .append(s.charAt(0)).append(s.charAt(0))
                    .append(s.charAt(1)).append(s.charAt(1))
                    .append(s.charAt(2)).append(s.charAt(2))
                    .toString();
        }

        int r = Integer.parseInt(s.substring(0, 2), 16);
        int g = Integer.parseInt(s.substring(2, 4), 16);
        int b = Integer.parseInt(s.substring(4, 6), 16);

        return new int[]{r, g, b};
    }
}
