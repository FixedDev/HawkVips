package me.fixeddev.hawkvips;

import me.fixeddev.hawkvips.crate.CrateManager;
import me.fixeddev.hawkvips.storage.PlayerStorageManager;

public interface HawkVipsAPI {
    CrateManager crateManager();

    PlayerStorageManager playerStorageManager();
}
