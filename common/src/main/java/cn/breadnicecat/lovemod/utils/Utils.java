package cn.breadnicecat.lovemod.utils;

import cn.breadnicecat.lovemod.LoveMod;
import net.minecraft.resources.ResourceLocation;

/**
 * Created in 2024/8/26 22:30
 * Project: lovemyfriend_furina
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 */
public class Utils {
	public static <T> T impossibleCode() {
		throw new AssertionError("Untransformed Method! Please issue the problem.");
	}
	
	public static ResourceLocation prefix(String path) {
		return ResourceLocation.fromNamespaceAndPath(LoveMod.MOD_ID, path);
	}
	
}
