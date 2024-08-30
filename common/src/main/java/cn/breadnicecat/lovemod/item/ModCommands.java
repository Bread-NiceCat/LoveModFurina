package cn.breadnicecat.lovemod.item;

import cn.breadnicecat.lovemod.PlayerAddition;
import cn.breadnicecat.lovemod.item.items.CommonRing;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.datafixers.util.Pair;
import dev.architectury.event.events.common.CommandRegistrationEvent;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.*;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

import java.util.*;

import static cn.breadnicecat.lovemod.LoveMod.LOGGER;
import static java.lang.Math.min;
import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;
import static net.minecraft.network.chat.Component.translatable;

/**
 * Created in 2024/8/29 14:26
 * Project: LoveModFurina
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 *
 * <p>
 **/
public class ModCommands {
	public static final String page_not_exist = "lovemod.cmd.page_not_exist";
	public static final String page_info = "lovemod.cmd.page_info";
	public static final String page_prev = "lovemod.cmd.page_prev";
	public static final String page_next = "lovemod.cmd.page_next";
	public static final String click_copy_uuid = "lovemod.cmd.click_copy_uuid";
	
	public static void register() {
		CommandRegistrationEvent.EVENT.register(ModCommands::on);
	}
	
	private static void on(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext context, Commands.CommandSelection selection) {
		LOGGER.info("Loading Commands");
		var root = literal("lovemod").requires(c -> c.hasPermission(Commands.LEVEL_GAMEMASTERS));
		root.then(literal("list_couples")
				.executes(c -> listCouples(c, 1))
				.then(argument("page", IntegerArgumentType.integer(1))
						.executes(c -> listCouples(c, IntegerArgumentType.getInteger(c, "page")))));
		root.then(literal("get_uuid")
				.then(argument("player", EntityArgument.player())
						.executes(c -> {
							ServerPlayer sp = EntityArgument.getPlayer(c, "player");
							UUID uuid = sp.getUUID();
							c.getSource().sendSystemMessage(sp.getName().copy().append(Component.literal("'s UUID: " + uuid)
									.withStyle(Style.EMPTY
											.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, translatable(click_copy_uuid)))
											.withClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, uuid.toString())))
									.withStyle(ChatFormatting.AQUA)));
							return 1;
						})));
		root.then(literal("erase_mate_data")
				.then(argument("player", EntityArgument.player())
						.executes(c -> {
							ServerPlayer player = EntityArgument.getPlayer(c, "player");
							PlayerAddition.setMateUUID(player, null);
							c.getSource().sendSystemMessage(Component.literal("Success!").withStyle(ChatFormatting.AQUA));
							return 1;
						})));
		root.then(literal("get_mate")
				.then(argument("player", EntityArgument.player())
						.executes(c -> {
							ServerPlayer player = EntityArgument.getPlayer(c, "player");
							c.getSource().sendSystemMessage(player.getName().copy().append(Component.literal("'s mate:").append(mayOnlineByUUID(c.getSource().getLevel(), PlayerAddition.getMate(player).orElse(null)))));
							return 1;
						})));
		root.then(literal("get_ring_data")
				.executes(c -> {
					CommandSourceStack source = c.getSource();
					ServerPlayer player = source.getPlayer();
					ServerLevel level = source.getLevel();
					String ht = "Main Hand";
					ItemStack hand = player.getItemInHand(InteractionHand.MAIN_HAND);
					if (hand.isEmpty() || !(hand.getItem() instanceof CommonRing)) {
						ht = "Off Hand";
						hand = player.getItemInHand(InteractionHand.OFF_HAND);
					}
					if (!(hand.getItem() instanceof CommonRing)) {
						source.sendSystemMessage(Component.literal("Not a Ring").withStyle(ChatFormatting.YELLOW));
					}
					var mateName = CommonRing.getMateName(hand).orElse("null");
					var mateUUID = CommonRing.getMateUUID(hand);
					var holderUUID = CommonRing.getHolderUUID(hand);
					var session = CommonRing.isRunning(hand);
					String head = "======" + ht + "=======";
					source.sendSystemMessage(Component.literal(head).withStyle(ChatFormatting.YELLOW));
					source.sendSystemMessage(Component.literal("mate_name:" + mateName).withStyle(ChatFormatting.AQUA));
					source.sendSystemMessage(Component.literal("mate_uuid:").withStyle(ChatFormatting.AQUA).append(mayOnlineByUUID(level, mateUUID.orElse(null))));
					source.sendSystemMessage(Component.literal("holder_uuid:").withStyle(ChatFormatting.AQUA).append(mayOnlineByUUID(level, holderUUID.orElse(null))));
					source.sendSystemMessage(Component.literal("session:").withStyle(ChatFormatting.AQUA).append(String.valueOf(session)));
					source.sendSystemMessage(Component.literal("=".repeat(head.length())).withStyle(ChatFormatting.YELLOW));
					
					return 1;
				}));
		dispatcher.register(root);
	}
	
	private static int listCouples(CommandContext<CommandSourceStack> context, @Range(from = 0, to = Integer.MAX_VALUE) int page) {
		CommandSourceStack source = context.getSource();
		List<Pair<Player, Object>> list = getCPs(context.getSource().getLevel());
		if (list.isEmpty()) {
			source.sendSystemMessage(Component.literal("No data found."));
			return -1;
		}
		int fromIndex = (page - 1) * 10;
		
		if (fromIndex >= list.size()) {
			source.sendFailure(translatable(page_not_exist));
			return -1;
		}
		int pageTotal = (list.size() / 10) + 1;
		MutableComponent head = Component.literal("=".repeat(4)).withStyle(ChatFormatting.GREEN)
				.append(translatable(page_info, page, pageTotal).withStyle(ChatFormatting.AQUA))
				.append(Component.literal("=".repeat(4)));
		
		MutableComponent tail = Component.literal("=".repeat(2)).withStyle(ChatFormatting.GREEN);
		source.sendSystemMessage(head);
		list = list.subList(fromIndex, min(list.size() - 1, fromIndex + 10));
		
		MutableComponent prev = translatable(page_prev);
		if (page > 0)
			prev.withStyle(Style.EMPTY.applyFormat(ChatFormatting.AQUA).withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/lovemod listCouples " + (page - 1))));
		tail.append(prev);
		tail.append(Component.literal(" ".repeat(2)));
		MutableComponent next = translatable(page_next);
		ServerLevel level = source.getLevel();
		for (Pair<Player, Object> pair : list) {
			MutableComponent pre = onlinePlayer((ServerPlayer) pair.getFirst()).copy().append(" ❤ ");
			Object second = pair.getSecond();
			if (second instanceof UUID uuid) {
				pre.append(mayOnlineByUUID(level, uuid));
			} else if (second instanceof ServerPlayer pp) {
				pre.append(onlinePlayer(pp));
			} else pre.append("unknown");
			source.sendSystemMessage(pre);
		}
		if (page < pageTotal)
			next.withStyle(Style.EMPTY.applyFormat(ChatFormatting.AQUA).withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/lovemod listCouples " + (page + 1))));
		tail.append(next);
		tail.append(Component.literal("=".repeat(2)));
		source.sendSystemMessage(tail);
		return 1;
	}
	
	private static List<Pair<Player, Object>> getCPs(ServerLevel level) {
		List<Pair<Player, Object>> list = new LinkedList<>();
		Set<Player> loaded = new HashSet<>();
		level.getPlayers(o -> !loaded.contains(o)).forEach((p) -> {
			Optional<UUID> mate = PlayerAddition.getMate(p);
			if (mate.isPresent()) {
				Player matePlayer = level.getPlayerByUUID(mate.get());
				if (matePlayer != null) {
					list.add(Pair.of(p, matePlayer));
					loaded.add(matePlayer);
				} else {
					list.add(Pair.of(p, mate.get()));
				}
			}
			loaded.add(p);
		});
		return list;
	}
	
	private static Component onlinePlayer(@NotNull ServerPlayer player) {
		return player.getName().copy()
				.withStyle(Style.EMPTY.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ENTITY, new HoverEvent.EntityTooltipInfo(player.getType(), player.getUUID(), player.getName())))
						.withClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, player.getUUID().toString())));
	}
	
	private static Component mayOnlineByUUID(ServerLevel sl, @Nullable UUID uuid) {
		if (uuid == null) return Component.literal("null");
		ServerPlayer player = (ServerPlayer) sl.getPlayerByUUID(uuid);
		if (player == null) {
			return Component.literal(uuid.toString()).withStyle(Style.EMPTY
					.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, translatable(CommonRing.player_offline, uuid.toString()).append(" ").append(translatable(click_copy_uuid).withStyle(ChatFormatting.GRAY))))
					.withClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, uuid.toString()))
			);
		}
		return onlinePlayer(player);
	}
}
