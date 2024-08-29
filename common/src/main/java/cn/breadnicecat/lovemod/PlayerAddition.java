package cn.breadnicecat.lovemod;

import cn.breadnicecat.lovemod.mixin.MixinEntity;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
	/**
	 * 由Player类的静态初始化块就行初始化赋值
	 */
	public static EntityDataAccessor<Optional<UUID>> DATA_MATE;
	public static final String DATA_MATE_UUID = "mate_uuid";
	
	public static Optional<UUID> getMate(@NotNull Player player) {
		return getDataSlot(player).get(DATA_MATE);
	}
	
	public static boolean isCP(@NotNull Player p1, @NotNull Player p2) {
		Optional<UUID> p1m = getMate(p1);
		Optional<UUID> p2m = getMate(p2);
		return p1m.isPresent() && p2m.isPresent()
				&& p2.getUUID().equals(p1m.get())
				&& p1.getUUID().equals(p2m.get());
	}
	
	public static void setMate(@NotNull Player player, @Nullable Player target) {
		setMateUUID(player, target == null ? null : target.getUUID());
	}
	
	public static void setMateUUID(@NotNull Player player, @Nullable UUID target) {
		getDataSlot(player).set(DATA_MATE, Optional.ofNullable(target));
	}
	
	public static SynchedEntityData getDataSlot(@NotNull Player player) {
		return ((MixinEntity) player).getEntityData();
	}
	
}
