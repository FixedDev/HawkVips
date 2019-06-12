package me.fixeddev.hawkvips.crate;

import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.Set;

/**
 * This class isn't thread safe, so please don't use it in another threads without synchronization
 */
public interface CrateManager {

    /**
     * This method creates a new crate instance with the specified name and save it to the data file,
     * this also makes it available for the crate registry so the method getCrate will return this instance
     * when you request it with the name
     * @throws IllegalStateException - when a crate with the same name already exists
     * @param name - The name for the crate, this will be used as an identifier
     * @return - A new crate instance
     */
    @NotNull
    Crate createCrate(@NotNull String name);

    /**
     * @param name - The name of the crate to search
     * @return - An optional instance of the crate, if this optional is not present that means
     * that a crate with that name doesn't exist. If you modify this the instance in all the jvm will be
     * modified, but it won't be saved to the data file until someone calls the method saveCrate
     */
    @NotNull
    Optional<Crate> getCrate(@NotNull String name);

    /**
     * @return - A shallow copy of the list of all the available crates, if you modify anything in that set
     * it will be modified in the entire jvm because all the returned crates are NOT a copy, the only thing
     * that is copied is the set to prevent you from deleting a crate in that way
     */
    Set<Crate> crates();

    /**
     * This method saves the crate instance into the data file
     * @param crate - The crate instance
     */
    void saveCrate(@NotNull Crate crate);

    /**
     * This method deletes the crate instance from this crate registry and
     * from the data file
     * @param crate - The crate instance
     */
    void deleteCrate(@NotNull Crate crate);
}
