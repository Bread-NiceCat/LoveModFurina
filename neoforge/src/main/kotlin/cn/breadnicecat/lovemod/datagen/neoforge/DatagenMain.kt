package cn.breadnicecat.lovemod.datagen.neoforge;

import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.fml.common.EventBusSubscriber.Bus
import net.neoforged.neoforge.data.event.GatherDataEvent

/**
 * Created in 2024/8/27 12:39
 * Project: LoveModFurina
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 *
 * <p>
 **/
@EventBusSubscriber(bus = Bus.MOD)
object DatagenMain {
    @JvmStatic
    @SubscribeEvent
    fun onGatherData(event: GatherDataEvent) {
    }

}
