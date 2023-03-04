package net.juligames.knockit.util;

import com.onarandombox.MultiverseCore.api.MVWorldManager;
import net.juligames.core.api.API;
import org.bukkit.World;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * @author Ture Bentzin
 * 04.03.2023
 */
public class KnockItUtil {

    public static <E> @NotNull Optional<E> getRandom(@NotNull Collection<E> e) {
        return e.stream()
                .skip((int) (e.size() * Math.random()))
                .findFirst();
    }

    @ApiStatus.Experimental
    public static @NotNull String ListToString(@NotNull List<String> list) {
        if(list.size() == 0) {
            return "";
        }
        if (list.size() == 1) {
            return list.get(0);
        }
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            String s = list.get(0);
            if(i == list.size() - 1) {
                builder.append(s);
            }
            builder.append(s).append(",");
        }
        return builder.toString();
    }

    public static void importWorldToMultiverse(@NotNull MVWorldManager manager, @NotNull String name) {
        if(manager.isMVWorld(name)) return;
        boolean b = manager.addWorld(name, World.Environment.CUSTOM, null, null, null, null);
        API.get().getAPILogger().debug("loaded world: " + name + " with result " + b + "!");

    }
}
