package cn.breadnicecat.lovemod.mixin;

import cn.breadnicecat.lovemod.mixin_ref.PlayerAddition;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static cn.breadnicecat.lovemod.mixin_ref.PlayerAddition.DATA_MATE_UUID;

/**
 * Created in 2024/8/27 14:35
 * Project: LoveModFurina
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 *
 * <p>
 **/
@Mixin(Player.class)
public abstract class MixinPlayer {
	
	
	@Inject(method = "defineSynchedData", at = @At("TAIL"))
	protected void defineSynchedData(SynchedEntityData.Builder builder, CallbackInfo ci) {
		builder.define(PlayerAddition.DATA_MATE, PlayerAddition.getMate((Player) (Object) this));
	}
	
	@Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
	public void readAdditionalSaveData(CompoundTag compound, CallbackInfo ci) {
		if (compound.contains(DATA_MATE_UUID)) {
			PlayerAddition.setMateUUID((Player) (Object) this, compound.getUUID(DATA_MATE_UUID));
		}
	}
	
	@Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
	public void addAdditionalSaveData(CompoundTag compound, CallbackInfo ci) {
		var mate_uuid = PlayerAddition.getMate((Player) (Object) this);
		mate_uuid.ifPresent(uuid -> compound.putUUID(DATA_MATE_UUID, uuid));
	}
}
