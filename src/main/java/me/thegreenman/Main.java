package me.thegreenman;

import me.thegreenman.beastmode.minkenedition.*;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import me.thegreenman.beastmode.EventListenersFromOtherClasses;
import me.thegreenman.beastmode.PlayerData;
import me.thegreenman.beastmode.PlayerDataHandler;
import me.thegreenman.beastmode.commands.MinkenMenu;
import me.thegreenman.beastmode.commands.StartMenu;
import me.thegreenman.beastmode.commands.StatsMenu;
import me.thegreenman.beastmode.commands.FullBright;
import me.thegreenman.beastmode.testing.FireResistanceOnDiamonds;

/*
PVP BEAST MODE

COMMAND BLOCKS VID TEAM-SELECTION
RED SKA VARA:   /tp @a[distance=..7] -1.5 52 -62.5 0 14
GREEN SKA VARA: /tp @a[distance=..7] 59.5 52 -1.5 90 14
PINK SKA VARA:  /tp @a[distance=..7] -1.5 52 59.5 180 14
BLUE SKA VARA:  /tp @a[distance=..7] -62.5 52 -1.5 -90 14

TO DO:

 * ALICE: TIMER PÅ/för RESPAWN, TYP SÅ SOM HYPIXEL(?). 5 sekunder.

 * Delvis fixat: ORGANISERA KLASSERNA, DELA UPP FEATURES TILL KLASSNAMN (typ StartCommand) !



FIXAT: FullBright för alla eftersom alla redan använder det... = Night Vision (INF)
ERROR: onGameModeChange -> Adventure -> Ge Spectator

Fixat: /minken kan toggla om varje spelare (respektive spawnpoint) får elytra/whatever via /minken
FIXAT: Gör om Trading-systemet till vanliga items som inte kräver speciell NBT-tagg:

Fixa commandblock för att spawna fram en villager för trading: 

BLUE Bow Villager Location: -63 53 5
YELLOW Bow Villager Location: 5 53 60
GREEN Bow Villager Location: 59 53 -9
RED Bow Villager Location: -9 53 -62

2) Fixa menyalternativ för att (in)aktivera fall damage, förslagsvis via itemet Iron_Boots på lämplig plats i Admin-menyn.
3) Fixa menyalternativ för att starta nedräkningen av klockan. Förslagsvis en Klass?Metod? som heter Start.

Fixat: Alla spelare blir healade vid /start

 */

public class Main extends JavaPlugin implements Listener {
	public static JavaPlugin plugin;
    public static World WORLD;
    public static Boolean DEBUG = false;

	public static String pvpbm = "[BeastMode] ";

	@Override
	public void onEnable() {
        plugin = this;


        if(!getDataFolder().exists()) {
            getDataFolder().mkdir();
            saveDefaultConfig();
        }

        PlayerData.loadConfig();

        WORLD = Bukkit.getWorld("world");

//        loadConfigs();




		StartMenu StartMenu = new StartMenu();
		getCommand("start").setExecutor(StartMenu);
		getServer().getPluginManager().registerEvents(StartMenu, this);
		
		MinkenMenu minkenkommandot = new MinkenMenu();
		getCommand("minken").setExecutor(minkenkommandot);
		getServer().getPluginManager().registerEvents(minkenkommandot, this);

		getServer().getPluginManager().registerEvents(new EventListenersFromOtherClasses(), this);


		getServer().getPluginManager().registerEvents(new ReturnDeathItems(), this);
		getServer().getPluginManager().registerEvents(new PreventGreif(), this);
		getServer().getPluginManager().registerEvents(new RemoveMetaOnItems(), this);
		getServer().getPluginManager().registerEvents(new FireResistanceOnDiamonds(), this);


		// CLOAK
		InvisibilityDye cloakkommandot = new InvisibilityDye();
		getCommand("addcloak").setExecutor(cloakkommandot);
		getServer().getPluginManager().registerEvents(cloakkommandot, this);
		// END OF CLOAK


        FullBright fullBright = new FullBright();
        getCommand("fullbright").setExecutor(fullBright);
        getServer().getPluginManager().registerEvents(fullBright, this);

        TntBow tntBow = new TntBow();
        getCommand("tntbow").setExecutor(tntBow);
        getServer().getPluginManager().registerEvents(tntBow, this);

        StatsMenu stats = new StatsMenu();
        getCommand("stats").setExecutor(stats);
		getCommand("stats").setTabCompleter(stats);
        getServer().getPluginManager().registerEvents(stats, this);
        getServer().getPluginManager().registerEvents(new PlayerDataHandler(), this);

        BridgeEgg bridgeEgg = new BridgeEgg();
        getCommand("bridgeegg").setExecutor(bridgeEgg);
        getServer().getPluginManager().registerEvents(bridgeEgg, this);

        getServer().getPluginManager().registerEvents(new TNTBowEgg(), this);
        getServer().getPluginManager().registerEvents(new FlameBowFire(), this);
        getServer().getPluginManager().registerEvents(new WoolGameBoots(), this);
        getServer().getPluginManager().registerEvents(new VoidFix(), this);



        new BukkitRunnable() {
			@Override
			public void run() {
				Main.log("Setting GameRule LOCATOR_BAR to FALSE.");
				WORLD.setGameRule(GameRule.LOCATOR_BAR, false);
				Main.log("Setting Difficulty to HARD.");
				WORLD.setDifficulty(Difficulty.HARD);
				Main.log("Setting GameRule IMMEDIATE_RESPAWN to TRUE.");
				WORLD.setGameRule(GameRule.DO_IMMEDIATE_RESPAWN, true);
                Main.log("Setting GameRule ANNOUNCE_ADVANCEMENTS to FALSE.");
                WORLD.setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false);
                Main.log("Setting GameRule DO_DAYLIGHT_CYCLE to FALSE.");
				WORLD.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
                Main.log("Setting GameRule PLAYERS_SLEEPING_PERCENTAGE to 200.");
                WORLD.setGameRule(GameRule.PLAYERS_SLEEPING_PERCENTAGE, 200);
                WORLD.setTime(1000);
                WORLD.setGameRule(GameRule.DO_MOB_SPAWNING, false);
				

				// Main.log("Setting Spawn Location in World to -232, 51, 147");
				// Bukkit.getWorld("world").setSpawnLocation(-232, 51, 147); // /tp Minken -231.5 51 147.5
			}
		}.runTaskLater(plugin, 40);

		log("MinkensPVPBeastMode Enabled!");
	}

	@Override
	public void onDisable() {
//		saveChestFile();
		log("MinkensPVPBeastMode Disabled!");
        PlayerData.saveAll();

	}

	public static void log(String msg) {
//		Logger.getLogger("Minecraft").log(Level.INFO, "[" + plugin.getDescription().getFullName() + "] " + msg);
		Main.plugin.getLogger().info(msg);
	}

    public static void severe(String msg) {
//		Logger.getLogger("Minecraft").log(Level.INFO, "[" + plugin.getDescription().getFullName() + "] " + msg);
        Main.plugin.getLogger().severe(msg);
    }


//    public void loadConfigs() {
//        // kluddisMap config
//        File kluddisMapFile = new File(getDataFolder(), "kluddisMap.yml");
//        if (!kluddisMapFile.exists()) {
//            severe("kluddisMap.yml dos not exists");
//        }
//        BeastModeData.kluddisMap = YamlConfiguration.loadConfiguration(kluddisMapFile);
//
//        // vanillaMap config
//        File vanillaMapfile = new File(getDataFolder(), "vanillaMap.yml");
//        if (!kluddisMapFile.exists()) {
//            severe("vanillaMap.yml dos not exists");
//        }
//        BeastModeData.vanillaMap = YamlConfiguration.loadConfiguration(vanillaMapfile);
//
//        log("loaded Configs");
//
//        log(String.valueOf(BeastModeData.vanillaMap.getDouble("villagerBowLocation.green.x")));
//    }


	////////////////////////////////////////////// Konstruktor //////////////////////////////////////////////
	public Main() {

	}

//	public static ScreBrd getScoreboard() {
//		return scoreboard;
//	}


	//////////////////////////////////////////////POSSIBLECHESTLOCATIONS //////////////////////////////////

//	private void loadChestFile() {
//		chestFile = new File(getDataFolder(), "chests.yml");
//		if (!chestFile.exists()) {
//			saveResource("chests.yml", false);
//		}
//		chestConfig = YamlConfiguration.loadConfiguration(chestFile);
//	}

//	public static void saveChestFile() {
//		try {
//			chestConfig.save(chestFile);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}

	////////////////////////////////////////////// END //////////////////////////////////////////////
}
