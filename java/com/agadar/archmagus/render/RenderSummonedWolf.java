package com.agadar.archmagus.render;

import org.lwjgl.opengl.GL11;

import com.agadar.archmagus.entity.EntitySummonedWolf;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderSummonedWolf extends RenderLiving 
{
	private static final ResourceLocation wolfTextures = new ResourceLocation("textures/entity/wolf/wolf.png");
	
	public RenderSummonedWolf(ModelBase par1ModelBase, ModelBase par2ModelBase, float par3) 
	{
		super(par1ModelBase, par3);
		this.setRenderPassModel(par2ModelBase);
	}
	
	@Override
    protected int shouldRenderPass(EntityLivingBase par1EntityLivingBase, int par2, float par3)
    {
		EntitySummonedWolf wolf = (EntitySummonedWolf) par1EntityLivingBase;
		
		if (par2 == 0 && wolf.getWolfShaking())
        {
            float f1 = wolf.getBrightness(par3) * wolf.getShadingWhileShaking(par3);
            this.bindTexture(wolfTextures);
            GL11.glColor3f(f1, f1, f1);
            return 1;
        }
        else
        {
            return -1;
        }
    }
    
    @Override
    protected float handleRotationFloat(EntityLivingBase par1EntityLivingBase, float par2)
    {
    	return ((EntitySummonedWolf) par1EntityLivingBase).getTailRotation();
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity par1Entity)
    {
    	return wolfTextures;
    }
}
