package net.xanthian.staffs;

import draylar.omegaconfig.OmegaConfig;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StaffsInit implements ModInitializer {

	public static final String MOD_ID = "staffs";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final ModConfig CONFIG = OmegaConfig.register(ModConfig.class);

	@Override
	public void onInitialize() {

		ModItems.registerModItems();
		ModEntities.registerModEntities();
		System.out.printf("Config value: %s%n", CONFIG.value);

	}

}

