package batsy.hardcore.mod.stat;

import batsy.hardcore.mod.BatsyHardcoreMod;
import batsy.hardcore.mod.util.BatsyHardcoreUtil;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.stat.Stat;
import net.minecraft.stat.StatFormatter;
import net.minecraft.stat.Stats;
import net.minecraft.util.Identifier;

public class BatsyHardcoreModPlayerStats {
    public static final Stat<Identifier> BATSY_HARDCORE_TIME_STAT = registerCustomTimeStat(BatsyHardcoreMod.TIME_IN_HARDCORE_STAT, BatsyHardcoreUtil.id(BatsyHardcoreMod.TIME_IN_HARDCORE_STAT));

    private static Stat<Identifier> registerCustomTimeStat(String id, Identifier identifier) {
        Registry.register(Registries.CUSTOM_STAT, id, identifier);
        return Stats.CUSTOM.getOrCreateStat(identifier, StatFormatter.TIME);
    }

    public static void registerPlayerStats() {
        BatsyHardcoreMod.LOGGER.info("Registering Batsy Hardcore player statistics");
    }
}
