package me.fixeddev.hawkvips.storage;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class PlayerStorageImpl implements PlayerStorage {

    private UUID ownerId;
    private Map<Integer, List<ItemStack>> itemsPerPage;

    public PlayerStorageImpl(UUID ownerId, Map<Integer, List<ItemStack>> items) {
        this.ownerId = ownerId;

        itemsPerPage = items;
    }

    public PlayerStorageImpl(UUID ownerId) {
        this(ownerId, new HashMap<>());
    }

    @SuppressWarnings("unchecked")
    PlayerStorageImpl(Map<String, Object> map) {
        ownerId = UUID.fromString((String) map.get("owner-id"));
        itemsPerPage = new HashMap<>();

        Map<Integer, List<ItemStack>> itemsMap = (Map<Integer, List<ItemStack>>) map.get("page-items");

        int counter = 0;
        for (Map.Entry<Integer, List<ItemStack>> entry : itemsMap.entrySet().stream().sorted(Comparator.comparing(Map.Entry::getKey)).collect(Collectors.toList())) {
            itemsPerPage.put(counter, entry.getValue());

            counter++;
        }

    }

    @NotNull
    @Override
    public UUID ownerId() {
        return ownerId;
    }

    @Override
    public Optional<List<ItemStack>> getPageItems(int page) {
        return Optional.ofNullable(itemsPerPage.get(page));
    }

    @Override
    public int getPagesNumber() {
        return itemsPerPage.size();
    }

    @NotNull
    @Override
    public List<ItemStack> getAllItems() {
        return itemsPerPage.values().stream().flatMap(Collection::stream).collect(Collectors.toList());
    }

    @Override
    public void setItems(int page, @NotNull List<ItemStack> items) {
        if (items.size() > PAGE_SIZE) {
            throw new IllegalArgumentException("The page size should be a maximum of 45");
        }

        if (itemsPerPage.size() <= page) {
            throw new IllegalArgumentException("The page number should be " + itemsPerPage.size());
        }

        itemsPerPage.put(page, items);
    }

    @Override
    public void setItems(@NotNull Map<Integer, List<ItemStack>> items) {
        itemsPerPage = items;
    }

    @NotNull
    @Override
    public List<ItemStack> addNewPage() {
        List<ItemStack> page = new ArrayList<>();

        itemsPerPage.put(getPagesNumber(), page);
        return page;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new LinkedHashMap<>();

        map.put("owner-id", ownerId.toString());
        map.put("page-items", itemsPerPage);

        return map;
    }
}
