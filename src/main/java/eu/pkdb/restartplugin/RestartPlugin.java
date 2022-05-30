package eu.pkdb.restartplugin;

import eu.pkdb.restartplugin.commands.RestartCommand;
import eu.pkdb.restartplugin.timer.RestartCounter;
import eu.pkdb.restartplugin.timer.RestartTimer;
import eu.pkdb.restartplugin.utils.ConsoleUtil;
import eu.pkdb.restartplugin.utils.Constants;
import eu.pkdb.restartplugin.utils.DateUtil;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Timer;
import java.util.logging.Level;

public final class RestartPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        LocalDateTime now = LocalDateTime.now();

        //TODO: read hours, mins, secs from yaml file
        LocalDateTime restartTime = now.plusHours(4);

        Bukkit.getLogger().log(Level.INFO, Constants.LOGGER_NAME + " Next server restart -> " + restartTime);

        Objects.requireNonNull(this.getCommand("restarter")).setExecutor(new RestartCommand(restartTime));

        Timer timer = new Timer();
        timer.schedule(new RestartTimer(this), DateUtil.convertToDateViaInstant(restartTime));

        Timer timer2 = new Timer();
        timer2.schedule(new RestartCounter(this, "15"), DateUtil.convertToDateViaInstant(restartTime.minusSeconds(15)));

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
}
