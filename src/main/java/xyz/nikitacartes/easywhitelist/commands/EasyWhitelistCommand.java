package xyz.nikitacartes.easywhitelist.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.command.ServerCommandSource;
import xyz.nikitacartes.easywhitelist.integrations.Permissions;

import static com.mojang.brigadier.arguments.StringArgumentType.getString;
import static com.mojang.brigadier.arguments.StringArgumentType.word;
import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;
import static net.minecraft.server.dedicated.command.WhitelistCommand.executeAdd;
import static net.minecraft.server.dedicated.command.WhitelistCommand.executeRemove;
import static xyz.nikitacartes.easywhitelist.EasyWhitelist.getProfileFromNickname;

public class EasyWhitelistCommand {

    public static void registerCommand(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal("easywhitelist")
                .requires(Permissions.require("easywhitelist.commands.easywhitelist.root", 3))
                .then(literal("add")
                        .requires(Permissions.require("easywhitelist.commands.easywhitelist.add", 3))
                        .then(argument("targets", word())
                                .executes(ctx ->
                                        executeAdd(ctx.getSource(), getProfileFromNickname(getString(ctx, "targets")))
                                )
                        )
                )
                .then(literal("remove")
                        .requires(Permissions.require("easywhitelist.commands.easywhitelist.remove", 3))
                        .then(argument("targets", word())
                                .executes(ctx ->
                                        executeRemove(ctx.getSource(), getProfileFromNickname(getString(ctx, "targets"))))
                        )
                )
        );
    }

}
