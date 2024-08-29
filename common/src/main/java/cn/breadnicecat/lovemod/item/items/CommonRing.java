package cn.breadnicecat.lovemod.item.items;

import cn.breadnicecat.lovemod.PlayerAddition;
import cn.breadnicecat.lovemod.item.ModItemData;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.RelativeMovement;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static cn.breadnicecat.lovemod.item.ModItemData.*;
import static net.minecraft.ChatFormatting.*;
import static net.minecraft.network.chat.Component.literal;
import static net.minecraft.network.chat.Component.translatable;

/**
 * Created in 2024/8/27 16:39
 * Project: LoveModFurina
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 *
 * <p>
 **/
public abstract class CommonRing extends Item {
	public static String me_married = "lovemod.me_married.desc";
	public static String ta_married = "lovemod.ta_married.desc";
	public static String request = "lovemod.request.desc";
	public static String me_request = "lovemod.me_request.desc";
	public static String congratulation = "lovemod.congratulation.desc";
	public static String ring_mate = "lovemod.ring_mate.desc";
	public static String not_my_ring = "lovemod.not_my_ring.desc";
	public static String unbind_ring = "lovemod.unbind_ring.desc";
	public static String unmarried = "lovemod.unmarried.desc";
	public static String player_offline = "lovemod.player_offline.desc";
	public static String not_from_ta = "lovemod.not_from_ta.desc";
	public static String rebind_ok = "lovemod.rebind_ok.desc";
	public static String unaccepted = "lovemod.unaccepted.desc";
	public static String divorced = "lovemod.divorced.desc";
	
	public CommonRing(Properties properties) {
		super(properties);
	}
	
	public CommonRing() {
		this(new Properties().stacksTo(1).rarity(Rarity.UNCOMMON));
	}
	
	/**
	 * 两种戒指都可以传送
	 */
	@Override
	public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player thisPlayer, InteractionHand usedHand) {
		ItemStack stack = thisPlayer.getItemInHand(usedHand);
		if (level.isClientSide()) {
			return InteractionResultHolder.success(stack);
		}
		
		Optional<UUID> ringHolderUUID = getHolderUUID(stack);
		Optional<UUID> ringMateUUID = getMateUUID(stack);
		Optional<String> ringMateName = getMateName(stack);
		Optional<UUID> mateUUID = PlayerAddition.getMate(thisPlayer);
		//戒指上面没保存数据
		if (!isValidRing(stack)) {
			//没结婚
			if (mateUUID.isEmpty()) {
				thisPlayer.sendSystemMessage(translatable(unmarried).withStyle(YELLOW));
				return InteractionResultHolder.fail(stack);
			}
			//未绑定
			thisPlayer.sendSystemMessage(translatable(unbind_ring).withStyle(ChatFormatting.YELLOW));
			return InteractionResultHolder.fail(stack);
		}
		//和数据不匹配
		if (thisPlayer.getUUID().equals(ringHolderUUID.get())) {
			thisPlayer.sendSystemMessage(translatable(not_my_ring).withStyle(RED));
			return InteractionResultHolder.fail(stack);
		}
		//持有者确实是自己，但是没有对象，则判断为没有同意这门婚事
		if (mateUUID.isEmpty()) {
			thisPlayer.sendSystemMessage(translatable(unaccepted).withStyle(YELLOW));
			return InteractionResultHolder.fail(stack);
		}
		//持有者确实是自己，但是物品上的对象与玩家身上的对象数据不匹配，则判定为已经离婚
		if (mateUUID.get().equals(ringMateUUID.get())) {
			Player mateByUUID = level.getPlayerByUUID(ringMateUUID.get());
			var mate = mateByUUID != null ? mateByUUID.getName() : ringMateName.orElse("null");
			thisPlayer.sendSystemMessage(translatable(divorced, mate).withStyle(YELLOW));
			return InteractionResultHolder.fail(stack);
		}
		Player mate = level.getPlayerByUUID(mateUUID.get());
		if (mate != null) {
			//传送
			float yRotDeg = Mth.wrapDegrees(mate.getYRot());
			float xRotDeg = Mth.wrapDegrees(mate.getXRot());
			thisPlayer.teleportTo((ServerLevel) mate.level(), mate.getX(), mate.getY(), mate.getZ(), EnumSet.noneOf(RelativeMovement.class), yRotDeg, xRotDeg);
			return InteractionResultHolder.consume(stack);
		} else {
			thisPlayer.sendSystemMessage(translatable(player_offline).withStyle(ChatFormatting.YELLOW));
		}
		return super.use(level, thisPlayer, usedHand);
	}
	
	@Override
	public @NotNull InteractionResult useOn(UseOnContext context) {
		Player player = context.getPlayer();
		return player == null
				? InteractionResult.FAIL
				: use(context.getLevel(), player, context.getHand()).getResult();
	}
	
	@Override
	@Deprecated
	public @NotNull InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity interactionTarget, InteractionHand usedHand) {
		if (player.level().isClientSide()) return InteractionResult.SUCCESS;
		
		if (interactionTarget instanceof ServerPlayer ta) {
			return interactPlayer(stack, (ServerPlayer) player, ta, usedHand);
		}
		return super.interactLivingEntity(stack, player, interactionTarget, usedHand);
	}
	
	protected InteractionResult interactPlayer(ItemStack stack, ServerPlayer thisPlayer, ServerPlayer ta, InteractionHand hand) {
		return InteractionResult.PASS;
	}
	
	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tips, TooltipFlag tooltipFlag) {
		super.appendHoverText(stack, context, tips, tooltipFlag);
		Minecraft instance = Minecraft.getInstance();
		if (isValidRing(stack)) {
			Optional<UUID> mateUUID = getMateUUID(stack);
			Player mate = instance.level.getPlayerByUUID(mateUUID.get());
			if (mate != null) {
				//伴侣在线绿色
				tips.add(translatable(ring_mate, mate.getName().copy().withStyle(GREEN)).withStyle(AQUA));
			} else {
				//伴侣离线红色
				Optional<String> mateName = getMateName(stack);
				if (mateName.isPresent()) {
					mateName.ifPresent(s -> tips.add(translatable(ring_mate, literal(s).withStyle(RED))));
				}
			}
			Optional<UUID> holderUUID = getHolderUUID(stack);
			if (instance.getGameProfile().getId().equals(holderUUID.get())) {
				tips.add(translatable(not_my_ring).withStyle(RED));
			}
		}
	}
	
	
	public static Optional<UUID> getMateUUID(ItemStack ring) {
		return Optional.ofNullable(ring.get(MATE_UUID.get())).map(UUID::fromString);
	}
	
	public static Optional<String> getMateName(ItemStack ring) {
		return Optional.ofNullable(ring.get(MATE_NAME.get()));
	}
	
	public static Optional<UUID> getHolderUUID(ItemStack ring) {
		return Optional.ofNullable(ring.get(HOLDER_UUID.get())).map(UUID::fromString);
	}
	
	public static void setRingData(ItemStack ring, Player holder, Player mate) {
		ring.set(MATE_UUID.get(), mate.getUUID().toString());
		ring.set(ModItemData.HOLDER_UUID.get(), holder.getUUID().toString());
		ring.set(ModItemData.MATE_NAME.get(), holder.getName().getString());
	}
	
	/**
	 * 检测关键数据HOLDER_UUID和MATE_UUID，但是不检测MATE_NAME
	 */
	public static boolean isValidRing(ItemStack ring) {
		return getHolderUUID(ring).isPresent()
				&& getMateUUID(ring).isPresent();
	}
}
