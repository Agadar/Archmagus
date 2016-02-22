package com.agadar.archmagus.render;

import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.lwjgl.opengl.GL11;

import com.agadar.archmagus.model.ModelRisenSkeleton;

@SideOnly(Side.CLIENT)
public class RenderRisenSkeleton extends RenderBiped
{
    private static final ResourceLocation skeletonTextures = new ResourceLocation("textures/entity/skeleton/skeleton.png");

    public RenderRisenSkeleton()
    {
        super(new ModelRisenSkeleton(), 0.5F);
    }

    @Override
    protected void func_82422_c()
    {
        GL11.glTranslatef(0.09375F, 0.1875F, 0.0F);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity par1EntitySkeleton)
    {
        return skeletonTextures;
    }
}