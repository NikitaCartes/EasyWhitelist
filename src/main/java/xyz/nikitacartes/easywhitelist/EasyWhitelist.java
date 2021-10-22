package xyz.nikitacartes.easywhitelist;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import org.apache.logging.log4j.LogManager;
import xyz.nikitacartes.easywhitelist.commands.EasyWhitelistCommand;

public class EasyWhitelist implements ModInitializer {

    @Override
    public void onInitialize() {
        LogManager.getLogger().info("[EasyWhitelist] Whitelist is now name-based.");

        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> EasyWhitelistCommand.registerCommand(dispatcher));
    }
}
