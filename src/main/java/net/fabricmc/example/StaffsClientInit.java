package net.fabricmc.example;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.impl.client.rendering.EntityRendererRegistryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StaffsClientInit implements ClientModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final String MOD_ID = "staffs";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);


	@Override
	public void onInitializeClient() {

		EntityRendererRegistryImpl.register(ModEntities.MOD_PROJECTILE, ModRenderer::new);
		//Registry.register(EntityRendererRegistryImpl.register(ModEntities.MOD_PROJECTILE, ModRenderer::new));
	}
}

