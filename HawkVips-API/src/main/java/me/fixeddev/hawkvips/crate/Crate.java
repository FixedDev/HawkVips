package me.fixeddev.hawkvips.crate;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public interface Crate extends ConfigurationSerializable {
    /**
     * @return - An immutable name for this crate, this shouldn't change at any time
     */
    @NotNull String name();

    /**
     * This display name is the name displayed in the menus and other things
     * this is NOT an identifier, so don't use it as it
     * @return - A display name for this crate, this can change at any time, so don't use it as a identifier
     */
    @NotNull String displayName();

    /**
     * @param name - The display name to set in this crate,
     *             in case of null this is set to the same value than name()
     */
    void setDisplayName(@Nullable String name);

    /**
     * This is the permission needed to use this crate,
     * if this returns a null value then that means that you don't need any permission to use it
     * An empty permission means the exact same thing
     * @return - A nullable permission needed to use this crate
     */
    @Nullable
    String permission();

    void setPermission(@Nullable String permission);

    /**
     * This are the items that are going to be given to the user that opens this crate
     * this can change at any moment and the users that already opened one of this crates
     * won't receive the new list of items in their personal storage
     * @return - A copy of the list of items in this crate
     */
    @NotNull List<ItemStack> items();

    void setItems(@NotNull List<ItemStack> items);

    @Override
    default Map<String, Object> serialize() {
        Map<String, Object> map = new LinkedHashMap<>();

        map.put("name", name());
        map.put("display-name", displayName());

        String permission = permission();
        map.put("permission", permission == null ? "" : permission);

        map.put("items", items());

        return map;
    }
}
