package me.thegreenman.beastmode.minkenedition;

import java.util.Iterator;

import me.thegreenman.Main;
import me.thegreenman.beastmode.BeastModeData;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class PreventGreif implements Listener {

	
	public static boolean suicideEggIgnoresProtection = false;

	@EventHandler
	public void onPlayerInteractEvent(PlayerInteractEvent event) {
		if (!event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
            return;
        }
		if (event.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
            return;
        }
        if (BeastModeData.selectedMapindex != 0) {
            return;
        }

		Block clickedBlock = event.getClickedBlock();

		if (clickedBlock == null) { return; }
		if (clickedBlock.getType().equals(Material.AIR)) { return; }

		if (clickedBlock.getType().equals(Material.SPAWNER)) {
			event.setCancelled(true);
		}

		if (isWool(clickedBlock.getType())) {
			event.setCancelled(true); 
		}
		
		if (clickedBlock.getType().equals(Material.GLOWSTONE)) { event.setCancelled(true); }
	}


	private boolean isWool(Material material) {
		return material.name().endsWith("_WOOL");
	}

    private boolean isBed(Material material) {
        return material.name().endsWith("_BED");
    }


	
	
	@EventHandler
    public void onEntityExplode(EntityExplodeEvent event) { //  GRÖNIS EDITION
        if (BeastModeData.selectedMapindex != 0) {
            return;
        }
        // Go through all the blocks that would be destroyed and remove the unauthorized ones
        Iterator<Block> it = event.blockList().iterator();
        while (it.hasNext()) {
            Block block = it.next();
            if (shouldProtectBlock(block)) {
                it.remove(); // protects the block from explosion
            }
        }
    }
	

    @EventHandler
    public void onBlockExplodeEvent(BlockExplodeEvent event) { //  GRÖNIS EDITION
        if (BeastModeData.selectedMapindex != 0) {
            return;
        }
        // Go through all the blocks that would be destroyed and remove the unauthorized ones
        Iterator<Block> it = event.blockList().iterator();
        while (it.hasNext()) {
            Block block = it.next();
            if (shouldProtectBlock(block)) {
                it.remove(); // protects the block from explosion
            }
        }
    }



	private boolean shouldProtectBlock(Block block) {
		return block.getType().equals(Material.SPAWNER) || block.getType().equals(Material.GLOWSTONE) || isWool(block.getType()) || isBed(block.getType());
	}

	@EventHandler
	public void onBlockBurn(BlockBurnEvent event) {
		Block block = event.getBlock();

		if (isWool(block.getType())) {
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onCraft(PrepareItemCraftEvent event) {
	    // Förhindra crafting genom att tömma crafting-resultatet
	    event.getInventory().setResult(null);
	}

}
