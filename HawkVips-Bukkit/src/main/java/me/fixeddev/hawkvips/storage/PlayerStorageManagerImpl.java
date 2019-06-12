package me.fixeddev.hawkvips.storage;

import me.fixeddev.hawkvips.Config;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerStorageManagerImpl implements PlayerStorageManager {

    @NotNull
    private Map<UUID, PlayerStorage> storageMap;
    @NotNull
    private Config config;

    public PlayerStorageManagerImpl(@NotNull Config config) {
        storageMap = new HashMap<>();
        this.config = config;

        ConfigurationSection section = config.getConfigurationSection("storage.");

        if (section == null) {
            return;
        }

        for (String key : section.getKeys(false)) {
            Object object = section.get(key);

            if (!(object instanceof PlayerStorage)) {
                continue;
            }

            PlayerStorage storage = (PlayerStorage) object;

            storageMap.put(storage.ownerId(), storage);
        }
    }

    @NotNull
    @Override
    public PlayerStorage getStorage(UUID playerId) {
        return storageMap.computeIfAbsent(playerId, PlayerStorageImpl::new);
    }

    @Override
    public void saveStorage(@NotNull PlayerStorage storage) {
        config.set("storage." + storage.ownerId().toString(), storage);
        config.save();
    }
}
