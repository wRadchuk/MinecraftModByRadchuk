package com.minecraft.mod.by.radchuk.utils.entities;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Mob;
import org.jetbrains.annotations.NotNull;

/**
 * Простой рендерер для мобов с одной текстурой.
 * <p>
 * Этот класс предоставляет базовую реализацию рендерера для мобов,
 * которые используют только одну текстуру. Упрощает создание рендереров
 * для простых мобов, не требующих сложной логики отображения.
 * <p>
 * Пример использования:
 * <pre>
 * public class MyMobRenderer extends SimpleMobRenderer&lt;MyMob&gt; {
 *     public static final ResourceLocation TEXTURE =
 *         new ResourceLocation("mymod", "textures/entity/my_mob.png");
 *
 *     public MyMobRenderer(EntityRendererProvider.Context context) {
 *         super(context,
 *               new MyMobModel&lt;&gt;(context.bakeLayer(MyMobModel.LAYER_LOCATION)),
 *               0.5F,
 *               TEXTURE);
 *     }
 * }
 * </pre>
 *
 * @param <T> Тип моба, для которого предназначен рендерер
 */
public class SimpleMobRenderer<T extends Mob> extends MobRenderer<T, EntityModel<T>> {

    /**
     * Ресурс текстуры, используемой для рендеринга моба.
     */
    private final ResourceLocation texture;

    /**
     * Создает новый простой рендерер для моба.
     *
     * @param context Контекст рендерера сущности, предоставляемый Forge
     * @param model Модель моба, используемая для рендеринга
     * @param shadowRadius Радиус тени моба
     * @param texture Ресурс текстуры для моба
     */
    public SimpleMobRenderer(EntityRendererProvider.Context context, EntityModel<T> model,
                             float shadowRadius, ResourceLocation texture) {
        super(context, model, shadowRadius);
        this.texture = texture;
    }

    /**
     * Возвращает местоположение текстуры для данного моба.
     * В этой реализации всегда возвращается одна и та же текстура.
     *
     * @param entity Моб, для которого запрашивается текстура
     * @return Ресурс текстуры для моба
     */
    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull T entity) {
        return texture;
    }
}
