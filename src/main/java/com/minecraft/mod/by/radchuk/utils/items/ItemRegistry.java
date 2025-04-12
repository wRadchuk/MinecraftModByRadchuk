package com.minecraft.mod.by.radchuk.utils.items;

import net.minecraft.world.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Универсальный регистратор предметов.
 * Может использоваться в любом моде.
 */
public abstract class ItemRegistry {
    /**
     * Логгер для вывода отладочной информации.
     */
    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * Хранилище регистров предметов для разных модов.
     */
    private static final Map<String, DeferredRegister<Item>> MOD_ITEM_REGISTRIES = new HashMap<>();

    /**
     * Получает или создает регистр предметов для указанного мода.
     *
     * @param modId ID мода
     * @return регистр предметов для мода
     */
    public static DeferredRegister<Item> getItemRegistry(String modId) {
        return MOD_ITEM_REGISTRIES.computeIfAbsent(modId,
            id -> {
                LOGGER.debug("Создание регистра предметов для мода: {}", id);
                return DeferredRegister.create(ForgeRegistries.ITEMS, id);
            });
    }

    /**
     * Регистрирует предмет в указанном моде.
     *
     * @param modId ID мода
     * @param itemId ID предмета
     * @param itemSupplier поставщик предмета
     * @return объект регистрации предмета
     */
    public static <T extends Item> RegistryObject<T> registerItem(String modId, String itemId, Supplier<T> itemSupplier) {
        LOGGER.debug("Регистрация предмета: {}.{}", modId, itemId);
        return getItemRegistry(modId).register(itemId, itemSupplier);
    }

    /**
     * Регистрирует все предметы мода в системе событий Forge.
     *
     * @param modId ID мода
     * @param eventBus шина событий Forge
     */
    public static void registerItemsForMod(String modId, IEventBus eventBus) {
        DeferredRegister<Item> registry = getItemRegistry(modId);
        LOGGER.info("Регистрация предметов для мода: {}", modId);
        LOGGER.info("Количество предметов для регистрации: {}", registry.getEntries().size());
        registry.register(eventBus);
        LOGGER.info("Завершение регистрации предметов для мода: {}", modId);
    }
}
