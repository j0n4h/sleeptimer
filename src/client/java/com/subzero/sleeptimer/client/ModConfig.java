package com.subzero.sleeptimer.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;
import java.io.File;
import java.nio.file.Files;

public class ModConfig {
    private static final File FILE = new File(FabricLoader.getInstance().getConfigDir().toFile(), "sleeptimermod.json");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    // Default X and Y coordinates on the screen
    public int xPosition = 1;
    public int yPosition = 3;
    public int preparationPeriod = 120;
    public boolean enabled = true;

    public boolean showExtendedText = true;
    public boolean showSecondsDuringDay = false;
    public boolean showInGameTime = true;
    public boolean onlyShowTimerInInventory = false;
    public boolean showPrepTimer = true;
    public boolean extendedTextPrep = false;

    public boolean onlyShowClockInInventory = false;

    public int earlyDayColor = 0xFF887799;
    public int availableColor = 0xFF00AAFF;
    public int inventoryColor = 0xFFE3D09D;
    public int thunderColor = 0xFFEE2F68;

    private static ModConfig INSTANCE = new ModConfig();

    public static ModConfig get() {
        return INSTANCE;
    }

    public static void load() {
        try {
            if (FILE.exists()) {
                INSTANCE = GSON.fromJson(Files.newBufferedReader(FILE.toPath()), ModConfig.class);
            } else {
                save();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void save() {
        try {
            Files.writeString(FILE.toPath(), GSON.toJson(INSTANCE));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}