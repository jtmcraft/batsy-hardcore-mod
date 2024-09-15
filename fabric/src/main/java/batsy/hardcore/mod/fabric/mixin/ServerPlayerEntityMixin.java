package batsy.hardcore.mod.fabric.mixin;

import batsy.hardcore.mod.event.BatsyHardcorePlayerDeathEvent;
import batsy.hardcore.mod.util.BatsyHardcorePlayerTagsUtil;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin {
    @Inject(method = "onDeath", at = @At("HEAD"))
    public void onDeath(DamageSource damageSource, CallbackInfo ci) {
        ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity) (Object) this;
        if (BatsyHardcorePlayerTagsUtil.isBatsyHardcore(serverPlayerEntity)) {
            BatsyHardcorePlayerDeathEvent event = new BatsyHardcorePlayerDeathEvent(serverPlayerEntity);
            event.callEvent();
        }
    }
}
