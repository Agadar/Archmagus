package com.agadar.archmagus.render;

import com.agadar.archmagus.Archmagus;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderHorse;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.util.ResourceLocation;

public class RenderRisenHorse extends RenderHorse 
{
	private static final ResourceLocation witherHorseTextures = new ResourceLocation(Archmagus.MODID, "/textures/entity/horse_wither.png");
	
	public RenderRisenHorse(ModelBase p_i1256_1_, float p_i1256_2_) 
	{
		super(p_i1256_1_, p_i1256_2_);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityHorse horse)
    {
		if (horse.getHorseType() != 5)
			return super.getEntityTexture(horse);
		else
			return witherHorseTextures;
    }
}
