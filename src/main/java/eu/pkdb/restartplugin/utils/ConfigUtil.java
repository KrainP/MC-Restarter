package eu.pkdb.restartplugin.utils;

import org.bukkit.configuration.file.FileConfiguration;

public class ConfigUtil {
    private final FileConfiguration customConfig;

    ConfigUtil(FileConfiguration customConfig) {
        this.customConfig = customConfig;
    }

    public String getDefaultConfig(String conf, String defValue) {
        return this.customConfig.isSet(conf) ? this.customConfig.getString(conf) : defValue;
    }

    public long getDefaultConfig(String conf, long defValue) {
        return this.customConfig.isSet(conf) ? this.customConfig.getLong(conf) : defValue;
    }
}
