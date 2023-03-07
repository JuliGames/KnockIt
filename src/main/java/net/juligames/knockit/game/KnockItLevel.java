package net.juligames.knockit.game;

import net.juligames.core.adventure.api.AdventureAPI;
import net.juligames.core.api.API;
import net.juligames.core.paper.PaperMessageRecipient;
import net.juligames.knockit.config.WorldConfigManager;
import net.juligames.knockit.util.KnockItUtil;
import net.juligames.knockit.world.KnockItWorld;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.NoSuchElementException;

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
        return new KnockItLevel(miniGame, KnockItUtil.getRandom(worldConfigManager.constructWorlds()).orElseThrow(()
                -> new NoSuchElementException("KnockIt failed to find a level! Make sure your levels are set up properly!")));
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
        //remove leftovers from kits?
    }

    public void pullPlayers() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            World craftBukkitWorld = world.getMvWorld().getCBWorld();
            player.teleport(getWorld().getSpawn().toLocation(craftBukkitWorld));
            final String miniMessageName = world.getMessageHolder().getNameAsMiniMessage();
            API.get().getMessageApi().sendMessage("knockit.levels.moved", new PaperMessageRecipient(player),
                    new String[]{miniMessageName});
            welcomePlayer(player);
        });
    }

    public void welcomePlayer(@NotNull Player player) {
        player.showTitle(buildTitle(player));
        //play sound?
        API.get().getMessageApi().sendMessage("knockit.currentmap", new PaperMessageRecipient(player));
        //display level or stats (kd, kills, deaths, current kit)
    }

    public @NotNull Title buildTitle(@NotNull Audience audience) {
        Component title =
                getWorld().getMessageHolder().getName(),
                subtitle =
                        AdventureAPI.get().forAudience(getWorld().getMessageHolder().getDescriptionKey(), audience);
        return Title.title(title, subtitle);
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
