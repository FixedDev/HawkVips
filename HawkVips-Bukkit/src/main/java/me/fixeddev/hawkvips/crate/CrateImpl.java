package me.fixeddev.hawkvips.crate;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CrateImpl implements Crate {
    @NotNull
    private String name;
    @NotNull
    private String displayName;
    @Nullable
    private String permission;
    @NotNull
    private List<ItemStack> itemList;

    CrateImpl(@NotNull String name, @NotNull String displayName, @Nullable String permission, @NotNull List<ItemStack> itemList) {
        this.name = name;
        this.displayName = displayName;
        this.permission = permission;
        this.itemList = itemList;
    }

    CrateImpl(String name) {
        this(name, name, null, new ArrayList<>());
    }

    @SuppressWarnings("unchecked")
    CrateImpl(Map<String, Object> map) {
        name = (String) map.get("name");
        displayName = (String) map.get("displayName");
        permission = (String) map.get("permission");
        itemList = (List<ItemStack>) map.get("itemsList");
    }

    @NotNull
    @Override
    public String name() {
        return name;
    }

    @NotNull
    @Override
    public String displayName() {
        return displayName;
    }

    @Override
    public void setDisplayName(@Nullable String name) {
        if (name == null) {
            displayName = name();

            return;
        }

        this.displayName = name;
    }

    @Nullable
    @Override
    public String permission() {
        return permission;
    }

    @Override
    public void setPermission(@Nullable String permission) {
        this.permission = permission;
    }

    @NotNull
    @Override
    public List<ItemStack> items() {
        return itemList;
    }

    @Override
    public void setItems(@NotNull List<ItemStack> items) {
        this.itemList = items;
    }
}
