package eu.pkdb.restartplugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;

public class RestartCommand implements CommandExecutor {
    private LocalDateTime restartTime;

    public RestartCommand(LocalDateTime restartTime) {
        this.restartTime = restartTime;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        sender.sendMessage("Next restart: " + this.restartTime);
        return true;
    }
}
