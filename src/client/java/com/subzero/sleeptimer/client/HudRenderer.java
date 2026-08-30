package com.subzero.sleeptimer.client;

import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.resources.Identifier;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.util.ARGB;
import net.minecraft.world.level.Level;

public class HudRenderer {

    private static final Identifier ID = Identifier.fromNamespaceAndPath("sleeptimermod", "sleep_timer");

    public static void register() {

        HudElementRegistry.addLast(ID, (context, tickDelta) -> {

            ModConfig config = ModConfig.get();

            if(!config.enabled) return;

            Minecraft client = Minecraft.getInstance();
            Screen screen = client.gui.screen();

            if(client.level == null || screen instanceof ChatScreen) return;

            long dayTime = client.level.getOverworldClockTime() % 24000;
            long playerTime = (client.level.getOverworldClockTime() + 6000) % 24000;
            long remaining = Math.max(0, 12542 - dayTime);

            boolean thunderstorm = client.level.isThundering();
            var dimension = client.level.dimension();

            boolean inventoryOpen = screen instanceof InventoryScreen
                    || screen instanceof CreativeModeInventoryScreen;

            float prepTicks = (float) config.preparationPeriod * 20;
            boolean prepPeriodActive = remaining <= prepTicks;

            if(config.onlyShowTimerInInventory && config.onlyShowClockInInventory && !inventoryOpen && remaining > 0 && !thunderstorm && !(prepPeriodActive && config.showPrepTimer)) return;
            if(config.onlyShowClockInInventory && !inventoryOpen && (dimension == Level.NETHER || dimension == Level.END)) return;

            boolean timerInv = config.onlyShowTimerInInventory || dimension == Level.NETHER || dimension == Level.END;

            int textColor;
            if(thunderstorm) {
                textColor = config.thunderColor;
            } else if (remaining == 0) {
                textColor = config.availableColor;
            } else if (inventoryOpen) {
                textColor = config.inventoryColor;
            }  else if (prepPeriodActive) {
                float percentage = (float) remaining / prepTicks;
                textColor = ARGB.srgbLerp(percentage, config.availableColor, config.earlyDayColor);
            } else {
                textColor = config.earlyDayColor;
            }

            long seconds = remaining / 20;

            String text = "";

            // Clock Logic
            if(config.showInGameTime) {
                if(!config.onlyShowClockInInventory || inventoryOpen) {
                    text += (int) Math.floor(playerTime / 1000f) + ":" + String.format("%02d", (int) (Math.floor((playerTime % 1000f) / 250f)) * 15);
                }
            }

            // Pipe Draw Logic
            boolean drawPipe =
                config.showInGameTime
                && (!(dimension == Level.NETHER || dimension == Level.END) || inventoryOpen)
                && (
                    (!config.onlyShowClockInInventory && !timerInv)
                    || inventoryOpen
                    || (prepPeriodActive && config.showPrepTimer && !config.onlyShowClockInInventory)
                    || (remaining == 0 && !config.onlyShowTimerInInventory)
                    || (thunderstorm && timerInv && !inventoryOpen && !config.onlyShowClockInInventory)
                );

            if (drawPipe) {
                text += " | ";
            }

            // Timer Logic
            if(!(dimension == Level.END || dimension == Level.NETHER) || inventoryOpen) {
                if (thunderstorm) {
                    text += "Thunder lets you sleep";
                } else if (remaining == 0) {
                    text += "You can sleep";
                } else {
                    if (!timerInv || (prepPeriodActive && config.showPrepTimer) || inventoryOpen) {
                        long minutes = seconds / 60;
                        long secs = seconds % 60;

                        if (config.showExtendedText || (prepPeriodActive && config.extendedTextPrep)) {
                            text += "Sleep in ";
                        }

                        if(minutes > 0) {
                            text += minutes + "m ";
                        }

                        if (config.showSecondsDuringDay || prepPeriodActive) {
                            text += String.format("%02d", secs) + "s";
                        }
                    }
                }
            }

            int x = Math.round(context.guiWidth() * (ModMenuIntegration.getPreviewXPosition() / 100f));
            int y = Math.round(context.guiHeight() * ((100 - ModMenuIntegration.getPreviewYPosition()) / 100f));

            context.text(
                    client.font,
                    text,
                    x,
                    y,
                    textColor,
                    true
            );
        });
    }
}