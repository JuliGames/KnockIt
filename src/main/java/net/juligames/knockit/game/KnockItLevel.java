package net.juligames.knockit.game;

import net.juligames.core.api.API;
import net.juligames.core.paper.PaperMessageRecipient;
import net.juligames.knockit.KnockItPlugin;
import net.juligames.knockit.config.WorldConfigManager;
import net.juligames.knockit.util.KnockItUtil;
import net.juligames.knockit.world.KnockItWorld;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;

/**
 * @author Ture Bentzin
 * 04.03.2023
 */
public class KnockItLevel {

    private final @NotNull KnockIt knockIt;
    private final @NotNull KnockItWorld world;

    public KnockItLevel(@NotNull KnockIt knockIt, @NotNull KnockItWorld world) {
        this.knockIt = knockIt;
        this.world = world;
    }

    public static @NotNull KnockItLevel chooseLevel(@NotNull WorldConfigManager worldConfigManager, @NotNull KnockIt miniGame) {
        return new KnockItLevel(miniGame, KnockItUtil.getRandom(worldConfigManager.constructWorlds()).orElseThrow());
    }

    /**
     * called before sending the players
     */
    public void prepare() throws InterruptedException {
    }

    /**
     * called after all players are in the new level
     */
    public void cleanUp() {

    }

    public void pullPlayers() {
        Bukkit.getOnlinePlayers().forEach(player ->{
            World craftBukkitWorld = world.getMvWorld().getCBWorld();
            player.teleport(getWorld().getSpawn().toLocation(craftBukkitWorld));
            API.get().getMessageApi().sendMessage("knockit.levels.moved", new PaperMessageRecipient(player));
        });
    }

    private @NotNull KnockIt getKnockIt() {
        return knockIt;
    }

    public @NotNull KnockItWorld getWorld() {
        return world;
    }

    public @NotNull Location getSpawn() {
        return getWorld().getSpawn().toLocation(getWorld().getMvWorld().getCBWorld());
    }

    @Override
    public @NotNull String toString() {
        return world.getId() + " by " + KnockItUtil.ListToString(world.getBuilders()) + " @" + world.getMvWorld().getCBWorld().getWorldFolder().getName();
    }
}
