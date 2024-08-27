package cn.breadnicecat.lovemod.item.items;

import net.minecraft.network.chat.Component
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack


/**
 * Created in 2024/8/27 12:01
 * Project: LoveModFurina
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 *
 * <p>
 **/
class Ring(val type: RingType) : Item(Properties()) {
    override fun interactLivingEntity(
        stack: ItemStack,
        player: Player,
        target: LivingEntity,
        hand: InteractionHand
    ): InteractionResult {
        if (hand == InteractionHand.MAIN_HAND) {
            player.sendSystemMessage(Component.literal("test ok on $target"))
        }
        return super.interactLivingEntity(stack, player, target, hand)
    }
}

enum class RingType {
    MALE, FEMALE
}
