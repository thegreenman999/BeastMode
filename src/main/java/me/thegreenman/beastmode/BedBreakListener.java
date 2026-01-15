package me.thegreenman.beastmode;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import me.thegreenman.Main;

public class BedBreakListener implements Listener {

	@EventHandler
	public void onBlockBreakEvent(BlockBreakEvent event) {
//		Bukkit.broadcastMessage("BlockBreakEvent DEBUG: BeastModeData.gameIsRunning = " + BeastModeData.gameIsRunning + ", .getBlock().getType().toString() = " + event.getBlock().getType().toString() + " ");
		if (!BeastModeData.gameIsRunning) { return; }
		
		if (BeastModeData.bedsCanBePickedUp) {
            Main.log("BlockBreakEvent DEBUG: " + event.getBlock().getType().toString() + " broken by " + event.getPlayer().getName() + ", returning here. onBlockBreakEvent.java, row 21.");
            return;
        }

		if (!event.getBlock().getType().toString().toUpperCase().endsWith("_BED")) {
            return;
        }
		event.setDropItems(false);
		
		Player bedbreaker = event.getPlayer();
		Bukkit.broadcastMessage(Main.pvpbm + "Bed broken and gone: " + event.getBlock().getType().toString() + " by " + bedbreaker.getName() + " (gets an Emerald for it instead).");
		event.getPlayer().getInventory().addItem(new ItemStack (Material.EMERALD, 1));

    }
}