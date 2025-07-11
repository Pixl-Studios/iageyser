package eu.pixlstudios.iageyser;

import com.google.common.eventbus.Subscribe;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import dev.lone.itemsadder.api.CustomBlock;
import dev.lone.itemsadder.api.CustomStack;
import dev.lone.itemsadder.api.Events.ResourcePackSendEvent;
import dev.lone.itemsadder.api.ItemsAdder;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.geysermc.geyser.api.GeyserApi;
import org.geysermc.geyser.api.block.custom.CustomBlockData;
import org.geysermc.geyser.api.event.lifecycle.GeyserDefineCustomItemsEvent;
import org.geysermc.geyser.api.event.lifecycle.GeyserPostInitializeEvent;
import org.geysermc.geyser.api.item.custom.CustomItemOptions;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class Iageyser extends JavaPlugin implements Listener {

    private static String UrlIp = "notinit";
    private Object CustomItemOptions;
    FileConfiguration config = getConfig();
    private boolean GeyserLoaded = false;
    //public String UrlRp = "ianotinit";
    public void sendPackToServer(String URL) {
        //TODO: SEND URL TO SERVER FOR PROCCESSING
    }
    public void reloadPlugin() {
        //TODO: Implement Reload Plugin Logic
    }
    @Override
    public void onEnable() {
        getLogger().info("IaGeyser enabled!");
        config.addDefault("serverurl", "CHANGEME");
        config.addDefault("enabled", true);
        config.addDefault("restart_on_convert", false);
        config.addDefault("convert_needed", false);
        try {
            config.save("config.yml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        getServer().getPluginManager().registerEvents(this, this);
        if (getServer().getMinecraftVersion() == "1.21.6" || getServer().getMinecraftVersion() == "1.21.7") {
            getLogger().warning("1.21.6/1.21.7 Is only supported by ItemsAdder BETA.");
        }
        //TODO: IMPLEMENT COMMAND: /iageyser, reload: type: item, block, both
        this.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, commands -> {

            LiteralArgumentBuilder<CommandSourceStack> command = Commands.literal("iageyser")
                    .requires(sender -> sender.getSender().isOp())
                    .then(Commands.literal("rpurl"))
                        .executes(ctx ->{
                            ctx.getSource().getSender().sendMessage(UrlIp);
                            return 0;
                        })
                    .then(Commands.literal("reload")
                        .executes(ctx ->{
                            //TODO: IMPLEMENT PERMISSION CHECK
                            reloadPlugin();
                            return 0;
                        }));
            LiteralCommandNode<CommandSourceStack> buildCommand = command.build();
            commands.registrar().register(buildCommand);
        });

    }

    @Override
    public @NotNull Path getDataPath() {
        return super.getDataPath();
    }

    @Override
    public void onDisable() {
        getLogger().info("IaGeyser disabled!");
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();


        if (player.hasPermission("iageyser.seealerts")) {
            player.sendMessage("§c[IAGeyser] §aThank you for using IAGeyser!");
        }
    }

    @Subscribe
    public void onGeyserPostInitializeEvent(GeyserPostInitializeEvent event) {
        getLogger().info("GeyserMC Has Initialized!");
        GeyserLoaded = true;
    }
    @Subscribe
    public void onGeyserDefineCustomItemsEvent(GeyserDefineCustomItemsEvent event) {

    }
    @Subscribe
    public void ResourcePackSendEvent(ResourcePackSendEvent event){
        UrlIp = event.getUrl();
    }
}
