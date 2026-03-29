package xyz.nikitacartes.easywhitelist;

import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.players.NameAndId;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.core.UUIDUtil;
import org.apache.logging.log4j.LogManager;
import xyz.nikitacartes.easywhitelist.commands.*;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

public class EasyWhitelist implements ModInitializer {

    public static Collection<NameAndId> getProfileFromNickname(String name, CommandContext<CommandSourceStack> ctx) {
        return Collections.singletonList(Objects.requireNonNullElseGet(getOnlineProfileFromNickname(name, ctx), () -> getOfflineProfileFromNickname(name)));
    }

    public static NameAndId getOfflineProfileFromNickname(String name) {
        return new NameAndId(UUIDUtil.createOfflinePlayerUUID(name), name);
    }

    public static NameAndId getOnlineProfileFromNickname(String name, CommandContext<CommandSourceStack> ctx) {
        ServerPlayer player = ctx.getSource().getServer().getPlayerList().getPlayerByName(name);
        if (player == null) {
            return null;
        }
        return new NameAndId(player.getGameProfile());
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
