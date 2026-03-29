package xyz.nikitacartes.easywhitelist.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import xyz.nikitacartes.easywhitelist.integrations.Permissions;

import static com.mojang.brigadier.arguments.StringArgumentType.getString;
import static com.mojang.brigadier.arguments.StringArgumentType.word;
import static net.minecraft.server.permissions.PermissionLevel.ADMINS;
import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;
import static net.minecraft.server.commands.PardonCommand.pardonPlayers;
import static xyz.nikitacartes.easywhitelist.EasyWhitelist.getProfileFromNickname;

public class EasyPardonCommand {

    public static void registerCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(literal("easypardon")
                .requires(Permissions.require("easywhitelist.commands.easypardon", ADMINS))
                .then(argument("targets", word())
                        .executes(ctx ->
                                pardonPlayers(ctx.getSource(), getProfileFromNickname(getString(ctx, "targets"), ctx)))));
    }
}
