package com.minecraft.mod.by.radchuk.registry;

import com.minecraft.mod.by.radchuk.common.MinecraftModByRadchukConfiguration;
import com.minecraft.mod.by.radchuk.custom_entities.CustomHumanMob;
import com.minecraft.mod.by.radchuk.utils.entities.AttributesBuilder;
import com.minecraft.mod.by.radchuk.utils.entities.MobFactory;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.RegistryObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Класс для регистрации мобов (существ) в моде.
 * <p>
 * Этот класс отвечает за создание и регистрацию всех пользовательских мобов,
 * которые добавляются модом. Он использует абстракцию MobFactory для упрощения
 * процесса создания и регистрации мобов.
 * <p>
 * Каждый моб регистрируется с определенными параметрами, такими как размер,
 * текстура, атрибуты (здоровье, скорость, урон и т.д.).
 */
public class ModMobs {
    /** Логгер для вывода информации о процессе регистрации мобов */
    private static final Logger LOGGER = LogManager.getLogger();

    /** Объект регистрации для пользовательского гуманоидного моба */
    public static RegistryObject<EntityType<CustomHumanMob>> CUSTOM_MOB_HUMAN;

    /**
     * Регистрирует всех мобов мода в системе Forge.
     * <p>
     * Этот метод должен вызываться во время инициализации мода для регистрации
     * всех пользовательских мобов. Он создает экземпляры мобов с заданными
     * параметрами и регистрирует их в системе Forge.
     *
     * @param modEventBus Шина событий мода, используемая для регистрации
     */
    public static void register(IEventBus modEventBus) {
        LOGGER.info("Registering mod entities");

        // Получаем фабрику для нашего мода
        MobFactory factory = MobFactory.getInstance(MinecraftModByRadchukConfiguration.MOD_ID);

        // Регистрируем гуманоидного моба
        CUSTOM_MOB_HUMAN = factory.createHumanoidMonster(
                "custom_human_mob",
                CustomHumanMob::new,
                MobCategory.MONSTER
            )
            .size(0.6F, 1.95F)  // Стандартный размер для гуманоидного моба (как у игрока)
            .texture("textures/entity/custom_human_mob.png")  // Путь к текстуре моба
            .attributes(() -> AttributesBuilder.mob()  // Используем базовый класс Mob
                .health(25.0D)  // Устанавливаем здоровье моба
                .damage(4.0D)   // Устанавливаем базовый урон
                .speed(0.3D)    // Устанавливаем скорость передвижения
                .build())
            .build();

        // Регистрируем фабрику в системе событий
        factory.register(modEventBus);
    }
}
