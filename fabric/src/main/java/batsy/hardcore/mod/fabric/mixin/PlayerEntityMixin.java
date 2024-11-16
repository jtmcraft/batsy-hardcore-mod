package batsy.hardcore.mod.fabric.mixin;

import batsy.hardcore.mod.util.BatsyHardcorePlayerTagsUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {
    @Inject(method = "dropInventory", at = @At("HEAD"), cancellable = true)
    public void dropInventory(CallbackInfo ci) {
        PlayerEntity playerEntity = (PlayerEntity) (Object) this;
        if (playerEntity instanceof ServerPlayerEntity serverPlayerEntity) {
            if (BatsyHardcorePlayerTagsUtil.isBatsyHardcore(serverPlayerEntity)) {
                ci.cancel();
            }
        }
    }
}
