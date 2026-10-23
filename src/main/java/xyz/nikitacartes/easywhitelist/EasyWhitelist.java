package xyz.nikitacartes.easywhitelist;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
//? if fabric {
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
//?} else {
/*import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
*///?}
import net.minecraft.server.players.NameAndId;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.core.UUIDUtil;
import org.apache.logging.log4j.LogManager;
import xyz.nikitacartes.easywhitelist.commands.*;
import xyz.nikitacartes.easywhitelist.integrations.Platform;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

//? if fabric {
public class EasyWhitelist implements ModInitializer {
//?} else {
/*@net.neoforged.fml.common.Mod("easywhitelist")
public class EasyWhitelist {
*///?}

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
        //? if >=1.21.9 {
        return new NameAndId(player.getGameProfile());
        //?} else
        /*return player.getGameProfile();*/
    }

    public static boolean permissionsLoaded = false;

    public static void init() {
        LogManager.getLogger().info("[EasyWhitelist] Whitelist is now name-based.");
        //? if fabric {
        if (Platform.isModLoaded("fabric-permissions-api-v0")) {
            permissionsLoaded = true;
        }
        //?}
    }

    public static void registerCommands(CommandDispatcher<CommandSourceStack> dispatcher) {
        EasyWhitelistCommand.registerCommand(dispatcher);
        EasyBanCommand.registerCommand(dispatcher);
        EasyPardonCommand.registerCommand(dispatcher);
        EasyOpCommand.registerCommand(dispatcher);
        EasyDeOpCommand.registerCommand(dispatcher);
    }

    //? if fabric {
    @Override
    public void onInitialize() {
        init();
        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated, environment) -> registerCommands(dispatcher));
    }
    //?} else {
    /*public EasyWhitelist() {
        init();
        NeoForge.EVENT_BUS.addListener((RegisterCommandsEvent event) -> registerCommands(event.getDispatcher()));
    }
    *///?}
}
