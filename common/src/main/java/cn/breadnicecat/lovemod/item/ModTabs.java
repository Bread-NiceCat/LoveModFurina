package cn.breadnicecat.lovemod.item;

import dev.architectury.registry.CreativeTabRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;

/**
 * Created in 2024/8/27 13:03
 * Project: LoveModFurina
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 *
 * <p>
 **/
public class ModTabs {
	public static final String TAB_I18N = "tab.lovemod.title";
	public static final CreativeModeTab tab =
			CreativeTabRegistry.create(
					Component.translatable(TAB_I18N),
					() -> ModItems.RING_WEDDING.get().getDefaultInstance()
			);
}