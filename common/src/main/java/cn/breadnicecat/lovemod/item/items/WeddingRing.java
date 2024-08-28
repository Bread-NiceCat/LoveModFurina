package cn.breadnicecat.lovemod.item.items;

import cn.breadnicecat.lovemod.PlayerAddition;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;

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
		super(new Properties().rarity(Rarity.UNCOMMON));
	}
	
	@Override
	protected InteractionResult interactPlayer(ItemStack stack, ServerPlayer thisPlayer, ServerPlayer ta, InteractionHand hand) {
		//只有结婚戒指可以重新绑定
		//未绑定的戒指，并且me和ta互为情侣
		Optional<UUID> meMate = PlayerAddition.getMate(thisPlayer);
		Optional<UUID> taMate = PlayerAddition.getMate(thisPlayer);
		if (!isValidRing(stack)
				&& meMate.isPresent() && taMate.isPresent()
				&& thisPlayer.getUUID().equals(taMate.get())
				&& ta.getUUID().equals(meMate.get())) {
			//重新绑定
			setRingData(stack, thisPlayer, ta);
		}
		return super.interactPlayer(stack, thisPlayer, ta, hand);
	}
}
