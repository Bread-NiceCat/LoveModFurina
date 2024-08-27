package cn.breadnicecat.lovemod

import cn.breadnicecat.lovemod.item.ModItems
import com.mojang.logging.LogUtils
import org.slf4j.Logger

object LoveMod {
    const val MOD_ID: String = "lovemod"
    val LOGGER: Logger = LogUtils.getLogger()
    fun init() {
        LOGGER.info("Initializing...")
        ModItems.register.register()
    }
}
