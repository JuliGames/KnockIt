package net.juligames.knockit.config;

import net.juligames.core.api.misc.ThrowableDebug;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

/**
 * @author Ture Bentzin
 * 04.03.2023
 */
public class WorldsConfigManager {

    private final @NotNull File dataFolder;
    private final @NotNull CompletableFuture<YamlConfiguration> worldsConfig = new CompletableFuture<>();
    private final @NotNull Supplier<File> worldsConfigFile = () -> new File(getDataFolder(), "worlds.yaml");

    public WorldsConfigManager(@NotNull File dataFolder) {
        this.dataFolder = dataFolder;
    }

    public void load() {
        worldsConfig.complete(YamlConfiguration.loadConfiguration(worldsConfigFile.get()));
    }

    public

    public void save() {
        if (worldsConfig.isDone()) {
            try {
                worldsConfig.get().save(worldsConfigFile.get());
            } catch (IOException | InterruptedException | ExecutionException e) {
                ThrowableDebug.debug(e);
            }
        }
    }

    public @NotNull File getDataFolder() {
        return dataFolder;
    }

    public @NotNull YamlConfiguration get() throws ExecutionException, InterruptedException {
        return worldsConfig.get();
    }

    public @Nullable YamlConfiguration getOrNull() {
        return worldsConfig.getNow(null);
    }

    public @NotNull CompletableFuture<YamlConfiguration> getWorldsConfig() {
        return worldsConfig;
    }
}
