package xyz.nikitacartes.easywhitelist;

import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.PlayerConfigEntry;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Uuids;
import org.apache.logging.log4j.LogManager;
import xyz.nikitacartes.easywhitelist.commands.*;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

public class EasyWhitelist implements ModInitializer {

    public static Collection<PlayerConfigEntry> getProfileFromNickname(String name, CommandContext<ServerCommandSource> ctx) {
        return Collections.singletonList(Objects.requireNonNullElseGet(getOnlineProfileFromNickname(name, ctx), () -> getOfflineProfileFromNickname(name)));
    }

    public static PlayerConfigEntry getOfflineProfileFromNickname(String name) {
        return new PlayerConfigEntry(Uuids.getOfflinePlayerUuid(name), name);
    }

    public static PlayerConfigEntry getOnlineProfileFromNickname(String name, CommandContext<ServerCommandSource> ctx) {
        ServerPlayerEntity player = ctx.getSource().getServer().getPlayerManager().getPlayer(name);
        if (player == null) {
            return null;
        }
        return new PlayerConfigEntry(player.getGameProfile());
    }

    public static boolean permissionsLoaded = false;

    @Override
    public void onInitialize() {
        LogManager.getLogger().info("[EasyWhitelist] Whitelist is now name-based.");

        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated, environment) -> {
            EasyWhitelistCommand.registerCommand(dispatcher);
            EasyBanCommand.registerCommand(dispatcher);
            EasyPardonCommand.registerCommand(dispatcher);
            EasyOpCommand.registerCommand(dispatcher);
            EasyDeOpCommand.registerCommand(dispatcher);
        });

        if (FabricLoader.getInstance().isModLoaded("fabric-permissions-api-v0")) {
            permissionsLoaded = true;
        }
    }
}
