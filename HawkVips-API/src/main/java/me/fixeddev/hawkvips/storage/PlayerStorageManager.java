package me.fixeddev.hawkvips.storage;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public interface PlayerStorageManager {

    /**
     * @param playerId - The id of the owner of the storage
     * @return - A PlayerStorage instance for the specified player
     *           in case that we don't have a PlayerStorage instance
     *           it will be created after the method is executed if we have a
     *           instance then the method will return the stored instance
     */
    @NotNull
    PlayerStorage getStorage(UUID playerId);

    /**
     * This method saves the player storage instance into the data file
     * @param storage - The player storage instance
     */
    void saveStorage(@NotNull PlayerStorage storage);

}
