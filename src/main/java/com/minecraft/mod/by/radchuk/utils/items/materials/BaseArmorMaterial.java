package com.minecraft.mod.by.radchuk.utils.items.materials;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

/**
 * Базовый класс для определения материала брони.
 * Предоставляет гибкий и удобный способ создания материалов брони
 * с использованием паттерна Builder.
 * <p>
 * Этот класс можно использовать в любом моде без изменений,
 * просто указав ID вашего мода при создании материала.
 */
public class BaseArmorMaterial implements ArmorMaterial {
    private final String modId;
    private final String name;
    private final int durabilityMultiplier;
    private final int[] protectionAmounts;
    private final int enchantability;
    private final SoundEvent equipSound;
    private final Supplier<Ingredient> repairIngredient;
    private final float toughness;
    private final float knockbackResistance;
    private final int fireResistance;

    /**
     * Приватный конструктор для создания материала брони.
     * Используется Builder для создания экземпляров.
     */
    private BaseArmorMaterial(
        String modId,
        String name,
        int durabilityMultiplier,
        int[] protectionAmounts,
        int enchantability,
        SoundEvent equipSound,
        Supplier<Ingredient> repairIngredient,
        float toughness,
        float knockbackResistance,
        int fireResistance
    ) {
        this.modId = modId;
        this.name = name;
        this.durabilityMultiplier = durabilityMultiplier;
        this.protectionAmounts = protectionAmounts;
        this.enchantability = enchantability;
        this.equipSound = equipSound;
        this.repairIngredient = repairIngredient;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.fireResistance = fireResistance;
    }

    @Override
    public int getDurabilityForType(ArmorItem.Type type) {
        return new int[]{13, 15, 16, 11}[type.getSlot().getIndex()] * this.durabilityMultiplier;
    }

    @Override
    public int getDefenseForType(ArmorItem.Type type) {
        return this.protectionAmounts[type.getSlot().getIndex()];
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantability;
    }

    @Override
    public @NotNull SoundEvent getEquipSound() {
        return this.equipSound;
    }

    @Override
    public @NotNull Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }

    @Override
    public @NotNull String getName() {
        return this.modId + ":" + this.name;
    }

    @Override
    public float getToughness() {
        return this.toughness;
    }

    @Override
    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }

    /**
     * Получить огнестойкость материала брони.
     * Этот метод не является частью интерфейса ArmorMaterial,
     * но может быть полезен для модов, которые добавляют огнестойкость.
     *
     * @return значение огнестойкости
     */
    public int getFireResistance() {
        return this.fireResistance;
    }

    /**
     * Builder для создания материала брони.
     */
    public static class Builder {
        private final String modId;
        private final String name;
        private int durabilityMultiplier = 15;
        private int[] protectionAmounts = new int[]{2, 5, 6, 2};
        private int enchantability = 9;
        private SoundEvent equipSound = SoundEvents.ARMOR_EQUIP_IRON;
        private Supplier<Ingredient> repairIngredient = () -> Ingredient.EMPTY;
        private float toughness = 0.0f;
        private float knockbackResistance = 0.0f;
        private int fireResistance = 0;

        /**
         * Создает новый Builder для материала брони.
         *
         * @param modId ID мода
         * @param name  имя материала
         */
        public Builder(String modId, String name) {
            this.modId = modId;
            this.name = name;
        }

        /**
         * Устанавливает множитель прочности брони.
         *
         * @param durabilityMultiplier множитель прочности
         * @return этот Builder
         */
        public Builder durabilityMultiplier(int durabilityMultiplier) {
            this.durabilityMultiplier = durabilityMultiplier;
            return this;
        }

        /**
         * Устанавливает защиту для каждого слота брони.
         *
         * @param helmet     защита шлема
         * @param chestplate защита нагрудника
         * @param leggings   защита поножей
         * @param boots      защита ботинок
         * @return этот Builder
         */
        public Builder protection(int helmet, int chestplate, int leggings, int boots) {
            this.protectionAmounts = new int[]{helmet, chestplate, leggings, boots};
            return this;
        }

        /**
         * Устанавливает уровень зачарования брони.
         *
         * @param enchantability уровень зачарования
         * @return этот Builder
         */
        public Builder enchantability(int enchantability) {
            this.enchantability = enchantability;
            return this;
        }

        /**
         * Устанавливает звук экипировки брони.
         *
         * @param equipSound звук экипировки
         * @return этот Builder
         */
        public Builder equipSound(SoundEvent equipSound) {
            this.equipSound = equipSound;
            return this;
        }

        /**
         * Устанавливает ингредиент для ремонта брони.
         *
         * @param repairIngredient ингредиент для ремонта
         * @return этот Builder
         */
        public Builder repairIngredient(Supplier<Ingredient> repairIngredient) {
            this.repairIngredient = repairIngredient;
            return this;
        }

        /**
         * Устанавливает ингредиент для ремонта брони из предмета.
         *
         * @param item предмет для ремонта
         * @return этот Builder
         */
        public Builder repairItem(Item item) {
            this.repairIngredient = () -> Ingredient.of(item);
            return this;
        }

        /**
         * Устанавливает прочность брони.
         *
         * @param toughness прочность
         * @return этот Builder
         */
        public Builder toughness(float toughness) {
            this.toughness = toughness;
            return this;
        }

        /**
         * Устанавливает сопротивление отбрасыванию.
         *
         * @param knockbackResistance сопротивление отбрасыванию
         * @return этот Builder
         */
        public Builder knockbackResistance(float knockbackResistance) {
            this.knockbackResistance = knockbackResistance;
            return this;
        }

        /**
         * Устанавливает огнестойкость брони.
         *
         * @param fireResistance огнестойкость
         * @return этот Builder
         */
        public Builder fireResistance(int fireResistance) {
            this.fireResistance = fireResistance;
            return this;
        }

        /**
         * Создает материал брони с указанными параметрами.
         *
         * @return новый материал брони
         */
        public BaseArmorMaterial build() {
            return new BaseArmorMaterial(
                modId,
                name,
                durabilityMultiplier,
                protectionAmounts,
                enchantability,
                equipSound,
                repairIngredient,
                toughness,
                knockbackResistance,
                fireResistance
            );
        }
    }

    /**
     * Фабричные методы для создания стандартных типов брони
     */
    public static class Factory {
        /**
         * Создает материал брони, похожий на кожаную, но с измененными параметрами.
         *
         * @param modId ID мода
         * @param name  имя материала
         * @return Builder для дальнейшей настройки
         */
        public static Builder leatherLike(String modId, String name) {
            return new Builder(modId, name)
                .durabilityMultiplier(5)
                .protection(1, 2, 2, 1)
                .enchantability(15)
                .equipSound(SoundEvents.ARMOR_EQUIP_LEATHER)
                .repairItem(Items.LEATHER)
                .toughness(0.0f)
                .knockbackResistance(0.0f);
        }

        /**
         * Создает материал брони, похожий на железную, но с измененными параметрами.
         *
         * @param modId ID мода
         * @param name  имя материала
         * @return Builder для дальнейшей настройки
         */
        public static Builder ironLike(String modId, String name) {
            return new Builder(modId, name)
                .durabilityMultiplier(15)
                .protection(2, 5, 6, 2)
                .enchantability(9)
                .equipSound(SoundEvents.ARMOR_EQUIP_IRON)
                .repairItem(Items.IRON_INGOT)
                .toughness(0.0f)
                .knockbackResistance(0.0f);
        }

        /**
         * Создает материал брони, похожий на золотую, но с измененными параметрами.
         *
         * @param modId ID мода
         * @param name  имя материала
         * @return Builder для дальнейшей настройки
         */
        public static Builder goldLike(String modId, String name) {
            return new Builder(modId, name)
                .durabilityMultiplier(7)
                .protection(1, 3, 5, 2)
                .enchantability(25)
                .equipSound(SoundEvents.ARMOR_EQUIP_GOLD)
                .repairItem(Items.GOLD_INGOT)
                .toughness(0.0f)
                .knockbackResistance(0.0f);
        }

        /**
         * Создает материал брони, похожий на алмазную, но с измененными параметрами.
         *
         * @param modId ID мода
         * @param name  имя материала
         * @return Builder для дальнейшей настройки
         */
        public static Builder diamondLike(String modId, String name) {
            return new Builder(modId, name)
                .durabilityMultiplier(33)
                .protection(3, 6, 8, 3)
                .enchantability(10)
                .equipSound(SoundEvents.ARMOR_EQUIP_DIAMOND)
                .repairItem(Items.DIAMOND)
                .toughness(2.0f)
                .knockbackResistance(0.0f);
        }

        /**
         * Создает материал брони, похожий на незеритовую, но с измененными параметрами.
         *
         * @param modId ID мода
         * @param name  имя материала
         * @return Builder для дальнейшей настройки
         */
        public static Builder netheriteLike(String modId, String name) {
            return new Builder(modId, name)
                .durabilityMultiplier(37)
                .protection(3, 6, 8, 3)
                .enchantability(15)
                .equipSound(SoundEvents.ARMOR_EQUIP_NETHERITE)
                .repairItem(Items.NETHERITE_INGOT)
                .toughness(3.0f)
                .knockbackResistance(0.1f)
                .fireResistance(1);
        }
    }
}
