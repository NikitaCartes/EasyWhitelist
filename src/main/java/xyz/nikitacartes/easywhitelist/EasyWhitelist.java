package xyz.nikitacartes.easywhitelist;

import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;

public class EasyWhitelist implements ModInitializer {

    @Override
    public void onInitialize() {
        LogManager.getLogger().info("[EasyWhitelist] Whitelist is now name-based.");
    }
}
