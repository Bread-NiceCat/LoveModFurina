package cn.breadnicecat.lovemod.mixin;

import cn.breadnicecat.lovemod.PlayerAddition;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

import static cn.breadnicecat.lovemod.PlayerAddition.DATA_MATE_UUID;

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
	
	@Inject(method = "defineSynchedData", at = @At("HEAD"))
	private void defineSynchedData(SynchedEntityData.Builder builder, CallbackInfo ci) {
		builder.define(PlayerAddition.DATA_MATE, Optional.empty());
	}
	
	@Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
	private void readAdditionalSaveData(CompoundTag compound, CallbackInfo ci) {
		if (compound.contains(DATA_MATE_UUID)) {
			PlayerAddition.setMateUUID((Player) (Object) this, compound.getUUID(DATA_MATE_UUID));
		}
	}
	
	@Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
	private void addAdditionalSaveData(CompoundTag compound, CallbackInfo ci) {
		var mate_uuid = PlayerAddition.getMate((Player) (Object) this);
		mate_uuid.ifPresent(uuid -> compound.putUUID(DATA_MATE_UUID, uuid));
	}
	
	@Inject(method = "<clinit>", at = @At("TAIL"))
	private static void clinit(CallbackInfo ci) {
		PlayerAddition.DATA_MATE = SynchedEntityData.defineId(Player.class, EntityDataSerializers.OPTIONAL_UUID);
	}
}
