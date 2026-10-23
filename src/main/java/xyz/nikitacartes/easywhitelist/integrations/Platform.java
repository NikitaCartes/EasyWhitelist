package xyz.nikitacartes.easywhitelist.integrations;

//? if fabric {
import net.fabricmc.loader.api.FabricLoader;
//?} else {
/*import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.fml.ModList;
*///?}

import java.nio.file.Path;

/** The loader-specific lookups, behind one API. */
public final class Platform {
    private Platform() {}

    public static Path configDir() {
        //? if fabric {
        return FabricLoader.getInstance().getConfigDir();
        //?} else
        /*return FMLPaths.CONFIGDIR.get();*/
    }

    public static Path gameDir() {
        //? if fabric {
        return FabricLoader.getInstance().getGameDir();
        //?} else
        /*return FMLPaths.GAMEDIR.get();*/
    }

    public static boolean isModLoaded(String id) {
        //? if fabric {
        return FabricLoader.getInstance().isModLoaded(id);
        //?} else
        /*return ModList.get().isLoaded(id);*/
    }
}
