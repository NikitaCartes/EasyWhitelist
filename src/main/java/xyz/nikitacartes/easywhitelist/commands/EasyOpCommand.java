package xyz.nikitacartes.easywhitelist.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import xyz.nikitacartes.easywhitelist.integrations.Permissions;

import static com.mojang.brigadier.arguments.StringArgumentType.getString;
import static com.mojang.brigadier.arguments.StringArgumentType.word;
import static net.minecraft.server.permissions.PermissionLevel.OWNERS;
import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;
import static net.minecraft.server.commands.OpCommand.opPlayers;
import static xyz.nikitacartes.easywhitelist.EasyWhitelist.getProfileFromNickname;

public class EasyOpCommand {

    public static void registerCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(literal("easyop")
                .requires(Permissions.require("easywhitelist.commands.easyop", OWNERS))
                .then(argument("targets", word())
                        .executes(ctx ->
                                opPlayers(ctx.getSource(), getProfileFromNickname(getString(ctx, "targets"), ctx)))));
    }
}
