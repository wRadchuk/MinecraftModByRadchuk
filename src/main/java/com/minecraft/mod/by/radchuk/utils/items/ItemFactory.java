package com.minecraft.mod.by.radchuk.utils.items;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.RegistryObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.function.Supplier;

/**
 * Фабрика для создания и автоматической регистрации различных типов предметов.
 */
public abstract class ItemFactory {
    /**
     * Логгер для вывода отладочной информации.
     */
    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * Создает и регистрирует базовый предмет.
     *
     * @param modId ID мода
     * @param itemId ID предмета
     * @param properties свойства предмета
     * @return объект регистрации предмета
     */
    public static RegistryObject<Item> createBasicItem(
        String modId,
        String itemId,
        Item.Properties properties) {
        LOGGER.info("Создание и регистрация базового предмета: {}.{}", modId, itemId);
        return ItemRegistry.registerItem(
            modId,
            itemId,
            () -> new Item(properties)
        );
    }

    /**
     * Создает и регистрирует яйцо спавна для сущности.
     *
     * @param modId ID мода
     * @param itemId ID предмета
     * @param entityType поставщик типа сущности
     * @param primaryColor основной цвет яйца
     * @param secondaryColor вторичный цвет яйца
     * @return объект регистрации яйца спавна
     */
    public static RegistryObject<Item> createSpawnEgg(
        String modId,
        String itemId,
        Supplier<? extends EntityType<? extends Mob>> entityType,
        int primaryColor,
        int secondaryColor
    ) {
        LOGGER.info("Создание и регистрация яйца спавна: {}.{}", modId, itemId);
        return ItemRegistry.registerItem(
            modId,
            itemId,
            () -> new ForgeSpawnEggItem(
                entityType,
                primaryColor,
                secondaryColor,
                new Item.Properties()
            )
        );
    }

    /**
     * Создает и регистрирует предмет еды.
     *
     * @param modId ID мода
     * @param itemId ID предмета
     * @param foodProperties свойства еды
     * @return объект регистрации предмета еды
     */
    public static RegistryObject<Item> createFoodItem(
        String modId,
        String itemId,
        FoodProperties foodProperties
    ) {
        LOGGER.info("Создание и регистрация предмета еды: {}.{}", modId, itemId);
        return ItemRegistry.registerItem(
            modId,
            itemId,
            () -> new Item(new Item.Properties().food(foodProperties))
        );
    }

    /**
     * Создает свойства еды с помощью билдера.
     *
     * @param nutrition питательность (количество восстанавливаемого голода)
     * @param saturation насыщение
     * @return билдер свойств еды
     */
    public static FoodProperties.Builder createFoodProperties(int nutrition, float saturation) {
        return new FoodProperties.Builder()
            .nutrition(nutrition)
            .saturationMod(saturation);
    }

    /**
     * Создает и регистрирует кирку.
     *
     * @param modId ID мода
     * @param itemId ID предмета
     * @param tier уровень материала
     * @param attackDamage урон атаки
     * @param attackSpeed скорость атаки
     * @param properties свойства предмета
     * @return объект регистрации кирки
     */
    public static RegistryObject<PickaxeItem> createPickaxe(
        String modId,
        String itemId,
        Tier tier,
        int attackDamage,
        float attackSpeed,
        Item.Properties properties
    ) {
        LOGGER.info("Создание и регистрация кирки: {}.{}", modId, itemId);
        return ItemRegistry.registerItem(
            modId,
            itemId,
            () -> new PickaxeItem(tier, attackDamage, attackSpeed, properties)
        );
    }

    /**
     * Создает и регистрирует топор.
     *
     * @param modId ID мода
     * @param itemId ID предмета
     * @param tier уровень материала
     * @param attackDamage урон атаки
     * @param attackSpeed скорость атаки
     * @param properties свойства предмета
     * @return объект регистрации топора
     */
    public static RegistryObject<AxeItem> createAxe(
        String modId,
        String itemId,
        Tier tier,
        float attackDamage,
        float attackSpeed,
        Item.Properties properties
    ) {
        LOGGER.info("Создание и регистрация топора: {}.{}", modId, itemId);
        return ItemRegistry.registerItem(
            modId,
            itemId,
            () -> new AxeItem(tier, attackDamage, attackSpeed, properties)
        );
    }

    /**
     * Создает и регистрирует лопату.
     *
     * @param modId ID мода
     * @param itemId ID предмета
     * @param tier уровень материала
     * @param attackDamage урон атаки
     * @param attackSpeed скорость атаки
     * @param properties свойства предмета
     * @return объект регистрации лопаты
     */
    public static RegistryObject<ShovelItem> createShovel(
        String modId,
        String itemId,
        Tier tier,
        float attackDamage,
        float attackSpeed,
        Item.Properties properties
    ) {
        LOGGER.info("Создание и регистрация лопаты: {}.{}", modId, itemId);
        return ItemRegistry.registerItem(
            modId,
            itemId,
            () -> new ShovelItem(tier, attackDamage, attackSpeed, properties)
        );
    }

    /**
     * Создает и регистрирует мотыгу.
     *
     * @param modId ID мода
     * @param itemId ID предмета
     * @param tier уровень материала
     * @param attackDamage урон атаки
     * @param attackSpeed скорость атаки
     * @param properties свойства предмета
     * @return объект регистрации мотыги
     */
    public static RegistryObject<HoeItem> createHoe(
        String modId,
        String itemId,
        Tier tier,
        int attackDamage,
        float attackSpeed,
        Item.Properties properties
    ) {
        LOGGER.info("Создание и регистрация мотыги: {}.{}", modId, itemId);
        return ItemRegistry.registerItem(
            modId,
            itemId,
            () -> new HoeItem(tier, attackDamage, attackSpeed, properties)
        );
    }

    /**
     * Создает и регистрирует меч.
     *
     * @param modId ID мода
     * @param itemId ID предмета
     * @param tier уровень материала
     * @param attackDamage урон атаки
     * @param attackSpeed скорость атаки
     * @param properties свойства предмета
     * @return объект регистрации меча
     */
    public static RegistryObject<SwordItem> createSword(
        String modId,
        String itemId,
        Tier tier,
        int attackDamage,
        float attackSpeed,
        Item.Properties properties
    ) {
        LOGGER.info("Создание и регистрация меча: {}.{}", modId, itemId);
        return ItemRegistry.registerItem(
            modId,
            itemId,
            () -> new SwordItem(tier, attackDamage, attackSpeed, properties)
        );
    }

    /**
     * Создает и регистрирует броню.
     *
     * @param modId ID мода
     * @param itemId ID предмета
     * @param material материал брони
     * @param slot слот брони
     * @param properties свойства предмета
     * @return объект регистрации брони
     */
    public static RegistryObject<ArmorItem> createArmor(
        String modId,
        String itemId,
        ArmorMaterial material,
        ArmorItem.Type slot,
        Item.Properties properties
    ) {
        LOGGER.info("Создание и регистрация брони для слота {}: {}.{}", slot, modId, itemId);
        return ItemRegistry.registerItem(
            modId,
            itemId,
            () -> new ArmorItem(material, slot, properties)
        );
    }
}
