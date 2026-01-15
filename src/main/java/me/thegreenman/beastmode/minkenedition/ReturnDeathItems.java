package me.thegreenman.beastmode.minkenedition;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import me.thegreenman.Main;
import me.thegreenman.beastmode.BeastModeData;

public class ReturnDeathItems implements Listener {
//	public List<Player> playerWhosItemsShouldBeDistributed = new ArrayList<Player>();
	public List<ItemStack> itemsToDistribute = new ArrayList<>();
	//	public List<Player> randomPlayer = new ArrayList<Player>();

    BukkitTask Distributer = null;

	Random random = new Random();

	@EventHandler
	public void onPlayerDeathEvent (PlayerDeathEvent event) {
		if (!BeastModeData.returnDeathItems) {

            if (BeastModeData.TNTBowKeepOnDeath){
                event.getDrops().remove(TntBow.TNTBOW);
            }
            return;
        }
		// else BeastModeData.returnDeathItems == true...:
		
//		Player player = (Player) event.getEntity();

//		if (!playerWhosItemsShouldBeDistributed.contains(player)) { return; }

		List<ItemStack> copyOfEventDrops = event.getDrops();

		for (ItemStack eachItem : copyOfEventDrops) {
            if (BeastModeData.TNTBowKeepOnDeath && eachItem.equals(TntBow.TNTBOW)) {
                continue;
            }
			itemsToDistribute.add(eachItem);
			Main.log("DRUTT: Adding " + eachItem + " to itemsToDistribute (size=" + itemsToDistribute.size() + ").");
			// Main.log("DRUTT: Adding " + eachItem + " to itemsToDistribute (size=" + itemsToDistribute.size() + ").");
		}

//		playerWhosItemsShouldBeDistributed.remove(player);
        event.getDrops().clear();

        Distributer = new BukkitRunnable() {
			@Override
			public void run() {
				distributeItems();
			}
		}.runTaskLater(Main.plugin, 20);
	}

//	@EventHandler
//	public void onx(Event event) {
//	}
	
//	@EventHandler
//	public void onEntityDamageEvent(EntityDamageEvent event) {
//		if (!(event.getEntity() instanceof Player)) return;
//		Player player = (Player) event.getEntity();
//		if (!(event.getDamage() == 0.0))
//		{
//			Main.log("[DRUTT] " + player.getName() + " took " + event.getDamage() + " damage (" + event.getCause().toString() + ")");

//			if (!playerWhosItemsShouldBeDistributed.contains(player)) {
//				Main.log("DRUTT: Adding player " + player.getName() + " to playerWhosItemsShouldBeDistributed (size=" + playerWhosItemsShouldBeDistributed.size() + ")");
//				playerWhosItemsShouldBeDistributed.add(player); }

			//			if (event.getCause().equals(DamageCause.LAVA)) {
			//				if (!playerWhosItemsShouldBeDistributed.contains(player)) {
			//					Main.log("DRUTT: Adding player " + player.getName() + " to playerWhosItemsShouldBeDistributed (size=" + playerWhosItemsShouldBeDistributed.size() + ")");
			//					playerWhosItemsShouldBeDistributed.add(player); }
			//				else
			//				{
			//					// Main.log("DRUTT: Player " + player.getName() + " is already in playerWhosItemsShouldBeDistributed.");
			//				}
			//			}
//		}
//	}

	// randomiza 50% item kastas eller återges till random spelare, som är i SURVIVAL
	public void distributeItems() {
		//		for (Player pl : Bukkit.getOnlinePlayers()) {
		//			if (!pl.getGameMode().equals(GameMode.SURVIVAL)) {
		//				Main.log("[DRUTT] Ignoring " + pl.getName() + " because of GM = " + pl.getGameMode().toString() + ".");
		//				return; // continue; } ?? 
		//			} 
		//
		//			Main.log("[DRUTT] Adding " + pl.getName() + " to randomPlayer.");
		//			randomPlayer.add(pl);
		//		}

		if (itemsToDistribute == null) { return; }

		for (ItemStack is : itemsToDistribute) {
			if (!(is == null)) {
//				boolean kasta = random.nextBoolean();
				boolean kasta = false;

				//				Main.log("DRUTT: kasta (item " + is + ") = " + kasta);

				if (kasta) {
					// itemsToDistribute.remove(is);
					Main.log("[DRUTT] Discarding " + is);

				}
				else {
					Player randomPlayer = getRandomSurvivalPlayer();
					if (!(randomPlayer == null))
					{
						randomPlayer.getInventory().addItem(is);
						Main.log("DRUTT return: I gave " + randomPlayer.getName() + " " + is);	
					}
					else {
						Main.log("DRUTT: There are no players who can get free returned items :(");
					}
				}
			}
		}

		itemsToDistribute.clear();

	}

	private Player getRandomSurvivalPlayer() {
		// Filter players in SURVIVAL mode
		List<Player> survivalPlayers = Bukkit.getOnlinePlayers().stream().filter(player -> player.getGameMode() == GameMode.SURVIVAL).collect(Collectors.toList());

		if (survivalPlayers.isEmpty()) {
			return null;
		}

		// Pick one randomly
		Random random = new Random();
		return survivalPlayers.get(random.nextInt(survivalPlayers.size()));
	}
}
