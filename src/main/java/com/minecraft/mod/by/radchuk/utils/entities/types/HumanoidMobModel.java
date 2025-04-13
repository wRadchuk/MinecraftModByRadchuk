package com.minecraft.mod.by.radchuk.utils.entities.types;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.Mob;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * Модель гуманоидного моба для использования в системе мобов.
 * <p>
 * Эта модель предоставляет готовую структуру для гуманоидных существ с:
 * - Головой и слоем шляпы
 * - Телом
 * - Двумя руками
 * - Двумя ногами
 * <p>
 * Модель использует текстуру размером 64x64 пикселей со следующим расположением:
 * - Голова: (0, 0) - размер 8x8x8
 * - Шляпа: (32, 0) - размер 8x8x8 (с увеличением на 0.5)
 * - Тело: (16, 16) - размер 8x12x4
 * - Руки: (40, 16) - размер 4x12x4 для каждой руки
 * - Ноги: (0, 16) - размер 4x12x4 для каждой ноги
 * <p>
 * Пример использования с фабрикой мобов:
 * <pre>
 * RegistryObject<EntityType<MyHumanoid>> MY_HUMANOID = mobFactory.createHumanoidMob(
 *     "my_humanoid",
 *     MyHumanoid::new,
 *     MobCategory.MONSTER
 * )
 * .size(0.6F, 1.95F)
 * .texture("textures/entity/my_humanoid.png")
 * .build();
 * </pre>
 *
 * @param <T> Тип моба, для которого используется эта модель
 */
@OnlyIn(Dist.CLIENT)
public class HumanoidMobModel<T extends Mob> extends HumanoidModel<T> {

    /**
     * Создает новую гуманоидную модель с указанной корневой частью.
     *
     * @param root Корневая часть модели, содержащая все подчасти
     */
    public HumanoidMobModel(ModelPart root) {
        super(root);
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
     * Полезно для создания вариантов модели или для поддержки брони.
     *
     * @param cubeDeformation Деформация, применяемая к кубам модели
     * @return Определение слоя модели
     */
    public static LayerDefinition createBodyLayer(CubeDeformation cubeDeformation) {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        // Голова
        partdefinition.addOrReplaceChild("head", CubeListBuilder.create()
                .texOffs(0, 0)
                .addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, cubeDeformation),
            PartPose.offset(0.0F, 0.0F, 0.0F));

        // Шляпа (внешний слой головы)
        partdefinition.addOrReplaceChild("hat", CubeListBuilder.create()
                .texOffs(32, 0)
                .addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, cubeDeformation.extend(0.5F)),
            PartPose.offset(0.0F, 0.0F, 0.0F));

        // Тело
        partdefinition.addOrReplaceChild("body", CubeListBuilder.create()
                .texOffs(16, 16)
                .addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, cubeDeformation),
            PartPose.offset(0.0F, 0.0F, 0.0F));

        // Правая рука
        partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create()
                .texOffs(40, 16)
                .addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, cubeDeformation),
            PartPose.offset(-5.0F, 2.0F, 0.0F));

        // Левая рука
        partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create()
                .texOffs(40, 16).mirror()
                .addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, cubeDeformation),
            PartPose.offset(5.0F, 2.0F, 0.0F));

        // Правая нога
        partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create()
                .texOffs(0, 16)
                .addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, cubeDeformation),
            PartPose.offset(-1.9F, 12.0F, 0.0F));

        // Левая нога
        partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create()
                .texOffs(0, 16).mirror()
                .addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, cubeDeformation),
            PartPose.offset(1.9F, 12.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }
}
