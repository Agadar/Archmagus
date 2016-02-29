package com.agadar.archmagus.render;

import java.util.Map;

import com.agadar.archmagus.entity.EntityRisenHorse;
import com.google.common.collect.Maps;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelHorse;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.LayeredTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderRisenHorse extends RenderLiving<EntityRisenHorse>
{
    private static final Map<String, ResourceLocation> field_110852_a = Maps.<String, ResourceLocation>newHashMap();
    private static final ResourceLocation whiteHorseTextures = new ResourceLocation("textures/entity/horse/horse_white.png");
    private static final ResourceLocation muleTextures = new ResourceLocation("textures/entity/horse/mule.png");
    private static final ResourceLocation donkeyTextures = new ResourceLocation("textures/entity/horse/donkey.png");
    private static final ResourceLocation zombieHorseTextures = new ResourceLocation("textures/entity/horse/horse_zombie.png");
    private static final ResourceLocation skeletonHorseTextures = new ResourceLocation("textures/entity/horse/horse_skeleton.png");

    public RenderRisenHorse(RenderManager rendermanagerIn, ModelHorse model, float shadowSizeIn)
    {
        super(rendermanagerIn, model, shadowSizeIn);
    }

    /**
     * Allows the render to do any OpenGL state modifications necessary before the model is rendered. Args:
     * entityLiving, partialTickTime
     */
    protected void preRenderCallback(EntityRisenHorse entitylivingbaseIn, float partialTickTime)
    {
        float f = 1.0F;
        int i = entitylivingbaseIn.getHorseType();

        if (i == 1)
        {
            f *= 0.87F;
        }
        else if (i == 2)
        {
            f *= 0.92F;
        }

        GlStateManager.scale(f, f, f);
        super.preRenderCallback(entitylivingbaseIn, partialTickTime);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityRisenHorse entity)
    {
        if (!entity.func_110239_cn())
        {
            switch (entity.getHorseType())
            {
                case 0:
                default:
                    return whiteHorseTextures;
                case 1:
                    return donkeyTextures;
                case 2:
                    return muleTextures;
                case 3:
                    return zombieHorseTextures;
                case 4:
                    return skeletonHorseTextures;
            }
        }
        else
        {
            return this.func_110848_b(entity);
        }
    }

    private ResourceLocation func_110848_b(EntityRisenHorse horse)
    {
        String s = horse.getHorseTexture();

        if (!horse.func_175507_cI())
        {
            return null;
        }
        else
        {
            ResourceLocation resourcelocation = (ResourceLocation)field_110852_a.get(s);

            if (resourcelocation == null)
            {
                resourcelocation = new ResourceLocation(s);
                Minecraft.getMinecraft().getTextureManager().loadTexture(resourcelocation, new LayeredTexture(horse.getVariantTexturePaths()));
                field_110852_a.put(s, resourcelocation);
            }

            return resourcelocation;
        }
    }
}
