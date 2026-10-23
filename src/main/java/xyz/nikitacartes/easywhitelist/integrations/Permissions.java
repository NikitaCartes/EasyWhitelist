package xyz.nikitacartes.easywhitelist.integrations;

import net.minecraft.commands.CommandSourceStack;
//? if >=1.21.11 {
import net.minecraft.server.permissions.Permission;
import net.minecraft.server.permissions.PermissionLevel;
//?}
import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

//? if fabric {
import static me.lucko.fabric.api.permissions.v0.Permissions.check;
import static xyz.nikitacartes.easywhitelist.EasyWhitelist.permissionsLoaded;
//?}

public class Permissions {

    // Permission levels as ints: the PermissionLevel enum only exists from 1.21.11.
    public static final int ADMINS = 3;
    public static final int OWNERS = 4;

    public static boolean hasPermission(@NotNull CommandSourceStack source, @NotNull String permission, int defaultRequiredLevel) {
        //? if fabric {
        if (permissionsLoaded) {
            return check(source, permission, defaultRequiredLevel);
        }
        //?}
        //? if >=1.21.11 {
        return source.permissions().hasPermission(new Permission.HasCommandLevel(PermissionLevel.byId(defaultRequiredLevel)));
        //?} else {
        /*return source.hasPermission(defaultRequiredLevel);
        *///?}
    }

    public static @NotNull Predicate<CommandSourceStack> require(@NotNull String permission, int defaultRequiredLevel) {
        return source -> hasPermission(source, permission, defaultRequiredLevel);
    }
}
