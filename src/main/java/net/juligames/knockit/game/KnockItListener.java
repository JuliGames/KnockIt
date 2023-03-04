package net.juligames.knockit.game;

import net.juligames.core.api.API;
import net.juligames.core.paper.PaperMessageRecipient;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
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
        if(!isMiniGameActive()) return;
        if(event.getBlock().getWorld().equals(knockIt.getLevelOrThrow().getWorld().getMvWorld().getCBWorld()))
            event.setCancelled(true);
    }

    @ApiStatus.Experimental
    @EventHandler
    public void onBlockPlace(@NotNull BlockPlaceEvent event) {
        if(!isMiniGameActive()) return;
        if(event.getBlock().getWorld().equals(knockIt.getLevelOrThrow().getWorld().getMvWorld().getCBWorld()))
            event.setCancelled(true);
    }

    @EventHandler
    public void onJoin(@NotNull PlayerJoinEvent event){
        if(!isMiniGameActive()) return;
        Player player = event.getPlayer();
        player.teleport(knockIt.getLevelOrThrow().getSpawn());
        PaperMessageRecipient messageRecipient = new PaperMessageRecipient(player);
        API.get().getMessageApi().sendMessage("knockit.border", messageRecipient);
        API.get().getMessageApi().sendMessage("knockit.welcome.header", messageRecipient);
        API.get().getMessageApi().sendMessage("knockit.welcome.text", messageRecipient);
        API.get().getMessageApi().sendMessage("knockit.welcome.footer", messageRecipient, new String[]{knockIt.getKnockItPlugin().getDescription().getFullName()});
        API.get().getMessageApi().sendMessage("knockit.border", messageRecipient);
    }

    protected @NotNull KnockIt getKnockIt() {
        return knockIt;
    }

}
