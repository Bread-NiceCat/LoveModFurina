package cn.breadnicecat.lovemod.fabric;

import cn.breadnicecat.lovemod.LoveMod;
import net.fabricmc.api.ModInitializer;

public class LoveModImpl implements ModInitializer {
	@Override
	public void onInitialize() {
		LoveMod.LOGGER.info("Loading Fabric");
		LoveMod.init();
	}
}
