package cn.breadnicecat.lovemod.item.items;

import cn.breadnicecat.lovemod.PlayerAddition;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

import java.util.Optional;
import java.util.UUID;

import static net.minecraft.ChatFormatting.RED;
import static net.minecraft.ChatFormatting.YELLOW;
import static net.minecraft.network.chat.Component.translatable;


/**
 * Created in 2024/8/27 12:01
 * Project: LoveModFurina
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 *
 * <p>
 **/
public class EngagementRing extends CommonRing {
	
	@Override
	protected InteractionResult interactPlayer(ItemStack stack, ServerPlayer thisPlayer, ServerPlayer ta, InteractionHand hand) {
		if (!(thisPlayer.level() instanceof ServerLevel level)) {
			return InteractionResult.SUCCESS;
		}
		//自己已经结婚了
		Optional<UUID> meMate = PlayerAddition.getMate(thisPlayer);
		if (meMate.isPresent()) {
			thisPlayer.sendSystemMessage(translatable(me_married).withStyle(ChatFormatting.RED));
			return InteractionResult.FAIL;
		}
		//他已经结婚了
		Optional<UUID> taMate = PlayerAddition.getMate(ta);
		if (taMate.isPresent()) {
			thisPlayer.sendSystemMessage(translatable(ta_married, ta.getName()).withStyle(ChatFormatting.RED));
			return InteractionResult.FAIL;
		}
		if (!isValidRing(stack)) {
			//这是个戒指未绑定
			//求婚戒指的holder应该为现在的mate
			setRingData(stack, ta, thisPlayer);
			//赠送
			if (give(ta, stack, true)) {
				thisPlayer.setItemInHand(hand, Items.AIR.getDefaultInstance());
			}
			//提醒
			thisPlayer.sendSystemMessage(translatable(me_request, thisPlayer.getName(), thisPlayer.getName()).withStyle(YELLOW));
			ta.sendSystemMessage(translatable(request, thisPlayer.getName(), thisPlayer.getName()).withStyle(YELLOW));
		} else {
			//这段逻辑应该由被求婚者调用
			Optional<UUID> ringHolderUUID = getHolderUUID(stack);
			//戒指已经绑定
			if (!thisPlayer.getUUID().equals(ringHolderUUID.get())) {
				//戒指不属于自己
				thisPlayer.sendSystemMessage(translatable(not_my_ring).withStyle(RED));
				return InteractionResult.FAIL;
			}
			Optional<UUID> ringMateUUID = getMateUUID(stack);
			if (!ta.getUUID().equals(ringMateUUID.get())) {
				//不是ta送的戒指
				thisPlayer.sendSystemMessage(translatable(not_from_ta, ta.getName()));
				return InteractionResult.FAIL;
			}
			//成功
			//互相绑定玩家数据
			PlayerAddition.setMate(thisPlayer, ta);
			PlayerAddition.setMate(ta, thisPlayer);
			
			MutableComponent component = translatable(congratulation,
					thisPlayer.getName().copy().withStyle(ChatFormatting.LIGHT_PURPLE, ChatFormatting.UNDERLINE),
					ta.getName().copy().withStyle(ChatFormatting.LIGHT_PURPLE, ChatFormatting.UNDERLINE)
			).withStyle(ChatFormatting.AQUA, ChatFormatting.BOLD);
			level.getServer().getPlayerList().broadcastSystemMessage(component, false);
			//返还结婚戒指
			ItemStack wed = new WeddingRing().getDefaultInstance();
			setRingData(wed, ta, thisPlayer);
			give(ta, wed, true);
			return InteractionResult.CONSUME;
		}
		return super.interactPlayer(stack, thisPlayer, ta, hand);
	}
	
	protected static boolean give(Player ta, ItemStack stack, boolean dropIfFail) {
		if (!ta.addItem(stack)) {
			if (dropIfFail) {
				Level level = ta.level();
				level.addFreshEntity(new ItemEntity(level, ta.getX(), ta.getY(), ta.getZ(), stack));
				return true;
			}
		}
		return false;
	}
}
