package cn.breadnicecat.lovemod.datagen.neoforge.providers

import cn.breadnicecat.lovemod.LoveMod
import net.minecraft.data.PackOutput
import net.neoforged.neoforge.client.model.generators.ItemModelProvider
import net.neoforged.neoforge.common.data.ExistingFileHelper

/**
 * Created in 2024/8/27 12:44
 * Project: LoveModFurina
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 *
 * <p>
 **/
class ItemModelsProvider(val output: PackOutput, val efHelper: ExistingFileHelper) :
    ItemModelProvider(output, LoveMod.MOD_ID, efHelper) {
    override fun registerModels() {
        TODO("Not yet implemented")
    }
}