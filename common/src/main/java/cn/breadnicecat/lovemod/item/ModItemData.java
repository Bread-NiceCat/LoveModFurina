package cn.breadnicecat.lovemod.item;

import cn.breadnicecat.lovemod.LoveMod;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.DeferredSupplier;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.util.ExtraCodecs;

import static cn.breadnicecat.lovemod.LoveMod.LOGGER;
import static cn.breadnicecat.lovemod.utils.Utils.prefix;

/**
 * Created in 2024/8/23 19:11
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 *
 * <p>
 **/
public class ModItemData {
	static {
		LOGGER.info("Loading Items Data");
	}
	
	public static DeferredRegister<DataComponentType<?>> register = DeferredRegister.create(LoveMod.MOD_ID, Registries.DATA_COMPONENT_TYPE);
	
	public static final DeferredSupplier<DataComponentType<String>> MATE_UUID = register.register(prefix("mate_uuid"),
			() -> DataComponentType.<String>builder().persistent(ExtraCodecs.NON_EMPTY_STRING).networkSynchronized(ByteBufCodecs.STRING_UTF8).build());
	
	public static final DeferredSupplier<DataComponentType<String>> HOLDER_UUID = register.register(prefix("holder_uuid"),
			() -> DataComponentType.<String>builder().persistent(ExtraCodecs.NON_EMPTY_STRING).networkSynchronized(ByteBufCodecs.STRING_UTF8).build());
	//供显示用，防止该玩家不在线
	public static final DeferredSupplier<DataComponentType<String>> MATE_NAME = register.register(prefix("mate_name"),
			() -> DataComponentType.<String>builder().persistent(ExtraCodecs.NON_EMPTY_STRING).networkSynchronized(ByteBufCodecs.STRING_UTF8).build());
	
}
