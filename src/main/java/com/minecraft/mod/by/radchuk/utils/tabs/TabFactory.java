package com.minecraft.mod.by.radchuk.utils.tabs;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Фабрика для создания вкладок креативного режима.
 * Универсальный утилитарный класс, не привязанный к конкретному моду.
 */
public class TabFactory {
    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * Создает новую вкладку креативного режима.
     *
     * @param modId ID мода
     * @param tabId идентификатор вкладки
     * @param iconSupplier поставщик иконки вкладки
     * @param itemsConsumer потребитель для добавления предметов во вкладку
     * @return объект регистрации вкладки
     */
    public static CreativeModeTab createTab(String modId, String tabId, Supplier<ItemStack> iconSupplier, Consumer<Consumer<Item>> itemsConsumer) {
        LOGGER.info("Создание вкладки: {}.{}", modId, tabId);

        return CreativeModeTab.builder()
            .title(Component.translatable("itemGroup." + modId + "." + tabId))
            .icon(iconSupplier)
            .displayItems((parameters, output) -> itemsConsumer.accept(output::accept))
            .build();
    }

    /**
     * Создает новую вкладку креативного режима с одним предметом.
     *
     * @param modId ID мода
     * @param tabId идентификатор вкладки
     * @param iconItem предмет, который будет использоваться как иконка и единственный предмет во вкладке
     * @return объект регистрации вкладки
     */
    public static CreativeModeTab createSimpleTab(String modId, String tabId, Supplier<Item> iconItem) {
        return createTab(
            modId,
            tabId,
            () -> new ItemStack(iconItem.get()),
            consumer -> consumer.accept(iconItem.get())
        );
    }

    /**
     * Создает новую вкладку креативного режима с несколькими предметами.
     *
     * @param modId ID мода
     * @param tabId идентификатор вкладки
     * @param iconItem предмет, который будет использоваться как иконка
     * @param items массив предметов для добавления во вкладку
     * @return объект регистрации вкладки
     */
    @SafeVarargs
    public static CreativeModeTab createTabWithItems(String modId, String tabId, Supplier<Item> iconItem, Supplier<Item>... items) {
        return createTab(
            modId,
            tabId,
            () -> new ItemStack(iconItem.get()),
            consumer -> {
                for (Supplier<Item> item : items) {
                    consumer.accept(item.get());
                }
            }
        );
    }

    /**
     * Создает новую вкладку креативного режима с настраиваемыми свойствами.
     *
     * @param modId ID мода
     * @param tabId идентификатор вкладки
     * @param iconSupplier поставщик иконки вкладки
     * @param configurator функция для дополнительной настройки построителя вкладки
     * @param itemsConsumer потребитель для добавления предметов во вкладку
     * @return объект регистрации вкладки
     */
    public static CreativeModeTab createCustomTab(
        String modId,
        String tabId,
        Supplier<ItemStack> iconSupplier,
        Consumer<CreativeModeTab.Builder> configurator,
        Consumer<Consumer<Item>> itemsConsumer) {

        LOGGER.info("Создание настраиваемой вкладки: {}.{}", modId, tabId);

        CreativeModeTab.Builder builder = CreativeModeTab.builder()
            .title(Component.translatable("itemGroup." + modId + "." + tabId))
            .icon(iconSupplier);

        // Применяем дополнительные настройки
        configurator.accept(builder);

        // Настраиваем отображение предметов
        builder.displayItems((parameters, output) -> itemsConsumer.accept(output::accept));

        return builder.build();
    }
}
