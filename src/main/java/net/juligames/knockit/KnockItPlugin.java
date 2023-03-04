package net.juligames.knockit;

import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.api.MVWorldManager;
import net.juligames.core.api.API;
import net.juligames.core.api.message.MessageApi;
import net.juligames.knockit.config.WorldConfigManager;
import net.juligames.knockit.game.KnockIt;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public final class KnockItPlugin extends JavaPlugin {

    private final @NotNull CompletableFuture<KnockIt> worldsConfigManager = new CompletableFuture<>();

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

        //introduce MiniGame
        KnockIt knockIt = new KnockIt(this);
        API.get().getLocalMiniGame().set(knockIt);
        worldsConfigManager.complete(knockIt);
    }

    private void registerMessages(@NotNull MessageApi messageApi) {
            messageApi.registerMessage("knockit.levels.moved", "<prefix><color_info>You where moved to <color_accent>{0}</color_accent>!");
            messageApi.registerMessage("knockit.border", "<gold>--------------------------------------");
            messageApi.registerMessage("knockit.welcome.header", "<info_color>Welcome to KnockIt!");
            messageApi.registerMessage("knockit.welcome.footer", "<color_debug><br>[0]");
            messageApi.registerMessage("knockit.welcome.text", "<info_color><br>This is a small explanation of what this Gamemode is all about and how it works. <br>This will be filled later in development <br>If you want you can enter your own Text here!");
            messageApi.registerMessage("knockit.closed", "<color_fatal>The game was closed!");
            messageApi.registerMessage("knockit.join", "<gray><green>+</red> {0}");
            messageApi.registerMessage("knockit.leave", "<gray><red>-</red> {0}");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
