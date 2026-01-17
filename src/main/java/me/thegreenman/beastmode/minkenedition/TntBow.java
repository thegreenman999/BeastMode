package me.thegreenman.beastmode.minkenedition;

import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.TNTPrimeEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.components.UseCooldownComponent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import me.thegreenman.Main;
import me.thegreenman.beastmode.BeastModeData;

import java.util.ArrayList;
import java.util.List;

public class TntBow implements Listener, CommandExecutor, TabCompleter {

    public static List<Player> autoGiveBow = new ArrayList<>();

    public static ItemStack TNTBOW = new ItemStack(Material.BOW) {{
        ItemMeta meta = this.getItemMeta();
        meta.itemName(Component.text("§4T§cN§4T §fbow"));
        meta.lore(List.of(Component.text("§7Shots a tnt Arrow")));
        meta.addEnchant(Enchantment.INFINITY, 1, true);
        this.setItemMeta(meta);
    }};

    public static void setCoolDowntime(float coolDowntime) {
        ItemMeta meta = TNTBOW.getItemMeta();
        UseCooldownComponent cooldownComponent = meta.getUseCooldown();
        cooldownComponent.setCooldownSeconds(coolDowntime);
        meta.setUseCooldown(cooldownComponent);
        TNTBOW.setItemMeta(meta);
    }

    @EventHandler
    public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
        if (event.getDamager().getType().equals(EntityType.TNT) && event.getDamage() > BeastModeData.TNTBowMaxDamage) {
            event.setDamage(BeastModeData.TNTBowMaxDamage);
        }
    }

    @EventHandler
    public void onProjectileLaunchEvent(ProjectileLaunchEvent event) {
        if (!(event.getEntity() instanceof Arrow arrow)) { return; }
        if (!(arrow.getShooter() instanceof Player player)) { return; }

        ItemStack bow = player.getInventory().getItemInMainHand();
        if (bow.getType() != Material.BOW) {
            bow = player.getInventory().getItemInOffHand();
        }
        if (bow.getType() != Material.BOW) {
            return;
        }

        if (!bow.getItemMeta().itemName().equals(Component.text("§4T§cN§4T §fbow"))) { return; }


        // Arrow arrow = (Arrow) event.getEntity();
        // arrow.setCustomName("TNTBow");
        // arrow.setCustomNameVisible(false);

        NamespacedKey key = new NamespacedKey(Main.plugin, "tnt_arrow");
        arrow.getPersistentDataContainer().set(key, PersistentDataType.STRING, "TNTBow");

    }

    @EventHandler
    public void onProjectileHitEvent(ProjectileHitEvent event) {
        if (!(event.getEntity() instanceof Arrow arrow)) {
            return;
        }

        if (event.getEntity().getShooter() == null) { return; }
        if (!(event.getEntity().getShooter() instanceof Player player)) { return; }

        NamespacedKey key = new NamespacedKey(Main.plugin, "tnt_arrow");

        if (!arrow.getPersistentDataContainer().has(key, PersistentDataType.STRING)) { return; }

        String value = arrow.getPersistentDataContainer().get(key, PersistentDataType.STRING);
        if ("TNTBow".equals(value)) {
            Location location = arrow.getLocation();
            arrow.remove();
            TNTPrimed tnt = location.getWorld().spawn(location, TNTPrimed.class);
            tnt.setSource(player);
            tnt.setFuseTicks(BeastModeData.TNTBowfuseTime);
            tnt.setGlowing(BeastModeData.TNTBowGlowingTnt);
            tnt.setVelocity(new Vector(0,0,0));
            tnt.setYield(BeastModeData.TNTBowExplosionPower);
        }

    }

    @EventHandler
    public void onPlayerRespawnEvent(PlayerRespawnEvent event) {
        if (!autoGiveBow.contains(event.getPlayer()))  {
            return;
        }

        event.getPlayer().getInventory().addItem(TNTBOW);
    }


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!(sender instanceof Player player)) {
            return false;
        }

        if (args.length == 0) {
            if (!BeastModeData.gameIsRunning) {
                if (autoGiveBow.contains(player)) {
                    autoGiveBow.remove(player);
                    player.sendMessage(Component.text("Tog bort att du inte får TNT bow vid start"));
                } else {
                    autoGiveBow.add(player);
                    player.sendMessage(Component.text("Du får nu TNT bow vid start"));
                }
            } else {
                player.getInventory().addItem(TNTBOW);
                if (!player.getInventory().contains(Material.ARROW)) {
                    player.getInventory().addItem(ItemStack.of(Material.ARROW, 1));
                }
            }
        }
        else if (args.length == 1) {
            if (args[0].equals("force")) {
                if (player.hasPermission("beastmode.tntbow.force")) {
                    if (!autoGiveBow.contains(player)) {
                        autoGiveBow.add(player);
                    }
                    else {
                        autoGiveBow.remove(player);
                    }
                }
            }
            else if (Bukkit.getOfflinePlayer(args[0]).isOnline()) {
                if (Bukkit.getPlayer(args[0]) == null){
                    return false;
                }
                if (player.hasPermission("beastmode.tntbow.forceothers")) {
                    Bukkit.getPlayer(args[0]).getInventory().addItem(TNTBOW);
                    if (!Bukkit.getPlayer(args[0]).getInventory().contains(Material.ARROW)) {
                        Bukkit.getPlayer(args[0]).getInventory().addItem(ItemStack.of(Material.ARROW, 1));
                    }
                }
            }
        }
        else if (args.length == 2 && args[0].equals("force")) {
            if (Bukkit.getOfflinePlayer(args[1]).isOnline()) {
                if (player.hasPermission("beastmode.tntbow.forceothers")) {
                    if (!autoGiveBow.contains(Bukkit.getPlayer(args[1]))) {
                        autoGiveBow.add(Bukkit.getPlayer(args[1]));
                    }
                    else {
                        autoGiveBow.remove(Bukkit.getPlayer(args[1]));
                    }
                }
            }
        }

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!(sender instanceof Player player)) {
            return List.of();
        }
        List<String> list = new ArrayList<>();

        if (args.length == 1){
            if (player.hasPermission("beastmode.tntbow.force")) {
                list.add("force");
            }
            if (player.hasPermission("beastmode.tntbow.others")) {
                for (Player player1 : Bukkit.getOnlinePlayers()) {
                    list.add(player1.getName());
                }
            }
        }
        if (args.length == 2) {
            if (player.hasPermission("beastmode.tntbow.forceothers")) {
                for (Player player1 : Bukkit.getOnlinePlayers()) {
                    list.add(player1.getName());
                }
            }
        }

        return list;
    }
}
