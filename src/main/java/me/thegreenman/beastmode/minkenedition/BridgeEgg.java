package me.thegreenman.beastmode.minkenedition;

import com.destroystokyo.paper.event.player.PlayerLaunchProjectileEvent;
import me.thegreenman.Main;
import me.thegreenman.beastmode.BeastModeData;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerEggThrowEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;

public class BridgeEgg implements Listener, CommandExecutor {

    public static NamespacedKey BRIDGE_KEY = new NamespacedKey(Main.plugin, "a_real_bridgeegg");
    private static ArrayList<Entity> BridgeEggInAir = new ArrayList<>();
    private static HashMap<Entity, BukkitTask> BridgeEggInAirtasks = new HashMap<>();

    public static ItemStack BridgeEgg = new ItemStack(Material.EGG, 10) {{
        ItemMeta meta = this.getItemMeta();
        meta.displayName(Component.text("BridgeEgg"));
        meta.getPersistentDataContainer().set(BRIDGE_KEY, PersistentDataType.BOOLEAN, true);
        this.setItemMeta(meta);
    }};

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Players only!");
            return false;
        }

        player.getInventory().addItem(BridgeEgg);
        return true;
    }



    private boolean isBridgeEgg(ItemStack item) {
        if (item == null || !item.hasItemMeta()) return false;
        PersistentDataContainer pdc = item.getItemMeta().getPersistentDataContainer();
        return Boolean.TRUE.equals(pdc.get(BRIDGE_KEY, PersistentDataType.BOOLEAN));
    }

//    //
//    // PLAYER INTERACT = manual launch path
//    //
//    @EventHandler
//    public void onPlayerInteract(PlayerInteractEvent event) {
//        Player player = event.getPlayer();
//
//        if (!event.getAction().isRightClick()) return;
//
//        ItemStack item = player.getInventory().getItemInMainHand();
//        if (!isBridgeEgg(item)) return;
//
//        event.setCancelled(true);
//
//        Egg egg = player.launchProjectile(Egg.class);
//        egg.setMetadata("BridgeEgg", new FixedMetadataValue(Main.plugin, true));
//
//        // REMOVE ONE FROM INVENTORY
//        item.setAmount(item.getAmount() - 1);
//    }

    @EventHandler
    public void onPlayerLaunchProjectileEvent(PlayerLaunchProjectileEvent event) {
        if (!(event.getProjectile() instanceof Egg egg)) {
            return;
        }
        if (!event.getItemStack().getPersistentDataContainer().has(BRIDGE_KEY)) {
            event.getPlayer().sendMessage("has not bridge key");
            return;
        }

        event.getPlayer().sendMessage("has bridge key");
        BridgeEggInAirtasks.put(egg, new BukkitRunnable() {
            @Override
            public void run() {
                placeBlock(egg.getLocation());
            }
        }.runTaskTimer(Main.plugin, 1L, 1L));

        new BukkitRunnable() {
            @Override
            public void run() {
                if (!BridgeEggInAirtasks.get(egg).isCancelled() || BridgeEggInAirtasks.get(egg) != null) {
                    BridgeEggInAirtasks.get(egg).cancel();
                    BridgeEggInAirtasks.remove(egg);
                }
            }
        }.runTaskLater(Main.plugin, 180L);
    }

    //
    // When a projectile hits, handle bridge egg finish
    //
    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        if (!(event.getEntity() instanceof Egg egg)) return;
        if (!egg.hasMetadata("BridgeEgg")) return;

        BridgeEggInAir.remove(egg);
        if (!BridgeEggInAirtasks.get(egg).isCancelled() || BridgeEggInAirtasks.get(egg) != null){
            BridgeEggInAirtasks.get(egg).cancel();
            BridgeEggInAirtasks.remove(egg);
        }
    }

    private void placeBlock(Location loc) {
        new BukkitRunnable() {
            @Override
            public void run() {
                loc.add(0,-1,0);
                loc.getBlock().setType(Material.DIRT);
                loc.getBlock().getRelative(0,0,1).setType(Material.DIRT);
                loc.getBlock().getRelative(1,0,0).setType(Material.DIRT);
                loc.getBlock().getRelative(0,0,-1).setType(Material.DIRT);
                loc.getBlock().getRelative(-1,0,0).setType(Material.DIRT);

            }
        }.runTaskLater(Main.plugin, 10L);
    }


}
