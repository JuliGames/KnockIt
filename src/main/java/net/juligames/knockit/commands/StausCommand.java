package net.juligames.knockit.commands;

import com.destroystokyo.paper.brigadier.BukkitBrigadierCommand;
import com.destroystokyo.paper.brigadier.BukkitBrigadierCommandSource;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

/**
 * @author Ture Bentzin
 * 04.03.2023
 */
public class StausCommand{

    public static @NotNull BukkitBrigadierCommand<BukkitBrigadierCommandSource> get() {
        return new BukkitBrigadierCommand<BukkitBrigadierCommandSource>() {
            @Override
            public int run(CommandContext<BukkitBrigadierCommandSource> context) throws CommandSyntaxException {
                return 0;
            }

            @Override
            public CompletableFuture<Suggestions> getSuggestions(CommandContext<BukkitBrigadierCommandSource> context, SuggestionsBuilder builder) throws CommandSyntaxException {
                return null;
            }

            @Override
            public boolean test(BukkitBrigadierCommandSource bukkitBrigadierCommandSource) {
                return false;
            }
        }
    }
}
