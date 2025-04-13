package com.minecraft.mod.by.radchuk;

import com.minecraft.mod.by.radchuk.common.MinecraftModByRadchukConfiguration;
import com.minecraft.mod.by.radchuk.registry.ModMobs;
import com.minecraft.mod.by.radchuk.registry.ModItems;
import com.minecraft.mod.by.radchuk.utils.tabs.ModTabsRegistry;
import net.minecraft.world.item.Items;
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
 * <p>
 * Этот класс является точкой входа для всего мода и управляет его инициализацией.
 * Здесь регистрируются все события, сущности, предметы, блоки и другие компоненты мода.
 * <p>
 * Класс аннотирован {@link Mod}, что указывает Forge на то, что этот класс
 * является основным классом мода с указанным идентификатором.
 * <p>
 * Процесс инициализации включает:
 * <ul>
 *   <li>Регистрацию обработчиков событий жизненного цикла</li>
 *   <li>Инициализацию и регистрацию мобов</li>
 *   <li>Регистрацию предметов</li>
 *   <li>Создание и регистрацию вкладок в творческом режиме</li>
 * </ul>
 */
@Mod(MinecraftModByRadchukConfiguration.MOD_ID)
public class MinecraftModByRadchuk {

    /**
     * Экземпляр мода для доступа из других классов.
     * <p>
     * В новой версии Forge экземпляр создается автоматически, но мы сохраняем
     * его для удобства доступа из других классов мода.
     */
    public static MinecraftModByRadchuk instance;

    /**
     * Логгер для вывода отладочной информации.
     * <p>
     * Используется для записи важных событий, ошибок и предупреждений
     * в процессе работы мода.
     */
    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * Конструктор основного класса мода.
     * <p>
     * Вызывается Forge при загрузке мода. Здесь происходит вся инициализация
     * и регистрация компонентов мода.
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

        // Инициализация мобов с использованием нашей новой фабрики
        ModMobs.register(modEventBus);

        // Регистрируем предметы ПОСЛЕ сущностей
        // (важно для предметов, зависящих от сущностей, например, яиц спавна)
        ModItems.register(modEventBus);

        // Создание вкладок с помощью утилитарного модуля
        ModTabsRegistry.registerTabWithItems(
            MinecraftModByRadchukConfiguration.MOD_ID, // ID мода
            "mod_items_tab", // ID вкладки
            () -> Items.CHEST, // Иконка - сундук
            // Все предметы в одной вкладке
            ModItems.CUSTOM_MOB_SPAWN_EGG
        );

        // Регистрация всех вкладок мода
        ModTabsRegistry.registerTabsForMod(MinecraftModByRadchukConfiguration.MOD_ID, modEventBus);

        // Регистрация мода на шине событий Forge
        MinecraftForge.EVENT_BUS.register(this);

        LOGGER.info("Мод инициализирован!");
    }

    /**
     * Обработчик события общей настройки мода.
     * <p>
     * Вызывается на этапе инициализации, когда все регистрации завершены,
     * но до загрузки мира. Здесь выполняются настройки, общие для клиента и сервера.
     *
     * @param event Событие общей настройки
     */
    private void setup(final FMLCommonSetupEvent event) {
        LOGGER.info("Выполняется общая настройка мода...");
        // Здесь можно инициализировать сетевые пакеты, генерацию мира и т.д.
    }

    /**
     * Обработчик события настройки клиентской части мода.
     * <p>
     * Вызывается только на клиенте. Здесь выполняются настройки,
     * специфичные для клиентской части (рендеринг, GUI и т.д.).
     *
     * @param event Событие настройки клиента
     */
    private void clientSetup(final FMLClientSetupEvent event) {
        LOGGER.info("Выполняется настройка клиентской части мода...");
        // Здесь можно регистрировать рендереры, обработчики клавиш и т.д.
    }

    /**
     * Обработчик события настройки серверной части мода.
     * <p>
     * Вызывается только на выделенном сервере. Здесь выполняются настройки,
     * специфичные для серверной части.
     *
     * @param event Событие настройки сервера
     */
    private void serverSetup(final FMLDedicatedServerSetupEvent event) {
        LOGGER.info("Выполняется настройка серверной части мода...");
        // Здесь можно настраивать серверные компоненты мода
    }
}
