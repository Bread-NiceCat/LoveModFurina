package cn.breadnicecat.lovemod.item.items;

import cn.breadnicecat.lovemod.mixin_ref.PlayerAddition;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.RelativeMovement;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.EnumSet;
import java.util.Optional;
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
public class WeddingRing extends CommonRing {
	
	
	public WeddingRing() {
		super(new Properties());
	}
	
	@Override
	public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
		if (level.isClientSide()) {
			Optional<UUID> mateUUID = PlayerAddition.getMate(player);
			ItemStack hand = player.getItemInHand(usedHand);
			if (mateUUID.isPresent()) {
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
	public @NotNull InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target, InteractionHand usedHand) {
		return super.interactLivingEntity(stack, player, target, usedHand);
	}
}
