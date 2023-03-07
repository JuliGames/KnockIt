package net.juligames.knockit.world;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class KnockItWorldBuilder {

    private @Nullable String id;
    private KnockItWorld.@Nullable MessageHolder messageHolder;
    private @Nullable KnockItLocation spawn;
    private @Nullable Limits limits;
    private int minPlayers = 0;
    private @Nullable List<String> builders;
    private long duration = 900_000;

    public @NotNull KnockItWorldBuilder setId(@NotNull String id) {
        this.id = id;
        return this;
    }

    public @NotNull KnockItWorldBuilder setMessageKeyHolder(KnockItWorld.@NotNull MessageHolder messageHolder) {
        this.messageHolder = messageHolder;
        return this;
    }

    public @NotNull KnockItWorldBuilder setSpawn(@NotNull KnockItLocation spawn) {
        this.spawn = spawn;
        return this;
    }

    public @NotNull KnockItWorldBuilder setLimits(@NotNull Limits limits) {
        this.limits = limits;
        return this;
    }

    public @NotNull KnockItWorldBuilder setMinPlayers(int minPlayers) {
        this.minPlayers = minPlayers;
        return this;
    }

    public @NotNull KnockItWorldBuilder setBuilders(@NotNull List<String> builders) {
        this.builders = builders;
        return this;
    }

    public @NotNull KnockItWorldBuilder setDuration(long duration) {
        this.duration = duration;
        return this;
    }

    public @NotNull KnockItWorld build() {
        return new KnockItWorld(Objects.requireNonNull(id), Objects.requireNonNull(messageHolder),
                Objects.requireNonNull(spawn), Objects.requireNonNull(limits), minPlayers,
                Objects.requireNonNull(builders), duration);
    }
}