package xyz.nikitacartes.easywhitelist.integrations;

import net.minecraft.server.command.ServerCommandSource;
import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

import static me.lucko.fabric.api.permissions.v0.Permissions.check;
import static xyz.nikitacartes.easywhitelist.EasyWhitelist.permissionsLoaded;

public class Permissions {

    public static @NotNull Predicate<ServerCommandSource> require(@NotNull String permission, boolean defaultValue) {
        if (permissionsLoaded) {
            return source -> check(source, permission, defaultValue);
        } else {
            return source -> defaultValue;
        }
    }

    public static @NotNull Predicate<ServerCommandSource> require(@NotNull String permission, int defaultRequiredLevel) {
        if (permissionsLoaded) {
            return source -> check(source, permission, defaultRequiredLevel);
        } else {
            return source -> source.hasPermissionLevel(defaultRequiredLevel);
        }
    }
}
