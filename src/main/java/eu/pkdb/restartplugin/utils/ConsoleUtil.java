package eu.pkdb.restartplugin.utils;

import org.bukkit.Bukkit;

public class ConsoleUtil {
    public static void toColsole(String text) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "say " + Constants.LOGGER_NAME + " " + text);
    }
}
