package com.subzero.sleeptimer.client;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.gui.entries.IntegerSliderEntry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class ModMenuIntegration implements ModMenuApi {

    private static IntegerSliderEntry xPositionSlider;
    private static IntegerSliderEntry yPositionSlider;
    private static Screen configScreen;

    public static int getPreviewXPosition() {
        if (xPositionSlider != null && Minecraft.getInstance().gui.screen() == configScreen) {
            return xPositionSlider.getValue();
        }
        return ModConfig.get().xPosition;
    }

    public static int getPreviewYPosition() {
        if (yPositionSlider != null && Minecraft.getInstance().gui.screen() == configScreen) {
            return yPositionSlider.getValue();
        }
        return ModConfig.get().yPosition;
    }

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parentScreen -> {
            ConfigBuilder builder = ConfigBuilder.create()
                    .setParentScreen(parentScreen)
                    .setTitle(Component.literal("Sleep Timer & Clock Settings"));

            ConfigEntryBuilder entryBuilder = builder.entryBuilder();

            ConfigCategory general = builder.getOrCreateCategory(Component.literal("General"));
            ConfigCategory advanced = builder.getOrCreateCategory(Component.literal("Advanced"));
            ConfigCategory colors = builder.getOrCreateCategory(Component.literal("Colors"));

            // --- GENERAL ---
            general.addEntry(entryBuilder.startBooleanToggle(Component.literal("Enabled"), ModConfig.get().enabled)
                    .setDefaultValue(true)
                    .setSaveConsumer(newValue -> ModConfig.get().enabled = newValue)
                    .build());

            general.addEntry(entryBuilder.startBooleanToggle(Component.literal("Show in-game clock"), ModConfig.get().showInGameTime)
                    .setDefaultValue(true)
                    .setSaveConsumer(newValue -> ModConfig.get().showInGameTime = newValue)
                    .build());

            xPositionSlider = entryBuilder
                    .startIntSlider(
                            Component.literal("X Position (%)"),
                            ModConfig.get().xPosition,
                            0,
                            100
                    )
                    .setDefaultValue(1)
                    .setSaveConsumer(value -> ModConfig.get().xPosition = value)
                    .build();
            general.addEntry(xPositionSlider);

            yPositionSlider = entryBuilder
                    .startIntSlider(
                            Component.literal("Y Position (%)"),
                            ModConfig.get().yPosition,
                            0,
                            100
                    )
                    .setDefaultValue(3)
                    .setSaveConsumer(value -> ModConfig.get().yPosition = value)
                    .build();
            general.addEntry(yPositionSlider);

            general.addEntry(entryBuilder.startBooleanToggle(Component.literal("Only Show Timer in Inventory"), ModConfig.get().onlyShowTimerInInventory)
                    .setDefaultValue(false)
                    .setSaveConsumer(newValue -> ModConfig.get().onlyShowTimerInInventory = newValue)
                    .build());

            general.addEntry(entryBuilder.startBooleanToggle(Component.literal("Only Show Clock in Inventory"), ModConfig.get().onlyShowClockInInventory)
                    .setDefaultValue(false)
                    .setSaveConsumer(newValue -> ModConfig.get().onlyShowClockInInventory = newValue)
                    .build());

            // --- ADVANCED ---
            advanced.addEntry(entryBuilder.startBooleanToggle(Component.literal("Extended Text"), ModConfig.get().showExtendedText)
                    .setDefaultValue(false)
                    .setSaveConsumer(newValue -> ModConfig.get().showExtendedText = newValue)
                    .setTooltip(Component.literal("Show the extended text i.e. 'Sleep in ...' instead of showing the timer"))
                    .build());

            advanced.addEntry(entryBuilder.startBooleanToggle(Component.literal("Always Show Seconds"), ModConfig.get().showSecondsDuringDay)
                    .setDefaultValue(false)
                    .setSaveConsumer(newValue -> ModConfig.get().showSecondsDuringDay = newValue)
                    .build());

            advanced.addEntry(entryBuilder
                    .startIntSlider(
                            Component.literal("Wind-Down Window (Seconds)"),
                            ModConfig.get().preparationPeriod,
                            1,
                            300
                    )
                    .setDefaultValue(90)
                    .setSaveConsumer(value -> ModConfig.get().preparationPeriod = value)
                    .setTooltip(Component.literal("Time before you can sleep, shows seconds and text changes color"))
                    .build());

            advanced.addEntry(entryBuilder.startBooleanToggle(Component.literal("Show Timer During Wind-Down"), ModConfig.get().showPrepTimer)
                    .setDefaultValue(true)
                    .setSaveConsumer(newValue -> ModConfig.get().showPrepTimer = newValue)
                    .setTooltip(Component.literal("During Wind-Down, show the timer outside of the inventory if it's set to only show in inventory"))
                    .build());

            advanced.addEntry(entryBuilder.startBooleanToggle(Component.literal("Extended Text During Wind-Down"), ModConfig.get().extendedTextPrep)
                    .setDefaultValue(true)
                    .setSaveConsumer(newValue -> ModConfig.get().extendedTextPrep = newValue)
                    .setTooltip(Component.literal("During Wind-Down, show the extended text (i.e. 'Sleep in') if it's disabled normally"))
                    .build());

            // --- COLORS ---
            colors.addEntry(entryBuilder.startAlphaColorField(Component.literal("Daytime Color"), ModConfig.get().earlyDayColor)
                    .setDefaultValue(0xFFE3D09D)
                    .setSaveConsumer(newValue -> ModConfig.get().earlyDayColor = newValue)
                    .build());

            colors.addEntry(entryBuilder.startAlphaColorField(Component.literal("Color when you can sleep"), ModConfig.get().availableColor)
                    .setDefaultValue(0xFF00AAFF)
                    .setSaveConsumer(newValue -> ModConfig.get().availableColor = newValue)
                    .build());

            colors.addEntry(entryBuilder.startAlphaColorField(Component.literal("Inventory Open Color"), ModConfig.get().inventoryColor)
                    .setDefaultValue(0xFF887799)
                    .setSaveConsumer(newValue -> ModConfig.get().inventoryColor = newValue)
                    .build());

            colors.addEntry(entryBuilder.startAlphaColorField(Component.literal("Thunderstorm Color"), ModConfig.get().thunderColor)
                    .setDefaultValue(0xFFEE2F68)
                    .setSaveConsumer(newValue -> ModConfig.get().thunderColor = newValue)
                    .setTooltip(Component.literal("Color when you can sleep due to a thunderstorm"))
                    .build());

            // Save settings to disk
            builder.setSavingRunnable(ModConfig::save);

            configScreen = builder.build();
            return configScreen;
        };
    }
}