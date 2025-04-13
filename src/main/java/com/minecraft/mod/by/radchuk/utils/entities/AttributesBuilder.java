package com.minecraft.mod.by.radchuk.utils.entities;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;

import java.util.function.Supplier;

/**
 * Билдер для создания атрибутов мобов любого типа.
 * <p>
 * Этот класс предоставляет удобный интерфейс для настройки атрибутов мобов,
 * таких как здоровье, урон, скорость и т.д. Поддерживает цепочку вызовов методов
 * для более удобного использования.
 * <p>
 * Пример использования:
 * <pre>
 * AttributeSupplier.Builder attributes = AttributesBuilder.monster()
 *     .health(30.0D)
 *     .damage(5.0D)
 *     .speed(0.3D)
 *     .armor(2.0D)
 *     .build();
 * </pre>
 */
public class AttributesBuilder {
    // Базовый поставщик атрибутов
    private Supplier<AttributeSupplier.Builder> baseSupplier;

    // Значения атрибутов
    private Double health = null;
    private Double damage = null;
    private Double speed = null;
    private Double armor = null;
    private Double knockbackResistance = null;
    private Double attackSpeed = null;
    private Double followRange = null;
    private Double luck = null;

    /**
     * Создает билдер с базовыми атрибутами моба.
     */
    public AttributesBuilder() {
        this.baseSupplier = Mob::createMobAttributes;
    }

    /**
     * Создает билдер с указанным базовым поставщиком атрибутов.
     *
     * @param baseSupplier Поставщик базовых атрибутов
     */
    public AttributesBuilder(Supplier<AttributeSupplier.Builder> baseSupplier) {
        this.baseSupplier = baseSupplier;
    }

    /**
     * Создает билдер для моба (базовый класс для всех мобов).
     *
     * @return Новый билдер атрибутов для моба
     */
    public static AttributesBuilder mob() {
        return new AttributesBuilder(Mob::createMobAttributes);
    }

    /**
     * Создает билдер для монстра с базовыми атрибутами монстра.
     *
     * @return Новый билдер атрибутов для монстра
     */
    public static AttributesBuilder monster() {
        return new AttributesBuilder(Monster::createMonsterAttributes);
    }

    /**
     * Создает билдер для животного с базовыми атрибутами животного.
     *
     * @return Новый билдер атрибутов для животного
     */
    public static AttributesBuilder animal() {
        return new AttributesBuilder(Animal::createMobAttributes);
    }

    /**
     * Создает билдер для любого живого существа.
     *
     * @return Новый билдер атрибутов для живого существа
     */
    public static AttributesBuilder living() {
        return new AttributesBuilder(LivingEntity::createLivingAttributes);
    }

    /**
     * Создает билдер для жителя с базовыми атрибутами жителя.
     *
     * @return Новый билдер атрибутов для жителя
     */
    public static AttributesBuilder villager() {
        return new AttributesBuilder(Villager::createAttributes);
    }

    /**
     * Создает билдер для игрока с базовыми атрибутами игрока.
     *
     * @return Новый билдер атрибутов для игрока
     */
    public static AttributesBuilder player() {
        return new AttributesBuilder(Player::createAttributes);
    }

    /**
     * Устанавливает максимальное здоровье моба.
     *
     * @param health Значение максимального здоровья
     * @return Этот билдер для цепочки вызовов
     */
    public AttributesBuilder health(double health) {
        this.health = health;
        return this;
    }

    /**
     * Устанавливает урон атаки моба.
     *
     * @param damage Значение урона атаки
     * @return Этот билдер для цепочки вызовов
     */
    public AttributesBuilder damage(double damage) {
        this.damage = damage;
        return this;
    }

    /**
     * Устанавливает скорость передвижения моба.
     *
     * @param speed Значение скорости передвижения
     * @return Этот билдер для цепочки вызовов
     */
    public AttributesBuilder speed(double speed) {
        this.speed = speed;
        return this;
    }

    /**
     * Устанавливает броню моба.
     *
     * @param armor Значение брони
     * @return Этот билдер для цепочки вызовов
     */
    public AttributesBuilder armor(double armor) {
        this.armor = armor;
        return this;
    }

    /**
     * Устанавливает сопротивление отбрасыванию моба.
     *
     * @param knockbackResistance Значение сопротивления отбрасыванию (от 0.0 до 1.0)
     * @return Этот билдер для цепочки вызовов
     */
    public AttributesBuilder knockbackResistance(double knockbackResistance) {
        this.knockbackResistance = knockbackResistance;
        return this;
    }

    /**
     * Устанавливает скорость атаки моба.
     *
     * @param attackSpeed Значение скорости атаки
     * @return Этот билдер для цепочки вызовов
     */
    public AttributesBuilder attackSpeed(double attackSpeed) {
        this.attackSpeed = attackSpeed;
        return this;
    }

    /**
     * Устанавливает дальность следования моба за целью.
     *
     * @param followRange Значение дальности следования
     * @return Этот билдер для цепочки вызовов
     */
    public AttributesBuilder followRange(double followRange) {
        this.followRange = followRange;
        return this;
    }

    /**
     * Устанавливает удачу (для игроков и некоторых мобов).
     *
     * @param luck Значение удачи
     * @return Этот билдер для цепочки вызовов
     */
    public AttributesBuilder luck(double luck) {
        this.luck = luck;
        return this;
    }

    /**
     * Создает билдер атрибутов с применением всех установленных значений.
     *
     * @return Готовый билдер атрибутов для регистрации
     */
    public AttributeSupplier.Builder build() {
        AttributeSupplier.Builder builder = baseSupplier.get();

        // Применяем только те атрибуты, которые были установлены
        if (health != null) {
            builder.add(Attributes.MAX_HEALTH, health);
        }
        if (damage != null) {
            builder.add(Attributes.ATTACK_DAMAGE, damage);
        }
        if (speed != null) {
            builder.add(Attributes.MOVEMENT_SPEED, speed);
        }
        if (armor != null) {
            builder.add(Attributes.ARMOR, armor);
        }
        if (knockbackResistance != null) {
            builder.add(Attributes.KNOCKBACK_RESISTANCE, knockbackResistance);
        }
        if (attackSpeed != null) {
            builder.add(Attributes.ATTACK_SPEED, attackSpeed);
        }
        if (followRange != null) {
            builder.add(Attributes.FOLLOW_RANGE, followRange);
        }
        if (luck != null) {
            builder.add(Attributes.LUCK, luck);
        }

        return builder;
    }

    /**
     * Создает базовые атрибуты для любого моба.
     * Устанавливает здоровье 20.0 и скорость 0.25.
     *
     * @return Готовый билдер атрибутов с базовыми значениями
     */
    public static AttributeSupplier.Builder createDefaultAttributes() {
        return mob()
            .health(20.0D)
            .speed(0.25D)
            .build();
    }
}
