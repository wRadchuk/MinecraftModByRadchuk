package com.minecraft.mod.by.radchuk.utils.entities;

import com.minecraft.mod.by.radchuk.utils.entities.types.AnimalMobModel;
import com.minecraft.mod.by.radchuk.utils.entities.types.HumanoidMobModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Фабрика для создания и регистрации мобов.
 * Позволяет разработчикам легко создавать новых мобов без необходимости
 * вручную регистрировать все компоненты.
 * <p>
 * Поддерживает создание стандартных типов мобов (гуманоиды, животные)
 * и уникальных мобов с пользовательскими моделями.
 * <p>
 * Пример использования:
 * <pre>
 * // Получение экземпляра фабрики
 * MobFactory factory = MobFactory.getInstance("mymod");
 *
 * // Регистрация фабрики
 * factory.register(modEventBus);
 *
 * // Создание гуманоидного моба
 * RegistryObject<EntityType<MyHumanoid>> MY_HUMANOID = factory.createHumanoidMob(
 *     "my_humanoid",
 *     MyHumanoid::new,
 *     MobCategory.MONSTER
 * )
 * .size(0.6F, 1.95F)
 * .texture("textures/entity/my_humanoid.png")
 * .build();
 *
 * // Создание уникального моба с пользовательской моделью
 * RegistryObject<EntityType<MyUniqueMob>> MY_UNIQUE_MOB = factory.createUniqueMob(
 *     "my_unique_mob",
 *     MyUniqueMob::new,
 *     MobCategory.MONSTER
 * )
 * .size(0.8F, 2.0F)
 * .texture("textures/entity/my_unique_mob.png")
 * .modelSupplier(context -> new MyUniqueModel<>(context.bakeLayer(modelLayer)))
 * .layerDefinition(MyUniqueModel::createBodyLayer)
 * .build();
 * </pre>
 */
public class MobFactory {
    private static final Map<String, MobFactory> INSTANCES = new HashMap<>();
    private final String modId;
    private final DeferredRegister<EntityType<?>> entityTypeRegistry;
    // Списки для хранения данных о мобах
    private final List<MobRegistration<?>> mobRegistrations = new ArrayList<>();

    /**
     * Получить экземпляр фабрики для указанного мода
     *
     * @param modId Идентификатор мода
     * @return Экземпляр фабрики для указанного мода
     */
    public static MobFactory getInstance(String modId) {
        return INSTANCES.computeIfAbsent(modId, MobFactory::new);
    }

    /**
     * Приватный конструктор для создания экземпляра фабрики мобов.
     * Используется паттерн Singleton для обеспечения единственного экземпляра
     * фабрики для каждого мода. Для получения экземпляра следует использовать
     * метод {@link #getInstance(String)}.
     *
     * @param modId Идентификатор мода, для которого создается фабрика
     */
    private MobFactory(String modId) {
        this.modId = modId;
        this.entityTypeRegistry = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, modId);
    }

    /**
     * Регистрирует фабрику в системе событий Forge
     *
     * @param modEventBus Шина событий мода
     */
    public void register(IEventBus modEventBus) {
        // Регистрируем типы сущностей
        entityTypeRegistry.register(modEventBus);
        // Регистрируем обработчики событий
        modEventBus.addListener(this::onRegisterAttributes);
        modEventBus.addListener(this::onRegisterRenderers);
        modEventBus.addListener(this::onRegisterLayerDefinitions);
    }

    /**
     * Создает гуманоидного моба
     *
     * @param name Имя моба
     * @param entityConstructor Конструктор сущности
     * @param category Категория моба
     * @return Строитель для настройки моба
     */
    public <T extends Mob> MobBuilder<T> createHumanoidMob(
        String name,
        BiFunction<EntityType<T>, Level, T> entityConstructor,
        MobCategory category) {
        return new MobBuilder<>(this, name, entityConstructor, category, MobType.HUMANOID);
    }

    /**
     * Создает гуманоидного монстра
     *
     * @param name Имя монстра
     * @param entityConstructor Конструктор сущности
     * @param category Категория моба
     * @return Строитель для настройки монстра
     */
    public <T extends Monster> MobBuilder<T> createHumanoidMonster(
        String name,
        BiFunction<EntityType<T>, Level, T> entityConstructor,
        MobCategory category) {
        return new MobBuilder<>(this, name, entityConstructor, category, MobType.HUMANOID);
    }

    /**
     * Создает животное
     *
     * @param name Имя животного
     * @param entityConstructor Конструктор сущности
     * @param category Категория моба
     * @return Строитель для настройки животного
     */
    public <T extends Mob> MobBuilder<T> createAnimalMob(
        String name,
        BiFunction<EntityType<T>, Level, T> entityConstructor,
        MobCategory category) {
        return new MobBuilder<>(this, name, entityConstructor, category, MobType.ANIMAL);
    }

    /**
     * Создает уникального моба с нестандартной моделью
     *
     * @param name Имя моба
     * @param entityConstructor Конструктор сущности
     * @param category Категория моба
     * @return Строитель для настройки уникального моба
     */
    public <T extends Mob> MobBuilder<T> createUniqueMob(
        String name,
        BiFunction<EntityType<T>, Level, T> entityConstructor,
        MobCategory category) {
        return new MobBuilder<>(this, name, entityConstructor, category, MobType.UNIQUE);
    }

    /**
     * Регистрирует атрибуты для всех мобов
     */
    private void onRegisterAttributes(EntityAttributeCreationEvent event) {
        for (MobRegistration<?> registration : mobRegistrations) {
            registration.registerAttributes(event);
        }
    }

    /**
     * Регистрирует рендереры для всех мобов
     */
    private void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        for (MobRegistration<?> registration : mobRegistrations) {
            registration.registerRenderer(event);
        }
    }

    /**
     * Регистрирует определения слоев для всех мобов
     */
    private void onRegisterLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        for (MobRegistration<?> registration : mobRegistrations) {
            registration.registerLayerDefinition(event);
        }
    }

    /**
     * Внутренний класс для хранения данных о регистрации моба
     */
    private static class MobRegistration<T extends Mob> {
        private final RegistryObject<EntityType<T>> entityType;
        private final ModelLayerLocation modelLayer;
        private final ResourceLocation texture;
        private final Supplier<AttributeSupplier.Builder> attributesSupplier;
        private final Supplier<LayerDefinition> layerDefinitionSupplier;
        private final Function<EntityRendererProvider.Context, EntityModel<T>> modelSupplier;
        private final float shadowRadius;

        MobRegistration(
            RegistryObject<EntityType<T>> entityType,
            MobType mobType,
            ModelLayerLocation modelLayer,
            ResourceLocation texture,
            Supplier<AttributeSupplier.Builder> attributesSupplier,
            Supplier<LayerDefinition> layerDefinitionSupplier,
            Function<EntityRendererProvider.Context, EntityModel<T>> modelSupplier,
            float shadowRadius) {
            this.entityType = entityType;
            this.modelLayer = modelLayer;
            this.texture = texture;
            this.attributesSupplier = attributesSupplier;
            this.layerDefinitionSupplier = layerDefinitionSupplier;
            this.modelSupplier = modelSupplier;
            this.shadowRadius = shadowRadius;
        }

        void registerAttributes(EntityAttributeCreationEvent event) {
            event.put(entityType.get(), attributesSupplier.get().build());
        }

        void registerRenderer(EntityRenderersEvent.RegisterRenderers event) {
            event.registerEntityRenderer(entityType.get(),
                context -> new SimpleMobRenderer<>(context, modelSupplier.apply(context), shadowRadius, texture));
        }

        void registerLayerDefinition(EntityRenderersEvent.RegisterLayerDefinitions event) {
            event.registerLayerDefinition(modelLayer, layerDefinitionSupplier);
        }
    }

    /**
     * Типы мобов, поддерживаемые фабрикой
     */
    public enum MobType {
        HUMANOID,
        ANIMAL,
        UNIQUE
    }

    /**
     * Строитель для создания и настройки моба
     */
    public class MobBuilder<T extends Mob> {
        private final String name;
        private final BiFunction<EntityType<T>, Level, T> entityConstructor;
        private final MobCategory category;
        private final MobType mobType;
        private float width = 0.6F;
        private float height = 1.95F;
        private float shadowRadius = 0.5F;
        private ResourceLocation texture;
        private Supplier<AttributeSupplier.Builder> attributesSupplier;
        private Supplier<LayerDefinition> layerDefinitionSupplier;
        private Function<EntityRendererProvider.Context, EntityModel<T>> modelSupplier;

        MobBuilder(MobFactory factory, String name, BiFunction<EntityType<T>, Level, T> entityConstructor,
                   MobCategory category, MobType mobType) {
            this.name = name;
            this.entityConstructor = entityConstructor;
            this.category = category;
            this.mobType = mobType;

            // Устанавливаем значения по умолчанию только для текстуры и слоя модели
            this.texture = new ResourceLocation(modId, "textures/entity/" + name + ".png");

            // Устанавливаем слой модели и поставщик модели в зависимости от типа моба
            switch (mobType) {
                case HUMANOID:
                    this.layerDefinitionSupplier = HumanoidMobModel::createBodyLayer;
                    this.modelSupplier = context -> new HumanoidMobModel<>(context.bakeLayer(
                        new ModelLayerLocation(new ResourceLocation(modId, name), "main")));
                    this.shadowRadius = 0.5F;
                    break;
                case ANIMAL:
                    this.layerDefinitionSupplier = AnimalMobModel::createBodyLayer;
                    this.modelSupplier = context -> new AnimalMobModel<>(context.bakeLayer(
                        new ModelLayerLocation(new ResourceLocation(modId, name), "main")));
                    this.shadowRadius = 0.3F;
                    break;
                case UNIQUE:
                    // Для уникальных мобов нужно будет установить поставщика модели вручную
                    this.layerDefinitionSupplier = null;
                    this.modelSupplier = null;
                    this.shadowRadius = 0.5F;
                    break;
            }

            // Устанавливаем атрибуты по умолчанию в зависимости от типа моба
            switch (mobType) {
                case ANIMAL:
                    this.attributesSupplier = () -> AttributesBuilder.animal()
                        .health(10.0D)
                        .speed(0.25D)
                        .build();
                    break;
                case UNIQUE:
                    this.attributesSupplier = () -> AttributesBuilder.mob()
                        .health(30.0D)
                        .damage(5.0D)
                        .speed(0.3D)
                        .armor(2.0D)
                        .knockbackResistance(0.5D)
                        .build();
                    break;
                default:
                    this.attributesSupplier = AttributesBuilder::createDefaultAttributes;
            }
        }

        /**
         * Устанавливает размеры моба
         *
         * @param width Ширина моба
         * @param height Высота моба
         * @return Этот строитель для цепочки вызовов
         */
        public MobBuilder<T> size(float width, float height) {
            this.width = width;
            this.height = height;
            return this;
        }

        /**
         * Устанавливает текстуру моба
         *
         * @param texturePath Путь к текстуре относительно assets/modid/
         * @return Этот строитель для цепочки вызовов
         */
        public MobBuilder<T> texture(String texturePath) {
            this.texture = new ResourceLocation(modId, texturePath);
            return this;
        }

        /**
         * Устанавливает поставщика атрибутов моба
         *
         * @param attributesSupplier Поставщик атрибутов
         * @return Этот строитель для цепочки вызовов
         */
        public MobBuilder<T> attributes(Supplier<AttributeSupplier.Builder> attributesSupplier) {
            this.attributesSupplier = attributesSupplier;
            return this;
        }

        /**
         * Устанавливает поставщика определения слоя модели
         *
         * @param layerDefinitionSupplier Поставщик определения слоя
         * @return Этот строитель для цепочки вызовов
         */
        public MobBuilder<T> layerDefinition(Supplier<LayerDefinition> layerDefinitionSupplier) {
            this.layerDefinitionSupplier = layerDefinitionSupplier;
            return this;
        }

        /**
         * Устанавливает поставщика модели для моба.
         * Особенно полезно для уникальных мобов с пользовательскими моделями.
         *
         * @param modelSupplier Функция, создающая модель из контекста рендерера
         * @return Этот строитель для цепочки вызовов
         */
        public MobBuilder<T> modelSupplier(Function<EntityRendererProvider.Context, EntityModel<T>> modelSupplier) {
            this.modelSupplier = modelSupplier;
            return this;
        }

        /**
         * Устанавливает радиус тени моба
         *
         * @param shadowRadius Радиус тени
         * @return Этот строитель для цепочки вызовов
         */
        public MobBuilder<T> shadowRadius(float shadowRadius) {
            this.shadowRadius = shadowRadius;
            return this;
        }

        /**
         * Завершает создание моба и регистрирует его
         *
         * @return Объект регистрации типа сущности
         * @throws IllegalStateException если для уникального моба не установлен поставщик модели или слоя
         */
        public RegistryObject<EntityType<T>> build() {
            // Проверяем, что для уникального моба установлены все необходимые компоненты
            if (mobType == MobType.UNIQUE) {
                if (modelSupplier == null) {
                    throw new IllegalStateException("Для уникального моба " + name + " не установлен поставщик модели. " +
                        "Используйте метод modelSupplier() для установки поставщика модели.");
                }
                if (layerDefinitionSupplier == null) {
                    throw new IllegalStateException("Для уникального моба " + name + " не установлен поставщик определения слоя. " +
                        "Используйте метод layerDefinition() для установки поставщика определения слоя.");
                }
            }

            // Создаем тип сущности
            RegistryObject<EntityType<T>> entityType = entityTypeRegistry.register(
                name,
                () -> EntityType.Builder.<T>of(
                        entityConstructor::apply,
                        category
                    )
                    .sized(width, height)
                    .build(new ResourceLocation(modId, name).toString())
            );

            // Создаем слой модели
            ModelLayerLocation modelLayer = new ModelLayerLocation(
                new ResourceLocation(modId, name), "main");

            // Регистрируем моба
            mobRegistrations.add(new MobRegistration<>(
                entityType,
                mobType,
                modelLayer,
                texture,
                attributesSupplier,
                layerDefinitionSupplier,
                modelSupplier,
                shadowRadius
            ));

            return entityType;
        }
    }
}
