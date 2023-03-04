package net.juligames.knockit;

import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.api.MVWorldManager;
import net.juligames.core.api.API;
import net.juligames.core.api.message.MessageApi;
import net.juligames.core.api.misc.ThrowableDebug;
import net.juligames.knockit.config.WorldsConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public final class KnockItPlugin extends JavaPlugin {

    private final @NotNull CompletableFuture<WorldsConfigManager> worldsConfigManager = new CompletableFuture<>();

    public static @NotNull MultiverseCore getMultiverseCore() {
        return (MultiverseCore) Objects.requireNonNull(Bukkit.getServer().
                getPluginManager().getPlugin("Multiverse-Core"));
    }

    public static @NotNull MVWorldManager getMVWorldManager() {
        return getMultiverseCore().getMVWorldManager();
    }

    @Override
    public void onEnable() {
        // Plugin startup logic

        //registerMessages
        registerMessages(API.get().getMessageApi());

        //loadConfig
        worldsConfigManager.complete(new WorldsConfigManager(getDataFolder()));
    }

    private void registerMessages(@NotNull MessageApi messageApi) {

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        if (worldsConfigManager.isDone()) {
            try {
                worldsConfigManager.get().save();
            } catch (InterruptedException | ExecutionException e) {
                ThrowableDebug.debug(e);
            }
        }
    }
}
