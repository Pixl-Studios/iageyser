package eu.pixlstudios.iageyser;

import com.google.common.eventbus.Subscribe;
import dev.lone.itemsadder.api.CustomBlock;
import dev.lone.itemsadder.api.CustomStack;
import dev.lone.itemsadder.api.ItemsAdder;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.geysermc.geyser.api.GeyserApi;
import org.geysermc.geyser.api.block.custom.CustomBlockData;
import org.geysermc.geyser.api.event.lifecycle.GeyserDefineCustomItemsEvent;
import org.geysermc.geyser.api.event.lifecycle.GeyserPostInitializeEvent;
import org.geysermc.geyser.api.item.custom.CustomItemOptions;

import java.util.logging.Level;
import java.util.logging.Logger;

public final class Iageyser extends JavaPlugin {

    private Object CustomItemOptions;

    @Override
    public void onEnable() {
        getLogger().info("Iageyser enabled!");
    }
    @Override
    public void onDisable() {
        getLogger().info("Iageyser disabled!");
    }

    @Subscribe
    public void onGeyserPostInitializeEvent(GeyserPostInitializeEvent event) {
        getLogger().info("GeyserMC Has Initialized!");
    }
    @Subscribe
    public void onGeyserDefineCustomItemsEvent(GeyserDefineCustomItemsEvent event) {
        CustomItemOptions itemOptions = CustomItemOptions.builder()
                .customModelData(1)
                .name("my_item")
                .damagePredicate(1) //This is a fractional value of damage/max damage and not a number between 0 and 1.
                .unbreakable(true)
                .build();
        event.register(itemOptions);
    }
}
