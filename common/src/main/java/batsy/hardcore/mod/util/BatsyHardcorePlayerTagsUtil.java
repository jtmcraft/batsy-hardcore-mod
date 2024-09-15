package batsy.hardcore.mod.util;

import batsy.hardcore.mod.BatsyHardcoreConfiguration;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.NotNull;

public final class BatsyHardcorePlayerTagsUtil {
    private BatsyHardcorePlayerTagsUtil() {}

    public static void setAltarLoaded(@NotNull ServerPlayerEntity serverPlayerEntity) {
        serverPlayerEntity.addCommandTag(BatsyHardcoreConfiguration.batsyHardcoreModeAltarLoaded);
    }

    public static void removeAltarLoaded(@NotNull ServerPlayerEntity serverPlayerEntity) {
        serverPlayerEntity.removeScoreboardTag(BatsyHardcoreConfiguration.batsyHardcoreModeAltarLoaded);
    }

    public static void add(@NotNull ServerPlayerEntity serverPlayerEntity) {
        serverPlayerEntity.addCommandTag(BatsyHardcoreConfiguration.batsyHardcoreModeTag);
    }

    public static void addAlive(@NotNull ServerPlayerEntity serverPlayerEntity) {
        serverPlayerEntity.addCommandTag(BatsyHardcoreConfiguration.batsyHardcoreModeAliveTag);
    }

    public static void addAll(ServerPlayerEntity serverPlayerEntity) {
        add(serverPlayerEntity);
        addAlive(serverPlayerEntity);
    }

    public static void remove(@NotNull ServerPlayerEntity serverPlayerEntity) {
        serverPlayerEntity.removeScoreboardTag(BatsyHardcoreConfiguration.batsyHardcoreModeTag);
    }

    public static void removeAlive(@NotNull ServerPlayerEntity serverPlayerEntity) {
        serverPlayerEntity.removeScoreboardTag(BatsyHardcoreConfiguration.batsyHardcoreModeAliveTag);
    }

    public static void removeAll(@NotNull ServerPlayerEntity serverPlayerEntity) {
        remove(serverPlayerEntity);
        removeAlive(serverPlayerEntity);
    }

    public static boolean isBatsyHardcore(@NotNull ServerPlayerEntity serverPlayerEntity) {
        return serverPlayerEntity.getCommandTags().contains(BatsyHardcoreConfiguration.batsyHardcoreModeTag);
    }

    public static void onClone(ServerPlayerEntity newPlayer) {
        BatsyHardcorePlayerTagsUtil.add(newPlayer);
        BatsyHardcorePlayerTagsUtil.removeAlive(newPlayer);
        BatsyHardcorePlayerTagsUtil.removeAltarLoaded(newPlayer);
    }

    public static boolean isAltarLoaded(ServerPlayerEntity serverPlayerEntity) {
        return serverPlayerEntity.getCommandTags().contains(BatsyHardcoreConfiguration.batsyHardcoreModeAltarLoaded);
    }
}
