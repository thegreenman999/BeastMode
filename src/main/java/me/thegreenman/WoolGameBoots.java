package me.thegreenman;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.kyori.adventure.text.Component;

public class WoolGameBoots implements Listener {
    // .\eclipse-workspace\_CRAP\MinkensWoolGame\src\org\minken\MinkensWoolGame.java, kopierad 18/1 -26 och garanterat modifierad efter det :)

    private boolean checkBoots = false;

    public static ItemStack fuskBoots = new ItemStack(Material.LEATHER_BOOTS) {{
        ItemMeta meta = this.getItemMeta();

        meta.itemName(Component.text("FuskBoots :D"));
        meta.lore(List.of(Component.text("Minken gillar fusk :D")));
        this.setItemMeta(meta);
    }};

    @EventHandler
    public void onPlayerCommandPreprocessEvent(PlayerCommandPreprocessEvent event) {
        if (!event.getMessage().toLowerCase().equals("/fuskboots")) return;

        event.getPlayer().getInventory().addItem(fuskBoots);
        checkBoots = true;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (!checkBoots) { return; }

        Player player = event.getPlayer();
        if (player.getInventory().getBoots() == null) { return; }

//		if (event.getFrom().getBlock().equals(event.getTo().getBlock())) { return; }

        Block blockBelow = player.getLocation().subtract(0, 1, 0).getBlock();
//		Block blockBelow = player.getLocation().getBlock();
        if (!blockBelow.getType().equals(Material.AIR)) { return; }

        if (!isSpecialBoots(player.getInventory().getBoots())) { return; }
//		if (blockBelow.getY() != -59) { return; }
        //			else { blockBelow.setType(newMaterial); }

        blockBelow.setType(Material.PINK_WOOL);
    }

    public boolean isSpecialBoots(ItemStack itemToCheck) {
        if (itemToCheck == null) { return false; }
        if (!itemToCheck.hasItemMeta()) { return false; }
        if (itemToCheck.getType().equals(Material.LEATHER_BOOTS)) {
            if (itemToCheck.getItemMeta().hasLore()) {
                @SuppressWarnings("deprecation")
                List<String> loretextlista = itemToCheck.getItemMeta().getLore();
                for (int row = 0; row < loretextlista.size(); row++) { if (loretextlista.get(row).contains("Minken")) { return true; } }
            }
        } return false;
    }
}