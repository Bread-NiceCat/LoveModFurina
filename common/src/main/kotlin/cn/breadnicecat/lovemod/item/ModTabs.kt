package cn.breadnicecat.lovemod.item

import dev.architectury.registry.CreativeTabRegistry
import net.minecraft.network.chat.Component
import net.minecraft.world.item.CreativeModeTab

/**
 * Created in 2024/8/27 13:03
 * Project: LoveModFurina
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 *
 * <p>
 **/
object ModTabs {
    const val TAB_I18N = "tab.lovemod.title"
    val tab: CreativeModeTab =
        CreativeTabRegistry.create(Component.translatable(TAB_I18N)) { ModItems.FEMALE_RING.get().defaultInstance }
}