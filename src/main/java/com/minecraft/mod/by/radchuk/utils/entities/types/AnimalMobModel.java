package com.minecraft.mod.by.radchuk.utils.entities.types;

import net.minecraft.client.model.QuadrupedModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.Mob;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * Модель четвероногого животного для использования в системе мобов.
 * <p>
 * Эта модель предоставляет готовую структуру для четвероногих животных с:
 * - Детализированной головой с ушами
 * - Анатомически корректным телом
 * - Четырьмя ногами
 * <p>
 * Модель использует текстуру размером 64x64 пикселей со следующим расположением:
 * - Голова: (0, 0) - размер 8x8x6
 * - Уши: (22, 0) - размер 3x4x1 для каждого уха
 * - Тело: (0, 14) - размер 10x16x8
 * - Ноги: (0, 38) - размер 4x12x4 для каждой ноги
 * <p>
 * Пример использования с фабрикой мобов:
 * <pre>
 * RegistryObject<EntityType<MyAnimal>> MY_ANIMAL = mobFactory.createAnimalMob(
 *     "my_animal",
 *     MyAnimal::new,
 *     MobCategory.CREATURE
 * )
 * .size(0.9F, 1.4F)
 * .texture("textures/entity/my_animal.png")
 * .build();
 * </pre>
 *
 * @param <T> Тип моба, для которого используется эта модель
 */
@OnlyIn(Dist.CLIENT)
public class AnimalMobModel<T extends Mob> extends QuadrupedModel<T> {

    /**
     * Создает новую модель животного с указанной корневой частью.
     *
     * @param root Корневая часть модели, содержащая все подчасти
     */
    public AnimalMobModel(ModelPart root) {
        super(root, false, 8.0F, 4.0F, 2.0F, 2.0F, 24);
    }

    /**
     * Создает определение слоя модели без деформации.
     *
     * @return Определение слоя модели
     */
    public static LayerDefinition createBodyLayer() {
        return createBodyLayer(CubeDeformation.NONE);
    }

    /**
     * Создает определение слоя модели с указанной деформацией.
     * Полезно для создания вариантов модели (например, детенышей).
     *
     * @param cubeDeformation Деформация, применяемая к кубам модели
     * @return Определение слоя модели
     */
    public static LayerDefinition createBodyLayer(CubeDeformation cubeDeformation) {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        // Голова
        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create()
                .texOffs(0, 0)
                .addBox(-4.0F, -4.0F, -6.0F, 8.0F, 8.0F, 6.0F, cubeDeformation),
            PartPose.offset(0.0F, 6.0F, -8.0F));

        // Уши
        head.addOrReplaceChild("right_ear", CubeListBuilder.create()
                .texOffs(22, 0)
                .addBox(-3.0F, -6.0F, -1.0F, 3.0F, 4.0F, 1.0F, cubeDeformation),
            PartPose.offset(0.0F, 0.0F, 0.0F));

        head.addOrReplaceChild("left_ear", CubeListBuilder.create()
                .texOffs(22, 0).mirror()
                .addBox(0.0F, -6.0F, -1.0F, 3.0F, 4.0F, 1.0F, cubeDeformation),
            PartPose.offset(0.0F, 0.0F, 0.0F));

        // Тело
        partdefinition.addOrReplaceChild("body", CubeListBuilder.create()
                .texOffs(0, 14)
                .addBox(-5.0F, -10.0F, -7.0F, 10.0F, 16.0F, 8.0F, cubeDeformation),
            PartPose.offsetAndRotation(0.0F, 11.0F, 2.0F, ((float)Math.PI / 2F), 0.0F, 0.0F));

        // Ноги
        partdefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create()
                .texOffs(0, 38)
                .addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, cubeDeformation),
            PartPose.offset(-3.0F, 12.0F, 7.0F));

        partdefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create()
                .texOffs(0, 38).mirror()
                .addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, cubeDeformation),
            PartPose.offset(3.0F, 12.0F, 7.0F));

        partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create()
                .texOffs(0, 38)
                .addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, cubeDeformation),
            PartPose.offset(-3.0F, 12.0F, -5.0F));

        partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create()
                .texOffs(0, 38).mirror()
                .addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, cubeDeformation),
            PartPose.offset(3.0F, 12.0F, -5.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }
}
