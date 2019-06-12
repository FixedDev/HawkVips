package me.fixeddev.hawkvips;

import me.fixeddev.bcm.bukkit.BukkitCommandHandler;
import me.fixeddev.bcm.parametric.ParametricCommandHandler;
import me.fixeddev.hawkvips.commands.CrateCommands;
import me.fixeddev.hawkvips.crate.Crate;
import me.fixeddev.hawkvips.crate.CrateImpl;
import me.fixeddev.hawkvips.crate.CrateManager;
import me.fixeddev.hawkvips.crate.CrateManagerImpl;
import me.fixeddev.hawkvips.storage.PlayerStorage;
import me.fixeddev.hawkvips.storage.PlayerStorageImpl;
import me.fixeddev.hawkvips.storage.PlayerStorageManager;
import me.fixeddev.hawkvips.storage.PlayerStorageManagerImpl;
import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

public class HawkVips extends JavaPlugin implements HawkVipsAPI {

    private Config storageConfig;
    private Config crateConfig;

    private CrateManager crateManager;
    private PlayerStorageManager playerStorageManager;

    @Override
    public void onLoad() {
        registerSerializables();
        createConfigs();
    }

    @Override
    public void onEnable() {
        startManagers();
        registerAsService();
        registerCommands();
    }

    private void registerSerializables() {
        ConfigurationSerialization.registerClass(PlayerStorage.class);
        ConfigurationSerialization.registerClass(PlayerStorageImpl.class);

        ConfigurationSerialization.registerClass(Crate.class);
        ConfigurationSerialization.registerClass(CrateImpl.class);
    }

    private void createConfigs() {
        storageConfig = new Config(this, "storage");
        crateConfig = new Config(this, "crate");
    }

    private void startManagers() {
        crateManager = new CrateManagerImpl(crateConfig);
        playerStorageManager = new PlayerStorageManagerImpl(storageConfig);
    }

    private void registerAsService() {
        Bukkit.getServicesManager().register(HawkVipsAPI.class, this, this, ServicePriority.Normal);
    }

    private void registerCommands() {
        ParametricCommandHandler commandHandler = new BukkitCommandHandler(this.getLogger(), null);

        commandHandler.registerCommandClass(new CrateCommands(crateManager));
    }

    // Api methods
    @Override
    public CrateManager crateManager() {
        return crateManager;
    }

    @Override
    public PlayerStorageManager playerStorageManager() {
        return playerStorageManager;
    }
}
