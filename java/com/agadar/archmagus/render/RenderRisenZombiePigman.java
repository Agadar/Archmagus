package com.agadar.archmagus.render;

import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderRisenZombiePigman extends RenderBiped
{
	private static final ResourceLocation zombiePigmanTextures = new ResourceLocation("textures/entity/zombie_pigman.png");

    public RenderRisenZombiePigman()
    {
        super(new ModelZombie(), 0.5F, 1.0F);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity par1Entity)
    {
    	return zombiePigmanTextures;
    }
}
