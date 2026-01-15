package me.thegreenman.beastmode.minkenedition;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class RemoveMetaOnItems implements Listener {
	
	// Om man plockar upp (PickupEvent) eller tar från CONTAINER (InteractEvent?) bronze, silver eller guld, ta bort all META
	
	@EventHandler
	public void onEntityPickupItemEvent (EntityPickupItemEvent event) {
		if (event.getItem() == null) return;
		if (!(event.getEntity() instanceof Player)) return;
		
//		Player player = (Player) event.getEntity();

		ItemStack item = event.getItem().getItemStack();
		if (!item.getType().equals(Material.BRICK) && !item.getType().equals(Material.IRON_INGOT) && !item.getType().equals(Material.GOLD_INGOT)) { return; }
        removeItemMeta(item);
		
//		Bukkit.broadcastMessage("" + player.getName() + " picked up: " + event.getItem().toString());
//		if (!event.getItem().hasMetadata(null)) { event.getItem().setMetadata("", Main.plugin); }
	}

    // Event handler for when a player clicks in an inventory
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        ItemStack item = event.getCurrentItem();
        if (item != null) {
        	if (!item.getType().equals(Material.BRICK) && !item.getType().equals(Material.IRON_INGOT) && !item.getType().equals(Material.GOLD_INGOT)) { return; }
            removeItemMeta(item);
        }
    }

    // Method to remove metadata from an item
    private void removeItemMeta(ItemStack item) {
        if (item.hasItemMeta()) {
            item.setItemMeta(null); // Removes all metadata from the item
        }
    }
}
