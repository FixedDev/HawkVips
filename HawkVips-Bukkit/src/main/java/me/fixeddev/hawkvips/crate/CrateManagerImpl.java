package me.fixeddev.hawkvips.crate;

import me.fixeddev.hawkvips.Config;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class CrateManagerImpl implements CrateManager {

    private Map<String, Crate> crates;
    private Config config;

    public CrateManagerImpl(Config config) {
        crates = new HashMap<>();
        this.config = config;

        ConfigurationSection section = config.getConfigurationSection("crate.");

        if (section == null) {
            return;
        }

        for (String key : section.getKeys(false)) {
            Object object = section.get(key);

            if (!(object instanceof Crate)) {
                continue;
            }

            Crate crate = (Crate) object;
            crates.put(crate.name(), crate);
        }
    }

    @NotNull
    @Override
    public Crate createCrate(@NotNull String name) {
        if (crates.containsKey(name)) {
            throw new IllegalStateException("A crate with this name already exists!");
        }

        Crate crate = new CrateImpl(name);
        crates.put(name, crate);

        return crate;
    }

    @NotNull
    @Override
    public Optional<Crate> getCrate(@NotNull String name) {
        return Optional.ofNullable(crates.get(name));
    }

    @Override
    public Set<Crate> crates() {
        return new HashSet<>(crates.values());
    }

    @Override
    public void saveCrate(@NotNull Crate crate) {
        config.set("crate." + crate.name(), crate);
        config.save();
    }

    @Override
    public void deleteCrate(@NotNull Crate crate) {
        crates.remove(crate.name());

        config.set("crate." + crate.name(), null);
        config.save();
    }
}
