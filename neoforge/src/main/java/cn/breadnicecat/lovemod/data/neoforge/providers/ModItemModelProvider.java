package cn.breadnicecat.lovemod.data.neoforge.providers;

import cn.breadnicecat.lovemod.item.ModItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import static cn.breadnicecat.lovemod.LoveMod.MOD_ID;

/**
 * Created in 2024/8/29 02:17
 * Project: LoveModFurina
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 *
 * <p>
 **/
public class ModItemModelProvider extends ItemModelProvider {
	public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
		super(output, MOD_ID, existingFileHelper);
	}
	
	@Override
	protected void registerModels() {
		basicItem(ModItems.ENGAGEMENT_RING.get());
		basicItem(ModItems.WEDDING_RING.get());
		basicItem(ModItems.DIVORCE_AGREEMENT.get());
	}
}
