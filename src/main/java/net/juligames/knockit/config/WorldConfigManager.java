package net.juligames.knockit.config;

import net.juligames.knockit.world.KnockItWorld;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.nio.file.NoSuchFileException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Ture Bentzin
 * 04.03.2023
 */
public class WorldConfigManager {

    private final @NotNull File searchDir;
    private final @NotNull Map<File, YamlConfiguration> knockItWorldSpecifications = new HashMap<>();

    public WorldConfigManager(@NotNull File searchDir) {
        this.searchDir = searchDir;
    }

    public @NotNull File getSearchDir() {
        return searchDir;
    }

    public void scan() throws NoSuchFileException {
        knockItWorldSpecifications.clear();
        if (getSearchDir().exists() && getSearchDir().isDirectory()) {
            File[] children = searchDir.listFiles(File::isDirectory);
            if (children != null)
                for (File child : children) {
                    //has knockit.yaml
                    File knockit = new File(child, "knockit.yaml");
                    if (knockit.exists()) {
                        knockItWorldSpecifications.put(child, YamlConfiguration.loadConfiguration(knockit));
                    }
                }
        } else {
            throw new NoSuchFileException(searchDir + " was not found!");
        }
    }

    public @NotNull Map<File, YamlConfiguration> getKnockItWorldSpecifications() {
        return knockItWorldSpecifications;
    }

    public @NotNull Collection<KnockItWorld> constructWorlds() {
        return getKnockItWorldSpecifications().entrySet().stream().map(entry ->
                KnockItWorld.fromSection(entry.getKey().getName().replace("./", ""), // oh, this could fire back...
                        entry.getValue())).toList();
    }
}
