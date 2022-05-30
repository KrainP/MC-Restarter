package eu.pkdb.restartplugin;

import eu.pkdb.restartplugin.commands.RestartCommand;
import eu.pkdb.restartplugin.timer.RestartCounter;
import eu.pkdb.restartplugin.timer.RestartTimer;
import eu.pkdb.restartplugin.utils.ConsoleUtil;
import eu.pkdb.restartplugin.utils.Constants;
import eu.pkdb.restartplugin.utils.DateUtil;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Timer;
import java.util.logging.Level;

public final class RestartPlugin extends JavaPlugin {
    private File customConfigFile;
    private FileConfiguration customConfig;

    @Override
    public void onEnable() {
        initStartup();

        LocalDateTime now = LocalDateTime.now();

        LocalDateTime restartTime = now.plusHours(this.customConfig.isSet(Constants.HOURS_CFG) ? this.customConfig.getLong(Constants.HOURS_CFG) : 4);
        restartTime = restartTime.plusMinutes(this.customConfig.isSet(Constants.MINS_CFG) ? this.customConfig.getLong(Constants.MINS_CFG) : 0);
        restartTime = restartTime.plusSeconds(this.customConfig.isSet(Constants.SECS_CFG) ? this.customConfig.getLong(Constants.SECS_CFG) : 0);

        Bukkit.getLogger().log(Level.INFO, Constants.LOGGER_NAME + " Next server restart -> " + restartTime);

        Objects.requireNonNull(this.getCommand("restarter")).setExecutor(new RestartCommand(restartTime));

        Timer timer = new Timer();
        timer.schedule(new RestartTimer(this), DateUtil.convertToDateViaInstant(restartTime));

        Timer timer2 = new Timer();
        timer2.schedule(new RestartCounter(this, "15"), DateUtil.convertToDateViaInstant(restartTime.minusSeconds(15)));
    }

    private void initStartup() {
        createCustomConfig();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static void restart() {
        ConsoleUtil.toColsole("restart server...");
        Bukkit.getServer().getOnlinePlayers().forEach(player -> player.kickPlayer("Server restart"));
        //TODO: Implement newer kick method
//        Bukkit.getServer().getOnlinePlayers().forEach(player -> player.kick(null, PlayerKickEvent.Cause.FLYING_PLAYER));
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "restart");
    }

    public static void counter(String seconds) {
        ConsoleUtil.toColsole("restart in " + seconds + " seconds");
    }

    public FileConfiguration getCustomConfig() {
        return this.customConfig;
    }

    private void createCustomConfig() {
        customConfigFile = new File(getDataFolder(), "config.yml");
        if (!customConfigFile.exists()) {
            customConfigFile.getParentFile().mkdirs();
            try {
                customConfigFile.createNewFile();
            } catch (IOException e) {
                Bukkit.getLogger().log(Level.SEVERE, e.getMessage());
            }
        }

        customConfig = new YamlConfiguration();

        try {
            customConfig.load(customConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            Bukkit.getLogger().log(Level.SEVERE, e.getMessage());
        }
        // Setting up default values if not set
        if(!customConfig.isSet(Constants.HOURS_CFG)) customConfig.set(Constants.HOURS_CFG, 4);
        if(!customConfig.isSet(Constants.MINS_CFG)) customConfig.set(Constants.MINS_CFG, 0);
        if(!customConfig.isSet(Constants.SECS_CFG)) customConfig.set(Constants.SECS_CFG, 0);

        try {
            customConfig.save(customConfigFile);
        } catch (IOException e) {
            Bukkit.getLogger().log(Level.SEVERE, e.getMessage());
        }
    }
}
