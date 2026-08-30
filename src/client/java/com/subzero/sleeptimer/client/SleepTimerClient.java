package com.subzero.sleeptimer.client;

import net.fabricmc.api.ClientModInitializer;

public class SleepTimerClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ModConfig.load();
		HudRenderer.register();
	}
}