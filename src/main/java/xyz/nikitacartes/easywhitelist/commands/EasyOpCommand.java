package xyz.nikitacartes.easywhitelist.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.command.ServerCommandSource;

import me.lucko.fabric.api.permissions.v0.Permissions;

import static com.mojang.brigadier.arguments.StringArgumentType.getString;
import static com.mojang.brigadier.arguments.StringArgumentType.word;
import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;
import static net.minecraft.server.dedicated.command.OpCommand.op;
import static xyz.nikitacartes.easywhitelist.EasyWhitelist.getProfileFromNickname;

public class EasyOpCommand {

    public static void registerCommand(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal("easyop")
                .requires(Permissions.require("easywhitelist.commands.easyop", 4))
                .then(argument("targets", word())
                        .executes(ctx ->
                                op(ctx.getSource(), getProfileFromNickname(getString(ctx, "targets"))))));
    }
}
