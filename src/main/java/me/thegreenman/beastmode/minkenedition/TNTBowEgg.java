package me.thegreenman.beastmode.minkenedition;

import java.util.List;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Egg;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerEggThrowEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import me.thegreenman.Main;
import me.thegreenman.beastmode.BeastModeData;
import net.kyori.adventure.text.Component;

@SuppressWarnings("deprecation")
public class TNTBowEgg implements Listener {
    final String OWNER_KEY = "owner";
    public int explosionValue = BeastModeData.TNTBowExplosionPower; // 4;

    public static ItemStack EggBow = new ItemStack(Material.BOW) {{
        ItemMeta meta = this.getItemMeta();
        meta.itemName(Component.text("Â§4EÂ§cGÂ§4G Â§fbow"));
        meta.lore(List.of(Component.text("Â§7Shoots an egg")));
        meta.addEnchant(Enchantment.INFINITY, 1, true);
        this.setItemMeta(meta);
    }};

    @EventHandler
    public void onEntityChickenDeathEvent(EntityDeathEvent event) {
        if (!event.getEntityType().equals(EntityType.CHICKEN)) { return; }

        event.getDrops().clear();
    }

    @EventHandler
    public void onEntityShootBowEvent(EntityShootBowEvent event) {
//		if (!(event.getEntity() instanceof Player)) { Main.broadcastMessage("vafan1"); return; }
//		if (!(event.getProjectile() instanceof Arrow)) { Main.broadcastMessage("vafan2"); return; }

        // Bukkit.broadcastMessage("EntityShootBowEvent");

        Player player = (Player) event.getEntity();

        if (!player.getInventory().getItemInMainHand().equals(EggBow)) {
//			Main.log(player.getName() + " used a NON-EggBow?"); // SLUTA SPAMMA, TACK :D
            return;
        }



        // Retrieve the current count of arrows shot by the player

//		int smartskaft = arrowsShot.getOrDefault(player, 0); // getArrowsShot(player);
        // Increment the count
//		smartskaft++;
        // Update the count in your tracking system
//		arrowsShot.put(player, smartskaft); // setArrowsShot(player, arrowsShot);
        // Optional: Notify the player of their current count
//		player.sendMessage("You have shot " + arrowsShot.get(player) + " arrows.");

        // Bukkit.broadcastMessage("ArrowsShot fÃ¶r " + player.getName() + ": " + arrowsShot.size());
        // Bukkit.broadcastMessage("Innan. ArrowsLEFT? fÃ¶r " + player.getName() + ": " + arrowsShot.get(player));
        // arrowsShot.put(player, smartskaft);
        // Bukkit.broadcastMessage("Efter. ArrowsLEFT? fÃ¶r " + player.getName() + ": " + arrowsShot.get(player));

        UUID playerUUID = player.getUniqueId();

        Egg egg = player.launchProjectile(Egg.class);
        egg.setVelocity(event.getProjectile().getVelocity());
        egg.setMetadata(OWNER_KEY, new FixedMetadataValue(Main.plugin, playerUUID.toString()));
        event.getProjectile().remove(); // Remove the frikkin' arrow!

        //		event.setProjectile();

//		Arrow arrow = (Arrow) event.getProjectile();

//		arrow.setMetadata(OWNER_KEY, new FixedMetadataValue(Main.plugin, playerUUID.toString()));

//		boolean hasArrows =	player.getInventory().contains(Material.ARROW);
//		if (player.getInventory().getItemInOffHand() != null && player.getInventory().getItemInOffHand().getType().equals(Material.ARROW)) { hasArrows = true; } //TODO LÃ¶ser detta buggen som Adde hittade?

//		if (hasArrows == false) {
//			// Om inga arrow kvar, vÃ¤nta X sekunder och Ã¶ppna adminmenyn
//			// Blockera spelaren frÃ¥n andra interaktioner
//			awaitingHighscoreUpdate.add(playerUUID);
//
//			player.sendMessage(ChatColor.YELLOW + "[Server] VÃ¤nta nÃ¥gra sekunder.");
//
//			new BukkitRunnable() {
//				@Override
//				public void run() {
//					// Ta bort spelaren frÃ¥n blockeringslistan
//					awaitingHighscoreUpdate.remove(playerUUID);
//
//					// Visa adminmenyn
//					openAdminMenu(player);
//
//					// Vafaan? GlÃ¶mde att anropa denna :FACEPALM:
//					endGameForPlayer(player);
//				}
//			}.runTaskLater(plugin, 20L); // VÃ¤nta X sekunder (400 ticks)
//		}
//		else {
//			// hasArrows Ã¤r TRUE
//		}
    }

    @EventHandler
    public void onProjectileHitEvent(ProjectileHitEvent event) {
        if (!(event.getEntity().getShooter() instanceof Player)) { return; }
        if (!(event.getEntity() instanceof Egg)) { return; }

        // Bukkit.broadcastMessage("ProjectileHitEvent"); // ProjectileHitEvent
        // Bukkit.broadcastMessage("" + event.getEntity().getAsString()); // SPAAAAAAM
        // Bukkit.broadcastMessage("" + event.getEntityType()); // ARROW
        // if (event.getHitBlock() != null) { Bukkit.broadcastMessage("" + event.getHitBlock()); }

        // if (event.getHitBlock() != null) { Bukkit.broadcastMessage("" + event.getHitBlockFace().toString()); } // SPAAAAAAM
        // if (event.getHitEntity() != null) { Bukkit.broadcastMessage("" + event.getHitEntity().getAsString()); } // SPAAAAAAM

//		Player player = (Player) event.getEntity().getShooter();
//		UUID playerUUID = player.getUniqueId();

        Egg egg = (Egg) event.getEntity();

        if (!egg.hasMetadata(OWNER_KEY)) { return; }
        if (egg.getMetadata(OWNER_KEY) != null) {
            // new BukkitRunnable() {
            // @Override
            // public void run() {
            if (egg.isValid()) {
                // Bukkit.broadcastMessage(player.getName() + " arrow.isValid");

//				Location loc = egg.getLocation();
//				double explosionPower = explosionValue;
                // Bukkit.broadcastMessage("explosionPower = " + explosionValue);

//				Set<Block> blocksBefore = getBlocksInRadius(loc, explosionPower);
//				egg.getWorld().createExplosion(loc, (float) explosionPower, false, true); // GrÃ¶nis har rÃ¤tt :D Bort med denna rad. Tack :)
//				Set<Block> blocksAfter = getBlocksInRadius(loc, explosionPower);
//				blocksBefore.removeAll(blocksAfter);
//				int destroyedBlocks = blocksBefore.size();
//				Bukkit.broadcastMessage(player.getName() + " fÃ¶rstÃ¶rde block = " + blocksBefore.size());

//				damageMap.put(playerUUID, damageMap.getOrDefault(playerUUID, 0) + destroyedBlocks);
                egg.remove();
            }
            // }
            // }.runTaskLater(plugin, 20L); // Explosion sker efter X sekunder (100 ticks, 5s)
        }
    }

    @EventHandler
    public void onChickenEntityDamageEvent(EntityDamageEvent event) {
        if (!event.getEntityType().equals(EntityType.CHICKEN)) { return; }

        if (event.getCause().equals(DamageCause.SUFFOCATION)) {
            event.setCancelled(true);
        }

        if (event.getCause().equals(DamageCause.BLOCK_EXPLOSION)) {
            event.setCancelled(true);
        }

        if (event.getCause().equals(DamageCause.ENTITY_EXPLOSION)) {
            event.setCancelled(true);
        }
    }

    // NEDAN Ã„R FRÃ…N MinkensChickenBomb/Main.java


    @EventHandler
    public void onEggThrow(PlayerEggThrowEvent event) {
        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();

        Egg egg = event.getEgg();
        event.setHatching(false);

        if (event.getEgg().getItem().getPersistentDataContainer().has(BridgeEgg.BRIDGE_KEY)) {
            return;
        }

        Chicken chicken = egg.getWorld().spawn(egg.getLocation(), Chicken.class);

        chicken.setMetadata(OWNER_KEY, new FixedMetadataValue(Main.plugin, playerUUID.toString()));

//		boolean hasEggs = player.getInventory().contains(Material.EGG);
//		if (hasEggs == false) {
        // Om inga Ã¤gg kvar, vÃ¤nta 20 sekunder och Ã¶ppna adminmenyn
        // Blockera spelaren frÃ¥n andra interaktioner
//			awaitingHighscoreUpdate.add(playerUUID);

//			player.sendMessage(ChatColor.YELLOW + "[Server] InvÃ¤ntar kycklingexplosioner och berÃ¤knar poÃ¤ng ... vÃ¤nta 20 sekunder.");

//			new BukkitRunnable() {
//				@Override
//				public void run() {
//					// Ta bort spelaren frÃ¥n blockeringslistan
////					awaitingHighscoreUpdate.remove(playerUUID);
//
//					// Visa adminmenyn
////					openAdminMenu(player);
//				}
//			}.runTaskLater(Main.plugin, 400L); // VÃ¤nta 20 sekunder (400 ticks)
//		}

        new BukkitRunnable() {
            @Override
            public void run() {
                if (chicken.isValid()) {
                    Location loc = chicken.getLocation();
                    double explosionPower = explosionValue;

//					Set<Block> blocksBefore = getBlocksInRadius(loc, explosionPower);
                    chicken.getWorld().createExplosion(loc, (float) explosionPower, false, true);
//					Set<Block> blocksAfter = getBlocksInRadius(loc, explosionPower);
//					blocksBefore.removeAll(blocksAfter);
//					int destroyedBlocks = blocksBefore.size();

//					damageMap.put(playerUUID, damageMap.getOrDefault(playerUUID, 0) + destroyedBlocks);
                    chicken.remove();
                }
            }
        }.runTaskLater(Main.plugin, 100L); // Explosion sker efter 5 sekunder
    }






    //////////////////////////////////////////////////////////////////////////////////////////////////////////

    @EventHandler
    public void onPlayerRespawnEvent(PlayerRespawnEvent event) {
        if (!TntBow.autoGiveBow.contains(event.getPlayer()))  {
            return;
        }

        event.getPlayer().getInventory().addItem(EggBow);
        event.getPlayer().getInventory().addItem(new ItemStack (Material.ARROW, 1));
//		event.getPlayer().getInventory().addItem(new ItemStack (Material.EGG, 16)); NÃ¶.
    }

}