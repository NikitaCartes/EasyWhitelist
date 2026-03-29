package xyz.nikitacartes.easywhitelist.integrations;

import net.minecraft.server.permissions.Permission;
import net.minecraft.server.permissions.PermissionLevel;
import net.minecraft.commands.CommandSourceStack;
import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

import static me.lucko.fabric.api.permissions.v0.Permissions.check;
import static xyz.nikitacartes.easywhitelist.EasyWhitelist.permissionsLoaded;

public class Permissions {

    public static @NotNull Predicate<CommandSourceStack> require(@NotNull String permission, PermissionLevel defaultRequiredLevel) {
        if (permissionsLoaded) {
            return source -> check(source, permission, defaultRequiredLevel);
        } else {
            return source -> source.permissions().hasPermission(new Permission.HasCommandLevel(defaultRequiredLevel));
        }
    }
}
