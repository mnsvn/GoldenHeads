package ru.mnsvn.goldenheads.listeners;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import ru.mnsvn.goldenheads.items.ItemManager;

import java.util.List;
import java.util.Objects;

public class CustomItemConsumptionListener implements Listener {
    private final ItemManager itemManager;

    private static final PotionEffect REGENERATION_EFFECT = new PotionEffect(PotionEffectType.REGENERATION, 20 * 12, 2);
    private static final PotionEffect SPEED_EFFECT = new PotionEffect(PotionEffectType.SPEED, 20 * 10, 2);
    private static final PotionEffect ABSORPTION_EFFECT = new PotionEffect(PotionEffectType.ABSORPTION, 20 * 45, 2);
    private static final List<PotionEffect> EFFECTS = List.of(REGENERATION_EFFECT, SPEED_EFFECT, ABSORPTION_EFFECT);

    private static final int HEAL_AMOUNT = 4 * 2;

    public CustomItemConsumptionListener(ItemManager itemManager) {
        this.itemManager = itemManager;
    }

    @EventHandler
    public void onItemInteractApplyEffects(PlayerInteractEvent e) {
        if (e.getAction() == Action.PHYSICAL)
            return;

        ItemStack item = e.getItem();

        if (Objects.isNull(item))
            return;

        if (!itemManager.isCustomItem(item))
            return;

        e.setUseItemInHand(Event.Result.DENY);

        Player player = e.getPlayer();
        if (player.hasCooldown(Material.PLAYER_HEAD)) {
            int cooldown = player.getCooldown(Material.PLAYER_HEAD) / 20;
            notifyAboutCooldown(player, cooldown);
            return;
        }

        int amount = item.getAmount();
        int newAmount = amount - 1;
        EquipmentSlot hand = e.getHand();
        EntityEquipment equipment = player.getEquipment();
        assert hand != null;
        equipment.getItem(hand).setAmount(newAmount);

        applyConsumptionEffects(player);
        applyConsumptionHeal(player);

        player.setCooldown(Material.PLAYER_HEAD, 45 * 20);
        notifyAboutConsumption(player);
    }

    private void applyConsumptionEffects(Player player) {
        player.addPotionEffects(EFFECTS);
    }

    private void applyConsumptionHeal(Player player) {
        player.heal(HEAL_AMOUNT);
    }

    private void notifyAboutCooldown(Player player, int cooldown) {
        player.playSound(Sound.sound(Key.key("entity.villager.no"), Sound.Source.PLAYER, 1f, 1f));
        player.sendMessage(Component.text()
                .color(NamedTextColor.RED)
                .append(Component.text("Нельзя сейчас использовать"))
                .appendSpace()
                .append(Component.text()
                        .color(NamedTextColor.GOLD)
                        .append(Component.text("золотую голову")))
                .append(Component.text(", попробуйте через"))
                .appendSpace()
                .append(Component.text(cooldown))
                .appendSpace()
                .append(Component.text("секундочек."))
        );
    }

    private void notifyAboutConsumption(Player player) {
        player.playSound(Sound.sound(Key.key("entity.player.burp"), Sound.Source.PLAYER, 1f, 1f));
        player.sendMessage(Component.text()
                .color(NamedTextColor.GRAY)
                .append(Component.text("Вы использовали"))
                .appendSpace()
                .append(Component.text()
                        .color(NamedTextColor.GOLD)
                        .append(Component.text("золотую голову")))
                .append(Component.text("."))
        );
    }
}
