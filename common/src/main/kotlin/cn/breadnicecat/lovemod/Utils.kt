package cn.breadnicecat.lovemod

import net.minecraft.resources.ResourceLocation

/**
 * Created in 2024/8/26 22:30
 * Project: lovemyfriend_furina
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 *
 *
 *
 *
 *
 */
object Utils {
    fun <T> impossibleCode(): T = throw AssertionError("Untransformed Method!Please issue the problem.")

    fun prefix(path: String): ResourceLocation = ResourceLocation.fromNamespaceAndPath(LoveMod.MOD_ID, path)
}
