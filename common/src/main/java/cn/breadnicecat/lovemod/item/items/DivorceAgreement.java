package cn.breadnicecat.lovemod.item.items;

import cn.breadnicecat.lovemod.mixin_ref.PlayerAddition;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import org.jetbrains.annotations.NotNull;

/**
 * Created in 2024/8/27 16:27
 * Project: LoveModFurina
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 *
 * <p>
 **/
public class DivorceAgreement extends Item {
	public static final String divorce = "lovemod.divorce.desc";
	
	public DivorceAgreement() {
		super(new Properties().stacksTo(1).rarity(Rarity.EPIC));
	}
	
	@Override
	public @NotNull InteractionResult interactLivingEntity(ItemStack stack, Player thisPlayer, LivingEntity interactionTarget, InteractionHand usedHand) {
		if (thisPlayer != null && interactionTarget instanceof Player mate) {
			if (!thisPlayer.level().isClientSide()) {
				if (PlayerAddition.getMate(thisPlayer).isPresent() && PlayerAddition.getMate(mate).isPresent()) {
					PlayerAddition.setMate(thisPlayer, null);
					PlayerAddition.setMate(mate, null);
					thisPlayer.sendSystemMessage(Component.translatable(divorce, mate.getName()));
					mate.sendSystemMessage(Component.translatable(divorce, thisPlayer.getName()));
				}
			}
		}
		return super.interactLivingEntity(stack, thisPlayer, interactionTarget, usedHand);
	}
}
