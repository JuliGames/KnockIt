package net.juligames.knockit.world;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;

/**
 * @author Ture Bentzin
 * 04.03.2023
 */
public record KnockItLocation(int x, int y, int z, float yaw, float pitch) {

    public static @NotNull KnockItLocation fromSection(@NotNull ConfigurationSection section) {
        final int
                x = section.getInt("x"),
                y = section.getInt("y"),
                z = section.getInt("z");
        final float
                yaw = (float) section.getDouble("yaw"),
                pitch = (float) section.getDouble("pitch");

        return new KnockItLocation(x, y, z, y, pitch);
    }

    public @NotNull Location toLocation(@NotNull World world) {
        return new Location(world, x, y, z, yaw, pitch);
    }

}
