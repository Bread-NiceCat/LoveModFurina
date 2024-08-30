package cn.breadnicecat.lovemod.data.neoforge.providers;


import cn.breadnicecat.lovemod.data.neoforge.providers.langs.EnUsCLanguageProvider;
import cn.breadnicecat.lovemod.data.neoforge.providers.langs.ZhCnCLanguageProvider;
import cn.breadnicecat.lovemod.item.ModItems;
import cn.breadnicecat.lovemod.item.ModTabs;
import dev.architectury.registry.registries.DeferredSupplier;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.data.LanguageProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import static cn.breadnicecat.lovemod.ModCommands.*;
import static cn.breadnicecat.lovemod.item.items.CommonRing.*;
import static cn.breadnicecat.lovemod.item.items.DivorceAgreement.agreement_not_suit;
import static cn.breadnicecat.lovemod.item.items.DivorceAgreement.divorce;


/**
 * Created in 2023/8/22 21:05
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 * 只生成en_us,剩下的手动补全
 */
public class CLanguageProvider implements DataProvider {
	private final EnUsCLanguageProvider enProv;
	private final ZhCnCLanguageProvider zhProv;
	private final Set<LanguageProvider> subs;
	private final Set<String> existKeys = new HashSet<>();
	
	public CLanguageProvider(PackOutput output) {
		subs = Set.of(
				enProv = new EnUsCLanguageProvider(output),
				zhProv = new ZhCnCLanguageProvider(output)
		);
	}
	
	protected void addTranslations() {
		addItemById(ModItems.WEDDING_RING, "结婚戒指");
		addItemById(ModItems.ENGAGEMENT_RING, "求婚戒指");
		addItemById(ModItems.DIVORCE_AGREEMENT, "离婚协议书");
		add(ModTabs.key, "LoveMod: Furina", "结婚：芙宁娜版");
		add(me_married, "You was married.", "你已经结婚了。");
		add(ta_married, "%s was married.", "%s已经结婚了。");
		add(player_offline, "%s was offline.", "%s目前不在线。");
		add(congratulation, "Congratulations to %s and %s on getting married.", "祝贺%s和%s喜结良缘！");
		add(request, "Would you like to marry %s. Right click %s with ring to agree.", "你愿意和%s结为夫妻吗？用戒指右键%s同意。");
		add(ring_mate, "The name %s is engraved on it.", "上面篆刻着%s的名字。");
		add(divorce, "You're divorced from %s.", "你和%s离婚了。");
		add(not_my_ring, "This ring does not belong to you.", "这不是你的戒指。");
		add(me_request, "You gave %s your engagement ring.", "你把你的求婚戒指给了%s。");
		add(not_from_ta, "This ring was not given by %s.", "这个戒指不是%s赠送的。");
		add(unbind_ring, "This ring is not bind yet.", "这个戒指还没有绑定。");
		add(rebind_ok, "Rebinding successful.", "重新绑定成功。");
		add(agreement_not_suit, "This divorce agreement does not belong to you.", "这张离婚协议书不属于你们。");
		add(unmarried, "You're still single.", "你尚未结婚。");
		add(unaccepted, "You have not consented to the marriage.", "你尚未同意这桩婚事。");
		add(divorced, "You have divorced from %s.", "你已与%s离婚。");
		add(page_not_exist, "Page not exists!", "页码不存在!");
		add(page_info, "Page %d/%d", "第%d页，共%d页");
		add(page_prev, "<Prev>", "<上一页>");
		add(page_next, "<Next>", "<下一页>");
		add(click_copy_uuid, "Click to copy UUID", "单击复制UUID");
		
		
	}
	
	public void addItemById(DeferredSupplier<? extends Item> ie, String zh_cn) {
		addItem(ie, byId(ie.getId().getPath()), zh_cn);
	}
	
	public void addItem(DeferredSupplier<? extends Item> ie, String en_us, String zh_cn) {
		add(ie.get().getDescriptionId(), en_us, zh_cn);
	}
	
	public void addById(String key, @Nullable String zh_cn) {
		add(key, byId(key), zh_cn);
	}
	
	public void add(String key, String en_us, @Nullable String zh_cn) {
		if (!existKeys.add(key)) {
			throw new IllegalArgumentException("Duplicate key: " + key);
		}
		enProv.add(key, en_us);
		if (zh_cn != null) {
			zhProv.add(key, zh_cn);
		}
	}
	
	/**
	 * 从注册名中获取名称
	 *
	 * @return 将id中的下划线替换为空格，并且每个空格后第一个字母大写
	 * <p>
	 * 例如{@code 输入this_is_a_example 返回 This Is A Example}
	 */
	
	private static String byId(String id) {
		StringBuilder sb = new StringBuilder();
		String[] s = id.split("_");
		for (String s1 : s) {
			sb.append(s1.substring(0, 1).toUpperCase()).append(s1.substring(1)).append(" ");
		}
		return modifyById(sb.substring(0, sb.length() - 1));
	}
	
	private static String modifyById(@NotNull String byId) {
		return byId;
	}
	
	@Override
	public @NotNull CompletableFuture<?> run(@NotNull CachedOutput output) {
		addTranslations();
		return CompletableFuture.allOf(
				subs.stream()
						.map(m -> m.run(output))
						.toArray(CompletableFuture[]::new));
	}
	
	@Override
	public @NotNull String getName() {
		return "LoveMod Languages";
	}
}
