package xyz.nikitacartes.easywhitelist.mixin;

import net.minecraft.server.PlayerConfigEntry;
import net.minecraft.server.Whitelist;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Whitelist.class)
public class WhitelistMixin {

    @Inject(method = "toString(Lnet/minecraft/server/PlayerConfigEntry;)Ljava/lang/String;", at = @At("HEAD"), cancellable = true)
    protected void toString(PlayerConfigEntry playerConfigEntry, CallbackInfoReturnable<String> cir) {
        cir.setReturnValue(playerConfigEntry.name());
    }
}
