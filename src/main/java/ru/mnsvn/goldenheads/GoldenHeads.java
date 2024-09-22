package ru.mnsvn.goldenheads;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;
import ru.mnsvn.goldenheads.items.ItemManager;
import ru.mnsvn.goldenheads.items.GoldenHeadItemManager;
import ru.mnsvn.goldenheads.listeners.CustomItemConsumptionListener;

public class GoldenHeads extends JavaPlugin {
    @Override
    public void onEnable() {
        ItemManager goldenHeadItemManager = new GoldenHeadItemManager(this);
        ItemStack goldenHead = goldenHeadItemManager.getCustomItem();

        NamespacedKey goldenHeadRecipeKey = new NamespacedKey(this, "goldenhead");
        ShapedRecipe goldenHeadRecipe = new ShapedRecipe(goldenHeadRecipeKey, goldenHead)
                .shape("GEG", "NHN", "SGS")
                .setIngredient('G', Material.GOLD_BLOCK)
                .setIngredient('E', Material.ENCHANTED_GOLDEN_APPLE)
                .setIngredient('N', Material.NETHERITE_INGOT)
                .setIngredient('H', Material.PLAYER_HEAD)
                .setIngredient('S', Material.NETHERITE_SCRAP);
        Bukkit.addRecipe(goldenHeadRecipe);

        Bukkit.getPluginManager().registerEvents(new CustomItemConsumptionListener(goldenHeadItemManager), this);
    }


}
