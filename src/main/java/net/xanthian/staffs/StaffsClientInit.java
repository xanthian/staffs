package net.xanthian.staffs;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.impl.client.rendering.EntityRendererRegistryImpl;

public class StaffsClientInit implements ClientModInitializer {

	@Override
	public void onInitializeClient() {

		EntityRendererRegistryImpl.register(ModEntities.MOD_PROJECTILE, ModRenderer::new);


	}
}

