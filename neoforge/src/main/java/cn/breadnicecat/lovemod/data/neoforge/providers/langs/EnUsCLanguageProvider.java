package cn.breadnicecat.lovemod.data.neoforge.providers.langs;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

import static cn.breadnicecat.lovemod.LoveMod.MOD_ID;

/**
 * Created in 2023/12/30 14:07
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 */
public class EnUsCLanguageProvider extends LanguageProvider {
	public EnUsCLanguageProvider(PackOutput output) {
		super(output, MOD_ID, "en_us");
	}
	
	@Override
	protected void addTranslations() {
	}
}
