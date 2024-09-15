package batsy.hardcore.mod.data;

import batsy.hardcore.mod.BatsyHardcoreMod;
import com.google.gson.Gson;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public final class ReviveAltarDataFile {
    private ReviveAltarDataFile() {}

    private static @NotNull Path getJsonFilePath(String gameDataDirectory, String uuid) {
        return Paths.get(gameDataDirectory, uuid + ".json");
    }

    public static boolean delete(String gameDataDirectory, String uuid) {
        try {
            Files.deleteIfExists(getJsonFilePath(gameDataDirectory, uuid));
            return true;
        } catch (IOException e) {
            BatsyHardcoreMod.LOGGER.error(e.getMessage());
            return false;
        }
    }

    public static boolean write(String gameDataDirectory, String uuid, String worldName, @NotNull BlockPos blockPos) {
        Map<String, Integer> locationMap = new HashMap<>();
        locationMap.put("x", blockPos.getX());
        locationMap.put("y", blockPos.getY());
        locationMap.put("z", blockPos.getZ());

        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("world_name", worldName);
        dataMap.put("location", locationMap);

        Gson gson = new Gson();
        String jsonString = gson.toJson(dataMap);

        Path jsonFilePath = getJsonFilePath(gameDataDirectory, uuid);

        try (FileWriter file = new FileWriter(jsonFilePath.toString())) {
            file.write(jsonString);
            return true;
        } catch (IOException e) {
            BatsyHardcoreMod.LOGGER.error(e.getMessage());
            return false;
        }
    }
}
