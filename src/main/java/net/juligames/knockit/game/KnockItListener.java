package net.juligames.knockit.game;

import net.juligames.core.adventure.api.AdventureAPI;
import net.juligames.core.api.API;
import net.juligames.core.paper.PaperMessageRecipient;
import net.juligames.knockit.world.KnockItWorld;
import net.juligames.knockit.world.Limits;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

import static net.juligames.knockit.game.KnockIt.isMiniGameActive;


/*
All of this will be removed and remade with the implementation of the dynamic kit system!
Everything in this file is subject to change!
 */

/**
 * @author Ture Bentzin
 * 04.03.2023
 */
public class KnockItListener implements Listener {

    private final @NotNull KnockIt knockIt;

    public KnockItListener(@NotNull KnockIt knockIt) {
        this.knockIt = knockIt;
    }

    @ApiStatus.Experimental
    @EventHandler
    public void onBlockBreak(@NotNull BlockBreakEvent event) {
        if (!isMiniGameActive()) return;
        if (event.getBlock().getWorld().equals(knockIt.getLevelOrThrow().getWorld().getMvWorld().getCBWorld()))
            event.setCancelled(true);
    }

    @ApiStatus.Experimental
    @EventHandler
    public void onBlockPlace(@NotNull BlockPlaceEvent event) {
        if (!isMiniGameActive()) return;
        if (event.getBlock().getWorld().equals(knockIt.getLevelOrThrow().getWorld().getMvWorld().getCBWorld()))
            event.setCancelled(true);
    }

    @EventHandler
    public void onJoin(@NotNull PlayerJoinEvent event) {
        if (!isMiniGameActive()) return;
        Player player = event.getPlayer();
        player.teleport(knockIt.getLevelOrThrow().getSpawn());
        knockIt.getSpawnUUIDs().add(player.getUniqueId());
        PaperMessageRecipient messageRecipient = new PaperMessageRecipient(player);
        API.get().getMessageApi().sendMessage("knockit.border", messageRecipient);
        API.get().getMessageApi().sendMessage("knockit.welcome.header", messageRecipient);
        API.get().getMessageApi().sendMessage("knockit.welcome.text", messageRecipient);
        API.get().getMessageApi().sendMessage("knockit.welcome.footer", messageRecipient,
                new String[]{knockIt.getKnockItPlugin().getDescription().getFullName()});
        API.get().getMessageApi().sendMessage("knockit.border", messageRecipient);
        event.joinMessage(AdventureAPI.get().forAudience("knockit.join", player));
    }

    @EventHandler
    public void onQuit(@NotNull PlayerQuitEvent event) {
        event.quitMessage(AdventureAPI.get().forAudience("knockit.join", event.getPlayer()));
        getKnockIt().getSpawnUUIDs().remove(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onBorder(@NotNull PlayerMoveEvent event) {
        if (!isMiniGameActive()) return;
        if (getKnockIt().getLevel() != null) {
            KnockItWorld knockItWorld = getKnockIt().getLevel().getWorld();
            Limits limits = knockItWorld.getLimits();
            //TODO check for state of the player?
            boolean checkLocation = limits.checkLocation(event.getTo(),
                    getKnockIt().getSpawnUUIDs().contains(event.getPlayer().getUniqueId()));
            if(!checkLocation) {
                //violation
                getKnockIt().playerKilledByViolation(event.getPlayer());
                getKnockIt().getLogger().info(event.getPlayer().getName() + " violated!");
            }
        }
    }

    @EventHandler
    public void onEntry(@NotNull PlayerMoveEvent event) {
        if (!isMiniGameActive()) return;
        if (!isMiniGameActive()) return;
        if (getKnockIt().getLevel() != null) {
            KnockItWorld knockItWorld = getKnockIt().getLevel().getWorld();
            Limits limits = knockItWorld.getLimits();
            UUID uuid = event.getPlayer().getUniqueId();
            if (getKnockIt().getSpawnUUIDs().contains(uuid)) {
                if (event.getTo().getBlockY() <= limits.entryLevel()) {
                    getKnockIt().getSpawnUUIDs().remove(uuid);
                    getKnockIt().playerMatchEntry(event.getPlayer());
                }
            }
        }
    }

    protected @NotNull KnockIt getKnockIt() {
        return knockIt;
    }

}
