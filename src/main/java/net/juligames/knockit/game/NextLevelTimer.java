package net.juligames.knockit.game;

import de.bentzin.tools.time.Timer;
import net.juligames.core.api.API;
import net.juligames.core.paper.PaperMessageRecipient;
import net.juligames.knockit.KnockItPlugin;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.util.function.Predicate;

import static net.juligames.core.api.message.MessageApi.repl;

/**
 * @author Ture Bentzin
 * 05.03.2023
 */
public class NextLevelTimer extends Timer {
    private final @NotNull Duration initial;
    private final @NotNull KnockItLevel knockItLevel;

    public NextLevelTimer(@NotNull Duration initial, @NotNull KnockItLevel knockItLevel) {
        super((int) initial.getSeconds());
        this.initial = initial;
        this.knockItLevel = knockItLevel;
    }

    public static @NotNull Predicate<Integer> durationPredicate(@NotNull Duration duration) {
        return integer -> integer.equals((int) duration.getSeconds());
    }

    public @NotNull Duration getInitial() {
        return initial;
    }

    public @NotNull KnockItLevel getTargetKnockItLevel() {
        return knockItLevel;
    }

    @Override
    public void update(int seconds) {
        if (durationPredicate(initial).test(seconds)) {
            //initial
            API.get().getMessageApi().broadcastMessage("knockit.next.time", repl())
        }

        if (durationPredicate(Duration.ofMinutes(10)).test(seconds)) {

        }
    }

    @Override
    protected void finish() {
        KnockItPlugin.getKnockIt().ifPresent(knockIt -> knockIt.getLogger().info("timer finished: next level is " + knockItLevel));
        KnockItPlugin.getKnockIt().ifPresent(knockIt -> knockIt.nextLevel(knockItLevel));
    }
}
