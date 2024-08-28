package cn.breadnicecat.lovemod.data.neoforge.providers;

import cn.breadnicecat.lovemod.item.ModItems;
import net.minecraft.advancements.critereon.EntityHurtPlayerTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

/**
 * Created in 2024/8/29 02:18
 * Project: LoveModFurina
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 *
 * <p>
 **/
public class ModRecipeProvider extends RecipeProvider {
	public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
		super(output, registries);
	}
	
	@Override
	protected void buildRecipes(@NotNull RecipeOutput writer) {
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.ENGAGEMENT_RING.get())
				.pattern("#@#")
				.pattern("# #")
				.pattern("###")
				.define('#', Items.GOLD_INGOT)
				.define('@', Items.RED_DYE)
				.unlockedBy("has_flower", has(ItemTags.FLOWERS))
				.save(writer);
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.WEDDING_RING.get())
				.pattern("###")
				.pattern("# #")
				.pattern("###")
				.define('#', Items.GOLD_INGOT)
				.unlockedBy("has_flower", has(ItemTags.FLOWERS))
				.save(writer);
		//只是为了JEI之类的加载
		ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.DIVORCE_AGREEMENT.get())
				.requires(ModItems.WEDDING_RING.get())
				.requires(ModItems.ENGAGEMENT_RING.get())
				.requires(Items.PAPER)
				.unlockedBy("never", EntityHurtPlayerTrigger.TriggerInstance.entityHurtPlayer())
				.save(writer);
		
		
	}
}