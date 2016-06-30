package com.github.agadar.archmagus.spell.aoe;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.world.World;

/** Lightning strikes all non-allied creatures in the area. */
public class SpellLightningStorm extends SpellAoE 
{
	@Override
	public int getManaCost()
    {
    	return 7;
    }
	
	@Override
	public String getName()
    {
        return "spell.lightningstorm";
    }
	
	@Override
	public String getModelResourceLocationString()
    {
    	return super.getModelResourceLocationString() + "lightning_book";
    }

	@Override
	protected void affectEntity(World par1World, EntityLivingBase par2EntityLivingBase) 
	{
		par1World.addWeatherEffect(new EntityLightningBolt(par1World, par2EntityLivingBase.posX, par2EntityLivingBase.posY, par2EntityLivingBase.posZ));
		par2EntityLivingBase.knockBack(par2EntityLivingBase, 0F, 1F, 0F);
	}
}
