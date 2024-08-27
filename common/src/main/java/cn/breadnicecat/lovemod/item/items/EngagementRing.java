package cn.breadnicecat.lovemod.item.items;

import cn.breadnicecat.lovemod.mixin_ref.PlayerAddition;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;


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
	public @NotNull InteractionResult interactLivingEntity(ItemStack stack, Player thisPlayer, LivingEntity target, InteractionHand usedHand) {
		if (thisPlayer != null && target instanceof Player mate) {
			if (thisPlayer.level() instanceof ServerLevel level) {
				if (PlayerAddition.getMate(thisPlayer).isPresent()) {
					thisPlayer.sendSystemMessage(Component.translatable(married).withStyle(ChatFormatting.RED));
				}
				if (PlayerAddition.getMate(mate).isPresent()) {
					thisPlayer.sendSystemMessage(Component.translatable(mate_married, mate.getName()).withStyle(ChatFormatting.RED));
				}
				
				UUID targUUID = getTarget(stack);
				if (targUUID == null || targUUID.equals(mate.getUUID())) {
					//把戒指给ta
					setTarget(stack, thisPlayer);
					thisPlayer.setItemInHand(usedHand, Items.AIR.getDefaultInstance());
					if (!mate.addItem(stack)) {
						level.addFreshEntity(new ItemEntity(level, mate.getX(), mate.getY(), mate.getZ(), stack));
					}
					mate.sendSystemMessage(Component.translatable(request, thisPlayer.getName(), thisPlayer.getName()).withStyle(ChatFormatting.YELLOW));
				} else {
					//已经走过上面了
					//成功
					PlayerAddition.setMate(thisPlayer, mate);
					PlayerAddition.setMate(mate, thisPlayer);
					ItemStack wed = new WeddingRing().getDefaultInstance();
					setTarget(wed, thisPlayer);
					if (!mate.addItem(stack)) {
						level.addFreshEntity(new ItemEntity(level, mate.getX(), mate.getY(), mate.getZ(), stack));
					}
					mate.sendSystemMessage(Component.translatable(request, thisPlayer.getName(), thisPlayer.getName()).withStyle(ChatFormatting.YELLOW));
					MutableComponent component = Component.translatable(marry,
									thisPlayer.getName().copy().withStyle(ChatFormatting.LIGHT_PURPLE, ChatFormatting.UNDERLINE),
									mate.getName().copy().withStyle(ChatFormatting.LIGHT_PURPLE, ChatFormatting.UNDERLINE)
							)
							.withStyle(ChatFormatting.GREEN, ChatFormatting.BOLD);
					for (ServerPlayer p : level.getPlayers(t -> true)) {
						p.sendSystemMessage(component);
					}
				}
				return InteractionResult.CONSUME;
			}
		} else return InteractionResult.SUCCESS;
		return super.interactLivingEntity(stack, thisPlayer, target, usedHand);
	}
}
