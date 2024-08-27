package cn.breadnicecat.lovemod.item.items;

import cn.breadnicecat.lovemod.item.ModItemDatas;
import cn.breadnicecat.lovemod.mixin_ref.PlayerAddition;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
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
	protected static String married = "lovemod.married.desc";
	protected static String mate_married = "lovemod.mate_married.desc";
	protected static String request = "lovemod.request.desc";
	protected static String marry = "lovemod.marry.desc";
	protected static String who_on_it = "lovemod.who_on_it.desc";
	
	public CommonRing(Properties properties) {
		super(properties);
	}
	
	public CommonRing() {
		this(new Properties().stacksTo(1).rarity(Rarity.UNCOMMON));
	}
	
	public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
		if (!level.isClientSide()) {
			Optional<UUID> mateUUID = PlayerAddition.getMate(player);
			if (mateUUID.isPresent()) {
				ItemStack hand = player.getItemInHand(usedHand);
				Player mate = level.getPlayerByUUID(mateUUID.get());
				if (mate != null) {
					float yRotDeg = Mth.wrapDegrees(mate.getYRot());
					float xRotDeg = Mth.wrapDegrees(mate.getXRot());
					player.teleportTo((ServerLevel) mate.level(), mate.getX(), mate.getY(), mate.getZ(), EnumSet.noneOf(RelativeMovement.class), yRotDeg, xRotDeg);
					return InteractionResultHolder.success(hand);
				}
			}
		}
		return super.use(level, player, usedHand);
	}
	
	@Override
	public @NotNull InteractionResult useOn(UseOnContext context) {
		return use(context.getLevel(), context.getPlayer(), context.getHand()).getResult();
	}
	
	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
		super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
		String targetName = getTargetName(stack);
		if (targetName != null) tooltipComponents.add(Component.translatable(who_on_it, targetName));
	}
	
	public static UUID getTarget(ItemStack ring) {
		String str = ring.get(ModItemDatas.MATE_UUID.get());
		if (str == null) return null;
		return UUID.fromString(str);
	}
	
	public static void setTarget(ItemStack ring, Player target) {
		ring.set(ModItemDatas.MATE_UUID.get(), target.getUUID().toString());
		ring.set(ModItemDatas.MATE_NAME.get(), target.getName().getString());
	}
	
	public static String getTargetName(ItemStack ring) {
		return ring.get(ModItemDatas.MATE_NAME.get());
	}
	
}
