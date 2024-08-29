package cn.breadnicecat.lovemod.mixin;

import cn.breadnicecat.lovemod.PlayerAddition;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Created in 2024/8/29 02:55
 * Project: LoveModFurina
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 *
 * <p>
 **/
@Mixin(ServerPlayer.class)
public abstract class MixinServerPlayer {
	
	@Inject(method = "tick", at = @At("TAIL"))
	public void tick(CallbackInfo ci) {
		//如果是CP，则添加爱心例子
		ServerPlayer self = (ServerPlayer) (Object) this;
		if (self.tickCount % 20 == 0) {
			ServerLevel level = self.serverLevel();
			Player nearestPlayer = level.getNearestPlayer(self, 3d);
			if(nearestPlayer!=null){
				if (PlayerAddition.isCP(self, nearestPlayer)) {
					level.sendParticles(ParticleTypes.HEART,
							self.getRandomX(1.0),
							self.getRandomY(),
							self.getRandomZ(1.0),
							1, 0, 0.5, 0,
							level.random.nextGaussian() * 0.02);
				}
			}
		}
	}
	
}
