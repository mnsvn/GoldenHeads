package ru.mnsvn.goldenheads.items;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import ru.mnsvn.goldenheads.GoldenHeads;

import java.util.UUID;

public class GoldenHeadItemManager implements ItemManager {
    private final GoldenHeads plugin;
    private final NamespacedKey key;
    private final ItemStack item;

    private static final String GOLDEN_HEAD_TEXTURE_VALUE = "ewogICJ0aW1lc3RhbXAiIDogMTcyNjIzMzgyODUxMiwKICAicHJvZmlsZUlkIiA6ICJhYzY1NDYwOWVkZjM0ODhmOTM0ZWNhMDRmNjlkNGIwMCIsCiAgInByb2ZpbGVOYW1lIiA6ICJzcGFjZUd1cmxTa3kiLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmUxNTUxYjk0MzRmYWUxZThlOTMyN2Y3YzFjZWFjN2JjYWUzY2Y3MWZiZDY3ZTc5NDJmMGMyY2U2MjgwNWMxOCIsCiAgICAgICJtZXRhZGF0YSIgOiB7CiAgICAgICAgIm1vZGVsIiA6ICJzbGltIgogICAgICB9CiAgICB9CiAgfQp9";
    private static final String GOLDEN_HEAD_TEXTURE_SIGNATURE = "ljGhM2/uEEwA6ahMbdFodPiX+LOdgQE7rTGYakzjG4lpijZXmaETDKJvCqQ+U2+knCXW+J6YcRkywR47H5uLwBvAW/ysxDhsFoYGDImXsspvW8mdjjNBYcrSoQDslztP1Z7TlCgCYOAqVj6fNavxeH61AUkQYWMokgRKUM2jH81T0iYzlsOO89CBOZN6oUfGfbZNjU7kfXtcUYQozz04dgoFU96c90X2ao2k9btBRbUAZdu3VZcZEw67Bc08S/uOPt505wdmM78FiL3mA66DUPkPzWqdA7E5fsEljLDNZR17p7elrnWcdbFNAIck1jH3U7D5kX+Cr5GO9TUBcC16f7i/4CuVftjlakQD4Kx/uDFlGW6rzBkVYcZMnKOvhEq/p+BxR8K8VrMDrU1qeobIX+ggyrWPpsg2/QU2ewMlhI5Ys7lB4FdDYt6dovrP90cFIDndAtlK9mYmy18hQLxB6GSpADvDwfbmnJNYRv+hTKH0ABuoCEOuyQfvSjNMyJ/A8rhEADRgfqKebspsOzntk58r9FIrzO54m3neJ/DQpffY0+uZRALvOR4PFLfisx4WIpZrbnHnUifO3CvryDsthUFjRN8GALTBVksHuxVBVBpjMb2DDGEc9nc6lYsEhS7NQZ2QRKoBEFMr5MjWnolhJ03NfFGawbrjD31tYJwDrGM=";

    public GoldenHeadItemManager (GoldenHeads plugin) {
        this.plugin = plugin;
        this.key = createGoldenHeadNamespacedKey();
        this.item = createGoldenHead();
    }

    @Override
    public @NotNull ItemStack getCustomItem() {
        return item;
    }

    @Override
    public boolean isCustomItem(@NotNull ItemStack item) {
        if (!item.hasItemMeta())
            return false;

        if (!(item.getItemMeta() instanceof SkullMeta meta))
            return false;

        PersistentDataContainer container = meta.getPersistentDataContainer();
        if (!container.has(key, PersistentDataType.BYTE))
            return false;

        return true;
    }

    private ItemStack createGoldenHead() {
        ItemStack goldenHead = ItemStack.of(Material.PLAYER_HEAD);

        goldenHead.editMeta(SkullMeta.class, meta -> {
            meta.displayName(Component.text()
                    .resetStyle()
                    .color(NamedTextColor.GOLD)
                    .append(Component.text("Золотая голова"))
                    .build()
            );

            meta.setPlayerProfile(createGoldenHeadProfile());

            PersistentDataContainer container = meta.getPersistentDataContainer();
            container.set(createGoldenHeadNamespacedKey(), PersistentDataType.BYTE, (byte) 1);
        });

        return goldenHead;
    }

    private PlayerProfile createGoldenHeadProfile() {
        PlayerProfile profile = Bukkit.createProfile(UUID.randomUUID());
        ProfileProperty property = new ProfileProperty("textures", GOLDEN_HEAD_TEXTURE_VALUE, GOLDEN_HEAD_TEXTURE_SIGNATURE);
        profile.setProperty(property);
        return profile;
    }

    private NamespacedKey createGoldenHeadNamespacedKey() {
        return new NamespacedKey(plugin, "goldenheaditem");
    }
}
