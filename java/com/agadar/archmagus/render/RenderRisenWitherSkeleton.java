package com.agadar.archmagus.render;

import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.lwjgl.opengl.GL11;

import com.agadar.archmagus.model.ModelRisenWitherSkeleton;

@SideOnly(Side.CLIENT)
public class RenderRisenWitherSkeleton extends RenderBiped
{
    private static final ResourceLocation witherSkeletonTextures = new ResourceLocation("textures/entity/skeleton/wither_skeleton.png");

    public RenderRisenWitherSkeleton()
    {
        super(new ModelRisenWitherSkeleton(), 0.5F);
    }

    @Override
    protected void func_82422_c()
    {
        GL11.glTranslatef(0.09375F, 0.1875F, 0.0F);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity par1EntitySkeleton)
    {
        return witherSkeletonTextures;
    }

    @Override
    protected void preRenderCallback(EntityLivingBase par1EntityLivingBase, float par2)
    {
    	GL11.glScalef(1.2F, 1.2F, 1.2F);
    }
}