package cn.breadnicecat.lovemod.item;

import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

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
			CreativeTabRegistry.create(builder -> builder.title(Component.translatable(TAB_I18N))
					.icon(() -> ModItems.RING_WEDDING.get().getDefaultInstance())
					.displayItems((a, b) -> {
						for (RegistrySupplier<Item> supplier : ModItems.register) {
							b.accept(supplier.get());
						}
					})
					.build()
			);
}