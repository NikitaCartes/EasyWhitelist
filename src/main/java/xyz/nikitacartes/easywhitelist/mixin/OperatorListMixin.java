package xyz.nikitacartes.easywhitelist.mixin;

import net.minecraft.server.OperatorList;
import net.minecraft.server.PlayerConfigEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(OperatorList.class)
public class OperatorListMixin {

    @Inject(method = "toString(Lnet/minecraft/server/PlayerConfigEntry;)Ljava/lang/String;", at = @At("HEAD"), cancellable = true)
    protected void toString(PlayerConfigEntry playerConfigEntry, CallbackInfoReturnable<String> cir) {
        cir.setReturnValue(playerConfigEntry.name());
    }
}
