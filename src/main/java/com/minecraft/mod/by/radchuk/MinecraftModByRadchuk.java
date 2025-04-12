package com.minecraft.mod.by.radchuk;

import com.minecraft.mod.by.radchuk.common.MinecraftModByRadchukConfiguration;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Основной класс мода.
 * Этот класс является точкой входа для всего мода и управляет его инициализацией.
 * Здесь регистрируются все события, сущности и другие компоненты мода.
 */
@Mod(MinecraftModByRadchukConfiguration.MOD_ID)
public class MinecraftModByRadchuk {

    /**
     * Экземпляр мода для доступа из других классов.
     * В новой версии Forge экземпляр создается автоматически.
     */
    public static MinecraftModByRadchuk instance;

    /**
     * Логгер для вывода отладочной информации.
     */
    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * Конструктор мода.
     * Инициализирует основные компоненты и регистрирует обработчики событий.
     */
    public MinecraftModByRadchuk() {
        // Сохраняем экземпляр для доступа из других классов
        instance = this;

        // Получаем шину событий мода
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Регистрация обработчиков событий жизненного цикла мода
        modEventBus.addListener(this::setup);
        modEventBus.addListener(this::clientSetup);
        modEventBus.addListener(this::serverSetup);

        // Регистрация мода на шине событий Forge
        MinecraftForge.EVENT_BUS.register(this);


        LOGGER.info("Мод инициализирован!");
    }

    /**
     * Настройка мода, общая для клиента и сервера.
     * Вызывается во время фазы настройки жизненного цикла мода.
     *
     * @param event Событие настройки.
     */
    private void setup(final FMLCommonSetupEvent event) {
        LOGGER.info("Выполняется общая настройка мода...");
        // Здесь код для общей настройки мода
    }

    /**
     * Настройка клиентской части мода.
     * Вызывается только на стороне клиента.
     *
     * @param event Событие настройки клиента.
     */
    private void clientSetup(final FMLClientSetupEvent event) {
        LOGGER.info("Выполняется настройка клиентской части мода...");
        // Здесь код для настройки клиентской части мода

        // Регистрация рендеров и других клиентских компонентов
    }

    /**
     * Настройка серверной части мода.
     * Вызывается только на выделенном сервере.
     *
     * @param event Событие настройки сервера.
     */
    private void serverSetup(final FMLDedicatedServerSetupEvent event) {
        LOGGER.info("Выполняется настройка серверной части мода...");
        // Здесь код для настройки серверной части мода
    }
}
