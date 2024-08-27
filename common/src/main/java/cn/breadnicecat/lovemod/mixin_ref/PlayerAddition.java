package cn.breadnicecat.lovemod.mixin_ref;

import cn.breadnicecat.lovemod.mixin.MixinEntity;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.player.Player;

import java.util.Optional;
import java.util.UUID;

/**
 * Created in 2024/8/27 14:38
 * Project: LoveModFurina
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 *
 * <p>
 **/
public class PlayerAddition {
	public static final EntityDataAccessor<Optional<UUID>> DATA_MATE = SynchedEntityData.defineId(Player.class, EntityDataSerializers.OPTIONAL_UUID);
	public static final String DATA_MATE_UUID = "mate_uuid";
	
	public static Optional<UUID> getMate(Player player) {
		SynchedEntityData data = ((MixinEntity) player).getEntityData();
		return data.get(DATA_MATE);
	}
	
	public static void setMate(Player player, Player target) {
		setMateUUID(player, target == null ? null : target.getUUID());
	}
	
	public static void setMateUUID(Player player, UUID target) {
		SynchedEntityData data = ((MixinEntity) player).getEntityData();
		data.set(DATA_MATE, Optional.ofNullable(target));
	}
	
}
