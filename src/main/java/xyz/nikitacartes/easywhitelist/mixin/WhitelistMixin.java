package xyz.nikitacartes.easywhitelist.mixin;

import com.mojang.authlib.GameProfile;
import net.minecraft.server.Whitelist;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Whitelist.class)
public class WhitelistMixin {

    @Inject(method = "toString(Lcom/mojang/authlib/GameProfile;)Ljava/lang/String;", at = @At("HEAD"), cancellable = true)
    protected void toString(GameProfile gameProfile, CallbackInfoReturnable<String> cir) {
        cir.setReturnValue(gameProfile.getName());
    }
}
