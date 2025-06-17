package xyz.nikitacartes.easywhitelist;

import com.mojang.authlib.GameProfile;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Uuids;
import org.apache.logging.log4j.LogManager;
import xyz.nikitacartes.easywhitelist.commands.*;

import java.util.Collection;
import java.util.Collections;

public class EasyWhitelist implements ModInitializer {

    public static Collection<GameProfile> getProfileFromNickname(String name) {
        return Collections.singletonList(new GameProfile(Uuids.getOfflinePlayerUuid(name), name));
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
