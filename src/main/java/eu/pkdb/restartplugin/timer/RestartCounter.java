package eu.pkdb.restartplugin.timer;

import eu.pkdb.restartplugin.RestartPlugin;
import org.bukkit.Bukkit;

import java.io.File;
import java.util.TimerTask;

public class RestartCounter extends TimerTask {
    private final RestartPlugin plugin;
    private final String seconds;

    public RestartCounter(RestartPlugin plugin, String seconds) {
        this.plugin = plugin;
        this.seconds = seconds;
    }

    @Override
    public void run() {
        Bukkit.getScheduler().runTask(this.plugin, () -> {
            RestartPlugin.counter(this.seconds);
        });
    }
}
