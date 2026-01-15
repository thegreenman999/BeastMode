package me.thegreenman.NOTinUSE;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import me.thegreenman.Main;

/*
 * FIXA: Spawna bara ny skeleton om den dog av en spelare (inte bara lava)
 * FIXA: Fördröjning innan spawn av ny skeleton
 * FIXA: Sätt max-antal spawnade skeletons, annars FPS = prosit
 *
 * 
 * 
 * 
 * 
 * [13:10:51 INFO]: Minken issued server command: /xp add Minken 1 levels
[13:10:51 WARN]: Target player is invalid or offline.
[13:10:51 ERROR]: Could not pass event PlayerCommandPreprocessEvent to MinkensPVPBeastMode v2025.06.10-1658
java.lang.NullPointerException: Cannot invoke "org.bukkit.entity.Player.getName()" because "closest" is null
        at MinkensPVPBeastMode.jar/org.minken.NOTinUSE.SkeletonTesting.onPlayerCommandPreprocess(SkeletonTesting.java:56) ~[MinkensPVPBeastMode.jar:?]
        at co.aikar.timings.TimedEventExecutor.execute(TimedEventExecutor.java:80) ~[paper-api-1.21.7-R0.1-SNAPSHOT.jar:?]
        at org.bukkit.plugin.RegisteredListener.callEvent(RegisteredListener.java:71) ~[paper-api-1.21.7-R0.1-SNAPSHOT.jar:?]
        at io.papermc.paper.plugin.manager.PaperEventManager.callEvent(PaperEventManager.java:54) ~[paper-1.21.7.jar:1.21.7-26-533d93c]
        at io.papermc.paper.plugin.manager.PaperPluginManagerImpl.callEvent(PaperPluginManagerImpl.java:131) ~[paper-1.21.7.jar:1.21.7-26-533d93c]
        at org.bukkit.plugin.SimplePluginManager.callEvent(SimplePluginManager.java:628) ~[paper-api-1.21.7-R0.1-SNAPSHOT.jar:?]
        at net.minecraft.server.network.ServerGamePacketListenerImpl.performUnsignedChatCommand(ServerGamePacketListenerImpl.java:2299) ~[paper-1.21.7.jar:1.21.7-26-533d93c]
        at net.minecraft.server.network.ServerGamePacketListenerImpl.lambda$handleChatCommand$12(ServerGamePacketListenerImpl.java:2286) ~[paper-1.21.7.jar:1.21.7-26-533d93c]
        at net.minecraft.server.TickTask.run(TickTask.java:18) ~[paper-1.21.7.jar:1.21.7-26-533d93c]
        at net.minecraft.util.thread.BlockableEventLoop.doRunTask(BlockableEventLoop.java:155) ~[paper-1.21.7.jar:1.21.7-26-533d93c]
        at net.minecraft.util.thread.ReentrantBlockableEventLoop.doRunTask(ReentrantBlockableEventLoop.java:24) ~[paper-1.21.7.jar:1.21.7-26-533d93c]
        at net.minecraft.server.MinecraftServer.doRunTask(MinecraftServer.java:1449) ~[paper-1.21.7.jar:1.21.7-26-533d93c]
        at net.minecraft.server.MinecraftServer.doRunTask(MinecraftServer.java:176) ~[paper-1.21.7.jar:1.21.7-26-533d93c]
        at net.minecraft.util.thread.BlockableEventLoop.pollTask(BlockableEventLoop.java:129) ~[paper-1.21.7.jar:1.21.7-26-533d93c]
        at net.minecraft.server.MinecraftServer.pollTaskInternal(MinecraftServer.java:1429) ~[paper-1.21.7.jar:1.21.7-26-533d93c]
        at net.minecraft.server.MinecraftServer.pollTask(MinecraftServer.java:1423) ~[paper-1.21.7.jar:1.21.7-26-533d93c]
        at net.minecraft.util.thread.BlockableEventLoop.managedBlock(BlockableEventLoop.java:139) ~[paper-1.21.7.jar:1.21.7-26-533d93c]
        at net.minecraft.server.MinecraftServer.managedBlock(MinecraftServer.java:1380) ~[paper-1.21.7.jar:1.21.7-26-533d93c]
        at net.minecraft.server.MinecraftServer.waitUntilNextTick(MinecraftServer.java:1388) ~[paper-1.21.7.jar:1.21.7-26-533d93c]
        at net.minecraft.server.MinecraftServer.runServer(MinecraftServer.java:1265) ~[paper-1.21.7.jar:1.21.7-26-533d93c]
        at net.minecraft.server.MinecraftServer.lambda$spin$2(MinecraftServer.java:310) ~[paper-1.21.7.jar:1.21.7-26-533d93c]
        at java.base/java.lang.Thread.run(Thread.java:1575) ~[?:?]

 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 */

public class SkeletonTesting implements Listener {

	public World world = Bukkit.getWorld("world");
	public Location pilbagstontLoc = new Location (world, -52, 50, -2);
	public Random random = new Random();
	public ItemStack pilbagstontBow = new ItemStack(Material.BOW, 1);

	public SkeletonTesting() {

//		ItemMeta pilbagstontBowMeta = pilbagstontBow.getItemMeta();
//		pilbagstontBowMeta.setDisplayName("Testar");
//		pilbagstontBowMeta.addEnchant(Enchantment.POWER, 255, true);
//		pilbagstontBowMeta.addEnchant(Enchantment.FLAME, 1, false);
//		pilbagstontBow.setItemMeta(pilbagstontBowMeta);
	}

	@EventHandler
	public void onPlayerCommandPreprocess (PlayerCommandPreprocessEvent event) {
		Player player = event.getPlayer();

		if (event.getMessage().startsWith("/x")) {
			if (!player.getName().contentEquals("Minken")) return;
			Player closest = findClosestPlayer(pilbagstontLoc);

			spawnSkeletonAndTargetPlayer(world, pilbagstontLoc, closest);
			Bukkit.broadcastMessage("Target: " + closest.getName());

			event.setCancelled(true);
		}
	}

	public void spawnSkeletonAndTargetPlayer(World world, Location location, Player targetPlayer) {
		// Ensure the target player is valid
		if (targetPlayer == null || !targetPlayer.isOnline()) {
			Bukkit.getLogger().warning("Target player is invalid or offline.");
			return;
		}

		// Define the location
		//	        Location location = new Location(world, x, y, z);

		// Spawn the skeleton
		Skeleton skeleton = (Skeleton) world.spawnEntity(location, EntityType.SKELETON);

		// Create a sword
		ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
		ItemMeta meta = sword.getItemMeta();
		if (meta != null) {
			meta.setDisplayName("Skeleton's Sword");

			sword.setItemMeta(meta);
		}

		// Equip the skeleton with the sword in the main hand
		//        skeleton.getEquipment().setItemInMainHand(sword);
		skeleton.getEquipment().setItemInMainHand(pilbagstontBow);
		skeleton.getEquipment().setItemInMainHandDropChance(0.0f); // Prevent the sword from being dropped on death

		// Remove the skeleton's default bow
		skeleton.getEquipment().setItemInOffHand(null);

		// Set the target to the player
		//		skeleton.setTarget(targetPlayer);
		skeleton.attack(targetPlayer);

		// Optional: Add custom attributes or behaviors
		skeleton.setCustomName("T: " + targetPlayer.getName());
		skeleton.setCustomNameVisible(true);

	}

	@EventHandler
	//	public void onProjectileLaunchEvent(ProjectileLaunchEvent event) {
	public void onProjectileHitEvent(ProjectileHitEvent event) {
		if (event.getHitEntity() == null) { return; }
		if (!(event.getEntity().getShooter() instanceof Skeleton)) { return; }
		if (!(event.getHitEntity() instanceof Player)) { return; }
		if (!(event.getEntity().getType().equals(EntityType.ARROW))) { return; }

		//		event.getEntity().getWorld().spawnEntity(event.getEntity().getLocation(), EntityType.TNT).setVelocity(event.getEntity().getVelocity());
		//		event.getEntity().remove();
		int kraft = 4; // random.nextInt(5) + 1;
		event.getEntity().getWorld().createExplosion(event.getEntity().getLocation(), kraft, true);
	}

	@EventHandler
	public void onEntityDeathEvent (EntityDeathEvent event) {
		if (event.getEntity() instanceof Player) { return; }
		if (!(event.getEntity() instanceof Skeleton)) { return; }

		event.getEntity().remove();
		// TODO: Kolla antalet skeletons först... prosit!
		spawnSkeletonAndTargetPlayer(world, pilbagstontLoc, findClosestPlayer(pilbagstontLoc));
		//		spawnSkeletonAndTargetPlayer(world, pilbagstontLoc, findClosestPlayer(pilbagstontLoc));
	}

	@EventHandler
	public void onEntityShootBowEvent (EntityShootBowEvent event) {
		if (!(event.getEntity() instanceof Skeleton)) return;
		new BukkitRunnable() {
			@Override
			public void run() {
				if (event.getEntity().getType() == EntityType.ARROW) {
					event.getEntity().remove();
				}
			}
		}.runTaskLater(Main.plugin, 20);
	}

	//	@EventHandler
	//	public void tntBowUsage (ProjectileHitEvent event) {
	//		if (event.getEntity() instanceof org.bukkit.entity.Arrow) {
	//			Player player = (Player)event.getEntity().getShooter();
	//						Location location = event.getEntity().getLocation();
	////			if (!player.getItemInUse().getItemMeta().getDisplayName().contentEquals(specialItemName)) return;
	//			event.getEntity().getWorld().spawnEntity(event.getEntity().getLocation(), EntityType.PRIMED_TNT);
	//			event.getEntity().remove();
	//		}
	//	}

	//	@EventHandler
	//	public void tntBowUsage (ProjectileLaunchEvent event) { // DO NOT USE ProjectileHitEvent, creates loop of TNT's blowing up the server
	//		if (!(event.getEntity().getShooter() instanceof Skeleton)) return;
	//		if (!(event.getEntity() instanceof Arrow)) return;
	//		
	//		EntityType tntp = (EntityType) tntp;
	//		EntityType tntn = new TNTPrimed(); // (org.bukkit.entity.TNTPrimed);
	//		
	//		Skeleton skelly = (Skeleton) event.getEntity().getShooter();
	//		skelly.getServer().broadcastMessage("DEBUG: " + event.getEntity().getShooter() + " used " + skelly.getItemInUse().getItemMeta().getDisplayName());
	//
	////		if (!skelly.getItemInUse().getItemMeta().getDisplayName().contentEquals(specialItemName))
	////		{
	////			skelly.getServer().broadcastMessage("DEBUG: " + event.getEntity().getShooter() + " used wrong item.");
	////			return;
	////		}
	//		event.getEntity().getWorld().spawnEntity(event.getEntity().getLocation(), tntn); // .TNTPrimed);
	//		event.getEntity().getWorld().spawnEntity(event.getEntity().getLocation(), tntn);
	////					event.getEntity().remove();
	//	}



	public Player findClosestPlayer(Location loc) {
		Player closest = null;
		double minDistance = 2147483647;
		double distance = 0;

		for (Player player : Main.plugin.getServer().getOnlinePlayers()) {
			if (player.getWorld().equals(loc.getWorld())) {
				if (!player.getGameMode().equals(GameMode.SURVIVAL)) continue; // TODO FIXA: Om INGEN är i survival = EXCEPTION! 

				distance = player.getLocation().distance(loc);
				if (distance < minDistance) {
					minDistance = distance;
					closest = player;
				}
			}
		}

		if (closest != null) { return closest; }
		else
		{
			return null;
		}
	}
}
