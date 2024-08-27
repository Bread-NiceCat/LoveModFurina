package cn.breadnicecat.lovemod.fabric

import cn.breadnicecat.lovemod.LoveMod
import net.fabricmc.api.ModInitializer

class LoveModImpl : ModInitializer {
    override fun onInitialize() {
        LoveMod.LOGGER.info("Loading Fabric")
        LoveMod.init()
    }
}
