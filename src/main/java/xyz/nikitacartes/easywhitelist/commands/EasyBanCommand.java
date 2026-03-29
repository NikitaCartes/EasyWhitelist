package xyz.nikitacartes.easywhitelist.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import xyz.nikitacartes.easywhitelist.integrations.Permissions;

import static com.mojang.brigadier.arguments.StringArgumentType.getString;
import static com.mojang.brigadier.arguments.StringArgumentType.word;
import static net.minecraft.commands.arguments.MessageArgument.getMessage;
import static net.minecraft.commands.arguments.MessageArgument.message;
import static net.minecraft.server.permissions.PermissionLevel.ADMINS;
import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;
import static net.minecraft.server.commands.BanPlayerCommands.banPlayers;
import static xyz.nikitacartes.easywhitelist.EasyWhitelist.getProfileFromNickname;

public class EasyBanCommand {

    public static void registerCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(literal("easyban")
                .requires(Permissions.require("easywhitelist.commands.easyban", ADMINS))
                .then((argument("targets", word())
                        .executes(ctx ->
                                banPlayers(ctx.getSource(), getProfileFromNickname(getString(ctx, "targets"), ctx), null)))
                        .then(argument("reason", message())
                                .executes(ctx ->
                                        banPlayers(ctx.getSource(), getProfileFromNickname(getString(ctx, "targets"), ctx), getMessage(ctx, "reason"))))));
    }
}
