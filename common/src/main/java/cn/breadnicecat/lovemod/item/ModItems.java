package cn.breadnicecat.lovemod.item;

import cn.breadnicecat.lovemod.LoveMod;
import cn.breadnicecat.lovemod.item.items.DivorceAgreement;
import cn.breadnicecat.lovemod.item.items.EngagementRing;
import cn.breadnicecat.lovemod.item.items.WeddingRing;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.DeferredSupplier;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;

import static cn.breadnicecat.lovemod.Utils.prefix;
import static com.mojang.text2speech.Narrator.LOGGER;

/**
 * Created in 2024/8/27 11:19
 * Project: LoveModFurina
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 *
 * <p>
 **/
public class ModItems {
	static {
		LOGGER.info("Loading Items");
	}
	
	public static DeferredRegister<Item> register = DeferredRegister.create(LoveMod.MOD_ID, Registries.ITEM);
	
	public static RegistrySupplier<EngagementRing> RING_ENGAGEMENT = register.register(prefix("ring_engagement"), EngagementRing::new);
	public static DeferredSupplier<WeddingRing> RING_WEDDING = register.register(prefix("ring_wedding"), WeddingRing::new);
	public static DeferredSupplier<DivorceAgreement> DIVORCE_AGREEMENT = register.register(prefix("divorce_agreement"), DivorceAgreement::new);
}