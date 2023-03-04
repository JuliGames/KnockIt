package net.juligames.knockit;

import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.api.MVWorldManager;
import net.juligames.core.api.API;
import net.juligames.core.api.message.MessageApi;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public final class KnockItPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic

        //registerMessages
        registerMessages(API.get().getMessageApi());
    }

    public static @NotNull MultiverseCore getMultiverseCore() {
        return (MultiverseCore) Objects.requireNonNull(Bukkit.getServer().
                getPluginManager().getPlugin("Multiverse-Core"));
    }

    public static @NotNull MVWorldManager getMVWorldManager() {
        return getMultiverseCore().getMVWorldManager();
    }

    private void registerMessages(@NotNull MessageApi messageApi) {

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
