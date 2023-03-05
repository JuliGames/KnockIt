package net.juligames.knockit.game;

import net.juligames.core.adventure.api.AdventureAPI;
import net.juligames.core.api.API;
import net.juligames.core.api.misc.ThrowableDebug;
import net.juligames.core.minigame.api.SimpleMiniGame;
import net.juligames.knockit.KnockItPlugin;
import net.juligames.knockit.config.WorldConfigManager;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.file.NoSuchFileException;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author Ture Bentzin
 * 04.03.2023
 */
public class KnockIt extends SimpleMiniGame {

    private final @NotNull CompletableFuture<WorldConfigManager> worldsConfigManager = new CompletableFuture<>();
    private final @NotNull KnockItPlugin knockItPlugin;
    private @Nullable KnockItLevel level;
    private boolean running;
    private @Nullable NextLevelTimer nextLevelTimer;

    public KnockIt(@NotNull KnockItPlugin knockItPlugin) {
        super("KnockIt",
                "0.1",
                "Ture Bentzin",
                "bentzin@tdrstudios.de",
                API.get().getAPILogger());
        this.knockItPlugin = knockItPlugin;
    }

    public static boolean isMiniGameActive() {
        return API.get().getLocalMiniGame().isPresent() && API.get().getLocalMiniGame().get().isRunning();
    }

    @Override
    protected void onLoad() {
        //loadConfig
        getLogger().info("Hello World!");
        worldsConfigManager.complete(new WorldConfigManager(Bukkit.getWorldContainer()));
        try {
            worldsConfigManager.get().scan();
            getLogger().info("scanned: " + worldsConfigManager.get().getKnockItWorldSpecifications().keySet());
        } catch (NoSuchFileException | ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        Bukkit.getPluginManager().registerEvents(new KnockItListener(this), knockItPlugin);
    }

    @Override
    protected boolean onStart() {
        running = true;
        getLogger().info("Thank you for using KnockIt!");
        getLogger().info("Jumping right into it... please wait...");
        try {
            nextLevel();
        } catch (Exception e) {
            getLogger().error("Error while starting KnockIt: " + e.getMessage());
            ThrowableDebug.debug(e);
            return false;
        }
        getLogger().info("completed start of KnockIt!");
        return true;
    }

    @Override
    protected void onAbort() {
        running = false;
        getLogger().warning("The game was aborted. All players will be kicked!");
        Bukkit.getOnlinePlayers().forEach(player ->
                player.kick(AdventureAPI.get().getAdventureTagManager().
                        resolve(API.get().getMessageApi().getMessageSmart("knockit.closed", player.locale()))));

    }

    @Override
    protected void onFinish() {
        running = false;
        //kick all players to fallback and save stats
    }

    @Override
    public boolean isRunning() {
        return running;
    }

    @Override
    public boolean isFinished() {
        return !running;
    }

    public @NotNull CompletableFuture<WorldConfigManager> getWorldsConfigManager() {
        return worldsConfigManager;
    }

    public @NotNull KnockItPlugin getKnockItPlugin() {
        return knockItPlugin;
    }

    public void nextLevel() {
        try {
            KnockItLevel knockItLevel = KnockItLevel.chooseLevel(worldsConfigManager.get(), this);
            nextLevel(knockItLevel);
        } catch (InterruptedException | ExecutionException e) {
            getLogger().error("Error while selecting level: " + e.getMessage());
            ThrowableDebug.debug(e);
        }
    }

    public void nextLevel(@NotNull KnockItLevel knockItLevel) {
        try {

            if (level != null) {
                getLogger().info("end of level:" + level.getWorld().getId());
            }

            getLogger().info("playing level: " + knockItLevel);
            getLogger().info("preparing level...");
            knockItLevel.prepare();
            getLogger().info("finished preparation of " + knockItLevel.getWorld().getId());
            level = knockItLevel;
            getLogger().info("teleporting players to next level...");
            level.pullPlayers();
        } catch (InterruptedException e) {
            getLogger().error("Error while selecting level: " + e.getMessage());
            ThrowableDebug.debug(e);
        }
    }

    public void nextLevel(@NotNull Duration duration) {
        if (nextLevelTimer != null) nextLevelTimer.abort();
        try {
            KnockItLevel knockItLevel = KnockItLevel.chooseLevel(worldsConfigManager.get(), this);
            nextLevelTimer = new NextLevelTimer(duration, knockItLevel);
        } catch (InterruptedException | ExecutionException e) {
            getLogger().error("Error while starting timer: " + e.getMessage());
            ThrowableDebug.debug(e);
        }

    }

    public @Nullable KnockItLevel getLevel() {
        return level;
    }

    public @NotNull KnockItLevel getLevelOrThrow() {
        if (level == null)
            throw new NullPointerException("level needs to be present at this time!");
        return level;
    }
}
