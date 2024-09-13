package ru.mnsvn.goldenheads.items;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public interface ItemManager {
    @NotNull ItemStack getCustomItem();
    boolean isCustomItem(@NotNull ItemStack customItem);
}
