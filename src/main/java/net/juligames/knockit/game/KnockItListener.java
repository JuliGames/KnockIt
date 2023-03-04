package net.juligames.knockit.game;

import net.juligames.core.api.API;
import net.juligames.core.paper.PaperMessageRecipient;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

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
        if(event.getBlock().getWorld().equals(knockIt.getLevel().getWorld().getMvWorld().getCBWorld()))
            event.setCancelled(true);
    }

    @EventHandler
    public void onJoin(@NotNull PlayerJoinEvent event){
        Player player = event.getPlayer();
        player.teleport(knockIt.getLevel().getSpawn());
        PaperMessageRecipient messageRecipient = new PaperMessageRecipient(player);
        API.get().getMessageApi().sendMessage("knockit.border", messageRecipient);
        API.get().getMessageApi().sendMessage("knockit.welcome.header", messageRecipient);
        API.get().getMessageApi().sendMessage("knockit.welcome.text", messageRecipient);
        API.get().getMessageApi().sendMessage("knockit.welcome.footer", messageRecipient);
        API.get().getMessageApi().sendMessage("knockit.border", messageRecipient);
    }

    protected @NotNull KnockIt getKnockIt() {
        return knockIt;
    }
}
