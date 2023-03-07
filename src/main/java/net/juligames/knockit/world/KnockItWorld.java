package net.juligames.knockit.world;

import com.onarandombox.MultiverseCore.api.MultiverseWorld;
import net.juligames.core.adventure.api.AdventureAPI;
import net.juligames.core.api.API;
import net.juligames.knockit.KnockItPlugin;
import net.juligames.knockit.util.KnockItUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

/**
 * @author Ture Bentzin
 * 04.03.2023
 */
public class KnockItWorld {

    private final @NotNull MultiverseWorld mvWorld;
    private final @NotNull String id;
    private final @NotNull KnockItWorld.MessageHolder messageHolder;
    private final @NotNull KnockItLocation spawn;
    private final @NotNull Limits limits;
    private final int minPlayers;
    private final @NotNull List<String> builders;
    private final long duration;

    protected static @NotNull MultiverseWorld worldFromID(@NotNull String id) {
       //load
        API.get().getAPILogger().debug("loading world with id: " + id);
        World world = importCBWorld(id);
        KnockItUtil.importWorldToMultiverse(KnockItPlugin.getMVWorldManager(), id);
        API.get().getAPILogger().debug("loaded world with id: " + id + " @" +  world.getWorldFolder().getPath());


        //get
        return KnockItPlugin.getMVWorldManager().getMVWorld(id);
    }

    private static @NotNull World importCBWorld(@NotNull String id) {
        return Objects.requireNonNull(new WorldCreator(id).createWorld());
    }

    public static @NotNull KnockItWorld fromSection(@NotNull String id, @NotNull ConfigurationSection section) {
        return new KnockItWorldBuilder().setId(id).
                setMessageKeyHolder(new MessageHolder(id, section.getString("defaultname", "unknown"),
                        section.getString("defaultdescription", "error")))
                .setMinPlayers(section.getInt("minplayers", 0))
                .setBuilders(section.getStringList("builders"))
                .setDuration(section.getLong("duration", 400_000))
                .setSpawn(KnockItLocation.fromSection(section.createSection("spawn")))
                .setLimits(Limits.fromSection(section.createSection("limits")))
                .build();
    }

    @Contract(pure = true)
    public KnockItWorld(@NotNull String id, @NotNull KnockItWorld.MessageHolder messageHolder,
                        @NotNull KnockItLocation spawn, @NotNull Limits limits, int minPlayers,
                        @NotNull List<String> builders, long duration) {

        this.mvWorld = worldFromID(id);
        this.id = id;
        this.messageHolder = messageHolder;
        this.spawn = spawn;
        this.limits = limits;
        this.minPlayers = minPlayers;
        this.builders = builders;
        this.duration = duration;
    }

    public @NotNull MultiverseWorld getMvWorld() {
        return mvWorld;
    }

    public @NotNull String getId() {
        return id;
    }

    public @NotNull KnockItWorld.MessageHolder getMessageHolder() {
        return messageHolder;
    }

    public @NotNull KnockItLocation getSpawn() {
        return spawn;
    }

    public @NotNull Limits getLimits() {
        return limits;
    }

    public int getMinPlayers() {
        return minPlayers;
    }

    public @NotNull List<String> getBuilders() {
        return builders;
    }

    public long getDuration() {
        return duration;
    }

    public static class MessageHolder {
        private final @NotNull Component name;
        private final @NotNull String descriptionKey;

        private MessageHolder(@NotNull Component name, @NotNull String descriptionKey) {
            this.name = name;
            this.descriptionKey = descriptionKey;
        }

        public MessageHolder(@NotNull String id, @NotNull String nameMiniMessage, @NotNull String description) {
            this(AdventureAPI.get().getAdventureTagManager().resolve(nameMiniMessage), "knockit_worlds_" + id + "_description");
            API.get().getMessageApi().registerMessage("knockit_worlds_" + id + "_description", description);
        }

        public @NotNull String getDescriptionKey() {
            return descriptionKey;
        }

        public @NotNull Component getName() {
            return name;
        }

        public @NotNull String getNameAsMiniMessage() {
            return AdventureAPI.get().getAdventureTagManager().fromComponent(getName());
        }

        @Override
        public @NotNull String toString() {
            return "\"" +  getName() + "\" + \"" + getDescriptionKey() + "\"";
        }
    }
}
