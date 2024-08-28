package cn.breadnicecat.lovemod.mixin;

import cn.breadnicecat.lovemod.PlayerAddition;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemCooldowns;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

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
	
	
	@Shadow
	public abstract boolean isSpectator();
	
	@Shadow
	public int takeXpDelay;
	@Shadow
	private int sleepCounter;
	
	@Shadow
	public abstract void stopSleepInBed(boolean wakeImmediately, boolean updateLevelForSleepingPlayers);
	
	@Shadow
	protected abstract boolean updateIsUnderwater();
	
	@Shadow
	public AbstractContainerMenu containerMenu;
	
	@Shadow
	public abstract void closeContainer();
	
	@Shadow
	@Final
	public InventoryMenu inventoryMenu;
	
	@Shadow
	protected abstract void moveCloak();
	
	@Shadow
	protected FoodData foodData;
	
	@Shadow
	public abstract void awardStat(ResourceLocation statKey);
	
	@Shadow
	private ItemStack lastItemInMainHand;
	
	@Shadow
	public abstract void resetAttackStrengthTicker();
	
	@Shadow
	protected abstract void turtleHelmetTick();
	
	@Shadow
	@Final
	private ItemCooldowns cooldowns;
	
	@Shadow
	protected abstract void updatePlayerPose();
	
	@Shadow
	private int currentImpulseContextResetGraceTime;
	
	@Inject(method = "defineSynchedData", at = @At("HEAD"))
	private void defineSynchedData(SynchedEntityData.Builder builder, CallbackInfo ci) {
		builder.define(PlayerAddition.DATA_MATE, PlayerAddition.getMate((Player) (Object) this));
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
