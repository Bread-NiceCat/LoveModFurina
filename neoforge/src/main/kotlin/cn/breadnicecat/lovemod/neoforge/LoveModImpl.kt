package cn.breadnicecat.lovemod.neoforge

import cn.breadnicecat.lovemod.LoveMod
import net.neoforged.fml.common.Mod

@Mod(LoveMod.MOD_ID)
class LoveModImpl {
    init {
        LoveMod.LOGGER.info("Loading Forge")
        LoveMod.init()
    }
}
