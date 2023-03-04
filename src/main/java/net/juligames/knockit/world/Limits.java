package net.juligames.knockit.world;

import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;

import java.lang.module.Configuration;

/**
 * @author Ture Bentzin
 * 04.03.2023
 */
public record Limits(int minX, int maxX, int minY, int minZ, int maxZ, int entryLevel) {

    public static Limits fromSection(@NotNull ConfigurationSection section) {
        new Location().get
        section.get("")
    }

    public boolean checkLocation(@NotNull Location location, boolean aboveEntryLevel) {
        return checkLocation(location.getBlockX(), location.getBlockY(), location.getBlockZ(), aboveEntryLevel);
    }

    public boolean checkLocation(@NotNull Location location) {
        return checkLocation(location, false);
    }

    public boolean checkLocation(int x, int y, int z, boolean aboveEntryLevel) {
        return (x < maxX && x > minX) && (z < maxZ && z > minZ) && (y > minY) && (aboveEntryLevel || (y < entryLevel));
    }
}
