package cn.breadnicecat.lovemod.item.items;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

import static cn.breadnicecat.lovemod.PlayerAddition.isCP;


/**
 * Created in 2024/8/27 12:01
 * Project: LoveModFurina
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 *
 * <p>
 **/
public class WeddingRing extends CommonRing {
	
	@Override
	protected InteractionResult interactPlayer(ItemStack stack, ServerPlayer thisPlayer, ServerPlayer ta, InteractionHand hand) {
		//只有结婚戒指可以重新绑定
		//未绑定的戒指，并且me和ta互为情侣
		if (!isValidRing(stack) && isCP(thisPlayer, ta)) {
			//重新绑定
			setRingData(stack, thisPlayer, ta);
			thisPlayer.sendSystemMessage(Component.translatable(rebind_ok));
			return InteractionResult.CONSUME;
		}
		return super.interactPlayer(stack, thisPlayer, ta, hand);
	}
	
	@Override
	protected void invalidAppendHoverText(ItemStack stack, TooltipContext context, List<Component> tips, TooltipFlag tooltipFlag) {
		super.invalidAppendHoverText(stack, context, tips, tooltipFlag);
		tips.add(Component.translatable(click_rebind).withStyle(ChatFormatting.YELLOW));
	}
}
