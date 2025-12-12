package xyz.nikitacartes.easywhitelist.integrations;

import net.minecraft.command.permission.Permission;
import net.minecraft.command.permission.PermissionLevel;
import net.minecraft.server.command.ServerCommandSource;
import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

import static me.lucko.fabric.api.permissions.v0.Permissions.check;
import static xyz.nikitacartes.easywhitelist.EasyWhitelist.permissionsLoaded;

public class Permissions {

    public static @NotNull Predicate<ServerCommandSource> require(@NotNull String permission, PermissionLevel defaultRequiredLevel) {
        if (permissionsLoaded) {
            return source -> check(source, permission, defaultRequiredLevel);
        } else {
            return source -> source.getPermissions().hasPermission(new Permission.Level(defaultRequiredLevel));
        }
    }
}
