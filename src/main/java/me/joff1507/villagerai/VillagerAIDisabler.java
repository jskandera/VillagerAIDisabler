/*
 * Plugin: VillagerAIDisabler
 * Version: 1.0
 * Description: Désactive l'IA des villageois selon la config.
 * Auteur: joff1507 & ChatGPT
 */

package me.joff1507.villagerai;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import java.util.List;

public class VillagerAIDisabler extends JavaPlugin {
    private FileConfiguration config;
    private final String PERM_COMMAND = "villagerai.use";
    private final String PERM_EXCLUDE = "villagerai.ignore";

    @Override
    public void onEnable() {
        saveDefaultConfig(); // Crée le fichier config.yml si inexistant
        loadConfig();

        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[VillagerAIDisabler] Plugin v1.0 chargé. Auteur: joff1507 & ChatGPT");

        new BukkitRunnable() {
            @Override
            public void run() {
                disableVillagerAI();
            }
        }.runTaskTimer(this, 0L, 100L); // toutes les 5 secondes
    }

    private void loadConfig() {
        this.config = getConfig(); // Charge la configuration depuis le fichier
    }

    public void disableVillagerAI() {
        boolean disableAll = config.getBoolean("disable-by-default", true);
        List<String> excludedProfessions = config.getStringList("excluded-professions");
        List<String> excludedNames = config.getStringList("excluded-names");
        int distanceThreshold = config.getInt("distance-from-player-threshold", 20);

        for (Villager villager : Bukkit.getWorlds().get(0).getEntitiesByClass(Villager.class)) {
            boolean shouldSkip = false;

            if (excludedProfessions.contains(villager.getProfession().name().toLowerCase()))
                shouldSkip = true;

            if (villager.getCustomName() != null && excludedNames.contains(villager.getCustomName()))
                shouldSkip = true;

            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player.hasPermission(PERM_EXCLUDE)) continue;
                if (player.getLocation().distance(villager.getLocation()) < distanceThreshold) {
                    shouldSkip = true;
                    break;
                }
            }

            villager.setAI(!disableAll || shouldSkip);
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("villagerai")) {
            if (!sender.hasPermission(PERM_COMMAND)) {
                sender.sendMessage(ChatColor.RED + "Vous n'avez pas la permission d'utiliser cette commande.");
                return true;
            }

            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("reload")) {
                    reloadConfig();
                    loadConfig(); // Utiliser loadConfig()
                    sender.sendMessage(ChatColor.YELLOW + "[VillagerAIDisabler] Config rechargée.");
                    return true;
                }
                if (args[0].equalsIgnoreCase("version")) {
                    sender.sendMessage(ChatColor.AQUA + "VillagerAIDisabler v1.0");
                    sender.sendMessage(ChatColor.GRAY + "Développé par joff1507 & ChatGPT");
                    return true;
                }
                if (args[0].equalsIgnoreCase("enable") || args[0].equalsIgnoreCase("disable")) {
                    if (sender instanceof Player player) {
                        // Ray tracing pour obtenir l'entité ciblée
                        Entity target = null;
                        for (Entity entity : player.getNearbyEntities(10, 10, 10)) {
                            if (entity instanceof Villager && player.hasLineOfSight(entity)) {
                                target = entity;
                                break;
                            }
                        }

                        if (target instanceof Villager villager) {
                            villager.setAI(args[0].equalsIgnoreCase("enable"));
                            player.sendMessage(ChatColor.YELLOW + "IA du villageois " + (villager.hasAI() ? "activée." : "désactivée."));
                        } else {
                            player.sendMessage(ChatColor.RED + "Regardez un villageois pour cela.");
                        }
                    } else {
                        sender.sendMessage("Cette commande est réservée aux joueurs.");
                    }
                    return true;
                }
            }
        }
        return false;
    }
}