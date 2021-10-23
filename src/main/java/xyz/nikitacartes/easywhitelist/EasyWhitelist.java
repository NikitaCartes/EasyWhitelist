package xyz.nikitacartes.easywhitelist;

import com.mojang.authlib.GameProfile;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.entity.player.PlayerEntity;
import org.apache.logging.log4j.LogManager;
import xyz.nikitacartes.easywhitelist.commands.*;

import java.util.Collection;
import java.util.Collections;

public class EasyWhitelist implements ModInitializer {

    public static Collection<GameProfile> getProfileFromNickname(String name) {
        return Collections.singletonList(new GameProfile(PlayerEntity.getOfflinePlayerUuid(name), name));
    }

    @Override
    public void onInitialize() {
        LogManager.getLogger().info("[EasyWhitelist] Whitelist is now name-based.");

        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
            EasyWhitelistCommand.registerCommand(dispatcher);
            EasyBanCommand.registerCommand(dispatcher);
            EasyPardonCommand.registerCommand(dispatcher);
            EasyOpCommand.registerCommand(dispatcher);
            EasyDeOpCommand.registerCommand(dispatcher);
        });
    }
}
