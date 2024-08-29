package cn.breadnicecat.lovemod.item.items;

import cn.breadnicecat.lovemod.PlayerAddition;
import net.minecraft.ChatFormatting;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import org.jetbrains.annotations.NotNull;

import static net.minecraft.network.chat.Component.translatable;

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
	public static final String divorce = "lovemod.divorce_from.desc";
	public static final String agreement_not_suit = "lovemod.agreement_not_suit.desc";
	
	public DivorceAgreement() {
		super(new Properties().stacksTo(1).rarity(Rarity.EPIC));
	}
	
	@Override
	public boolean isFoil(ItemStack stack) {
		return validate(stack);
	}
	
	public boolean validate(ItemStack agreement) {
		return true;
	}
	
	public boolean isValidFor(ItemStack agreement, Player p1, Player p2) {
		return validate(agreement);
	}
	
	@Override
	public @NotNull InteractionResult interactLivingEntity(ItemStack stack, Player thisPlayer, LivingEntity interactionTarget, InteractionHand usedHand) {
		if (thisPlayer instanceof ServerPlayer && interactionTarget instanceof ServerPlayer ta) {
			if (PlayerAddition.isCP(thisPlayer, ta)) {
				PlayerAddition.setMate(thisPlayer, null);
				PlayerAddition.setMate(ta, null);
				thisPlayer.sendSystemMessage(translatable(divorce, ta.getName()).withStyle(ChatFormatting.YELLOW));
				ta.sendSystemMessage(translatable(divorce, thisPlayer.getName()).withStyle(ChatFormatting.YELLOW));
				stack.shrink(1);
			}
		}
		return super.interactLivingEntity(stack, thisPlayer, interactionTarget, usedHand);
	}
}
