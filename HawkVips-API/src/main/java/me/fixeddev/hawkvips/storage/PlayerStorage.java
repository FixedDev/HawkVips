package me.fixeddev.hawkvips.storage;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface PlayerStorage extends ConfigurationSerializable {
    /**
     * This constant defines the size of a page without the last row
     * This removes the size of the last row because we can use the last row
     * as navigation buttons between pages
     */
    int PAGE_SIZE = 45;

    /**
     * @return - The id of the owner of this storage
     */
    @NotNull
    UUID ownerId();

    /**
     * @param page - The page number starting from 0
     * @return - An optional list of items that represent the items in the specified page,
     *          in case that the page doesn't exist then the optional won't be present
     *          The returned list can have a size up to 45 items
     */
    @NotNull
    Optional<List<ItemStack>> getPageItems(int page);

    int getPagesNumber();

    /**
     * @return - A copy of the list of all the items
     */
    @NotNull
    List<ItemStack> getAllItems();

    /**
     * This method replaces the items in the specified page
     *
     * @param page - The page number to be replaced
     * @param items - The list of items that we're setting
     * @throws IllegalArgumentException - If the page number is not the next page number
     *                              or an already created page
     *                              For example, if the last page was 10 and you add the page 12
     * @throws IllegalArgumentException - If the items size is more than 45
     */
    void setItems(int page, @NotNull List<ItemStack> items);

    /**
     * This method replaces the items in the specified page
     *
     * @param items - A map representing all the pages content
     */
    void setItems(@NotNull Map<Integer, List<ItemStack>> items);

    @NotNull
    List<ItemStack> addNewPage();

}
