package cn.breadnicecat.lovemod.item

import cn.breadnicecat.lovemod.LoveMod
import cn.breadnicecat.lovemod.LoveMod.LOGGER
import cn.breadnicecat.lovemod.Utils.prefix
import cn.breadnicecat.lovemod.item.items.Ring
import cn.breadnicecat.lovemod.item.items.RingType
import dev.architectury.registry.registries.DeferredRegister
import dev.architectury.registry.registries.DeferredSupplier
import net.minecraft.core.registries.Registries
import net.minecraft.world.item.Item

/**
 * Created in 2024/8/27 11:19
 * Project: LoveModFurina
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 *
 * <p>
 **/
object ModItems {
    init {
        LOGGER.info("Loading Items")
    }

    val register: DeferredRegister<Item> = DeferredRegister.create(LoveMod.MOD_ID, Registries.ITEM);

    val MALE_RING: DeferredSupplier<Ring> = register.register(prefix("male_ring")) { Ring(RingType.MALE) }
    val FEMALE_RING: DeferredSupplier<Ring> = register.register(prefix("female_ring")) { Ring(RingType.FEMALE) }

}