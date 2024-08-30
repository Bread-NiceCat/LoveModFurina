package cn.breadnicecat.lovemod;

import cn.breadnicecat.lovemod.item.ModCommands;
import cn.breadnicecat.lovemod.item.ModItemData;
import cn.breadnicecat.lovemod.item.ModItems;
import cn.breadnicecat.lovemod.item.ModTabs;
import com.mojang.logging.LogUtils;
import org.slf4j.Logger;

public class LoveMod {
	public static final String MOD_ID = "lovemod";
	public static final Logger LOGGER = LogUtils.getLogger();
	
	public static void init() {
		LOGGER.info("Initializing...");
		ModItems.register.register();
		ModItemData.register.register();
		ModTabs.register.register();
		ModCommands.register();
		LOGGER.info("Initializing Done");
	}
}
