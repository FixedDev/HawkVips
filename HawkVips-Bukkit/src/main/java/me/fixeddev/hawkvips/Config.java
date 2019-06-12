package me.fixeddev.hawkvips;


import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.logging.Level;

public class Config extends YamlConfiguration {
    private final String fileName;
    private final Plugin plugin;
    private final File folder;

    public Config(final Plugin plugin, final String fileName) {
        this(plugin, plugin.getDataFolder(), fileName, ".yml");
    }

    public Config(final Plugin plugin, final File folder, final String fileName, final String fileExtension) {
        this.plugin = plugin;
        this.folder = folder;
        this.fileName = fileName + (fileName.endsWith(fileExtension) ? "" : fileExtension);
        this.createFile();
    }

    public String getFileName() {
        return this.fileName;
    }

    public Plugin getPlugin() {
        return this.plugin;
    }

    private void createFile() {
        try {
            final File file = new File(folder, this.fileName);
            if (!file.exists()) {
                if (this.plugin.getResource(this.fileName) != null) {
                    this.plugin.saveResource(this.fileName, false);
                } else {
                    this.save(file);
                }
                this.load(file);
            } else {
                this.load(file);
                this.save(file);
            }
        } catch (Exception ex) {
            plugin.getLogger().log(Level.SEVERE, "An error occurred while creating the file " + fileName, ex);
        }
    }

    public void save() {
        try {
            this.save(new File(folder, this.fileName));
        } catch (Exception ex) {
            plugin.getLogger().log(Level.SEVERE, "An error saving the configuration " + fileName, ex);
        }
    }

}
