package com.minecraft.mod.by.radchuk.registry;

import com.minecraft.mod.by.radchuk.common.MinecraftModByRadchukConfiguration;
import com.minecraft.mod.by.radchuk.utils.items.ItemFactory;
import com.minecraft.mod.by.radchuk.utils.items.ItemRegistry;
import net.minecraft.world.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.RegistryObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Класс для создания и регистрации предметов мода.
 * <p>
 * Этот класс отвечает за определение всех пользовательских предметов,
 * которые добавляются модом. Он использует абстракции ItemFactory и ItemRegistry
 * для упрощения процесса создания и регистрации предметов.
 * <p>
 * Здесь определяются все предметы мода, включая обычные предметы, инструменты,
 * оружие, броню, еду и специальные предметы, такие как яйца спавна мобов.
 */
public class ModItems {
    /** Логгер для вывода информации о процессе регистрации предметов */
    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * Яйцо спавна для пользовательского гуманоидного моба.
     * <p>
     * Этот предмет позволяет игрокам создавать экземпляры моба CustomHumanMob
     * в игровом мире. Цвета яйца настроены на белый (основной) и черный (вторичный).
     */
    public static final RegistryObject<Item> CUSTOM_MOB_SPAWN_EGG = ItemFactory.createSpawnEgg(
        MinecraftModByRadchukConfiguration.MOD_ID,
        "custom_human_mob_spawn_egg",
        ModMobs.CUSTOM_MOB_HUMAN,
        0xffffff,  // Основной цвет (белый)
        0x000000   // Вторичный цвет (черный)
    );

    /**
     * Регистрирует все предметы мода в системе Forge.
     * <p>
     * Этот метод должен вызываться во время инициализации мода для регистрации
     * всех пользовательских предметов. Он использует ItemRegistry для автоматической
     * регистрации всех предметов, определенных в этом классе.
     *
     * @param eventBus Шина событий мода, используемая для регистрации
     */
    public static void register(IEventBus eventBus) {
        LOGGER.info("Начало регистрации предметов мода");

        // Регистрируем предметы мода на шине событий
        ItemRegistry.registerItemsForMod(MinecraftModByRadchukConfiguration.MOD_ID, eventBus);

        LOGGER.info("Регистрация предметов мода завершена");
    }
}
