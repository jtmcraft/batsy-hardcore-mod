package batsy.hardcore.mod.kubejs.api;

import dev.latvian.mods.kubejs.player.SimplePlayerEventJS;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;

import java.util.Objects;

public class PlayerRevivingEventJS extends SimplePlayerEventJS {
    public PlayerRevivingEventJS(PlayerEntity playerEntity) {
        super(playerEntity);
    }

    public String getUuidAsString() {
        return Objects.requireNonNull(getPlayer()).getUuidAsString();
    }

    public void sendMessage(String message) {
        Objects.requireNonNull(getPlayer()).sendMessage(Text.literal(message));
    }
}
