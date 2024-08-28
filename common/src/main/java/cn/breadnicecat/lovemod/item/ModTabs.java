package cn.breadnicecat.lovemod.item;

import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

import static cn.breadnicecat.lovemod.LoveMod.MOD_ID;
import static cn.breadnicecat.lovemod.utils.Utils.prefix;

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
	public static final DeferredRegister<CreativeModeTab> register = DeferredRegister.create(MOD_ID, Registries.CREATIVE_MODE_TAB);
	public static final String key = "tab.lovemod.title";
	public static final RegistrySupplier<CreativeModeTab> MOD_TAB =
			register.register(prefix(MOD_ID), () -> CreativeTabRegistry.create(builder -> builder.title(Component.translatable(key))
					.icon(() -> ModItems.WEDDING_RING.get().getDefaultInstance())
					.displayItems((a, b) -> {
						for (RegistrySupplier<Item> supplier : ModItems.register) {
							b.accept(supplier.get());
						}
					})
					.build()
			));
}