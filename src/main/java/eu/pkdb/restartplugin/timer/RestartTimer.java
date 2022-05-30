package eu.pkdb.restartplugin.timer;

import eu.pkdb.restartplugin.RestartPlugin;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.TimerTask;

public class RestartTimer extends TimerTask {
    private File restartScript;
    private final RestartPlugin plugin;

    public RestartTimer(RestartPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        Bukkit.getScheduler().runTask(plugin, RestartPlugin::restart);
    }
}
