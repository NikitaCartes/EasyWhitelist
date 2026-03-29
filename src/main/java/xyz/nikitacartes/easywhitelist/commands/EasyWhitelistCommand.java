package xyz.nikitacartes.easywhitelist.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import xyz.nikitacartes.easywhitelist.integrations.Permissions;

import static com.mojang.brigadier.arguments.StringArgumentType.getString;
import static com.mojang.brigadier.arguments.StringArgumentType.word;
import static net.minecraft.server.permissions.PermissionLevel.ADMINS;
import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;
import static net.minecraft.server.commands.WhitelistCommand.addPlayers;
import static net.minecraft.server.commands.WhitelistCommand.removePlayers;
import static xyz.nikitacartes.easywhitelist.EasyWhitelist.getProfileFromNickname;

public class EasyWhitelistCommand {

    public static void registerCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(literal("easywhitelist")
                .requires(Permissions.require("easywhitelist.commands.easywhitelist.root", ADMINS))
                .then(literal("add")
                        .requires(Permissions.require("easywhitelist.commands.easywhitelist.add", ADMINS))
                        .then(argument("targets", word())
                                .executes(ctx ->
                                        addPlayers(ctx.getSource(), getProfileFromNickname(getString(ctx, "targets"), ctx))
                                )
                        )
                )
                .then(literal("remove")
                        .requires(Permissions.require("easywhitelist.commands.easywhitelist.remove", ADMINS))
                        .then(argument("targets", word())
                                .executes(ctx ->
                                        removePlayers(ctx.getSource(), getProfileFromNickname(getString(ctx, "targets"), ctx)))
                        )
                )
        );
    }

}
