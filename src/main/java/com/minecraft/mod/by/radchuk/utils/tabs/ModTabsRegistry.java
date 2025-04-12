package com.minecraft.mod.by.radchuk.utils.tabs;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Универсальный регистратор вкладок креативного режима.
 * Может использоваться в любом моде.
 */
public class ModTabsRegistry {
    /**
     * Логгер для вывода отладочной информации.
     */
    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * Хранилище регистров вкладок для разных модов.
     */
    private static final Map<String, DeferredRegister<CreativeModeTab>> MOD_TABS_REGISTRIES = new HashMap<>();

    /**
     * Получает или создает регистр вкладок для указанного мода.
     *
     * @param modId ID мода
     * @return регистр вкладок для мода
     */
    public static DeferredRegister<CreativeModeTab> getTabsRegistry(String modId) {
        return MOD_TABS_REGISTRIES.computeIfAbsent(modId,
            id -> DeferredRegister.create(Registries.CREATIVE_MODE_TAB, id));
    }

    /**
     * Регистрирует простую вкладку с одним предметом.
     *
     * @param modId    ID мода
     * @param tabId    идентификатор вкладки
     * @param iconItem предмет-иконка и единственный предмет во вкладке
     */
    public static void registerSimpleTab(String modId, String tabId, Supplier<Item> iconItem) {
        LOGGER.info("Регистрация простой вкладки: {}.{}", modId, tabId);
        getTabsRegistry(modId).register(
            tabId,
            () -> TabFactory.createSimpleTab(modId, tabId, iconItem)
        );
    }

    /**
     * Регистрирует вкладку с несколькими предметами.
     *
     * @param modId    ID мода
     * @param tabId    идентификатор вкладки
     * @param iconItem предмет-иконка
     * @param items    предметы для добавления во вкладку
     */
    @SafeVarargs
    public static void registerTabWithItems(
        String modId, String tabId, Supplier<Item> iconItem, Supplier<Item>... items) {
        LOGGER.info("Регистрация вкладки с предметами: {}.{}", modId, tabId);
        getTabsRegistry(modId).register(
            tabId,
            () -> TabFactory.createTabWithItems(modId, tabId, iconItem, items)
        );
    }

    /**
     * Регистрирует все вкладки мода в системе событий Forge.
     *
     * @param modId ID мода
     * @param eventBus шина событий Forge
     */
    public static void registerTabsForMod(String modId, IEventBus eventBus) {
        DeferredRegister<CreativeModeTab> registry = getTabsRegistry(modId);
        LOGGER.info("Регистрация вкладок для мода: {}", modId);
        LOGGER.info("Количество вкладок для регистрации: {}", registry.getEntries().size());
        registry.register(eventBus);
        LOGGER.info("Завершение регистрации вкладок для мода: {}", modId);
    }
}
