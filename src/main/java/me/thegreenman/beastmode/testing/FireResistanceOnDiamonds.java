package me.thegreenman.beastmode.testing;

import java.util.Iterator;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ItemDespawnEvent;

public class FireResistanceOnDiamonds implements Listener {

	@EventHandler
	public void onItemDespawnEvent(ItemDespawnEvent event) { // BURN
		Item item = event.getEntity();
		Material itemType = item.getItemStack().getType();
		if (itemType == Material.DIAMOND) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onEntityDamageEvent(EntityDamageEvent event) { // FIRE
		if (!(event.getEntity() instanceof Item)) { return; }

		Item item = (Item) event.getEntity();
		Material itemType = item.getItemStack().getType();
		if (itemType == Material.DIAMOND) {
			event.setCancelled(true);
		}
	}

	@EventHandler (priority = EventPriority.HIGH)
	public void onEntityExplode(EntityExplodeEvent event) { // TNT/ETC.
		Iterator<org.bukkit.block.Block> iterator = event.blockList().iterator();
		while (iterator.hasNext()) {
			Block block = iterator.next();
			// if (block.getType() == Material.GOLD_BLOCK) {
			// iterator.remove();
			// }
			if (block.getType().equals(Material.DIAMOND)) {
				iterator.remove();
				event.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void onEntityCombust(EntityCombustEvent event) { // UMM, I FORGOT.
		if (!(event.getEntity() instanceof Item)) { return; }

		Item item = (Item) event.getEntity();
		if (item.getItemStack().getType() == Material.DIAMOND) {
			event.setCancelled(true);
		}
	}
}
