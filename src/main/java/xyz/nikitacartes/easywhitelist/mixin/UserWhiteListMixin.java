package xyz.nikitacartes.easywhitelist.mixin;

import net.minecraft.server.players.NameAndId;
import net.minecraft.server.players.UserWhiteList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(UserWhiteList.class)
public class UserWhiteListMixin {

    @Inject(method = "getKeyForUser(Lnet/minecraft/server/players/NameAndId;)Ljava/lang/String;", at = @At("HEAD"), cancellable = true)
    protected void toString(NameAndId playerConfigEntry, CallbackInfoReturnable<String> cir) {
        cir.setReturnValue(playerConfigEntry.name());
    }
}
