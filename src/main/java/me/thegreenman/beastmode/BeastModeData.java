package me.thegreenman.beastmode;

import org.bukkit.Difficulty;
import org.bukkit.Location;

import java.util.List;
import java.util.Random;

import static me.thegreenman.Main.WORLD;

public class BeastModeData {
	public static Random random = new Random();

	public static int startHP = 20;
	public static int startFoodlevel = 20;

/*
	  public static boolean spawnHacksTraderInTheMiddle = true;
	  public static boolean reuseDeadLavaItems = true;
*/
    /////////// MAPS ////////////////
    public static int selectedMapindex = 0;
    public static List<String> Maps = List.of(
            "Original map",
            "Kluddis castle"
    );

    public static void loadVanillaMap() {
        villagerBowLocation = new Location[]{
                new Location(WORLD, 59.5, 53, -8.5, 90, 14), // GRÖN
                new Location(WORLD, -8.5, 53, -62.5, 0, 14), // RÖD
                new Location(WORLD, -62.5, 53, 5.5, -90, 14), // BLÅ
                new Location(WORLD, 5.5, 53, 59.5, -180, 14) // ROSA
        };
        villagerSwordLocation = new Location[]{
                new Location(WORLD, 60.5, 53, -6.5, 90, 14), // GRÖN
                new Location(WORLD, -6.5, 53, -63.5, 0, 14), // RÖD
                new Location(WORLD, -63.5, 53, 3.5, -90, 14), // BLÅ
                new Location(WORLD, 3.5, 53, 60.5, -180, 14) // ROSA
        };
        villagerArmorLocation = new Location[]{
                new Location(WORLD, 61.5, 53, -4.5, 90, 14), // GRÖN
                new Location(WORLD, -4.5, 53, -64.5, 0, 14), // RÖD
                new Location(WORLD, -64.5, 53, 1.5, -90, 14), // BLÅ
                new Location(WORLD, 1.5, 53, 61.5, -180, 14) // ROSA
        };
        villagerFoodLocation = new Location[]{
                new Location(WORLD, 61.5, 53, 1.5, 90, 14), // GRÖN
                new Location(WORLD, 1.5, 53, -64.5, 0, 14), // RÖD
                new Location(WORLD, -64.5, 53, -4.5, -90, 14), // BLÅ
                new Location(WORLD, -4.5, 53, 61.5, -180, 14) // ROSA
        };
        villagerBlocksLocation = new Location[]{
                new Location(WORLD, 60.5, 53, 3.5, 90, 14), // GRÖN
                new Location(WORLD, 3.5, 53, -63.5, 0, 14), // RÖD
                new Location(WORLD, -63.5, 53, -6.5, -90, 14), // BLÅ
                new Location(WORLD, -6.5, 53, 60.5, -180, 14) // ROSA
        };
        villagerPotionLocation = new Location[]{
                new Location(WORLD, 59.5, 53, 5.5, 90, 14), // GRÖN
                new Location(WORLD, 5.5, 53, -62.5, 0, 14), // RÖD
                new Location(WORLD, -62.5, 53, -8.5, -90, 14), // BLÅ
                new Location(WORLD, -8.5, 53, 59.5, -180, 14) // ROSA
        };

        villagerMiddleLocation = new Location (WORLD, -1.5, 47, -1.5);

    }

    public static Location[] villagerBowLocation = new Location[]{
            new Location(WORLD, 53.5, 92, 283.5, 90, 14), // GRÖN
            new Location(WORLD, 11.5, 80, 225.5, 0, 14), // RÖD
            new Location(WORLD, 77.5, 75, 230.5, 90, 14), // BLÅ
            new Location(WORLD, 54.5, 75, 177.5, 0, 14) // ROSA
    };
    public static Location[] villagerSwordLocation = new Location[]{
            new Location(WORLD, 48.5, 92, 281.5, -90, 14), // GRÖN
            new Location(WORLD, 9.5, 80, 230.5, -180, 14), // RÖD
            new Location(WORLD, 72.5, 75, 228.5, -90, 14), // BLÅ
            new Location(WORLD, 52.5, 75, 182.5, -180, 14) // ROSA
    };
    public static Location[] villagerArmorLocation = new Location[]{
            new Location(WORLD, 48.5, 92, 285.5, -90, 14), // GRÖN
            new Location(WORLD, 13.5, 80, 230.5, -180, 14), // RÖD
            new Location(WORLD, 72.5, 75, 232.5, -90, 14), // BLÅ
            new Location(WORLD, 56.5, 75, 182.5, -180, 14) // ROSA
    };
    public static Location[] villagerFoodLocation = new Location[]{
            new Location(WORLD, 53.5, 92, 281.5, 90, 14), // GRÖN
            new Location(WORLD, 9.5, 80, 225.5, 0, 14), // RÖD
            new Location(WORLD, 77.5, 75, 228.5, 90, 14), // BLÅ
            new Location(WORLD, 52.5, 75, 177.5, 0, 14) // ROSA
    };
    public static Location[] villagerBlocksLocation = new Location[]{
            new Location(WORLD, 48.5, 92, 283.5, -90, 14), // GRÖN
            new Location(WORLD, 11.5, 80, 230.5, -180, 14), // RÖD
            new Location(WORLD, 72.5, 75, 230.5, -90, 14), // BLÅ
            new Location(WORLD, 54.5, 75, 182.5, -180, 14) // ROSA
    };
    public static Location[] villagerPotionLocation = new Location[]{
            new Location(WORLD, 53.5, 92, 285.5, 90, 14), // GRÖN
            new Location(WORLD, 13.5, 80, 225.5, 0, 14), // RÖD
            new Location(WORLD, 77.5, 75, 232.5, 90, 14), // BLÅ
            new Location(WORLD, 56.5, 75, 177.5, 0, 14) // ROSA
    };
    public static Location villagerMiddleLocation = new Location(WORLD, 51.5, 115, 229.5);

    public static Location[] greenTeamSpawn = new Location[]{
        new Location(WORLD, 59.5, 52, -1.5, 90, 14), // vanilla Map
        new Location(WORLD, 48, 92, 289, 90, 14) // kluddes map
    };
    public static Location[] pinkTeamSpawn = new Location[]{
            new Location(WORLD, -1.5, 52, 59.5, 180, 14), // vanilla Map
            new Location(WORLD, 59, 75, 177, 180, 14) // kluddes map
    };
    public static Location[] blueTeamSpawn = new Location[]{
            new Location(WORLD, -62.5, 52, -1.5, -90, 14), // vanilla Map
            new Location(WORLD, 75, 80, 236, -90, 14) // kluddes map
    };
    public static Location[] redTeamSpawn = new Location[]{
            new Location(WORLD, -1.5, 52, -62.5, 0, 14), // vanilla Map
            new Location(WORLD, 19, 80, 227, 0, 14) // kluddes map
    };


    public static boolean glowEnabled = false;
    public static List<String> GlowTimeLore = List.of(
            "Always",
            "15min",
            "10min",
            "5min",
            "2min",
            "Never"
    );
    public static int selectedGlowtime = 5;

	public static boolean gameIsRunning = false;
	public static boolean minkenEditionThusFalseIsOriginalRules = true; // false = Originalregler, true = Minken Edition
	public static boolean freeStartBow = true;
	public static boolean freeStartBlocks = true;

	public static boolean includeSuperSnowball = false;
	public static boolean returnDeathItems = true;
	
	public static boolean bedsCanBePickedUp = true;

	public static int middleDiamondDropDelayInSeconds = 30;

    // middle trades prices
	public static int diaForShield = 2;
	public static int diaForWaterBucket = 1;
	public static int diaForSuperGoldenApple = 10;
	public static int diaForEnderPearl = 3;
	public static int diaForAddeBow = 10;
	public static int goldsForOneDia = 3;
	public static int diaForMace = 10;
	public static int goldForWindCharge = 2;

	public static int countdown = 3;

	public static final String[] StylizedTime = {"❶","❷","❸","❹","❺","❻","❼","❽","❾","❿"}; // ,".",".","..","..","...","...","....","....",".....","....."


    // Game Rules
    public static boolean SpawnMobs = false;
    public static boolean AnnounceAdvancements = false;
    public static Difficulty Difficulty = org.bukkit.Difficulty.HARD;


    // TNT Bow
    public static int TNTBowfuseTime = 20;
    public static int TNTBowCoolDown = 1;
    public static int TNTBowExplosionPower = 4;
    public static boolean TNTBowGlowingTnt = false;
    public static float TNTBowMaxDamage = 10.0f;
    public static boolean TNTBowKeepOnDeath = false;


}
