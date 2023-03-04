package net.juligames.knockit.world;

import com.onarandombox.MultiverseCore.MVWorld;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

/**
 * @author Ture Bentzin
 * 04.03.2023
 */
public class KnockItWorld {

    private final @NotNull MVWorld mvWorld;
    private final @NotNull String id;
    private final @NotNull String nameKey;
    private final @NotNull String descriptionKey;
    private final @NotNull Location spawn;
    private final @NotNull Limits limits;

    public KnockItWorld(@NotNull MVWorld mvWorld, @NotNull String id, @NotNull String nameKey,
                        @NotNull String descriptionKey, @NotNull Location spawn, @NotNull Limits limits) {
        this.mvWorld = mvWorld;
        this.id = id;
        this.nameKey = nameKey;
        this.descriptionKey = descriptionKey;
        this.spawn = spawn;
        this.limits = limits;
    }
}
