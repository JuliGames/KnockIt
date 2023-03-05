package net.juligames.knockit.commands;

import net.juligames.core.api.API;
import net.juligames.core.api.minigame.BasicMiniGame;
import net.juligames.core.minigame.api.MiniGame;
import net.juligames.core.paper.PaperMessageRecipient;
import net.juligames.core.paper.perms.PermissionConditions;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import static net.juligames.core.api.message.MessageApi.repl;

/**
 * @author Ture Bentzin
 * 04.03.2023
 */
public class StausCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        PaperMessageRecipient messageRecipient = new PaperMessageRecipient(sender);
        if (PermissionConditions.hasPermission(sender, "knockit.status").getResult()) {
            if (API.get().getLocalMiniGame().isPresent()) {
                BasicMiniGame miniGame = API.get().getLocalMiniGame().get();
                if (miniGame instanceof MiniGame miniGameImpl) {
                    if (miniGameImpl.getMiniGameState().isPresent()) {
                        API.get().getMessageApi().sendMessage("knockit.cmd.status.cuurent",
                                messageRecipient, repl(miniGame.getFullDescription(),
                                        miniGameImpl.getMiniGameState().get().name()));
                    } else {
                        API.get().getMessageApi().sendMessage("knockit.cmd.status.cuurent",
                                messageRecipient, repl(miniGame.getFullDescription(), "Unknown"));
                    }
                } else {
                    API.get().getMessageApi().sendMessage("knockit.cmd.status.cuurent",
                            messageRecipient, repl(miniGame.getFullDescription(), "Unsupported"));
                }
                return true;
            } else {
                API.get().getMessageApi().sendMessage("knockit.cmd.status.error", messageRecipient);
                return false;
            }

        }
        //NoPerms
        return false;
    }
}
