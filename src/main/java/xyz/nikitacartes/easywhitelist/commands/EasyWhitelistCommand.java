package xyz.nikitacartes.easywhitelist.commands;

import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.dedicated.command.WhitelistCommand;

import java.util.Collection;
import java.util.Collections;

import static com.mojang.brigadier.arguments.StringArgumentType.getString;
import static com.mojang.brigadier.arguments.StringArgumentType.word;
import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;
import static net.minecraft.server.dedicated.command.WhitelistCommand.executeAdd;

public class EasyWhitelistCommand {

    public static void registerCommand(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal("easywhitelist")
                .requires(source -> source.hasPermissionLevel(3))
                .then(literal("add")
                        .then(argument("targets", word())
                                .executes(ctx ->
                                        executeAdd(ctx.getSource(), getProfileFromNickname(getString(ctx, "targets")))
                                ))));
    }

    public static Collection<GameProfile> getProfileFromNickname(String name) {
        return Collections.singletonList(new GameProfile(PlayerEntity.getOfflinePlayerUuid(name), name));
    }

}
