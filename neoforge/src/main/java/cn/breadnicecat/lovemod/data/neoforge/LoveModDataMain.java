package cn.breadnicecat.lovemod.data.neoforge;

import cn.breadnicecat.lovemod.data.neoforge.providers.CLanguageProvider;
import cn.breadnicecat.lovemod.data.neoforge.providers.CTerminalStateProvider;
import cn.breadnicecat.lovemod.data.neoforge.providers.ModItemModelProvider;
import cn.breadnicecat.lovemod.data.neoforge.providers.ModRecipeProvider;
import cn.breadnicecat.lovemod.utils.Accessor;
import cn.breadnicecat.lovemod.utils.SafeAccessor;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

import static cn.breadnicecat.lovemod.LoveMod.LOGGER;

/**
 * Created in 2024/8/29 01:28
 * Project: LoveModFurina
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 *
 * <p>
 **/
@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class LoveModDataMain {
	private static final Accessor<Boolean> STATE = new SafeAccessor<>(false);
	
	@SubscribeEvent
	public static void onGatherData(GatherDataEvent event) {
		LOGGER.warn("RUNNING DATAGEN ENVIRONMENT");
		launchProcessTerminator();
		DataGenerator generator = event.getGenerator();
		PackOutput output = generator.getPackOutput();
		ExistingFileHelper efHelper = event.getExistingFileHelper();
		CompletableFuture<HolderLookup.Provider> lookup = event.getLookupProvider();
		generator.addProvider(event.includeClient(), new CLanguageProvider(output));
		generator.addProvider(event.includeClient(), new ModItemModelProvider(output, efHelper));
		generator.addProvider(event.includeServer(), new ModRecipeProvider(output, lookup));
		
		generator.addProvider(true, new CTerminalStateProvider(STATE));
	}
	
	/**
	 * Arch 会启动几个非daemon的线程，导致运行完毕后无法正常退出
	 * 此线程通过在main线程运行终止后exit来解决此问题
	 */
	private static void launchProcessTerminator() {
		Thread main = Thread.currentThread();
		Thread helper = new Thread(() -> {
			LOGGER.info("Thread {} started!", Thread.currentThread().getName());
			while (main.isAlive()) {
				Thread.yield();
			}
			int status;
			if (STATE.get()) {
				LOGGER.info("with status = 0");
				status = 0;
			} else {
				LOGGER.warn("with status = -1");
				status = -1;
			}
			LOGGER.info("Preparing exit after 5s!");
			try {
				for (int i = 0; i < 5; i++) {
					Thread.sleep(1000);
					System.out.print("\rPreparing exit after " + (5 - i) + "s!");
				}
				System.out.println();
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
			LOGGER.warn("bye");
			System.exit(status);
		}, "Process Terminator");
		helper.setDaemon(true);
		helper.start();
	}
	
}
