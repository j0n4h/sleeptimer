package com.subzero.sleeptimer.client;

import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.ClientCommands;
import net.minecraft.network.chat.Component;

public class Commands {
    public static void register() {
        //noinspection CodeBlock2Expr
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, _) -> {
            dispatcher.register(ClientCommands.literal("sleepTimerToggle")
                    .executes(context -> {
                        ModConfig.get().enabled = !ModConfig.get().enabled;
                        String response;
                        if(ModConfig.get().enabled){
                            response = "Enabled";
                        } else {
                            response = "Disabled";
                        }
                        context.getSource().sendFeedback(Component.literal(response + " " + "Sleep Timer"));
                        return 1;
                    })
            );
        });
    }
}
