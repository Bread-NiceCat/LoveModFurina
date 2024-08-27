package cn.breadnicecat.lovemod.neoforge;

import cn.breadnicecat.lovemod.LoveMod;
import net.neoforged.fml.common.Mod;

@Mod(LoveMod.MOD_ID)
public class LoveModImpl {
	public LoveModImpl() {
		LoveMod.LOGGER.info("Loading Forge");
		LoveMod.init();
	}
}
